package de.eat4speed.repositories;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.json.JSONArray;

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
                        "DATE_FORMAT(b.Timestamp, '%d.%m.%y - %H:%i')," +
                        "DATE_FORMAT(a.Timestamp_On_Customer_Demand, '%d.%m.%y - %H:%i')" +
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
                        "AND TIMESTAMPDIFF(SECOND, b.Timestamp, CURRENT_TIMESTAMP()) > 300 " +
                        "order by  FIELD(b.Status, 'bezahlt', 'bearbeitung', 'abholbereit'), b.Bestell_ID").setParameter(1, email);
        restaurantBestellungen = query.getResultList();
        return restaurantBestellungen;
    }

    @Transactional
    public void updateBestellungStatusRestaurantUndKundeDontTouchThis(Bestellung bestellung) {
        update("status = ?1 where bestell_ID = ?2", bestellung.getStatus(), bestellung.getBestell_ID());
    }

    @Transactional
    public void updateBestellungStatus(Bestellung bestellung) {
        update("status = ?1 where bestell_ID = ?2", bestellung.getStatus(), bestellung.getBestell_ID());
    }

    public JSONArray getGerichteByAuftragID(int id)
    {
        Query query = entityManager.createNativeQuery("Select Gericht_IDs from eatforspeed.Bestellung " +
                "Where Auftrags_ID= " + id);

        return new JSONArray(query.getResultList());
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

    @Transactional
    public List getKundeBestellungen(String status, String email) {
        List getKundeBestellungen;

        Query query = entityManager.createNativeQuery(
                "select best.Bestell_ID, DATE_FORMAT(best.Timestamp, '%d.%m.%y - %H:%i'), best.Status, r.Betrag," +
                        "TIMESTAMPDIFF(SECOND, best.Timestamp, CURRENT_TIMESTAMP()) AS SECONDS," +
                        "DATE_FORMAT(a.Timestamp_Lieferung, '%d.%m.%y - %H:%i') " +
                        "from Bestellung best, Auftrag a, Rechnung r " +
                        "where best.Auftrags_ID = a.Auftrags_ID " +
                        " AND best.STATUS =  ?1 " +
                        "and best.Rechnung = r.Rechnungs_ID " +
                        "and a.Kundennummer = " +
                        "(select Kundennummer from Kunde where Benutzer_ID = " +
                        "(select Benutzer_ID from Benutzer where EmailAdresse = ?2)) " +
                        "order by best.Timestamp desc").setParameter(1, status).setParameter(2, email);
        getKundeBestellungen = query.getResultList();
        return getKundeBestellungen;
    }

    @Transactional
    public List getKundeBestellungenAktiv(String email) {
        List getKundeBestellungenAktiv;

        Query query = entityManager.createNativeQuery(
                "select best.Bestell_ID, DATE_FORMAT(best.Timestamp, '%d.%m.%y - %H:%i'), best.Status, r.Betrag," +
                        "TIMESTAMPDIFF(SECOND, best.Timestamp, CURRENT_TIMESTAMP()) AS SECONDS," +
                        "DATE_FORMAT(a.Timestamp_On_Customer_Demand, '%d.%m.%y - %H:%i') " +
                        "from Bestellung best, Auftrag a, Rechnung r " +
                        "where best.Auftrags_ID = a.Auftrags_ID " +
                        " AND best.STATUS in ('bearbeitung', 'abholbereit', 'abgeholt') " +
                        "and best.Rechnung = r.Rechnungs_ID " +
                        "and a.Kundennummer = " +
                        "(select Kundennummer from Kunde where Benutzer_ID = " +
                        "(select Benutzer_ID from Benutzer where EmailAdresse = ?1)) " +
                        "order by best.Timestamp desc").setParameter(1, email);
        getKundeBestellungenAktiv = query.getResultList();
        return getKundeBestellungenAktiv;
    }

    @Transactional
    public List getGerichtIds(int id) {
        List getGerichtIds;

        Query query = entityManager.createNativeQuery(
                "select best.Gericht_IDs from Bestellung best where best.Bestell_ID = ?1").setParameter(1, id);
        getGerichtIds = query.getResultList();
        return getGerichtIds;
    }

    @Transactional
    public List getAnzahlFertigerAuftraege(int id) {
        List getAnzahlFertigerAuftraege;

        Query query = entityManager.createNativeQuery(
                "SELECT ((select count(*) from Bestellung best " +
                        "where best.Auftrags_ID = (select b.Auftrags_ID from Bestellung b where b.Bestell_ID = ?1)) " +
                        "- " +
                        "(select count(*) from Bestellung best " +
                        "where best.Auftrags_ID = (select Auftrags_ID from Bestellung where Bestell_ID = ?1) " +
                        "AND best.Status IN ('storniert', 'abgeliefert'))) as Anzahl," +
                        " Auftrags_ID from Bestellung where Bestell_ID = ?1").setParameter(1, id);

        getAnzahlFertigerAuftraege = query.getResultList();
        return getAnzahlFertigerAuftraege;
    }




}
