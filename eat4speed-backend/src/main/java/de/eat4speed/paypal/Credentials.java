package de.eat4speed.paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

public class Credentials {
    static String clientId = "AXRp-5L-rrysXqpsxXS5QMJqEFOixV1DafUMDKhAcweRhtclldIahvz8zEv2zona25US6wtILhLdEwBt";
    static String secret = "ENyNhH0kCZLRqxx--11svQ7jLt9M93Oitn0aBr1RZEpfRPqu116han3H9NuXLWGBQ9xG6atJaeWhrkAe";

    // Creating a sandbox environment
    private static PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, secret);

    // Creating a client for the environment
    static PayPalHttpClient client = new PayPalHttpClient(environment);
}