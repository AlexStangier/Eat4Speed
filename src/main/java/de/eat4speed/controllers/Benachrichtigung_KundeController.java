package de.eat4speed.controllers;


import de.eat4speed.repositories.Benachrichtigung_KundeRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Benachrichtigung_Kunde")
public class Benachrichtigung_KundeController {


    @Inject
    Benachrichtigung_KundeRepository benachrichtigung_KundeRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return benachrichtigung_KundeRepository.listAll().toString();
    }

}
