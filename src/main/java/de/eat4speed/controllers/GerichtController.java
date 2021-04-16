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
    @Path("addGericht")
    public Response add(Gericht gericht)
    {
        gerichtService.addGericht(gericht);

        return Response.status(Response.Status.CREATED).entity(gericht).build();
    }

    @PUT
    @Path("updateGerichtAllData")
    public Response updateGerichtAllData(Gericht gericht)
    {
        return gerichtService.updateGerichtAllData(gericht);
    }

    @GET
    @Path("{id}")
    public Gericht getGerichtByGericht_ID(@PathParam("id") int gericht_ID)
    {
        return gerichtService.getGerichtByGerichtID(gericht_ID);
    }

    @GET
    @Path("/getAllGerichtDataRestaurantSpeisekarte/{id}")
    public List getAllGerichtDataRestaurantSpeisekarte(@PathParam("id") int restaurant_id){

        return gerichtService.getAllGerichteDataRestaurantSpeisekarte(restaurant_id);
    }

    @GET
    @Path("/getAllGetraenkDataRestaurantSpeisekarte/{id}")
    public List getAllGetraenkDataRestaurantSpeisekarte(@PathParam("id") int restaurant_id){

        return gerichtService.getAllGetraenkeDataRestaurantSpeiseKarte(restaurant_id);
    }

    @DELETE
    @Path("{id}")
    public Response deleteGericht(@PathParam("id") int gericht_ID)
    {
        return gerichtService.deleteGericht(gericht_ID);
    }

}
