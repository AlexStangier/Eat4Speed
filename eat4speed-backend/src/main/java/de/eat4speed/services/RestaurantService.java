package de.eat4speed.services;

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

    private RestaurantRepository restaurantRepository;

    @Inject
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Response addRestaurant(Restaurant restaurant)
    {
        restaurantRepository.addRestaurant(restaurant);

        return Response.status(Response.Status.CREATED).entity(restaurant).build();
    }

    @Override
    public List getAllRestaurant()
    {
        return restaurantRepository.getAllRestaurant();
    }

    @Override
    public List getNotVerifiedRestaurant()
    {
        return restaurantRepository.getNotVerifiedRestaurant();
    }

    @Override
    public List getVerifiedRestaurant()
    {
        return restaurantRepository.getVerifiedRestaurant();
    }

    @Override
    public List getRestaurantDataByRestaurantName(String restaurantName)
    {
        return restaurantRepository.getRestaurantDataByRestaurantName(restaurantName);
    }

    @Override
    public List getAllRestaurantDataByRestaurant_ID(int restaurant_ID)
    {
        return restaurantRepository.getAllRestaurantDataByRestaurant_ID(restaurant_ID);
    }

    @Override
    public Response updateRestaurant_Verifiziert(int id)
    {
        restaurantRepository.updateRestaurant_Verifiziert(id);

        return Response.status(Response.Status.OK).build();
    }

    @Override
    public Response deleteRestaurant(int id)
    {
        restaurantRepository.deleteRestaurant(id);

        return Response.status(Response.Status.OK).build();
    }

}
