package de.eat4speed.controllers;

import de.eat4speed.services.BenutzerService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Benutzer")
public class BenutzerController {

    @Inject
    BenutzerService _benutzer;

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("id") long id) {
        return _benutzer.getById((int) id).getBenutzername();
    }

}
