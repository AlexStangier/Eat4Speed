package de.eat4speed.controllers;

import de.eat4speed.entities.BenachrichtigungFahrerAuftrag;
import de.eat4speed.entities.Benachrichtigung_Fahrer;
import de.eat4speed.repositories.BenachrichtigungFahrerAuftragRepository;
import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Path("/BenachrichtigungFahrerAuftrag")
public class BenachrichtigungFahrerAuftragController {

    @Inject
    BenachrichtigungFahrerAuftragRepository benachrichtigungFahrerAuftragRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return benachrichtigungFahrerAuftragRepository.listAll().toString();
    }

    @PUT
    public Response add(@Context HttpHeaders headers, InputStream requestBody) {

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            reader.close();

            BenachrichtigungFahrerAuftrag benachrichtigung = BenachrichtigungFahrerAuftrag.fromJSON(out.toString());

            benachrichtigungFahrerAuftragRepository.addBenachrichtigungFahrerAuftrag(benachrichtigung);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{Auftrags_ID}/{Benachrichtigungs_ID}")
    public Response delete(@PathParam("Auftrags_ID") int auftrags_ID, @PathParam("Benachrichtigungs_ID") int benachrichtigungs_ID) {

        benachrichtigungFahrerAuftragRepository.deleteBenachrichtigungFahrerAuftrag(auftrags_ID, benachrichtigungs_ID);
        return Response.ok().build();
    }
}
