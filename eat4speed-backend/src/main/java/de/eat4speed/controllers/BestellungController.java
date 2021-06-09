package de.eat4speed.controllers;

import de.eat4speed.dto.*;
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

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("getStatistic")
    public StatisticDtoWrapper getStatistic(StatisticRequestDto req) throws SQLException {
        return _bestellungen.getStatistic(req.restaurantId, req.start, req.end);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(){
        return _bestellungen.listAll().toString();
    }

    @GET
    @Path("/{Auftrag_ID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("Auftrag_ID") int Auftrag_ID) {

        return Response.ok().entity(_bestellungen.getGerichteByAuftragID(Auftrag_ID).get(0)).build();
    }
}
