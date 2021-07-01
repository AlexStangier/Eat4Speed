package de.eat4speed.controllers;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.eat4speed.services.interfaces.IRoutingService;

@Path("/route")
public class RoutingController {

    @Inject
    IRoutingService _router;

    @GET
    @Path("/calculate/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String get_Sorted_Waypoints_test(@PathParam("email") String email) {

        try {
            return _router.get_best_Route(email).toString();
        } catch (Exception e) {
            System.out.println("Null was returned: " + e.getMessage());
            return null;
        }
    }


    @PUT
    @Path("/confirm/{art}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public int confirm_action(@PathParam("art") String art, @QueryParam("auftraege") String auftraege, @QueryParam("data") String data, @QueryParam("email") String email) {

        return _router.confirm(art, auftraege, data, email);

    }

    @PUT
    @Path("/accident/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void report_accident(@QueryParam("auftraege") String auftraege){
        _router.accident(auftraege);
    }
}
