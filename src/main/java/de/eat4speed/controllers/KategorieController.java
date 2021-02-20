package de.eat4speed.controllers;


import de.eat4speed.entities.Kategorie;
import de.eat4speed.repositories.KategorieRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Kategorie")
public class KategorieController {


    @Inject
    KategorieRepository kategorieRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return kategorieRepository.listAll().toString();
    }

}
