package de.eat4speed.controllers;


import de.eat4speed.entities.Auftrag;
import de.eat4speed.repositories.AuftragRepository;
import de.eat4speed.services.interfaces.IAuftragService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/Auftrag")
public class AuftragController {


    @Inject
    AuftragRepository auftragRepository;

    @Inject
    IAuftragService _auftrag;

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
    @Path("updateAuftragStatusRestaurant")
    public Response updateAuftragStatusRestaurant(Auftrag auftrag) {
        return _auftrag.updateAuftragStatusRestaurant(auftrag);
    }

}
