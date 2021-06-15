package de.eat4speed.controllers;


import de.eat4speed.dto.CategoriePreferences;
import de.eat4speed.entities.Kategorie;
import de.eat4speed.services.interfaces.IKategorieService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Kategorie")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KategorieController {


    @Inject
    IKategorieService _kategorie;

    @GET
    public List get(){
        return _kategorie.listAll();
    }

    @POST
    public Response add(Kategorie kategorie)
    {
        return _kategorie.addKategorie(kategorie);
    }

    @GET
    @Path("getPreferences/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CategoriePreferences getCustomerPreferences(@PathParam("id") Integer customerId){
        return _kategorie.getFavouriteCategorie(customerId);
    }
}
