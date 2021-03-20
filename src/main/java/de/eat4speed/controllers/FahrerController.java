package de.eat4speed.controllers;


import de.eat4speed.entities.Fahrer;
import de.eat4speed.repositories.FahrerRepository;
import de.eat4speed.services.FahrerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Fahrer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FahrerController {


    @Inject
    FahrerRepository fahrerRepository;

    @POST
    public Response add(Fahrer fahrer)
    {
        fahrerRepository.addFahrer(fahrer);

        return Response.status(Response.Status.CREATED).entity(fahrer).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return fahrerRepository.listAll().toString();
    }

}
