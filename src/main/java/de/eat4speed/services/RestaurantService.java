package de.eat4speed.services;

import de.eat4speed.entities.Adressen;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.RestaurantRepository;
import de.eat4speed.services.interfaces.IRestaurantService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class RestaurantService implements IRestaurantService {

    private RestaurantRepository _restaurant;

    @Inject
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this._restaurant = restaurantRepository;
    }

    @Override
    public Response addRestaurant(Restaurant restaurant) {
        _restaurant.addRestaurant(restaurant);

        return Response.status(Response.Status.CREATED).entity(restaurant).build();
    }

    @Override
    public List getAllRestaurant() {
        return _restaurant.getAllRestaurant();
    }

    @Override
    public List getNotVerifiedRestaurant() {
        return _restaurant.getNotVerifiedRestaurant();
    }

    @Override
    public List getVerifiedRestaurant() {
        return _restaurant.getVerifiedRestaurant();
    }

    @Override
    public List getRestaurantDataByRestaurantName(String restaurantName) {
        return _restaurant.getRestaurantDataByRestaurantName(restaurantName);
    }

    @Override
    public List getAllRestaurantDataByRestaurant_ID(int restaurant_ID) {
        return _restaurant.getAllRestaurantDataByRestaurant_ID(restaurant_ID);
    }

    @Override
    public Response updateRestaurant_Verifiziert(int id) {
        _restaurant.updateRestaurant_Verifiziert(id);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response deleteRestaurant(int id) {
        _restaurant.deleteRestaurant(id);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response updateRestaurantStammdaten(Restaurant restaurant) {
        _restaurant.updateRestaurantStammdaten(restaurant);
        return Response.status(Response.Status.OK).entity(restaurant).build();
    }

}
