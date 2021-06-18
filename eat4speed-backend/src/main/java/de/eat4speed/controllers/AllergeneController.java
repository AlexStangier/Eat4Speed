package de.eat4speed.controllers;


import de.eat4speed.entities.Allergene;
import de.eat4speed.repositories.AllergeneRepository;
import de.eat4speed.repositories.FahrzeugRepository;
import de.eat4speed.services.interfaces.IAllergeneService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Allergene")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AllergeneController {


    @Inject
    IAllergeneService _allergene;

    @GET
    public List get(){
        return _allergene.listAll();
    }

    @POST
    public Response add(Allergene allergene)
    {
        return _allergene.addAllergene(allergene);
    }

}
