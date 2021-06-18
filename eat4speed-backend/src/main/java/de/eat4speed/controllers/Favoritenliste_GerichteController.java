package de.eat4speed.controllers;

import de.eat4speed.entities.Favoritenliste_Gerichte;
import de.eat4speed.services.interfaces.IFavoritenliste_GerichteService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Favoritenliste_Gerichte")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Favoritenliste_GerichteController {


    @Inject
    IFavoritenliste_GerichteService favoritenliste_gerichteService;

    @POST
    public Response add(Favoritenliste_Gerichte favoritenliste_gerichte)
    {
        favoritenliste_gerichteService.addFavoritenliste_Gerichte(favoritenliste_gerichte);

        return Response.status(Response.Status.CREATED).entity(favoritenliste_gerichte).build();
    }

    @DELETE
    @Path("remove/{kundennummer}/{gericht_ID}")
    @RolesAllowed("kunde")
    public Response removeFavoritenliste_Gerichte(@PathParam("gericht_ID") int gericht_id,@PathParam("kundennummer") int kundennummer)
    {
        return favoritenliste_gerichteService.removeFavoritenliste_Gerichte(gericht_id,kundennummer);
    }

}
