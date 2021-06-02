package de.eat4speed.controllers;


import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.services.interfaces.IEntfernungKundeRestaurantService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/EntfernungKundeRestaurant")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EntfernungKundeRestaurantController {


    @Inject
    IEntfernungKundeRestaurantService entfernungKundeRestaurantService;

    @POST
    public Response add(EntfernungKundeRestaurant entfernungKundeRestaurant)
    {
        return entfernungKundeRestaurantService.addEntfernungKundeRestaurant(entfernungKundeRestaurant);
    }

    @GET
    @Path("/getEntfernungByKundennummerRestaurant_ID/{kundennummer}/{restaurant_ID}")
    public List getEntfernungByKundennummerRestaurant_ID(@PathParam("kundennummer") int kundennummer, @PathParam("restaurant_ID") int restaurant_ID)
    {
        return entfernungKundeRestaurantService.getEntfernungByKundennummerRestaurant_ID(kundennummer, restaurant_ID);
    }

}
