package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Auftrag;
import de.eat4speed.entities.Benutzer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AdressenRepository implements PanacheRepository<Adressen> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addAdresse(Adressen adresse)
    {
        persist(adresse);
    }

    @Transactional
    public void updateAdresse(Adressen adresse) {
        update("strasse = ?1, ort = ?2, postleitzahl = ?3, hausnummer = ?4 where adress_id = ?5", adresse.getStrasse(), adresse.getOrt(), adresse.getPostleitzahl(), adresse.getHausnummer(), adresse.getAdress_ID());
    }

    @Transactional
    public List getAllKundeLngLat()
    {
        List kundeData;

        Query query = entityManager.createQuery(
                "SELECT k.kundennummer, a.lng, a.lat " +
                        "FROM Kunde k, Adressen a " +
                        "WHERE k.anschrift = a.adress_ID "
        );

        kundeData = query.getResultList();

        return kundeData;
    }

    @Transactional
    public List getAllRestaurantLngLat()
    {
        List restaurantData;

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID, a.lng, a.lat " +
                        "FROM Restaurant r, Adressen a " +
                        "WHERE r.anschrift = a.adress_ID "
        );

        restaurantData = query.getResultList();

        return restaurantData;
    }

    @Transactional
    public Adressen getAdresseByCustomerId(int id){
        return find("Adress_ID", id).firstResult();
    }

    @Transactional
    public void deleteAdresse(int addressId) {
        delete("Adress_ID", addressId);
    }
}
