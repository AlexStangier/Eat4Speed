package de.eat4speed.services;
//im a diff
import de.eat4speed.dto.OrderDto;
import de.eat4speed.entities.*;
import de.eat4speed.repositories.*;
import de.eat4speed.services.interfaces.IBestellungService;
import io.vertx.core.json.Json;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
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

    @Inject
    public BestellungService(BestellungRepository bestellungRepository,
                             RechnungRepository rechnungRepository,
                             AuftragRepository auftragRepository,
                             StatusRepository statusRepository,
                             BestellzuordnungRepository bestellzuordnungRepository,
                             GerichtRepository gerichtRepository,
                             BenutzerRepository benutzerRepository,
                             AdressenRepository adressenRepository) {
        _bestellungRepository = bestellungRepository;
        _rechnungRepository = rechnungRepository;
        _auftragRepository = auftragRepository;
        _statusRepository = statusRepository;
        _bestellzuordnungRepository = bestellzuordnungRepository;
        _gerichtRepository = gerichtRepository;
        _benutzerRepository = benutzerRepository;
        _adressenRepository = adressenRepository;
    }

    @Override
    public Response addBestellung(Bestellung bestellung) {
        _bestellungRepository.addBestellung(bestellung);

        return Response.status(Response.Status.CREATED).entity(bestellung).build();
    }

    /**
     * Creates and persists an order with all attached items
     *
     * @param obj Dto containing a list of items and an customer identifier
     * @return HTTP Response
     */
    @Override
    @Transactional
    public Response createBestellung(OrderDto obj) throws SQLException {
        ArrayList<Gericht> safeItems = new ArrayList<>();
        ArrayList<Integer> gerichtIDs = new ArrayList<>();
        Benutzer benutzer = null;
        Date date = new Date();

        try {
            //get items by id¡
            for (int item : obj.items) {
                //make sure items are valid and not tempered
                safeItems.add(_gerichtRepository.getGerichtByGerichtID(item));
                gerichtIDs.add(item);
            }

            //get customer by id
            benutzer = _benutzerRepository.getBenutzerByID(obj.getCustomerId());
        } catch (Exception e) {
            System.out.println("Failed while creating order:" + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }

        if (!safeItems.isEmpty() && benutzer != null) {
            //create new rechnung
            Rechnung rechnung = null;
            Adressen adresse = null;
            Auftrag auftrag = null;
            Bestellung bestellung = null;

            try {
                rechnung = new Rechnung((safeItems.stream().mapToDouble(Gericht::getPreis).sum() * 1.07 + 2.00), new Timestamp(date.getTime()), (byte) 0);
            } catch (Exception e) {
                System.out.println("Failed while creating rechnung:" + e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
            }

            _rechnungRepository.persist(rechnung);
            if (_rechnungRepository.isPersistent(rechnung)) {

                try {
                    adresse = _adressenRepository.getAdresseByCustomerId(benutzer.getBenutzer_ID());
                } catch (Exception e) {
                    System.out.println("Failed while querying address:" + e);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
                }
                try {
                    auftrag = new Auftrag(safeItems.get(0).getRestaurant_ID(), new Timestamp(date.getTime()), adresse.getAdress_ID(), 23.00, benutzer.getBenutzer_ID(), "offen", 10);
                } catch (Exception e) {
                    System.out.println("Failed while creating auftrag:" + e);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
                }

                _auftragRepository.persist(auftrag);
                if (_auftragRepository.isPersistent(auftrag)) {
                    try {
                        bestellung = new Bestellung((int) auftrag.getAuftrags_ID(), new Timestamp(date.getTime()), rechnung.getRechnungs_ID());
                        bestellung.setGericht_IDs(Json.encode(gerichtIDs));
                    } catch (Exception e) {
                        System.out.println("Failed while creating bestellung:" + e);
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
                    }
                    _bestellungRepository.persist(bestellung);

                    if (_bestellungRepository.isPersistent(bestellung)) {
                        Map<Integer, Integer> amountMap = new HashMap<Integer, Integer>();
                        for (int id : gerichtIDs) {
                            if (amountMap.containsKey(id)) {
                                int currVal = amountMap.get(id);
                                amountMap.replace(id, currVal + 1);
                            } else amountMap.put(id, 1);
                        }
                        for (Map.Entry<Integer, Integer> entry : amountMap.entrySet()) {
                            try {
                                _bestellzuordnungRepository.addBestellzuordnung(new Bestellzuordnung(bestellung.getBestell_ID(), entry.getKey(), entry.getValue()));
                            } catch (Exception e) {
                                System.out.println("Something went wrong persisting bestellzuordnung: " + e);
                            }
                        }
                    }
                }
            }

            return Response.status(Response.Status.CREATED).entity(auftrag).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
    }
}
