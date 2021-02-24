package de.eat4speed.controllers;


import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Kunde;
import de.eat4speed.repositories.KundeRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/Kunde")
public class KundeController {


    @Inject
    KundeRepository kundeRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return kundeRepository.listAll().toString();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Kunde kunde)
    {
        kundeRepository.persist(kunde);

        return Response.status(Response.Status.CREATED).entity(kunde).build();
    }

}
