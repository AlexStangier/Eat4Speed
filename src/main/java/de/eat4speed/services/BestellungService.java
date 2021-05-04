package de.eat4speed.services;

import de.eat4speed.entities.*;
import de.eat4speed.repositories.*;
import de.eat4speed.services.interfaces.IBestellungService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class BestellungService implements IBestellungService {

    private BestellungRepository _bestellungRepository;
    private RechnungRepository _rechnungRepository;
    private AuftragRepository _auftragRepository;
    private BestellhistorieRepository _bestellhistorieRepository;
    private StatusRepository _statusRepository;
    private BestellzuordnungRepository _bestellzuordnungRepository;
    private GerichtRepository _gerichtRepository;
    private BenutzerRepository _benutzerRepository;
    private AdressenRepository _adressenRepository;

    @Inject
    public BestellungService(BestellungRepository bestellungRepository,
                             RechnungRepository rechnungRepository,
                             AuftragRepository auftragRepository,
                             BestellhistorieRepository bestellhistorieRepository,
                             StatusRepository statusRepository,
                             BestellzuordnungRepository bestellzuordnungRepository,
                             GerichtRepository gerichtRepository,
                             BenutzerRepository benutzerRepository,
                             AdressenRepository adressenRepository) {
        _bestellungRepository = bestellungRepository;
        _rechnungRepository = rechnungRepository;
        _auftragRepository = auftragRepository;
        _bestellhistorieRepository = bestellhistorieRepository;
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
     * @param items      the order itmes
     * @param customerId the customer
     * @return HTTP Response
     */
    @Override
    public Response createBestellung(int[] items, long customerId) throws SQLException {
        ArrayList<Gericht> safeItems = new ArrayList<>();
        Benutzer orderer = null;
        Date date = new Date();

        try {
            //get items by id
            for (int item : items) {
                //make sure items are valid and not tempered
                safeItems.add(_gerichtRepository.getGerichtByGerichtID(item));
            }

            //get customer by id
            orderer = _benutzerRepository.findById(customerId);
        } catch (Exception e) {
            System.out.println("Failed while creating order:" + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }

        if (!safeItems.isEmpty() && orderer != null) {
            //create new bill
            Rechnung bill = new Rechnung();

            bill.setBetrag(safeItems.stream().mapToDouble(Gericht::getPreis).sum());
            //TODO add delivery cost
            //add tax
            bill.setBetrag(bill.getBetrag() * 1.07);
            bill.setRechnungsdatum(new Timestamp(date.getTime()));
            bill.setZahlungseingang((byte) 0);
            _rechnungRepository.persist(bill);

            //make sure changes have been applied
            _rechnungRepository.flush();

            //set Status
            Status status = new Status();
            status.setStatus_Name("offen");
            status.setRechnungs_ID(bill.getRechnungs_ID());
            _statusRepository.persist(status);

            //create Auftrag
            Auftrag order = new Auftrag();
            order.setKundennummer(orderer.getBenutzer_ID());
            //TODO set different ids if order is composit of mutiple restaurants
            order.setAuftragnehmer(safeItems.get(0).getRestaurant_ID());
            order.setTimestamp(new Timestamp(date.getTime()));
            order.setAnschrift(orderer.getAnschrift());
            order.setStatus(status.getStatus_Name().toLowerCase(Locale.ROOT));

            //TODO add algorithm result here
            order.setLieferdistanz(23.0);
            order.setGeschaetzte_fahrzeit_restaurant_ziel(15);

            _auftragRepository.addAuftrag(order);

            //make sure changes have been applied
            _auftragRepository.flush();

            //create new Bestellhistorie entry
            Bestellhistorie orderHistoryEntry = new Bestellhistorie();
            orderHistoryEntry.setBestellhistorien_ID((int) order.getAuftrags_ID());
            _bestellhistorieRepository.addBestellhistorie(orderHistoryEntry);
            return Response.status(Response.Status.CREATED).entity(order).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
    }
}
