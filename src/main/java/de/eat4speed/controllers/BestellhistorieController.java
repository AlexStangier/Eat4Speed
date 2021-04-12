package de.eat4speed.controllers;


import de.eat4speed.repositories.BestellhistorieRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Bestellhistorie")
public class BestellhistorieController {

    @Inject
    BestellhistorieRepository bestellhistorieRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return bestellhistorieRepository.listAll().toString();
    }
}
