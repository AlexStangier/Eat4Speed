package de.eat4speed;

import Database.Entites.Benutzer;
import Database.Entites.Dtos.BenutzerDto;
import Database.Manager.DatabaseUserService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/Benutzer")
public class GreetingResource {

    @GET
    @Path("get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getBenutzer(@PathParam("id") long id) {
        Benutzer ben = Benutzer.findById(id);
        return ("Name: "+ ben.getBenutzername());
    }
}

