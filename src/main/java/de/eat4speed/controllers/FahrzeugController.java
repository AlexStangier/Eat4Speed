package de.eat4speed.controllers;


import de.eat4speed.entities.Auftrag;
import de.eat4speed.repositories.AuftragRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Fahrzeug")
public class FahrzeugController {


    @Inject
    FahrzeugRepository fahrzeugRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return fahrzeugRepository.listAll().toString();
    }

}
