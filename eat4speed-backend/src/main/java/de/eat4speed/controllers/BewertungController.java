package de.eat4speed.controllers;

import de.eat4speed.entities.Bewertung;
import de.eat4speed.repositories.BewertungRepository;
import de.eat4speed.services.interfaces.IBewertungService;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Bewertung")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BewertungController {

    @Inject
    IBewertungService bewertungService;

    @PUT
    public Response addBewertung(Bewertung bewertung) {
        return bewertungService.addOrUpdateBewertung(bewertung);
    }

    @GET
    @Path("/getBewertungDataByKundennummerAndRestaurant_ID/{kundennummer}/{restaurant_ID}")
    public List getBewertungDataByKundennummerAndRestaurant_ID(@PathParam("kundennummer") int kundennummer, @PathParam("restaurant_ID") int restaurant_ID) {
        return bewertungService.getBewertungDataByKundennummerAndRestaurant_ID(kundennummer, restaurant_ID);
    }

    @GET
    @Path("/getBewertungDataByRestaurant_ID/{restaurant_ID}")
    public List getBewertungDataByRestaurant_ID(@PathParam("restaurant_ID") int restaurant_ID) {
        return bewertungService.getBewertungDataByRestaurant_ID(restaurant_ID);
    }

    @GET
    @Path("/getAverageBewertungAndCountBewertungByRestaurant_ID/{restaurant_ID}")
    public List getAverageBewertungAndCountBewertungByRestaurant_ID(@PathParam("restaurant_ID") int restaurant_ID) {
        return bewertungService.getAverageBewertungAndCountBewertungByRestaurant_ID(restaurant_ID);
    }
}
