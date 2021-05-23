package de.eat4speed.controllers;


import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.repositories.FahrzeugRepository;
import de.eat4speed.services.FahrzeugService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/Fahrzeug")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FahrzeugController {


    @Inject
    FahrzeugRepository fahrzeugRepository;

    @POST
    public Response add(Fahrzeug fahrzeug)
    {
        fahrzeugRepository.addFahrzeug(fahrzeug);

        return Response.status(Response.Status.CREATED).entity(fahrzeug).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return fahrzeugRepository.listAll().toString();
    }

}
