package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Favoritenliste_Gerichte;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.Favoritenliste_GerichteRepository;
import de.eat4speed.services.BenutzerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Favoritenliste_Gerichte")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Favoritenliste_GerichteController {

    @Inject
    Favoritenliste_GerichteRepository favoritenliste_gerichteRepository;

    @POST
    public Response add(Favoritenliste_Gerichte favoritenliste_gerichte)
    {
        favoritenliste_gerichteRepository.addFavoritenliste_Gerichte(favoritenliste_gerichte);

        return Response.status(Response.Status.CREATED).entity(favoritenliste_gerichte).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){ return favoritenliste_gerichteRepository.listAll().toString(); }


}
