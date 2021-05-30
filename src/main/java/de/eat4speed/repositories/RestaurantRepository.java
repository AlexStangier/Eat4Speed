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
                "SELECT r.restaurant_ID, r.name_des_Restaurants, r.allgemeine_Beschreibung, r.mindestbestellwert, r.bestellradius, a.strasse, a.hausnummer, a.postleitzahl, a.ort, b.telefonnummer, a.lng, a.lat " +
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
    public List<Integer> getRestaurant_IDsByRestaurantName(String restaurantName)
    {
        List<Integer> restaurantData;

        String likeName = "%"+restaurantName+"%";

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID " +
                        "FROM Restaurant r " +
                        "WHERE r.name_des_Restaurants LIKE ?1"
        ).setParameter(1,likeName);

        restaurantData = query.getResultList();

        return restaurantData;
    }

    @Transactional
    public List<Integer> getRestaurant_IDsByMindestbestellwertMax(Double mindestbestellwert)
    {
        List<Integer> restaurantData;

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID " +
                        "FROM Restaurant r " +
                        "WHERE r.mindestbestellwert > ?1"
        ).setParameter(1,mindestbestellwert);

        restaurantData = query.getResultList();

        return restaurantData;
    }

    @Transactional
    public List<Integer> getRestaurant_IDsByBewertung(double bewertung)
    {
        List<Integer> restaurantData;

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID " +
                        "FROM Restaurant r, Bewertung b " +
                        "WHERE b.restaurant_ID = r.restaurant_ID " +
                        "GROUP BY r.restaurant_ID " +
                        "HAVING AVG(b.sterne) >= ?1"
        ).setParameter(1,bewertung);

        restaurantData = query.getResultList();

        return restaurantData;
    }

    @Transactional
    public List<Integer> getRestaurant_IDsByDistance(int kundennummer, Double distance)
    {
        List<Integer> restaurantData;
        System.out.println(distance);

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID " +
                        "FROM Kunde k, Restaurant r, EntfernungKundeRestaurant ekr " +
                        "WHERE r.restaurant_ID = ekr.restaurant_ID " +
                        "AND k.kundennummer = ekr.kundennummer " +
                        "AND k.kundennummer = ?1 " +
                        "AND ekr.entfernung > ?2"
        ).setParameter(1,kundennummer).setParameter(2,distance);

        restaurantData = query.getResultList();

        return restaurantData;
    }

    @Transactional
    public List getRestaurantDataByKundennummer_Favoriten(int kundennummer)
    {
        List restaurantData;

        Query query = entityManager.createQuery(
                "SELECT r.restaurant_ID, r.name_des_Restaurants, r.allgemeine_Beschreibung, r.mindestbestellwert, r.bestellradius, a.strasse, a.hausnummer, a.postleitzahl, a.ort, a.lng, a.lat, fr.anzahl_Bestellungen, fr.hinzufuegedatum " +
                        "FROM Restaurant r, Kunde k, Favoritenliste_Restaurants fr, Adressen a " +
                        "WHERE r.restaurant_ID = fr.restaurant_ID " +
                        "AND k.kundennummer = fr.kundennummer " +
                        "AND a.adress_ID = r.anschrift " +
                        "AND k.kundennummer = ?1"
        ).setParameter(1,kundennummer);

        restaurantData = query.getResultList();

        return restaurantData;
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
