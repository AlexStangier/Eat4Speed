package de.eat4speed.repositories;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.Benutzer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BenutzerRepository implements PanacheRepository<Benutzer> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addBenutzer(Benutzer benutzer) {
        persist(benutzer);
    }

    @Transactional
    public Benutzer getBenutzerByID(int id) {
        return find("benutzer_ID", id).firstResult();
    }

    @Transactional
    public Integer getBenutzerIdByEmail(UserEmailDto email) {
        Benutzer dummy = find("EmailAdresse", email.getEmail()).firstResult();
        return dummy.getBenutzer_ID();
    }

    @Transactional
    public List getBenutzerByLogin(String email) {
        List restaurantBenutzer;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, b.emailAdresse, b.telefonnummer, r.name_des_Restaurants, r.bestellradius, r.mindestbestellwert, a.strasse, a.ort, a.postleitzahl, a.hausnummer, b.benutzer_ID, r.restaurant_ID, a.adress_ID from Benutzer b, Restaurant r, Adressen a where b.emailAdresse LIKE ?1 and b.benutzer_ID = r.benutzer_ID and r.anschrift = a.adress_ID"
        ).setParameter(1, email);

        restaurantBenutzer = query.getResultList();
        return restaurantBenutzer;
    }

    @Transactional
    public void updateBenutzerRestaurant(Benutzer benutzer) {
        update("emailAdresse = ?1, vorname = ?2, nachname = ?3, telefonnummer = ?4 where benutzer_ID = ?5", benutzer.getEmailAdresse(), benutzer.getVorname(), benutzer.getNachname(), benutzer.getTelefonnummer(), benutzer.getBenutzer_ID());
    }


    @Transactional
    public List getBenutzerKundeEinstellungenByLogin(String email) {
        List kundeBenutzer;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, b.emailAdresse, b.telefonnummer, a.strasse, a.ort, a.postleitzahl, a.hausnummer, b.benutzer_ID, a.adress_ID, k.kundennummer from Benutzer b, Kunde k, Adressen a where b.emailAdresse LIKE ?1 and b.benutzer_ID = k.benutzer_ID and k.anschrift = a.adress_ID"
        ).setParameter(1, email);

        kundeBenutzer = query.getResultList();
        return kundeBenutzer;
    }

    @Transactional
    public List getRestaurantBestellungen(String email) {
        List restaurantBestellungen;

        Query query = entityManager.createNativeQuery(
                "SELECT a.auftrags_ID, k.name, a.status, r.Betrag, Artikel.Produkte, Artikel.Gesamtanzahl " +
                        "FROM Auftrag a, " +
                        "Bestellung b, " +
                        "Kunde k, " +
                        "Rechnung r, " +
                        "Bestellzuordnung bz, " +
                        "Gericht g, " +
                        "Benutzer be, " +
                        "Restaurant re, " +
                        "(SELECT bz.Bestell_ID, GROUP_CONCAT(g.NAME) AS Produkte, GROUP_CONCAT(bz.Anzahl) as Gesamtanzahl " +
                            "FROM Bestellzuordnung bz, Gericht g " +
                            "WHERE bz.Gericht_ID = g.Gericht_ID " +
                            "GROUP BY bz.Bestell_ID ) AS Artikel " +
                        "WHERE a.Auftrags_ID = b.Auftrags_ID " +
                        "AND a.Kundennummer = k.Benutzer_ID " +
                        "AND b.Rechnung = r.Rechnungs_ID " +
                        "AND b.Bestell_ID = bz.Bestell_ID " +
                        "AND b.Gericht_IDs IS NOT NULL " +
                        "AND Artikel.Bestell_ID = b.Bestell_ID " +
                        "AND a.Auftragnehmer = re.Restaurant_ID " +
                        "AND re.Benutzer_ID = be.Benutzer_ID " +
                        "AND a.Status IN ('bezahlt','bearbeitung','abholbereit')" +
                        "AND be.EmailAdresse like ?1 " +
                        "GROUP BY a.Auftrags_ID, k.NAME, a.STATUS, r.Betrag, Artikel.Produkte, Artikel.Gesamtanzahl " +
                        "ORDER BY a.Auftrags_ID asc").setParameter(1, email);

        restaurantBestellungen = query.getResultList();
        return restaurantBestellungen;
    }

}
