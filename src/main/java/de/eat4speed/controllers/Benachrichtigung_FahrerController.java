package de.eat4speed.controllers;

import de.eat4speed.entities.Benachrichtigung_Fahrer;
import de.eat4speed.repositories.Benachrichtigung_FahrerRepository;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/Benachrichtigung_Fahrer")
public class Benachrichtigung_FahrerController {


    @Inject
    Benachrichtigung_FahrerRepository benachrichtigung_FahrerRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return benachrichtigung_FahrerRepository.listAll().toString();
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
            benachrichtigung_FahrerRepository.addBenachrichtigung_Fahrer(benachrichtigung_fahrer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok().build();
    }

    @POST
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
            id = benachrichtigung_FahrerRepository.findIDByNachricht(
                    json.getString("benachrichtigung"), json.getInt("fahrernummer"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.ok().entity("{\"id\": " + id + "}").build();
    }

    @DELETE
    @Path("/{Benachrichtigungs_ID}")
    public Response delete(@PathParam("Benachrichtigungs_ID") int benachrichtigungs_ID) {

        benachrichtigung_FahrerRepository.deleteBenachrichtigungFahrer(benachrichtigungs_ID);
        return Response.ok().build();
    }

}
