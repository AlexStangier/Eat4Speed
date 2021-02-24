package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.BenutzerService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Benutzer")
public class BenutzerController {

    @Inject
    BenutzerService _benutzer;

    @Inject
    BenutzerRepository benutzerRepository;

    @POST
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response add(Benutzer benutzer)
    {
        benutzerRepository.persist(benutzer);

        return Response.status(Response.Status.CREATED).entity(benutzer).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){ return benutzerRepository.listAll().toString(); }

    @GET
    @Path("{benutzerName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("benutzerName") String benutzername) {

        Benutzer benutzer = new Benutzer();
        benutzer.setBenutzername("testBenutzer9");
        benutzer.setE_Mail_Addresse("test@4.com");
        benutzer.setRolle("test");
        benutzer.setPasswort("testPW");
        benutzer.setPaypal_Account("ppAcc");

        benutzerRepository.addBenutzer(benutzer);

        return benutzerRepository.findByBenutzerName(benutzername).toString();
    }

}
