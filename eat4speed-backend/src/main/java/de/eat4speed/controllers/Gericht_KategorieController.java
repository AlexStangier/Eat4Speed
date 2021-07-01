package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht_Kategorie;
import de.eat4speed.services.interfaces.IGericht_KategorieService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Gericht_Kategorie")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Gericht_KategorieController {


    @Inject
    IGericht_KategorieService gericht_kategorieService;

    @POST
    public Response add(Gericht_Kategorie gericht_kategorie)
    {
        return gericht_kategorieService.addGericht_Kategorie(gericht_kategorie);
    }

    @GET
    @Path("getGericht_KategorieByGericht_ID/{id}")
    public List getGericht_KategorieByGericht_ID(@PathParam("id") int id)
    {
        return gericht_kategorieService.getGericht_KategorieByGericht_ID(id);
    }

    @DELETE
    @Path("deleteGerichtKategorieByGerichtID/{id}")
    @RolesAllowed("restaurant")
    public Response deleteGerichtKategorieByGerichtID(@PathParam("id") int id)
    {
        gericht_kategorieService.deleteGerichtKategorieByGerichtID(id);

        return Response.status(Response.Status.OK).build();
    }

}
