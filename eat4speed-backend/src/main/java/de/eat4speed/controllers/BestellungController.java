package de.eat4speed.controllers;

import de.eat4speed.dto.*;
import de.eat4speed.entities.Bestellung;
import de.eat4speed.repositories.BestellungRepository;
import de.eat4speed.repositories.RestaurantRepository;
import de.eat4speed.services.interfaces.IBestellungService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/Bestellung")
public class BestellungController {

    @Inject
    IBestellungService _bestellungen;

    @Inject
    BestellungRepository bestellungRepository;

    @POST
    @RolesAllowed("kunde")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response add(OrderDto obj) throws SQLException {
        return _bestellungen.createBestellung(obj);
    }

    @POST
    @RolesAllowed("kunde")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("pay")
    public PaymentDto pay(PayDto jobId) throws SQLException {
        return _bestellungen.payForOrder(jobId.getJobId());
    }

    @POST
    @RolesAllowed("restaurant")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getStatistic")
    public StatisticDtoWrapper getStatistic(StatisticRequestDto req) throws SQLException {
        return _bestellungen.getStatistic(req.restaurantId, req.start, req.end);
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("kunde")
    @Path("getAllOrdersFromCustomerByDishId/{customerId}/{dishId}")
    public Integer getAllOrdersFromCustomerByDishId(@PathParam("customerId") int customerId, @PathParam("dishId") int dishId) {
        return _bestellungen.getAmountOrdersByCustomerIdAndGerichtId(customerId, dishId);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("kunde")
    @Path("getAllOrdersFromRestaurantId/{customerId}/{restaurantId}")
    public Integer getAllOrdersFromRestaurantId(@PathParam("customerId") int customerId, @PathParam("restaurantId") int restaurantId) {
        return _bestellungen.getAllOrdersForRestaurantIdByCustomerID(restaurantId,customerId);
    }

    @GET
    @Path("getRestaurantBestellungen/{email}")
    public List getRestaurantBestellungen(@PathParam("email") String email) {return _bestellungen.getRestaurantBestellungen(email);}

    @PUT
    @Path("updateBestellungStatus")
    @RolesAllowed("restaurant")
    public Response updateBestellungStatus(Bestellung bestellung) {
        return _bestellungen.updateBestellungStatus(bestellung);
    }

    @GET
    @Path("checkForUserOrders/{kundennummer}")
    @RolesAllowed("kunde")
    public List checkForUserOrders(@PathParam("kundennummer") int kundennummer)
    {
        return bestellungRepository.checkForUserOrders(kundennummer);
    }

    @GET
    @Path("getProduktUndAnzahl/{id}")
    public List getProduktUndAnzahl(@PathParam("id") int id) {return _bestellungen.getProduktUndAnzahl(id);}
}
