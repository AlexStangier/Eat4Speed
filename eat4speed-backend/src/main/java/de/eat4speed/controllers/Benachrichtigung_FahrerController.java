package de.eat4speed.controllers;


import de.eat4speed.entities.Benachrichtigung_Fahrer;
import de.eat4speed.entities.Gericht;
import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;
import de.eat4speed.repositories.FahrzeugRepository;
import de.eat4speed.services.interfaces.IBenachrichtigung_FahrerService;
import org.json.JSONObject;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Path("/Benachrichtigung_Fahrer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Benachrichtigung_FahrerController {


    @Inject
    IBenachrichtigung_FahrerService benachrichtigung_fahrerService;

    @PUT
    @Path("/markAsGelesen/{benachrichtigungs_ID}")
    public void markAsGelesen(@PathParam("benachrichtigungs_ID") int benachrichtigungs_ID)
    {
        benachrichtigung_fahrerService.markAsGelesen(benachrichtigungs_ID);
    }

    @GET
    @RolesAllowed("fahrer")
    @Path("/getAllBenachrichtigungFahrerUngelesen/{fahrernummer}")
    public List getAllBenachrichtigung_Fahrer_ungelesen(@PathParam("fahrernummer") int fahrernummer)
    {
        return benachrichtigung_fahrerService.getAllBenachrichtigung_Fahrer_ungelesen(fahrernummer);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return benachrichtigung_fahrerService.listAll().toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBenachrichtigung_Fahrer(@Context HttpHeaders headers, InputStream requestBody) {

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            reader.close();

            Benachrichtigung_Fahrer benachrichtigung_fahrer = Benachrichtigung_Fahrer.fromJSON(out.toString());
            benachrichtigung_fahrerService.addBenachrichtigung_Fahrer(benachrichtigung_fahrer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok().build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id")
    public Response getBenachrichtigung_FahrerID(@Context HttpHeaders headers, InputStream requestBody) {

        int id = 0;

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            reader.close();

            JSONObject json = new JSONObject(out.toString());
            id = benachrichtigung_fahrerService.findIDByNachricht(
                    json.getString("benachrichtigung"), json.getInt("fahrernummer"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok().entity("{\"id\": " + id + "}").build();
    }

    @DELETE
    @Path("/{Benachrichtigungs_ID}")
    public Response delete(@PathParam("Benachrichtigungs_ID") int benachrichtigungs_ID) {

        benachrichtigung_fahrerService.deleteBenachrichtigungFahrer(benachrichtigungs_ID);
        return Response.ok().build();
    }

}
