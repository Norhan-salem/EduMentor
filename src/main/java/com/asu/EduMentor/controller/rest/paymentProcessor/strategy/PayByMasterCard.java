package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

public class PayByMasterCard implements IPaymentStrategy {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing MasterCard payment of amount: $" + amount);
    }
}