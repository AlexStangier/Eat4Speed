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
import java.net.HttpURLConnection;
import java.net.URL;
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
    @RolesAllowed("restaurant")
    @Path("getRestaurantBestellungen/{email}")
    public List getRestaurantBestellungen(@PathParam("email") String email) {return _bestellungen.getRestaurantBestellungen(email);}

    @PUT
    @Path("updateBestellungStatus")
    public Response updateBestellungStatus(Bestellung bestellung)
    {
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
    @RolesAllowed({"kunde","restaurant"})
    @Path("getProduktUndAnzahl/{id}")
    public List getProduktUndAnzahl(@PathParam("id") int id) {return _bestellungen.getProduktUndAnzahl(id);}

    @PUT
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("updateBestellungStatusRestaurantUndKundeDontTouchThis/{id}/{status}")
    public Response updateBestellungStatus(@PathParam("id") long id, @PathParam("status") String status ){
        BestellungUpdateDto dto = new BestellungUpdateDto(status, id);

        //Response zwischen speichern, da nach Überprüfung erst nach Datenbank zugriff
        Response response = _bestellungen.updateBestellungStatusRestaurantUndKundeDontTouchThis(dto);

        try {
            //Überprüfung ob Fahrerauswahl starten soll
            System.out.println("TEST");
            URL url = new URL("https://eat4speed.xyz/FahrerAuswahl/start/" + (int)id);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(false);
            http.setReadTimeout(10);
            http.getInputStream();
            http.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }

        return response;
    }
    @GET
    @RolesAllowed("kunde")
    @Path("getKundeBestellungen/{status}/{email}")
    public List getKundeBestellungen(@PathParam("status") String status, @PathParam("email") String email) {return _bestellungen.getKundeBestellungen(status, email);}

    @GET
    @RolesAllowed("kunde")
    @Path("getKundeBestellungenAktiv/{email}")
    public List getKundeBestellungenAktiv(@PathParam("email") String email) {return _bestellungen.getKundeBestellungenAktiv(email);}

    @GET
    @RolesAllowed("kunde")
    @Path("getGerichtIds/{id}")
    public List getGerichtIds(@PathParam("id") int id) {return _bestellungen.getGerichtIds(id);}

    @GET
    @RolesAllowed({"kunde","restaurant"})
    @Path("getAnzahlFertigerAuftraege/{id}")
    public List getAnzahlFertigerAuftraege(@PathParam("id") int id) {return _bestellungen.getAnzahlFertigerAuftraege(id);}

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
