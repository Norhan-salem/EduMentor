package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

public interface IPaymentStrategy {
    String processPayment(double amount);
}