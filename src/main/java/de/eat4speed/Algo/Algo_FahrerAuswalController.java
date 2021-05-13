package de.eat4speed.Algo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/FahrerAuswahl")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Algo_FahrerAuswalController {

    @Inject
    Algo_FahrerAuswahl _Fahrer_Auswahl;

    @PUT
    @Path("{Start_ID}")
    public void FahrerAuswahl(@PathParam("Start_ID") int id)
    {
        _Fahrer_Auswahl = new Algo_FahrerAuswahl();
        _Fahrer_Auswahl.Fahrtenvergabe(id);
    }

}
