package de.eat4speed.controllers;

import de.eat4speed.repositories.FahrtenplanRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Fahrtenplan")
public class FahrtenplanController {

    @Inject
    FahrtenplanRepository fahrtenplanRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return fahrtenplanRepository.listAll().toString();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id}")
    public String getFahrerID(@PathParam("id") int id) { return fahrtenplanRepository.findByStationsID(id).toJSON(); }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("exist/{AuftragID}")
    public Response doesFahrtenPlanExistFromAuftrag(@PathParam("AuftragID") int Auftrag)
    {
        return Response.ok().entity(fahrtenplanRepository.doesFahrtenPlanExistFromAuftrag(Auftrag)).build();
    }

}
