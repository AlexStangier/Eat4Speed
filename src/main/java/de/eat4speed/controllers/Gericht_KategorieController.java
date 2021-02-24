package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht_Kategorie;
import de.eat4speed.repositories.Gericht_KategorieRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Gericht_Kategorie")
public class Gericht_KategorieController {


    @Inject
    Gericht_KategorieRepository gericht_kategorieRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return gericht_kategorieRepository.listAll().toString();
    }

}
