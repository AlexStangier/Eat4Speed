package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;
import de.eat4speed.repositories.FahrzeugRepository;
import de.eat4speed.services.interfaces.IBenachrichtigung_FahrerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/Benachrichtigung_Fahrer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Benachrichtigung_FahrerController {


    @Inject
    IBenachrichtigung_FahrerService benachrichtigung_fahrerService;

    @PUT
    @Path("/markAsGelesen/{benachrichtigungs_ID}")
    public void markAsGelesen(@PathParam("benachrichtigungs_ID") int benachrichtigungs_ID)
    {
        benachrichtigung_fahrerService.markAsGelesen(benachrichtigungs_ID);
    }

    @GET
    @Path("/getAllBenachrichtigungFahrerUngelesen/{fahrernummer}")
    public List getAllBenachrichtigung_Fahrer_ungelesen(@PathParam("fahrernummer") int fahrernummer)
    {
        return benachrichtigung_fahrerService.getAllBenachrichtigung_Fahrer_ungelesen(fahrernummer);
    }

}
