package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DonationController {


    @PostMapping("/api/donation/create-payment-intent")
    @ResponseBody
    public ResponseEntity<CreatePaymentResponse> createPaymentIntent(@RequestBody DonationRequest request) {
        try {
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            paymentProcessor.setPaymentStrategy(new VisaPaymentStrategy());
            String response = paymentProcessor.executePayment(1050.5);
            return ResponseEntity.ok(new CreatePaymentResponse(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CreatePaymentResponse(e.getMessage()));
        }
    }
}
