package de.eat4speed.controllers;


import de.eat4speed.entities.Restaurant_Zeiten;
import de.eat4speed.repositories.Restaurant_ZeitenRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Restaurant_Zeiten")
public class Restaurant_ZeitenController {


    @Inject
    Restaurant_ZeitenRepository restaurant_zeitenRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return restaurant_zeitenRepository.listAll().toString();
    }

}
