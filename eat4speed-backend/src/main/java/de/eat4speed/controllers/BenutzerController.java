package de.eat4speed.controllers;

import de.eat4speed.dto.BenutzerDto;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("register")
    public Response add(BenutzerDto benutzer) {
        return _benutzer.addBenutzer(benutzer);
    }

    @POST
    @PermitAll
    @Path("getIdByEmail")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getIdByEmail(UserEmailDto email) {
        return _benutzer.getEmailById(email);
    }

    @GET
    @RolesAllowed("restaurant")
    @Path("getBenutzerByLogin/{email}")
    public List getBenutzerByLogin(@PathParam("email") String email) {
        return _benutzer.getBenutzerByLogin(email);
    }

    @PUT
    @RolesAllowed({"kunde","restaurant"})
    @Path("updateBenutzerRestaurant")
    public Response updateBenutzerRestaurant(Benutzer benutzer) {
        return _benutzer.updateBenutzerRestaurant(benutzer);
    }

    @GET
    @RolesAllowed("kunde")
    @Path("getBenutzerKundeEinstellungenByLogin/{email}")
    public List getBenutzerKundeEinstellungenByLogin(@PathParam("email") String email) {return _benutzer.getBenutzerKundeEinstellungenByLogin(email);}

    @GET
    @Path("getKundennummerByBenutzername/{username}")
    public List getKundennummerByBenutzername(@PathParam("username") String username)
    {
        return _benutzer.getKundennummerByBenutzername(username);
    }

    @GET
    @RolesAllowed({"restaurant","admin"})
    @Path("getRestaurant_IDByBenutzername/{username}")
    public List getRestaurant_IDByBenutzername(@PathParam("username") String username)
    {
        return _benutzer.getRestaurant_IDByBenutzername(username);
    }

    @GET
    @RolesAllowed("fahrer")
    @Path("getFahrernummerByBenutzername/{username}")
    public List getFahrernummerByBenutzername(@PathParam("username") String username)
    {
        return benutzerRepository.getFahrernummerByBenutzername(username);
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

    @GET
    @Path("checkIfBenutzerIsGeloescht/{email}")
    public List checkIfBenutzerIsGeloescht(@PathParam("email") String email)
    {
        return benutzerRepository.checkIfBenutzerIsGeloescht(email);
    }

    @GET
    @Path("checkIfBenutzerIsBlacklist/{email}")
    public List checkIfBenutzerIsBlacklist(@PathParam("email") String email)
    {
        return benutzerRepository.checkIfBenutzerIsBlacklist(email);
    }

    @GET
    @Path("getBenutzer_IDByBenutzername/{benutzername}")
    public List getBenutzer_IDByBenutzername(@PathParam("benutzername") String benutzername)
    {
        return benutzerRepository.getBenutzer_IDByBenutzername(benutzername);
    }

    @GET
    @Path("getBenutzer_IDByEmail/{email}")
    public List getBenutzer_IDByEmail(@PathParam("email") String email)
    {
        return benutzerRepository.getBenutzer_IDByEmail(email);
    }
}
