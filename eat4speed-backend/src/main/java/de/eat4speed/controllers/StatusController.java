package de.eat4speed.controllers;


import de.eat4speed.entities.Status;
import de.eat4speed.repositories.StatusRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Status")
public class StatusController {


    @Inject
    StatusRepository statusRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return statusRepository.listAll().toString();
    }

}
