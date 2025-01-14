package com.asu.EduMentor.logging;

public class DonationLog implements Loggable {
    String donorName;
    double amount;

    public DonationLog(String donorName, double amount) {
        this.donorName = donorName;
        this.amount = amount;
    }

    @Override
    public String toLog() {
        return donorName + " donated $" + amount;
    }
}
