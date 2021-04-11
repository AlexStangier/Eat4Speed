package de.eat4speed.repositories;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.entities.Kunde;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.drools.core.runtime.help.impl.XStreamJSon;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RestaurantRepository implements PanacheRepository<Restaurant> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addRestaurant(Restaurant restaurant)
    {
        persist(restaurant);
    }


    @Transactional
    public void updateRestaurant_Verifiziert(int restaurant_ID)
    {
        update("verifiziert = 1 where restaurant_ID = ?1", restaurant_ID);
    }

    @Transactional
    public List getAllRestaurant()
    {
        List allRestaurantData;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, r.restaurant_Id, r.name_Des_Restaurants, a.strasse, a.hausnummer, a.ort, a.postleitzahl, r.verifiziert  " +
                        "FROM Restaurant r, Benutzer b, Adressen a " +
                        "WHERE r.anschrift = a.adress_Id " +
                        "AND r.benutzer_Id = b.benutzer_ID "

        );
        allRestaurantData = query.getResultList();

        return allRestaurantData;
    }

    @Transactional
    public List getNotVerifiedRestaurant()
    {
        List notVerifiedRestaurantData;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, r.restaurant_Id, r.name_Des_Restaurants, a.strasse, a.hausnummer, a.ort, a.postleitzahl, r.verifiziert  " +
                        "FROM Restaurant r, Benutzer b, Adressen a " +
                        "WHERE r.anschrift = a.adress_Id " +
                        "AND r.benutzer_Id = b.benutzer_ID " +
                        "AND r.verifiziert = 0"

        );
        notVerifiedRestaurantData = query.getResultList();

        return notVerifiedRestaurantData;
    }

    @Transactional
    public List getVerifiedRestaurant()
    {
        List verifiedRestaurantData;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, r.restaurant_Id, r.name_Des_Restaurants, a.strasse, a.hausnummer, a.ort, a.postleitzahl, r.verifiziert  " +
                        "FROM Restaurant r, Benutzer b, Adressen a " +
                        "WHERE r.anschrift = a.adress_Id " +
                        "AND r.benutzer_Id = b.benutzer_ID " +
                        "AND r.verifiziert = 1"

        );
        verifiedRestaurantData = query.getResultList();

        return verifiedRestaurantData;
    }

    @Transactional
    public int deleteRestaurant(int restaurant_ID)
    {
        delete("restaurant_ID", restaurant_ID);

        return restaurant_ID;
    }


    public Restaurant findByRestaurantnummer(int restaurant_ID)
    {
        return find("restaurant_ID", restaurant_ID).firstResult();
    }
}
