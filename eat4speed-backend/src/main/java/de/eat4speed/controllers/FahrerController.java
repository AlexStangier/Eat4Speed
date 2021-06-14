package de.eat4speed.controllers;


import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.entities.Gericht;
import de.eat4speed.services.interfaces.IFahrerService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Fahrer")
public class FahrerController {


    @Inject
    IFahrerService _fahrer;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Fahrer fahrer)
    {
        return _fahrer.addFahrer(fahrer);
    }

    @PUT
    @Path("setPause/{id}/{pause}")
    public Response setPause(@PathParam("id") int id, @PathParam("pause") int pause)
    {
        return _fahrer.setPause(pause, id);
    }

    @PUT
    @Path("updateFahrzeugId/{id}")
    public Response updateFahrer_Fahrzeug_Id(@PathParam("id") int id, Fahrzeug fahrzeug)
    {
        return _fahrer.updateFahrer_Fahrzeug_Id(id, fahrzeug);
    }

    @PUT
    @Path("updateVerifiziert/{id}")
    public Response updateFahrer_Verifiziert(@PathParam("id") int id)
    {
        return _fahrer.updateFahrer_Verifiziert(id);
    }


    @GET
    @Path("getAll")
    @RolesAllowed("admin")
    public List getAllDrivers(){
        return _fahrer.getAllFahrer();
    }

    @GET
    @Path("get/{id}")
    public List getFahrerByID(@PathParam("id") int benutzer_ID)
    {
        return _fahrer.getFahrerByID(benutzer_ID);
    }

    @GET
    @Path("{selection}")
    @PermitAll
    public List getAllFahrer(@PathParam("selection") String fahrerSelectionVerifizierung)
    {
        List fahrerData = null;

        switch(fahrerSelectionVerifizierung)
        {
            case "ALL":
                fahrerData = _fahrer.getAllFahrer();
                break;
            case "VERIFIED":
                fahrerData = _fahrer.getVerifiedFahrer();
                break;
            case "NOT_VERIFIED":
                fahrerData = _fahrer.getNotVerifiedFahrer();
                break;
        }
        return fahrerData;
    }

    @DELETE
    @Path("{id}")
    public Response deleteFahrer(@PathParam("id") int id)
    {
        return _fahrer.deleteFahrer(id);
    }




}
