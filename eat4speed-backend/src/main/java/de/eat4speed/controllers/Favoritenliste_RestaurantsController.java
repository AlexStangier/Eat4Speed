package de.eat4speed.controllers;

import de.eat4speed.entities.Favoritenliste_Restaurants;
import de.eat4speed.services.interfaces.IFavoritenliste_RestaurantsService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Favoritenliste_Restaurants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Favoritenliste_RestaurantsController {

    @Inject
    IFavoritenliste_RestaurantsService favoritenliste_restaurantsService;

    @POST
    public Response add(Favoritenliste_Restaurants favoritenliste_restaurants)
    {
        favoritenliste_restaurantsService.addFavoritenliste_Restaurants(favoritenliste_restaurants);

        return Response.status(Response.Status.CREATED).entity(favoritenliste_restaurants).build();
    }

    @DELETE
    @Path("remove/{kundennummer}/{restaurant_ID}")
    @RolesAllowed("kunde")
    public Response removeFavoritenliste_Restaurants(@PathParam("restaurant_ID") int restaurant_ID,@PathParam("kundennummer") int kundennummer)
    {
        return favoritenliste_restaurantsService.removeFavoritenliste_Restaurants(restaurant_ID,kundennummer);
    }

}
