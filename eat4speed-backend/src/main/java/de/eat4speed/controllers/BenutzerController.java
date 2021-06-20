package de.eat4speed.controllers;

import de.eat4speed.dto.UserEmailDto;
import de.eat4speed.entities.Benutzer;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.services.interfaces.IBenutzerService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Benutzer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BenutzerController {

    @Inject
    IBenutzerService _benutzer;

    @Inject
    BenutzerRepository benutzerRepository;

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

    @POST
    @PermitAll
    @Path("getIdByEmail")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getIdByEmail(UserEmailDto email) {
        return _benutzer.getEmailById(email);
    }

    @GET
    @Path("getBenutzerByLogin/{email}")
    public List getBenutzerByLogin(@PathParam("email") String email) {
        return _benutzer.getBenutzerByLogin(email);
    }

    @PUT
    @Path("updateBenutzerRestaurant")
    public Response updateBenutzerRestaurant(Benutzer benutzer) {
        return _benutzer.updateBenutzerRestaurant(benutzer);
    }

    @GET
    @Path("getBenutzerKundeEinstellungenByLogin/{email}")
    public List getBenutzerKundeEinstellungenByLogin(@PathParam("email") String email) {return _benutzer.getBenutzerKundeEinstellungenByLogin(email);}

    @GET
    @Path("getKundennummerByBenutzername/{username}")
    public List getKundennummerByBenutzername(@PathParam("username") String username)
    {
        return _benutzer.getKundennummerByBenutzername(username);
    }

    @GET
    @Path("getRestaurant_IDByBenutzername/{username}")
    public List getRestaurant_IDByBenutzername(@PathParam("username") String username)
    {
        return _benutzer.getRestaurant_IDByBenutzername(username);
    }

    @GET
    @Path("getRoleById/{id}")
    public String getRoleById(@PathParam("id") long id){
        return _benutzer.getRoleById(id);
    }

    @GET
    @Path("getRoleByEmail/{email}")
    public String getRoleById(@PathParam("email") String email){
        return benutzerRepository.getRoleByEmail(email);
    }

    @PUT
    @Path("deleteBenutzerByEmail/{email}")
    public Response deleteBenutzerByEmail(@PathParam("email") String email)
    {
        benutzerRepository.deleteBenutzerByEmail(email);
        return Response.status(Response.Status.OK).build();
    }
}
