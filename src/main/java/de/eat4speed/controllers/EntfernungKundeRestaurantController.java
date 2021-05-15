package de.eat4speed.controllers;


import de.eat4speed.dishAlternatives.DishAlternativesOptions;
import de.eat4speed.entities.EntfernungKundeRestaurant;
import de.eat4speed.entities.Gericht;
import de.eat4speed.services.interfaces.IEntfernungKundeRestaurantService;
import de.eat4speed.services.interfaces.IGerichtService;

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

}
