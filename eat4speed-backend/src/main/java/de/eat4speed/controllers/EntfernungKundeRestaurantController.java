package de.eat4speed.controllers;


import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.repositories.EntfernungKundeRestaurantRepository;
import de.eat4speed.services.interfaces.IEntfernungKundeRestaurantService;

import javax.annotation.security.RolesAllowed;
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

    @Inject
    EntfernungKundeRestaurantRepository entfernungKundeRestaurantRepository;

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

    @GET
    @Path("/getEntfernungByKundennummer/{kundennummer}")
    public List getEntfernungByKundennummer(@PathParam("kundennummer") int kundennummer)
    {
        return entfernungKundeRestaurantRepository.getEntfernungByKundennummer(kundennummer);
    }

    @DELETE
    @Path("/deleteEntfernungByKundennummer/{kundennummer}")
    @RolesAllowed("kunde")
    public Response deleteEntfernungByKundennummer(@PathParam("kundennummer") int kundennummer)
    {
        return entfernungKundeRestaurantService.deleteEntfernungByKundennummer(kundennummer);
    }

    @DELETE
    @Path("/deleteEntfernungByRestaurant_ID/{restaurant_ID}")
    @RolesAllowed("restaurant")
    public Response deleteEntfernungByRestaurant_ID(@PathParam("restaurant_ID") int restaurant_ID)
    {
        return entfernungKundeRestaurantService.deleteEntfernungByRestaurant_ID(restaurant_ID);
    }

}
