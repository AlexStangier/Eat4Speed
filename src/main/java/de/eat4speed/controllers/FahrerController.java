package de.eat4speed.controllers;


import de.eat4speed.entities.Fahrer;
import de.eat4speed.entities.Fahrzeug;
import de.eat4speed.services.interfaces.IFahrerService;
import org.hibernate.annotations.Parameter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/Fahrer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FahrerController {


    @Inject
    IFahrerService _fahrer;

    @POST
    public Response add(Fahrer fahrer)
    {
        return _fahrer.addFahrer(fahrer);
    }

    @PUT
    @Path("{id}")
    public Response updateFahrer_Fahrzeug_Id(@PathParam("id") int id, Fahrzeug fahrzeug)
    {
        return _fahrer.updateFahrer_Fahrzeug_Id(id, fahrzeug);
    }

    @GET
    @Path("{selection}")
    public List getAllFahrer(@PathParam("selection") String fahrerSelectionVerifizierung)
    {
        List fahrerData = null;

        switch(fahrerSelectionVerifizierung)
        {
            case "ALL":
                fahrerData = _fahrer.getAllFahrer();
                System.out.println("Case All");
                break;
            case "VERIFIED":
                fahrerData = null;
                break;
            case "NOT_VERIFIED":
                fahrerData = null;
                break;
        }
        return fahrerData;
    }



}
