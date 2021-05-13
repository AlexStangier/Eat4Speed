package de.eat4speed.controllers;

import de.eat4speed.entities.Schicht;
import de.eat4speed.services.interfaces.ISchichtService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Schicht")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class SchichtController {

    @Inject
    ISchichtService _schicht;

    @POST
    public Response add(Schicht schicht)
    {
        return _schicht.addSchicht(schicht);
    }

    @GET
    @Path("{id}")
    public List getAllSchichtFromID(@PathParam("id") int id)
    {
        return _schicht.getAllSchichtFromID(id);
    }

    @GET
    @Path("{fahrernummer}/Heute")
    public Schicht getSchichtHeute(@PathParam("fahrernummer") int fahrernummer)
    {
        return _schicht.getSchichtHeute(fahrernummer);
    }

    @DELETE
    @Path("{fahrernummer}")
    public Response deleteSchicht(@PathParam("fahrernummer") int id)
    {
        return _schicht.deleteSchicht(id);
    }

}
