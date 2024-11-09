package com.asu.EduMentor.model;

import java.sql.*;
import java.util.List;

public class OnlineDonation implements CRUD
{
    private int donationID;
    private int paymentType;
    private double amount;
    private boolean isDeleted;

    public OnlineDonation(int paymentType, double amount)
    {
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public int getDonationID() {
        return donationID;
    }

    public void setDonationID(int donationID) {
        this.donationID = donationID;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }



    public Invoice getInvoice()
    {
        // to be implemented
        return null;
    }

    public void makeDonation(OnlineDonor od)
    {
        // to be implemented
    }

    @Override
    public Object create() {
        return null;
    }

    @Override
    public Object update(Object updatedObject) {
        return null;
    }

    @Override
    public Object read(int id) {
        return null;
    }

    @Override
    public List<Object> readAll() {
        return List.of();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
