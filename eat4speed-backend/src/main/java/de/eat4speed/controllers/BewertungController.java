package de.eat4speed.controllers;

import de.eat4speed.repositories.BewertungRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Bewertung")
public class BewertungController {

    @Inject
    BewertungRepository bewertungRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return bewertungRepository.listAll().toString();
    }
}
