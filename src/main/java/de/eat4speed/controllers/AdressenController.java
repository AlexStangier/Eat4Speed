package de.eat4speed.controllers;


import de.eat4speed.entities.Adressen;
import de.eat4speed.repositories.AdressenRepository;
import de.eat4speed.repositories.FahrzeugRepository;
import de.eat4speed.services.interfaces.IAdressenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Adressen")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdressenController {


    @Inject
    AdressenRepository adressenRepository;

    @Inject
    IAdressenService _adressen;

    @POST
    public Response add(Adressen adressen) {
        adressenRepository.addAdresse(adressen);

        return Response.status(Response.Status.CREATED).entity(adressen).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return adressenRepository.listAll().toString();
    }

    @GET
    @Path("/getAllKundeLngLat")
    public List getAllKundeLngLat() {
        return _adressen.getAllKundeLngLat();
    }

    @GET
    @Path("/getAllRestaurantLngLat")
    public List getAllRestaurantLngLat() {
        return _adressen.getAllRestaurantLngLat();
    }

    @PUT
    @Path("updateAdresse")
    public Response updateAdresse(Adressen adressen) {
        return _adressen.updateAdresse(adressen);
    }


}
