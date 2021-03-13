package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.BenutzerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/Benutzer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BenutzerController {

    @Inject
    BenutzerService _benutzer;

    @Inject
    BenutzerRepository benutzerRepository;

    @POST
    public Response add(Benutzer benutzer)
    {
        benutzerRepository.addBenutzer(benutzer);

        return Response.status(Response.Status.CREATED).entity(benutzer).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){ return benutzerRepository.listAll().toString(); }


}
