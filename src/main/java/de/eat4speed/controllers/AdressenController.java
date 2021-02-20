package de.eat4speed.controllers;


import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Adressen")
public class AdressenController {


    @Inject
    AdressenRepository adressenRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return adressenRepository.listAll().toString();
    }

}
