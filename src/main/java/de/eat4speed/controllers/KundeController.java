package de.eat4speed.controllers;


import de.eat4speed.entities.Kunde;
import de.eat4speed.repositories.KundeRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Kunde")
public class KundeController {


    @Inject
    KundeRepository kundeRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return kundeRepository.listAll().toString();
    }

}
