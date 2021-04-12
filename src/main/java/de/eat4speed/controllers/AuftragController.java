package de.eat4speed.controllers;


import de.eat4speed.entities.Auftrag;
import de.eat4speed.repositories.AuftragRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;

@Path("/Auftrag")
public class AuftragController {


    @Inject
    AuftragRepository auftragRepository;

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

}
