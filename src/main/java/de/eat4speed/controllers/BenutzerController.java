package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.services.interfaces.IBenutzerService;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Benutzer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BenutzerController {

    @Inject
    IBenutzerService _benutzer;

    @POST
    public Response add(Benutzer benutzer) {
        return _benutzer.addBenutzer(benutzer);
    }

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return _benutzer.listAll();
    }

}
