package de.eat4speed.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.eat4speed.dto.*;
import de.eat4speed.entities.Bestellung;
import de.eat4speed.repositories.BestellungRepository;
import de.eat4speed.repositories.RestaurantRepository;
import de.eat4speed.services.interfaces.IBestellungService;

import javax.annotation.security.PermitAll;
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
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response add(OrderDto obj) throws SQLException {
        return _bestellungen.createBestellung(obj);
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("pay")
    public PaymentDto pay(PayDto jobId) throws SQLException {
        return _bestellungen.payForOrder(jobId.getJobId());
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getStatistic")
    public StatisticDtoWrapper getStatistic(StatisticRequestDto req) throws SQLException {
        return _bestellungen.getStatistic(req.restaurantId, req.start, req.end);
    }

    @GET
    @PermitAll
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("getAllOrdersFromCustomerByDishId/{customerId}/{dishId}")
    public Integer getAllOrdersFromCustomerByDishId(@PathParam("customerId") int customerId, @PathParam("dishId") int dishId) {
        return _bestellungen.getAmountOrdersByCustomerIdAndGerichtId(customerId, dishId);
    }

    @GET
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllOrdersFromRestaurantId/{customerId}/{restaurantId}")
    public Integer getAllOrdersFromRestaurantId(@PathParam("customerId") int customerId, @PathParam("restaurantId") int restaurantId) {
        return _bestellungen.getAllOrdersForRestaurantIdByCustomerID(restaurantId,customerId);
    }

    @GET
    @Path("getRestaurantBestellungen/{email}")
    public List getRestaurantBestellungen(@PathParam("email") String email) {return _bestellungen.getRestaurantBestellungen(email);}

    @PUT
    @JsonIgnore
    @Path("updateBestellungStatus")
    public Response updateBestellungStatus(Bestellung bestellung) {
        return _bestellungen.updateBestellungStatus(bestellung);
    }

    @GET
    @Path("checkForUserOrders/{kundennummer}")
    public List checkForUserOrders(@PathParam("kundennummer") int kundennummer)
    {
        return bestellungRepository.checkForUserOrders(kundennummer);
    }

    @GET
    @Path("getProduktUndAnzahl/{id}")
    public List getProduktUndAnzahl(@PathParam("id") int id) {return _bestellungen.getProduktUndAnzahl(id);}

    @GET
    @Path("getKundeBestellungen/{status}/{email}")
    public List getKundeBestellungen(@PathParam("status") String status, @PathParam("email") String email) {return _bestellungen.getKundeBestellungen(status, email);}

    @GET
    @Path("getKundeBestellungenAktiv/{email}")
    public List getKundeBestellungenAktiv(@PathParam("email") String email) {return _bestellungen.getKundeBestellungenAktiv(email);}

}
