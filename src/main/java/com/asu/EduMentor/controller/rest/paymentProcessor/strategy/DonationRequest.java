package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

import com.asu.EduMentor.model.PaymentType;

public class DonationRequest {
    private Long amount;
    private String paymentType;
    private String firstName;
    private String lastName;
    private String email;
    private int donorId;

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String  paymentType) {
        if (paymentType.equals("Card") || paymentType.equals("Courier")) {
            this.paymentType = paymentType;
        }else{
            throw new IllegalArgumentException("Payment type must be (Card) or (Courier)" + paymentType
            );
        }
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
