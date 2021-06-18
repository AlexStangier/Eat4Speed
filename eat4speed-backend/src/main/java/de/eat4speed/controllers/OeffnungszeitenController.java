package de.eat4speed.controllers;


import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.services.interfaces.IOeffnungszeitenService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Oeffnungszeiten")
public class OeffnungszeitenController {

    @Inject
    IOeffnungszeitenService oeffnungszeitenService;

    @POST
    @Path("setArbeitstag")
    public Response add(Oeffnungszeiten zeit){
        return oeffnungszeitenService.setArbeitstag(zeit);
    }

    @PUT
    @Path("updateArbeitstag")
    public Response updateArbeitstag(Oeffnungszeiten zeit) {
        return oeffnungszeitenService.updateArbeitstag(zeit);
    }

    @GET
    @Path("/getAllZeiten/{id}")
    public List getAllZeiten(@PathParam("id") int restaurant_id) {
        return oeffnungszeitenService.getAllZeiten(restaurant_id);
    }

}
