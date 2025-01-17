package com.asu.EduMentor.controller.rest.response;

public class CreatePaymentResponse {
    private String clientSecret;
    private double amount;
    private String currency;

    public CreatePaymentResponse(String clientSecret, double amount, String currency) {
        this.clientSecret = clientSecret;
        this.amount = amount;
        this.currency = currency;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}