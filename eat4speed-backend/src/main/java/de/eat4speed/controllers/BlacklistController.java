package de.eat4speed.controllers;

import de.eat4speed.entities.Benutzer;
import de.eat4speed.entities.Blacklist;
import de.eat4speed.repositories.BenutzerRepository;
import de.eat4speed.repositories.BlacklistRepository;
import de.eat4speed.services.BenutzerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Blacklist")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlacklistController {

    @Inject
    BlacklistRepository blacklistRepository;

    @POST
    public Response add(Blacklist blacklist)
    {
        blacklistRepository.addBlacklist(blacklist);

        return Response.status(Response.Status.CREATED).entity(blacklist).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){ return blacklistRepository.listAll().toString(); }


}
