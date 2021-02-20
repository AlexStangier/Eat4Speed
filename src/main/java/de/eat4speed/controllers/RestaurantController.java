package de.eat4speed.controllers;


import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.RestaurantRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Restaurant")
public class RestaurantController {


    @Inject
    RestaurantRepository restaurantRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return restaurantRepository.listAll().toString();
    }

}
