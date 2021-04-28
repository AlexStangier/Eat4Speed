package de.eat4speed.controllers;


import de.eat4speed.entities.Bestellhistorie;
import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.BestellhistorieRepository;
import de.eat4speed.services.BestellhistorieService;
import de.eat4speed.services.interfaces.IBestellhistorieService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Bestellhistorie")
public class BestellhistorieController {

    @Inject
    IBestellhistorieService bestellhistorieService;

    @POST
    @Path("addBestellhistorie")
    public Response add(Bestellhistorie bestellhistorie)
    {
        return bestellhistorieService.addBestellhistorie(bestellhistorie);
    }


}
