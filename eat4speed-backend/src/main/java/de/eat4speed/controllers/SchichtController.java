package de.eat4speed.controllers;

import de.eat4speed.entities.Schicht;
import de.eat4speed.services.interfaces.ISchichtService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Schichten")
public class SchichtController {


    @Inject
    ISchichtService schichtplanService;


    @POST
    @Path("/setSchicht")
    @RolesAllowed("fahrer")
    public Response add(Schicht zeit){
        schichtplanService.setSchicht(zeit);
        return Response.status(Response.Status.CREATED).entity(zeit).build();
    };

    @PUT
    @Path("/updateSchicht")
    @RolesAllowed("fahrer")
    public Response updateSchicht(Schicht zeit)
    {
        return schichtplanService.updateSchicht(zeit);
    }

    @GET
    @Path("getAmountActiveSchicht")
    @RolesAllowed("fahrer")
    public Object getAmountActiveSchicht()
    {
        return schichtplanService.getAmountActiveSchicht();
    }

    @GET
    @Path("/getSchicht/{id}")
    @RolesAllowed("fahrer")
    public List getAllZeiten(@PathParam("id") int schicht_id){

        return schichtplanService.getSchicht(schicht_id);
    }

}
