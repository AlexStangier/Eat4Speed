package de.eat4speed.controllers;


import de.eat4speed.entities.Oeffnungszeiten;
import de.eat4speed.repositories.OeffnungszeitenRepository;
import de.eat4speed.services.interfaces.IOeffnungszeitenService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

    @Inject
    OeffnungszeitenRepository oeffnungszeitenRepository;

    @POST
    @Path("setArbeitstag")
    @RolesAllowed("restaurant")
    public Response add(Oeffnungszeiten zeit){
        return oeffnungszeitenService.setArbeitstag(zeit);
    }

    @PUT
    @Path("updateArbeitstag")
    @RolesAllowed("restaurant")
    public Response updateArbeitstag(Oeffnungszeiten zeit) {
        return oeffnungszeitenService.updateArbeitstag(zeit);
    }

    @GET
    @RolesAllowed("restaurant")
    @Path("/getAllZeiten/{id}")
    public List getAllZeiten(@PathParam("id") int restaurant_id) {
        return oeffnungszeitenService.getAllZeiten(restaurant_id);
    }

    @GET
    @RolesAllowed("kunde","restaurant")
    @Path("/getAllZeitenWochentag/{id}/{wochentag}")
    public List getAllZeitenWochentag(@PathParam("id") int restaurant_id, @PathParam("wochentag") int wochentag) {

        String tag = "";

        switch(wochentag)
        {
            case 0: tag = "Sonntag";

                break;
            case 1: tag = "Montag";

                break;
            case 2: tag = "Dienstag";

                break;
            case 3: tag = "Mittwoch";

                break;
            case 4: tag = "Donnerstag";

                break;
            case 5: tag = "Freitag";

                break;
            case 6: tag = "Samstag";

                break;
        }

        return oeffnungszeitenRepository.getAllZeitenWochentag(restaurant_id, tag);
    }

    @GET
    @Path("getAllZeitenRestaurant_ID/{id}")
    public List getAllZeitenRestaurant_ID(@PathParam("id") int restaurant_ID)
    {
        return oeffnungszeitenRepository.getAllZeitenRestaurant_ID(restaurant_ID);
    }

}
