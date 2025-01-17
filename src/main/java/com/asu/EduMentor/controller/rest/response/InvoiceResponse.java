package com.asu.EduMentor.controller.rest.response;

public class InvoiceResponse {
    private double originalAmount;
    private double amountWithTax;
    private double convertedTotal;
    private String currency;

    public InvoiceResponse(double originalAmount, double amountWithTax, double convertedTotal, String currency) {
        this.originalAmount = originalAmount;
        this.amountWithTax = amountWithTax;
        this.convertedTotal = convertedTotal;
        this.currency = currency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getConvertedTotal() {
        return convertedTotal;
    }

    public void setConvertedTotal(double convertedTotal) {
        this.convertedTotal = convertedTotal;
    }

    public double getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(double amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Getters and setters
}

