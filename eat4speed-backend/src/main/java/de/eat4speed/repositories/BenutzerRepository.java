package de.eat4speed.repositories;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.Benutzer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
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
        Benutzer user = find("EmailAdresse", email.getEmail()).firstResult();
        if (user == null) {
            return -1;
        }
        return user.getBenutzer_ID();
    }

    @Transactional
    public List checkIfBenutzerIsGeloescht(String email)
    {
        List restaurantBenutzer;

        Query query = entityManager.createQuery(
                "SELECT b.geloescht " +
                        "from Benutzer b " +
                        "where b.emailAdresse = ?1 "
        ).setParameter(1, email);

        restaurantBenutzer = query.getResultList();
        return restaurantBenutzer;
    }

    @Transactional
    public List checkIfBenutzerIsBlacklist(String email)
    {
        List restaurantBenutzer;

        Query query = entityManager.createQuery(
                "SELECT b.loeschbegruendung " +
                        "from Blacklist b " +
                        "where b.emailAdresse = ?1 "
        ).setParameter(1, email);

        restaurantBenutzer = query.getResultList();
        return restaurantBenutzer;
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
    public List getKundennummerByBenutzername(String username) {
        List kundennummer;

        Query query = entityManager.createQuery(
                "SELECT k.kundennummer " +
                        "from Benutzer b, Kunde k " +
                        "where b.emailAdresse LIKE ?1 " +
                        "and b.benutzer_ID = k.benutzer_ID"
        ).setParameter(1, username);

        kundennummer = query.getResultList();
        return kundennummer;
    }

    @Transactional
    public List getRestaurant_IDByBenutzername(String username) {
        List restaurant_ID;

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID " +
                        "from Benutzer b, Restaurant r " +
                        "where b.emailAdresse LIKE ?1 " +
                        "and b.benutzer_ID = r.benutzer_ID"
        ).setParameter(1, username);

        restaurant_ID = query.getResultList();
        return restaurant_ID;
    }

    @Transactional
    public List getFahrernummerByBenutzername(String username) {
        List restaurant_ID;

        Query query = entityManager.createQuery(
                "SELECT f.fahrernummer " +
                        "from Benutzer b, Fahrer f " +
                        "where b.emailAdresse LIKE ?1 " +
                        "and f.benutzer_ID = b.benutzer_ID"
        ).setParameter(1, username);

        restaurant_ID = query.getResultList();
        return restaurant_ID;
    }

    @Transactional
    public List getBenutzer_IDByBenutzername(String benutzername)
    {
        List benutzer_ID;

        Query query = entityManager.createQuery(
                "SELECT b.benutzer_ID " +
                        "from Benutzer b " +
                        "where b.benutzername LIKE ?1 "
        ).setParameter(1, benutzername);

        benutzer_ID = query.getResultList();
        return benutzer_ID;
    }

    @Transactional
    public List getBenutzer_IDByEmail(String email)
    {
        List benutzer_ID;

        Query query = entityManager.createQuery(
                "SELECT b.benutzer_ID " +
                        "from Benutzer b " +
                        "where b.emailAdresse LIKE ?1 "
        ).setParameter(1, email);

        benutzer_ID = query.getResultList();
        return benutzer_ID;
    }

    @Transactional
    public void deleteBenutzerByEmail(String email)
    {
        update("geloescht = 1 where emailAdresse = ?1", email);
    }

    /**
     * Should only be used in Test cleanup
     *
     * @param name name of entities to be deleted
     */
    @Transactional
    public void deleteBenutzerByUsername(String name) {
        delete("Benutzername", name);
    }

    /**
     * Returns the role of the specified user
     * @param id
     * @return
     */
    @Transactional
    public String getRoleById(long id) {
        return find("Benutzer_ID", id).firstResult().getRolle();
    }

    @Transactional
    public String getRoleByEmail(String email) {
        return find("EmailAdresse", email).firstResult().getRolle();
    }
}
