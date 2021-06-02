package de.eat4speed.controllers;

import de.eat4speed.dto.CheckoutDto;
import de.eat4speed.dto.PaypalDto;
import de.eat4speed.services.interfaces.IPaypalService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/Paypal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CheckoutController {

    @Inject
    IPaypalService _paypal;

    @POST
    @Path("checkout")
    public PaypalDto add(CheckoutDto id) {
        return _paypal.checkOutViaPaypal(id.getId());
    }
}
