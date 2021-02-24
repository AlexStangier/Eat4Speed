package de.eat4speed.controllers;


import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;
import de.eat4speed.repositories.FahrzeugRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Benachrichtigung_Fahrer")
public class Benachrichtigung_FahrerController {


    @Inject
    Benachrichtigung_FahrerRepository benachrichtigung_FahrerRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return benachrichtigung_FahrerRepository.listAll().toString();
    }

}
