package de.eat4speed.controllers;


import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.RestaurantRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/Restaurant")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantController {


    @Inject
    RestaurantRepository restaurantRepository;

    @POST
    public Response add(Restaurant restaurant)
    {
        restaurantRepository.addRestaurant(restaurant);

        return Response.status(Response.Status.CREATED).entity(restaurant).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return restaurantRepository.listAll().toString();
    }

}
