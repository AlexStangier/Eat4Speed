package de.eat4speed.controllers;


import de.eat4speed.repositories.BestellungRepository;
import io.restassured.http.ContentType;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Bestellung")
public class BestellungController {


    @Inject
    BestellungRepository bestellungRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return bestellungRepository.listAll().toString();
    }

    @GET
    @Path("/{Auftrag_ID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("Auftrag_ID") int Auftrag_ID) {

        return Response.ok().entity(bestellungRepository.getGerichteByAuftragID(Auftrag_ID).get(0)).build();
    }

}
