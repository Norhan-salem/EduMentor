package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import io.github.cdimascio.dotenv.Dotenv;


import java.util.HashMap;
import java.util.Map;

public class StripeAPI {
    private static final Dotenv dotenv = Dotenv.load();
    private String stripeApiKey = dotenv.get("STRIPE_API_KEY");

    public String createPaymentIntent(long amount) {
        try {
            Stripe.apiKey = stripeApiKey;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount * 100); // amount in cents
            params.put("currency", "usd");
            params.put("payment_method_types", new String[]{"card"});
            params.put("description", "Donation payment");

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return paymentIntent.getClientSecret();
        } catch (StripeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
