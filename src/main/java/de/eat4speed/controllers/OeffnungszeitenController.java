package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht;
import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.repositories.OeffnungszeitenRepository;
import de.eat4speed.services.OeffnungszeitenService;
import de.eat4speed.services.interfaces.IOeffnungszeitenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/Oeffnungszeiten")
public class OeffnungszeitenController {


    @Inject
    IOeffnungszeitenService oeffnungszeitenService;


    @POST
    @Path("setArbeitstag")
    public Response add(Oeffnungszeiten zeit){
        oeffnungszeitenService.setArbeitstag(zeit);

        return Response.status(Response.Status.CREATED).entity(zeit).build();
    };

}
