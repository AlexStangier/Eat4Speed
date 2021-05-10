package de.eat4speed.controllers;


import de.eat4speed.dishAlternatives.DishAlternativesOptions;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.GerichtRepository;
import de.eat4speed.services.GerichtService;
import de.eat4speed.services.interfaces.IGerichtService;
import org.hibernate.annotations.Parameter;

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

    @GET
    @Path("/getGerichtDataByGerichtName/{name}")
    public List getGerichtDataByGerichtName(@PathParam("name") String gerichtName)
    {
        return gerichtService.getGerichtDataByGerichtName(gerichtName);
    }

    @GET
    @Path("/getGerichtDataByGerichtKategorie/{kategorie}")
    public List getGerichtDataByGerichtKategorie(@PathParam("kategorie") String kategorie)
    {
        return gerichtService.getGerichtDataByGerichtKategorie(kategorie);
    }

    @GET
    @Path("/getGerichtDataByRestaurant_ID/{id}")
    public List getGerichtDataByRestaurant_ID(@PathParam("id") int restaurant_ID)
    {
        return gerichtService.getGerichtDataByRestaurant_ID(restaurant_ID);
    }

    @GET
    @Path("/getGerichtDataByGericht_ID/{id}")
    public List getGerichtDataByGericht_ID(@PathParam("id") int gericht_ID)
    {
        return gerichtService.getGerichtDataByGericht_ID(gericht_ID);
    }

    @GET
    @Path("/getGerichtDataByKundennummer_Favoriten/{kundennummer}")
    public List getGerichtDataByKundennummer_Favoriten(@PathParam("kundennummer") int kundennummer)
    {
        return gerichtService.getGerichtDataByKundennummer_Favoriten(kundennummer);
    }

    @POST
    @Path("/getGerichtAlternatives")
    public List getGerichtAlternatives(DishAlternativesOptions options)
    {
        System.out.println(options.getGerichtName());
        //System.out.println(options.getKategorien().get(0));
        System.out.println(options.isUseName());
        System.out.println(options.isUseKategorien());

        return gerichtService.getGerichtAlternatives(options);
    }

    @DELETE
    @Path("{id}")
    public Response deleteGericht(@PathParam("id") int gericht_ID)
    {
        return gerichtService.deleteGericht(gericht_ID);
    }

}
