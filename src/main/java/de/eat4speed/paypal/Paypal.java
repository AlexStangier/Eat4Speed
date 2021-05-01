package de.eat4speed.paypal;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class Paypal {

    String clientId = "sb-cefyj6088080@personal.example.com";
    String clientSecret = "AXRp-5L-rrysXqpsxXS5QMJqEFOixV1DafUMDKhAcweRhtclldIahvz8zEv2zona25US6wtILhLdEwBt";

    APIContext context = new APIContext(clientId, clientSecret, "sandbox");
}
