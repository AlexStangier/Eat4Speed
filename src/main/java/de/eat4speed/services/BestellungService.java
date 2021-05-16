package de.eat4speed.services;

import de.eat4speed.dto.OrderDto;
import de.eat4speed.entities.*;
import de.eat4speed.repositories.*;
import de.eat4speed.services.interfaces.IBestellungService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        Benutzer benutzer = null;
        Date date = new Date();

        try {
            //get items by id
            for (int item : obj.items) {
                //make sure items are valid and not tempered
                safeItems.add(_gerichtRepository.getGerichtByGerichtID(item));
            }

            //get customer by id
            benutzer = _benutzerRepository.getBenutzerByID(obj.getCustomerId());
        } catch (Exception e) {
            System.out.println("Failed while creating order:" + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }

        if (!safeItems.isEmpty() && benutzer != null) {
            //create new rechnung
            Rechnung rechnung = new Rechnung();

            rechnung.setBetrag(safeItems.stream().mapToDouble(Gericht::getPreis).sum());
            //add tax
            rechnung.setBetrag(rechnung.getBetrag() * 1.07 + 2.00);
            rechnung.setRechnungsdatum(new Timestamp(date.getTime()));
            rechnung.setZahlungseingang((byte) 0);
            _rechnungRepository.persist(rechnung);

            //make sure changes have been applied
            _rechnungRepository.flush();

            //set Status
            Status status = new Status();
            status.setStatus_Name("offen");
            status.setRechnungs_ID(rechnung.getRechnungs_ID());

            //create Auftrag
            Auftrag auftrag = new Auftrag();
            auftrag.setKundennummer(benutzer.getBenutzer_ID());
            //TODO set different ids if order is composit of mutiple restaurants
            auftrag.setAuftragnehmer(safeItems.get(0).getRestaurant_ID());
            auftrag.setTimestamp(new Timestamp(date.getTime()));
            //TODO fix Anschrift key
            auftrag.setAnschrift(1);
            auftrag.setStatus(status.getStatus_Name().toLowerCase(Locale.ROOT));

            //TODO add algorithm result here
            auftrag.setLieferdistanz(23.0);
            auftrag.setGeschaetzte_fahrtzeit_restaurant_ziel(15);

            _auftragRepository.addAuftrag(auftrag);

            //make sure changes have been applied
            _auftragRepository.flush();

            //bestellung
            Bestellung bestellung = new Bestellung();
            bestellung.setRechnung(rechnung.getRechnungs_ID());
            bestellung.setTimestamp(new Timestamp(date.getTime()));
            bestellung.setAuftrags_ID((int) auftrag.getAuftrags_ID());
            _bestellungRepository.persist(bestellung);

            //_bestellhistorieRepository.addBestellhistorie(orderHistoryEntry);
            return Response.status(Response.Status.CREATED).entity(auftrag).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(null).build();
    }
}
