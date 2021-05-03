package de.eat4speed.services.interfaces;

import de.eat4speed.entities.Restaurant;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

public interface IRestaurantService {

    Response addRestaurant(Restaurant obj);

    List<String> getAllRestaurant();

    List<String> getNotVerifiedRestaurant();

    List<String> getVerifiedRestaurant();

    List getRestaurantDataByRestaurantName(String restaurantName);

    public List getAllRestaurantDataByRestaurant_ID(int restaurant_ID);

    Response updateRestaurant_Verifiziert(int id);

    Response deleteRestaurant(int id);
}
