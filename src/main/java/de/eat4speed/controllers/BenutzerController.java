package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.BenutzerService;
import de.eat4speed.services.interfaces.IBenutzerService;

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
    IBenutzerService _benutzer;


    @POST
    public Response add(Benutzer benutzer) {
        return _benutzer.addBenutzer(benutzer);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return _benutzer.listAll().toString();
    }


}
