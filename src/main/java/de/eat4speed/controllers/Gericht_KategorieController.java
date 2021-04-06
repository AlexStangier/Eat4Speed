package de.eat4speed.controllers;


import de.eat4speed.entities.Gericht_Kategorie;
import de.eat4speed.services.interfaces.IGericht_KategorieService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Gericht_Kategorie")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Gericht_KategorieController {


    @Inject
    IGericht_KategorieService gericht_kategorieService;

    @POST
    public Response add(Gericht_Kategorie gericht_kategorie)
    {
        return gericht_kategorieService.addGericht_Kategorie(gericht_kategorie);
    }

}
