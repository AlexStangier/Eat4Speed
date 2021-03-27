package de.eat4speed.controllers;


import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.GerichtRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/Gericht")
public class GerichtController {


    @Inject
    GerichtRepository gerichtRepository;

    @POST
    public Response add(Gericht gericht)
    {
        gerichtRepository.addGericht(gericht);

        return Response.status(Response.Status.CREATED).entity(gericht).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return gerichtRepository.listAll().toString();
    }

}
