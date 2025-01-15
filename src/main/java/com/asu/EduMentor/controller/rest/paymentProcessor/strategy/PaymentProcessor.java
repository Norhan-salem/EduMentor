package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

public class PaymentProcessor {
    private IPaymentStrategy paymentStrategy;

    public void setPaymentStrategy(IPaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public String  executePayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy is not set");
        }
        return paymentStrategy.processPayment(amount);
    }
}