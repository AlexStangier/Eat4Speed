package de.eat4speed.controllers;

import de.eat4speed.entities.Inhaltsstoffe;
import de.eat4speed.repositories.InhaltsstoffeRepository;
import de.eat4speed.services.InhaltsstoffeService;
import de.eat4speed.services.interfaces.IInhaltsstoffeService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/Inhaltsstoffe")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InhaltsstoffeController {

    @Inject
    IInhaltsstoffeService _inhaltsstoffe;

    @POST
    public Response add(Inhaltsstoffe inhaltsstoffe) {
        return _inhaltsstoffe.addInhaltsstoffe(inhaltsstoffe);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List get() {
        return _inhaltsstoffe.listAll();
    }


}
