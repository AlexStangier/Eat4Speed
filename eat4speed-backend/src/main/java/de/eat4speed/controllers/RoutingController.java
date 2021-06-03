package de.eat4speed.controllers;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.eat4speed.services.interfaces.IRoutingService;
import org.json.JSONObject;

@Path("/route")
public class RoutingController {

    @Inject
    IRoutingService _router;

    /**
     * @Daniel Zum Testen mit mehr / weniger Wegpunkten, den Return unten auskommentieren, den darüber wieder zulassen und in der Klasse "RoutingService.java",
     * in der Funktion "get_best_route()", noch ein "add_shipment()" hinzufügen oder wegmachen.
     * Bitte auf richitge Koordianten im Bereich um Freiburg herum achten.
     */

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get_Sorted_Waypoints_test(@PathParam("email") String email) throws Exception {

        _router.db_test(email);
        System.out.println(email);
        return _router.get_best_Route().toString();
        //return "{\"sorted_waypoints\":[[7.84529,47.993263],[7.862707,48.01426],[7.862707,48.01426],[7.857937,48.011837],[7.819171,48.017708],[7.823581,48.01125],[7.827899,48.010338],[7.830039,48.009247],[7.840162,48.018658],[7.840162,48.018658]]}";
    }


    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String get_Sorted_Waypoints() throws Exception {

        return "test";
    }
}
