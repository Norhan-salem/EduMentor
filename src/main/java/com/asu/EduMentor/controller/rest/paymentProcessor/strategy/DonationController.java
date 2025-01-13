package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DonationController {

    private String stripeApiKey = System.getenv("STRIPE_API_KEY");;

    @GetMapping("/")
    public String showDonationForm() {
        return "donation";
    }

    @PostMapping("/api/donation/create-payment-intent")
    @ResponseBody
    public ResponseEntity<CreatePaymentResponse> createPaymentIntent(@RequestBody DonationRequest request) {
        try {
            Stripe.apiKey = stripeApiKey;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", request.getAmount() * 100); // amount in cents
            params.put("currency", "usd");
            params.put("payment_method_types", new String[]{"card"});
            params.put("description", "Donation payment");

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return ResponseEntity.ok(new CreatePaymentResponse(paymentIntent.getClientSecret()));
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(new CreatePaymentResponse(e.getMessage()));
        }
    }
}
