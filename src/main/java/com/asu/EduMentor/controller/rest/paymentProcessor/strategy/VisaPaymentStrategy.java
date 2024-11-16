package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

public class VisaPaymentStrategy implements IPaymentStrategy {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Visa payment of amount: $" + amount);
    }
}