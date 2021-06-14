package de.eat4speed.controllers;


import de.eat4speed.entities.Auftrag;
import de.eat4speed.repositories.AuftragRepository;
import de.eat4speed.services.interfaces.IAuftragService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.List;

@Path("/Auftrag")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuftragController {


    @Inject
    AuftragRepository auftragRepository;
    @Inject
    IAuftragService auftragService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        Auftrag auftrag = new Auftrag();

        auftrag.setAuftragnehmer(1);
        auftrag.setAnschrift(1);
        auftrag.setLieferdistanz(2.0);
        auftrag.setTimestamp(new Timestamp(System.currentTimeMillis()));
        auftrag.setKundennummer(1);
        auftrag.setStatus("aktiv");

        auftragRepository.addAuftrag(auftrag);

        return auftragRepository.listAll().toString();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("id") long id) {
        return auftragRepository.findById(id).toString();
    }

    @PUT
    @Path("updateAuftragFahrernummer/{auftrags_ID}/{fahrernummer}")
    public Response updateAuftragFahrernummer(@PathParam("auftrags_ID") int auftrags_ID, @PathParam("fahrernummer") int fahrernummer)
    {
        return auftragService.updateAuftragFahrernummer(auftrags_ID, fahrernummer);
    }

    @GET
    @Path("getAuftragFahrernummerByAuftrags_ID/{auftrags_ID}")
    public List getAuftragFahrernummerByAuftrags_ID(@PathParam("auftrags_ID") int auftrags_ID)
    {
        return auftragService.getAuftragFahrernummerByAuftrags_ID(auftrags_ID);
    }

}
