package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

public class VisaPaymentStrategy implements IPaymentStrategy {

    StripeAPI api = new StripeAPI();

    @Override
    public String processPayment(double amount) {

        long roundedAmount = Math.round(amount);

        return api.createPaymentIntent(roundedAmount);
    }
}