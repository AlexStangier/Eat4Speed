package de.eat4speed.paypal;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class Paypal {

    String clientId = "YOUR APPLICATION CLIENT ID";
    String clientSecret = "YOUR APPLICATION CLIENT SECRET";

    APIContext context = new APIContext(clientId, clientSecret, "sandbox");
}
