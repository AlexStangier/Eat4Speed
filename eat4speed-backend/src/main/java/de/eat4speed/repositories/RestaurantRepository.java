package de.eat4speed.repositories;

import de.eat4speed.entities.Restaurant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RestaurantRepository implements PanacheRepository<Restaurant> {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void addRestaurant(Restaurant restaurant) {
        persist(restaurant);
    }


    @Transactional
    public void updateRestaurant_Verifiziert(int restaurant_ID) {
        update("verifiziert = 1 where restaurant_ID = ?1", restaurant_ID);
    }

    @Transactional
    public List getAllRestaurant() {
        List allRestaurantData;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, r.restaurant_ID, r.name_des_Restaurants, a.strasse, a.hausnummer, a.ort, a.postleitzahl, r.verifiziert  " +
                        "FROM Restaurant r, Benutzer b, Adressen a " +
                        "WHERE r.anschrift = a.adress_ID " +
                        "AND r.benutzer_ID = b.benutzer_ID "

        );
        allRestaurantData = query.getResultList();

        return allRestaurantData;
    }

    @Transactional
    public List getRestaurantDataByRestaurantName(String restaurantName) {
        List restaurantsData;

        String likeName = "%" + restaurantName + "%";

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID, r.name_des_Restaurants, r.allgemeine_Beschreibung, r.mindestbestellwert, r.bestellradius " +
                        "FROM Restaurant r " +
                        "WHERE r.name_des_Restaurants LIKE ?1 "
        ).setParameter(1, likeName);

        restaurantsData = query.getResultList();

        return restaurantsData;
    }

    @Transactional
    public List getAllRestaurantDataByRestaurant_ID(int restaurant_ID) {
        List restaurantsData;

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID, r.name_des_Restaurants, r.allgemeine_Beschreibung, r.mindestbestellwert, r.bestellradius, a.strasse, a.hausnummer, a.postleitzahl, a.ort, b.telefonnummer " +
                        "FROM Restaurant r, Adressen a, Benutzer b " +
                        "WHERE r.benutzer_ID = b.benutzer_ID " +
                        "AND r.anschrift = a.adress_ID " +
                        "AND r.restaurant_ID = ?1"
        ).setParameter(1, restaurant_ID);

        restaurantsData = query.getResultList();

        return restaurantsData;
    }

    @Transactional
    public List getNotVerifiedRestaurant() {
        List notVerifiedRestaurantData;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, r.restaurant_ID, r.name_des_Restaurants, a.strasse, a.hausnummer, a.ort, a.postleitzahl, r.verifiziert  " +
                        "FROM Restaurant r, Benutzer b, Adressen a " +
                        "WHERE r.anschrift = a.adress_ID " +
                        "AND r.benutzer_ID = b.benutzer_ID " +
                        "AND r.verifiziert = 0"

        );
        notVerifiedRestaurantData = query.getResultList();

        return notVerifiedRestaurantData;
    }

    @Transactional
    public List getVerifiedRestaurant() {
        List verifiedRestaurantData;

        Query query = entityManager.createQuery(
                "SELECT b.vorname, b.nachname, r.restaurant_ID, r.name_des_Restaurants, a.strasse, a.hausnummer, a.ort, a.postleitzahl, r.verifiziert  " +
                        "FROM Restaurant r, Benutzer b, Adressen a " +
                        "WHERE r.anschrift = a.adress_ID " +
                        "AND r.benutzer_ID = b.benutzer_ID " +
                        "AND r.verifiziert = 1"

        );
        verifiedRestaurantData = query.getResultList();

        return verifiedRestaurantData;
    }

    @Transactional
    public int deleteRestaurant(int restaurant_ID) {
        delete("restaurant_ID", restaurant_ID);

        return restaurant_ID;
    }


    public Restaurant findByRestaurantnummer(int restaurant_ID) {
        return find("restaurant_ID", restaurant_ID).firstResult();
    }

    @Transactional
    public void updateRestaurantStammdaten(Restaurant restaurant) {
        update("name_des_Restaurants = ?1, mindestbestellwert = ?2, bestellradius = ?3 where restaurant_ID = ?4", restaurant.getName_des_Restaurants(), restaurant.getMindestbestellwert(), restaurant.getBestellradius(), restaurant.getRestaurant_ID());
    }
}
