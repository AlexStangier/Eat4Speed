package de.eat4speed.controllers;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.eat4speed.services.interfaces.IRoutingService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

@Path("/route")
public class RoutingController {

    @Inject
    IRoutingService _router;

    @GET
    @Path("/calculate/{email}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String get_Sorted_Waypoints_test(@PathParam("email") String email) throws Exception {

        //_router.db_test(email);
        //System.out.println(email);
        try{
            return _router.get_best_Route(email).toString();
        }catch (Exception e){
            System.out.println("Null was returned: " + e.getMessage());
            return null;
        }
        //return "{\"sorted_waypoints\":[[7.84529,47.993263],[7.862707,48.01426],[7.862707,48.01426],[7.857937,48.011837],[7.819171,48.017708],[7.823581,48.01125],[7.827899,48.010338],[7.830039,48.009247],[7.840162,48.018658],[7.840162,48.018658]]}";
    }


    @GET
    @Path("/confirm/{art}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get_Sorted_Waypoints(@PathParam("art") String art,@QueryParam("auftraege") String auftraege, @QueryParam("data") String data, @QueryParam("email") String email) throws Exception {

        _router.confirm(art, auftraege, data, email);

        return "okay";
    }
}
