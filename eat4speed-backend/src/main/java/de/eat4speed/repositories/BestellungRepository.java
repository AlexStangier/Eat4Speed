package de.eat4speed.repositories;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BestellungRepository implements PanacheRepository<Bestellung> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addBestellung(Bestellung bestellung) {
        persist(bestellung);
    }

    @Transactional
    public Bestellung getBestellungByID(int id) {
        return find("Bestell_ID", id).firstResult();
    }

    @Transactional
    public Bestellung getBestellungByAuftragsId(int id) {
        return find("Auftrags_ID", id).firstResult();
    }

    @Transactional
    public List<Bestellung> getAllBestellungByAuftragsId(int id) {
        return find("Auftrags_ID", id).list();
    }

    @Transactional
    public List<Bestellung> getAllBestellungenByAuftragsId(long id) {
        List<Bestellung> result = find("Auftrags_ID", id).list();
        return result;
    }

    @Transactional
    public List getRestaurantBestellungen(String email) {
        List restaurantBestellungen;

        Query query = entityManager.createNativeQuery(
                "SELECT " +
                        "b.Bestell_ID, " +
                        "k.NAME, " +
                        "b.STATUS, " +
                        "r.Betrag," +
                        "DATE_FORMAT(b.Timestamp, '%d.%m.%y - %H:%i')" +
                        "FROM Auftrag a, Bestellung b, Rechnung r, Kunde k " +
                        "WHERE b.restaurant_ID = " +
                        "( SELECT Restaurant_ID FROM Restaurant " +
                        "WHERE Benutzer_ID = " +
                        "( SELECT Benutzer_ID FROM Benutzer " +
                        "WHERE EmailAdresse LIKE ?1 ) ) " +
                        "AND b.STATUS IN ( 'bezahlt', 'bearbeitung', 'abholbereit' ) " +
                        "AND b.Rechnung = r.Rechnungs_ID " +
                        "AND b.Gericht_IDs IS NOT NULL " +
                        "AND b.Auftrags_ID = a.Auftrags_ID " +
                        "AND a.Kundennummer = k.Kundennummer " +
                        "order by  FIELD(b.Status, 'bezahlt', 'bearbeitung', 'abholbereit'), b.Bestell_ID").setParameter(1, email);
        restaurantBestellungen = query.getResultList();
        return restaurantBestellungen;
    }

    @Transactional
    public void updateBestellungStatus(Bestellung bestellung) {
        update("status = ?1 where bestell_ID = ?2", bestellung.getStatus(), bestellung.getBestell_ID());
    }

    @Transactional
    public List getProduktUndAnzahl(int id) {
        List getProdutAndAnzahl;

        Query query = entityManager.createNativeQuery(
                "select " +
                        "group_concat(DISTINCT A.Name SEPARATOR ', ') Produkte, " +
                        "group_concat(DISTINCT cnt.Anzahl SEPARATOR ', ') Anzahl " +
                        "from (SELECT Name FROM Gericht WHERE Gericht_ID " +
                        "IN ( SELECT json_as_rows.* FROM Bestellung best, " +
                        "JSON_TABLE(Gericht_IDs, '$[*]' COLUMNS(Gericht_ID INT PATH '$')) json_as_rows where Bestell_ID = ?1 ) ) as A, " +
                        "(select count(Gericht_ID) as Anzahl FROM Bestellung best, " +
                        "JSON_TABLE(Gericht_IDs, '$[*]' COLUMNS(Gericht_ID INT PATH '$')) json_as_rows where Bestell_ID = ?1 group by Gericht_ID) as cnt").setParameter(1, id);
        getProdutAndAnzahl = query.getResultList();
        return getProdutAndAnzahl;
    }

    @Transactional
    public List checkForUserOrders(int kundennummer) {
        List getProdutAndAnzahl;

        Query query = entityManager.createQuery(
                "SELECT b.bestell_ID " +
                        "FROM Kunde k, Auftrag a, Bestellung b " +
                        "WHERE k.kundennummer = a.kundennummer " +
                        "AND a.auftrags_ID = b.auftrags_ID " +
                        "AND k.kundennummer = ?1"
        ).setParameter(1,kundennummer);

        getProdutAndAnzahl = query.getResultList();
        return getProdutAndAnzahl;
    }

    @Transactional
    public List<Bestellung> getAllBestellungenByRestaurantId(Integer restaurantId){
        return find("restaurant_ID", restaurantId).list();
    }
}
