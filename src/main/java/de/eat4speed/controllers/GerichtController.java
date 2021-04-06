package de.eat4speed.controllers;


import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.services.GerichtService;
import de.eat4speed.services.interfaces.IGerichtService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;

@Path("/Gericht")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GerichtController {


    @Inject
    IGerichtService gerichtService;

    @POST
    public Response add(Gericht gericht)
    {
        gerichtService.addGericht(gericht);

        return Response.status(Response.Status.CREATED).entity(gericht).build();
    }

    @GET
    @Path("/getAllGerichtDataRestaurantSpeisekarte/{id}")
    public List getAllGerichtDataRestaurantSpeisekarte(@PathParam("id") int restaurant_id){

        return gerichtService.getAllGerichteDataRestaurantSpeisekarte(restaurant_id);
    }

}
