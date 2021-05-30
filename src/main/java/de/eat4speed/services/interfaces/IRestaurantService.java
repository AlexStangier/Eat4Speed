package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.searchOptions.RestaurantSearchOptions;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

public interface IRestaurantService {

    Response addRestaurant(Restaurant obj);

    List<String> getAllRestaurant();

    List<String> getNotVerifiedRestaurant();

    List<String> getVerifiedRestaurant();

    List getRestaurantDataByRestaurantName(String restaurantName);

    List getAllRestaurantDataByRestaurant_ID(int restaurant_ID);

    List searchRestaurants(RestaurantSearchOptions options);

    List getRestaurantDataByKundennummer_Favoriten(int kundennummer);

    Response updateRestaurant_Verifiziert(int id);

    Response deleteRestaurant(int id);

    Response updateRestaurantStammdaten(Restaurant restaurant);
}
