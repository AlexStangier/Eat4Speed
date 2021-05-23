package de.eat4speed.controllers;


import de.eat4speed.entities.Rechnung;
import de.eat4speed.repositories.RechnungRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Rechnung")
public class RechnungController {


    @Inject
    RechnungRepository rechnungRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return rechnungRepository.listAll().toString();
    }

}
