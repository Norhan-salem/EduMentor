package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

public interface IPaymentStrategy {
    void processPayment(double amount);
}