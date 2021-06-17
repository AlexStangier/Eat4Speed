package de.eat4speed.services;

import de.eat4speed.dto.OrderDto;
import de.eat4speed.dto.PaymentDto;
import de.eat4speed.dto.StatisticDto;
import de.eat4speed.dto.StatisticDtoWrapper;
import de.eat4speed.entities.*;
import de.eat4speed.repositories.*;
import de.eat4speed.services.interfaces.IBestellungService;
import io.vertx.core.json.Json;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@ApplicationScoped
public class BestellungService implements IBestellungService {

    private BestellungRepository _bestellungRepository;
    private RechnungRepository _rechnungRepository;
    private AuftragRepository _auftragRepository;
    private StatusRepository _statusRepository;
    private BestellzuordnungRepository _bestellzuordnungRepository;
    private GerichtRepository _gerichtRepository;
    private BenutzerRepository _benutzerRepository;
    private AdressenRepository _adressenRepository;
    private KundeRepository _kundeRepository;
    private RestaurantRepository _restaurantRepository;

    @Inject
    public BestellungService(BestellungRepository bestellungRepository,
                             RechnungRepository rechnungRepository,
                             AuftragRepository auftragRepository,
                             StatusRepository statusRepository,
                             BestellzuordnungRepository bestellzuordnungRepository,
                             GerichtRepository gerichtRepository,
                             BenutzerRepository benutzerRepository,
                             AdressenRepository adressenRepository,
                             KundeRepository kundeRepository,
                             RestaurantRepository restaurantRepository
    ) {
        _bestellungRepository = bestellungRepository;
        _rechnungRepository = rechnungRepository;
        _auftragRepository = auftragRepository;
        _statusRepository = statusRepository;
        _bestellzuordnungRepository = bestellzuordnungRepository;
        _gerichtRepository = gerichtRepository;
        _benutzerRepository = benutzerRepository;
        _adressenRepository = adressenRepository;
        _kundeRepository = kundeRepository;
        _restaurantRepository = restaurantRepository;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    @Override
    public Response addBestellung(Bestellung bestellung) {
        _bestellungRepository.addBestellung(bestellung);

        return Response.status(Response.Status.CREATED).entity(bestellung).build();
    }

    /**
     * Creates and persists an order with all attached items
     * During creation the Auftrag will be attached to a dummy Fahrer with id 9999
     *
     * @param obj Dto containing a list of items and an customer identifier
     * @return HTTP Response
     */
    @Override
    @Transactional
    public Response createBestellung(OrderDto obj) throws SQLException {
        ArrayList<Gericht> safeItems = new ArrayList<>();
        ArrayList<Integer> gerichtIDs = new ArrayList<>();
        HashSet<Restaurant> restaurants = new HashSet<>();
        Benutzer benutzer = null;
        Kunde kunde = null;
        Date date = new Date();

        try {
            //get items by id¡
            for (int item : obj.items) {
                //make sure items are valid and not tempered
                Gericht gericht = _gerichtRepository.getGerichtByGerichtID(item);
                safeItems.add(gericht);
                gerichtIDs.add(item);
                restaurants.add(_restaurantRepository.findByRestaurantnummer(gericht.getRestaurant_ID()));
            }

            //get customer by id
            benutzer = _benutzerRepository.getBenutzerByID(obj.getCustomerId());
        } catch (Exception e) {
            System.out.println("Failed while creating order:" + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }

        kunde = _kundeRepository.getKundeByBenutzerID(benutzer.getBenutzer_ID());

        if (!safeItems.isEmpty() && benutzer != null) {
            //create new rechnung
            Rechnung rechnung = null;
            Adressen adresse = null;
            Auftrag auftrag = null;
            Bestellung bestellung = null;
            HashMap<Integer, List<Gericht>> restaurantOrderMapper = new HashMap<>();

            try {
                auftrag = new Auftrag(safeItems.get(0).getRestaurant_ID(), new Timestamp(date.getTime()), kunde.getAnschrift(), 0.0, kunde.getKundennummer(), "offen", 0);
            } catch (Exception e) {
                System.out.println("Failed while creating auftrag:" + e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
            }
            _auftragRepository.persist(auftrag);

            if (_auftragRepository.isPersistent(auftrag)) {
                try {
                    for (Restaurant rest : restaurants) {
                        List<Gericht> gerichteForRestaurant = new ArrayList<>();
                        for (Gericht ger : safeItems) {
                            if (ger.getRestaurant_ID() == rest.getRestaurant_ID())
                                gerichteForRestaurant.add(ger);
                        }
                        restaurantOrderMapper.put(rest.getRestaurant_ID(), gerichteForRestaurant);
                        try {
                            rechnung = new Rechnung((gerichteForRestaurant.stream().mapToDouble(Gericht::getPreis).sum() * 1.07 + 2.00), new Timestamp(date.getTime()), (byte) 0);
                        } catch (Exception e) {
                            System.out.println("Failed while creating rechnung:" + e);
                            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
                        }
                        _rechnungRepository.persist(rechnung);

                        bestellung = new Bestellung((int) auftrag.getAuftrags_ID(), new Timestamp(date.getTime()), rechnung.getRechnungs_ID(), "offen", rest.getRestaurant_ID());

                        List<Integer> relevantGerichte = new ArrayList<>();
                        for (Gericht ger : restaurantOrderMapper.get(rest.getRestaurant_ID())) {
                            relevantGerichte.add(ger.getGericht_ID());
                        }

                        bestellung.setGericht_IDs(Json.encode(relevantGerichte));
                        _bestellungRepository.persist(bestellung);
                        _bestellungRepository.flush();
                    }
                } catch (Exception e) {
                    System.out.println("Failed while creating bestellung:" + e);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
                }
                if (_bestellungRepository.isPersistent(bestellung)) {
                    for (Restaurant rest : restaurants) {
                        Map<Integer, Integer> amountMap = new HashMap<Integer, Integer>();
                        for (Gericht gericht : restaurantOrderMapper.get(rest.getRestaurant_ID())) {
                            if (amountMap.containsKey(gericht.getGericht_ID())) {
                                int currVal = amountMap.get(gericht.getGericht_ID());
                                amountMap.replace(gericht.getGericht_ID(), currVal + 1);
                            } else amountMap.put(gericht.getGericht_ID(), 1);
                        }
                    }
                }
            }
            return Response.status(Response.Status.CREATED).entity(auftrag).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
    }

    /**
     * Updates Auftragsstatus to 'bezahlt'
     *
     * @param AuftragsId auftrag to be updated
     * @return success or error
     */
    @Override
    @Transactional
    public PaymentDto payForOrder(Integer AuftragsId) throws SQLException {
        Double total = 0.0;
        Date date = new Date();
        try {
            Auftrag auftrag = _auftragRepository.getAuftragByID(AuftragsId);
            auftrag.setStatus("bezahlt");
            List<Bestellung> bestellungen = _bestellungRepository.getAllBestellungenByAuftragsId(auftrag.getAuftrags_ID());
            _auftragRepository.persist(auftrag);

            for (Bestellung best : bestellungen) {
                Rechnung rechnung = _rechnungRepository.getRechnungByID(best.getRechnung());

                String idsString = best.getGericht_IDs();
                idsString = idsString.replaceAll("[\\[\\]\\(\\)]", "");
                String[] ids = idsString.split("\\,");

                for (String s : ids) {
                    try {
                        Gericht gericht = _gerichtRepository.getGerichtByGerichtID(Integer.parseInt(s.trim()));
                        total += gericht.getPreis();
                    } catch (Exception e) {
                        System.out.println("Failed to retrieve Gericht: " + e);
                    }
                }
                rechnung.setZahlungseingang((byte) 1);
                rechnung.setDatum_Zahlungseingang(new Timestamp(date.getTime()));
                _rechnungRepository.persist(rechnung);

                best.setStatus("bezahlt");
                _bestellungRepository.persist(best);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new PaymentDto(total, "error");
        }
        return new PaymentDto(total, "success");
    }

    /**
     * Creates a statistic for all previous orders
     *
     * @param restaurantId
     * @param startTime
     * @param endTime
     * @return
     * @throws SQLException
     */
    @Override
    public StatisticDtoWrapper getStatistic(Long restaurantId, long startTime, long endTime) throws SQLException {

        StatisticDtoWrapper wrapper = new StatisticDtoWrapper();

        try {
            List<Auftrag> aufträge = _auftragRepository.getAllAuftragByRestaurant(restaurantId);
            for (Auftrag auftrag : aufträge) {
                try {
                    List<Bestellung> bestellungen = _bestellungRepository.getAllBestellungenByAuftragsId(auftrag.getAuftrags_ID());
                    for (Bestellung bestellung : bestellungen) {
                        if (bestellung.getTimestamp().getTime() >= startTime && bestellung.getTimestamp().getTime() <= endTime) {
                            try {
                                Rechnung rechnungen = _rechnungRepository.getRechnungByID(bestellung.getRechnung());
                                if (rechnungen.getZahlungseingang() == 1) {
                                    wrapper.add(new StatisticDto(rechnungen.getRechnungsdatum().getTime(), rechnungen.getBetrag()));
                                }

                            } catch (Exception e) {
                                System.out.println("@Get Stat Failed to retrieve Rechnung: " + e);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("@Get Stat Failed to retrieve Bestellung: " + e);
                }
            }
        } catch (Exception s) {
            System.out.println("@Get Stat Failed to retrieve Auftrag: " + s);
        }
        wrapper.convert();

        return wrapper;
    }

    /**
     * Returns the amount of Gericht orders by customer
     *
     * @param customerId
     * @param gerichtId
     * @return
     */
    @Override
    public Integer getAmountOrdersByCustomerIdAndGerichtId(int customerId, int gerichtId) {
        Kunde customer = null;
        List<Auftrag> orders = null;
        List<String> allOrders = null;
        String purchases = "";
        int count = 0;

        try {
            customer = _kundeRepository.getKundeByKundenId(customerId);
            orders = _auftragRepository.getAllAuftaregeByKundenId(customerId);
        } catch (Exception e) {
            System.out.println("@AmountOrdersGericht Failed to get Data " + e);
        }

        if (customer != null && orders != null) {
            for (Auftrag order : orders) {
                Bestellung purchase = _bestellungRepository.getBestellungByAuftragsId((int) order.getAuftrags_ID());
                if (purchase != null) {
                    try {
                        purchases = purchases.concat(purchase.getGericht_IDs());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            String idsString = purchases.replaceAll("[\\[\\]\\(\\)]", "");
            String[] ids = idsString.split("\\,");
            for (String id : ids) {
                if (gerichtId == Integer.parseInt(id.trim())) count++;
            }
        } catch (Exception e) {
            System.out.println("@AmountOrdersGericht Failed to get Data " + e);
        }

        return count;
    }

    /**
     * Returns the amount of All Orders from a restaurant by customer
     *
     * @param restaurantId
     * @param customerId
     * @return
     */
    @Override
    public Integer getAllOrdersForRestaurantIdByCustomerID(int restaurantId, int customerId) {
        List<Bestellung> ordersMatchingId = null;
        int count = 0;

        try {
            ordersMatchingId = _bestellungRepository.getAllBestellungenByRestaurantId(restaurantId);

            for (Bestellung order : ordersMatchingId) {
                Auftrag job = _auftragRepository.getAuftragByID(order.getAuftrags_ID());
                if (job.getKundennummer() == customerId) count++;
            }

        } catch (Exception e) {
            System.out.println("@GetAllOrders Failed to get Data " + e);
        }

        return count;
    }

    @Override
    public List getRestaurantBestellungen(String email) {return _bestellungRepository.getRestaurantBestellungen(email);}

    @Override
    public Response updateBestellungStatus(Bestellung bestellung) {
        _bestellungRepository.updateBestellungStatus(bestellung);
        return Response.status(Response.Status.OK).entity(bestellung).build();
    }

    @Override
    public List getProduktUndAnzahl(int id) {return _bestellungRepository.getProduktUndAnzahl(id);}

}
