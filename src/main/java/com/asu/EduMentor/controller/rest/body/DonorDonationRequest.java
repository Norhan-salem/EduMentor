package com.asu.EduMentor.controller.rest.body;

import com.asu.EduMentor.model.OnlineDonation;
import com.asu.EduMentor.model.OnlineDonor;
import com.asu.EduMentor.model.PaymentType;

public class DonorDonationRequest {
    private OnlineDonation onlineDonation;
    private OnlineDonor onlineDonor;
    private PaymentType paymentType;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount > 0) {
            this.amount = amount;
        }
    }

    public OnlineDonation getOnlineDonation() {
        return onlineDonation;
    }

    public boolean setOnlineDonation(OnlineDonation onlineDonation) {
        if (onlineDonation != null) {
            this.onlineDonation = onlineDonation;
            return true;
        }

        return false;
    }

    public OnlineDonor getOnlineDonor() {
        return onlineDonor;
    }

    public boolean setOnlineDonor(OnlineDonor onlineDonor) {
        if (onlineDonor != null) {
            this.onlineDonor = onlineDonor;
            return true;
        }

        return false;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

}
