package com.asu.EduMentor.controller.rest.response;

public class InvoiceResponse {
    private double originalAmount;
    private double calculatedTotal;
    private String currency;

    public InvoiceResponse(double originalAmount, double calculatedTotal, String currency) {
        this.originalAmount = originalAmount;
        this.calculatedTotal = calculatedTotal;
        this.currency = currency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getCalculatedTotal() {
        return calculatedTotal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public void setCalculatedTotal(double calculatedTotal) {
        this.calculatedTotal = calculatedTotal;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
