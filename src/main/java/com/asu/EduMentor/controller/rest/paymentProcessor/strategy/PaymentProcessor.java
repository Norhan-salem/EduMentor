package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

public class PaymentProcessor {
    private IPaymentStrategy paymentStrategy;

    public void setPaymentStrategy(IPaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void executePayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy is not set");
        }
        paymentStrategy.processPayment(amount);
    }
}