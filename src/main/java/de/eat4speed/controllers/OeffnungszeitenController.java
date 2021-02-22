package de.eat4speed.controllers;


import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.repositories.OeffnungszeitenRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Oeffnungszeiten")
public class OeffnungszeitenController {


    @Inject
    OeffnungszeitenRepository oeffnungszeitenRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return oeffnungszeitenRepository.listAll().toString();
    }

}