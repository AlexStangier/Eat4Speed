package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht_Allergene;
import de.eat4speed.repositories.Gericht_AllergeneRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Gericht_Allergene")
public class Gericht_AllergeneController {


    @Inject
    Gericht_AllergeneRepository gericht_allergeneRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return gericht_allergeneRepository.listAll().toString();
    }

}
