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


}
