package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Favoritenliste_Restaurants;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.Favoritenliste_RestaurantsRepository;
import de.eat4speed.services.BenutzerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Favoritenliste_Restaurants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Favoritenliste_RestaurantsController {

    @Inject
    Favoritenliste_RestaurantsRepository favoritenliste_restaurantsRepository;

    @POST
    public Response add(Favoritenliste_Restaurants favoritenliste_restaurants)
    {
        favoritenliste_restaurantsRepository.addFavoritenliste_Restaurants(favoritenliste_restaurants);

        return Response.status(Response.Status.CREATED).entity(favoritenliste_restaurants).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){ return favoritenliste_restaurantsRepository.listAll().toString(); }


}
