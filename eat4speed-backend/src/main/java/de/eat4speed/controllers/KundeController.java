package de.eat4speed.controllers;


import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Kunde;
import de.eat4speed.entities.Restaurant;
import de.eat4speed.repositories.KundeRepository;
import de.eat4speed.services.interfaces.IKundeService;
import de.eat4speed.services.interfaces.IRestaurantService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/Kunde")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KundeController {

    @Inject
    IKundeService _kunde;

    @Inject
    KundeRepository kundeRepository;

    @POST
    public Response add(Kunde kunde)
    {
        kundeRepository.addKunde(kunde);

        return Response.status(Response.Status.CREATED).entity(kunde).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return kundeRepository.listAll().toString();
    }

    @PUT
    @Path("updateKundeEinstellungen")
    @RolesAllowed("kunde")
    public Response updateKundeEinstellungen(Kunde kunde) {
        return _kunde.updateKundeEinstellungen(kunde);
    }

}
