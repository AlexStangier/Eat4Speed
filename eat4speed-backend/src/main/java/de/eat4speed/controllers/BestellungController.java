package de.eat4speed.controllers;

import de.eat4speed.dto.OrderDto;
import de.eat4speed.dto.PayDto;
import de.eat4speed.dto.PaymentDto;
import de.eat4speed.entities.Bestellung;
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

    @GET
    @Path("getRestaurantBestellungen/{email}")
    public List getRestaurantBestellungen(@PathParam("email") String email) {return _bestellungen.getRestaurantBestellungen(email);}

    @PUT
    @Path("updateBestellungStatus")
    public Response updateBestellungStatus(Bestellung bestellung) {
        return _bestellungen.updateBestellungStatus(bestellung);
    }

}
