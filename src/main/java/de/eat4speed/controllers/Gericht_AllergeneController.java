package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht_Allergene;
import de.eat4speed.services.interfaces.IGericht_AllergeneService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Gericht_Allergene")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Gericht_AllergeneController {


    @Inject
    IGericht_AllergeneService gericht_allergeneService;

    @POST
    public Response add(Gericht_Allergene gericht_allergene)
    {
        return gericht_allergeneService.addGericht_Allergene(gericht_allergene);
    }

}
