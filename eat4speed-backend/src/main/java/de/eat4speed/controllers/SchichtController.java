package de.eat4speed.controllers;

import de.eat4speed.entities.Schicht;
import de.eat4speed.services.interfaces.ISchichtService;

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
    public Response add(Schicht zeit){
        schichtplanService.setSchicht(zeit);

        return Response.status(Response.Status.CREATED).entity(zeit).build();
    };

    //@PUT
    //@Path("updateArbeitstag")
    //public Response updateArbeitstag(Oeffnungszeiten zeit)
    //{
      //  return oeffnungszeitenService.updateArbeitstag(zeit);
    //}

    @GET
    @Path("/getAllZeiten/{id}")
    public List getAllZeiten(@PathParam("id") int schicht_id){

        return schichtplanService.getAllZeiten(schicht_id);
    }

}
