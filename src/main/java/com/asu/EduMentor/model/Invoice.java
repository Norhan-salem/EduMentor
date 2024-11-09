package com.asu.EduMentor.model;

import java.util.List;

public class Invoice implements CRUD {

    private int invoiceID;
    private String description;


    public Invoice(int invoiceID, String description) {
        this.invoiceID = invoiceID;
        this.description = description;
    }


    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getTotal() {
        // to be implemented
        return 0;
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
