package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht_Allergene;
import de.eat4speed.services.interfaces.IGericht_AllergeneService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @GET
    @Path("getGericht_AllergeneByGericht_ID/{id}")
    public List getGericht_AllergeneByGericht_ID(@PathParam("id") int id)
    {
        return gericht_allergeneService.getGericht_AllergeneByGericht_ID(id);
    }

    @DELETE
    @Path("deleteGerichtAllergeneByGerichtID/{id}")
    public Response deleteGerichtAllergeneByGerichtID(@PathParam("id") int id)
    {
        gericht_allergeneService.deleteGerichtAllergeneByGerichtID(id);

        return Response.status(Response.Status.OK).build();
    }

}
