package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.GerichtRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Gericht")
public class GerichtController {


    @Inject
    GerichtRepository gerichtRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return gerichtRepository.listAll().toString();
    }

}
