package de.eat4speed.controllers;


import de.eat4speed.entities.Restaurant;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.services.interfaces.IRestaurantService;
import org.hibernate.annotations.Parameter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Restaurant")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantController {


    @Inject
    IRestaurantService _restaurant;

    @POST
    public Response add(Restaurant restaurant) {
        return _restaurant.addRestaurant(restaurant);
    }


    @PUT
    @Path("updateVerifiziert/{id}")
    public Response updateRestaurant_Verifiziert(@PathParam("id") int id) {
        return _restaurant.updateRestaurant_Verifiziert(id);
    }

    @GET
    @Path("{selection}")
    public List getAllRestaurant(@PathParam("selection") String restaurantSelectionVerifizierung) {
        List restaurantData = null;

        switch (restaurantSelectionVerifizierung) {
            case "ALL":
                restaurantData = _restaurant.getAllRestaurant();
                System.out.println("Case All");
                break;
            case "VERIFIED":
                restaurantData = _restaurant.getVerifiedRestaurant();
                break;
            case "NOT_VERIFIED":
                restaurantData = _restaurant.getNotVerifiedRestaurant();
                break;
        }
        return restaurantData;
    }

    @GET
    @Path("getRestaurantDataByRestaurantName/{name}")
    public List getRestaurantDataByRestaurantName(@PathParam("name") String restaurantName) {
        return _restaurant.getRestaurantDataByRestaurantName(restaurantName);
    }

    @GET
    @Path("getAllRestaurantDataByRestaurant_ID/{id}")
    public List getAllRestaurantDataByRestaurant_ID(@PathParam("id") int restaurant_ID)
    {
        return _restaurant.getAllRestaurantDataByRestaurant_ID(restaurant_ID);
    }

    @DELETE
    @Path("{id}")
    public Response deleteRestaurant(@PathParam("id") int id)
    {
        return _restaurant.deleteRestaurant(id);
    }




}
