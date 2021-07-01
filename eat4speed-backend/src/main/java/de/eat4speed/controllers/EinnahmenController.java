package de.eat4speed.controllers;


import de.eat4speed.repositories.EinnahmenRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Einnahmen")
public class EinnahmenController {


    @Inject
    EinnahmenRepository einnahmenRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("restaurant")
    public String get(){
        return einnahmenRepository.listAll().toString();
    }

}
