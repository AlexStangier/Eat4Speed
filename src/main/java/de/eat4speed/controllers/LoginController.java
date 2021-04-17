package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.services.interfaces.IBenutzerService;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Login")
public class LoginController {

    @Inject
    IBenutzerService _benutzer;

    @POST
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    public Response checkAdminCredentials(Benutzer requestedUser) {
        return _benutzer.checkCredentials(requestedUser);
    }
}
