package com.asu.EduMentor.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A subclass of Invoice that represents an invoice with additional charges.
 */
public class InvoiceDetails extends Invoice {

    private double amountCharged;

    /**
     * Constructs an InvoiceDetails with a specified invoice ID and additional charge.
     *
     * @param invoiceID The unique invoice ID.
     */
    public InvoiceDetails(int invoiceID, double amountCharged) {
        super(invoiceID);
        this.amountCharged = amountCharged;
    }

    /**
     * Returns the charged amount of the invoice.
     *
     * @return charged amount of the invoice.
     */
    @Override
    public double getTotal() {
        return amountCharged;
    }

    /**
     * Gets the additional amount charged.
     *
     * @return The amount charged.
     */
    public double getAmountCharged() {
        return amountCharged;
    }

    /**
     * Sets the additional amount charged.
     *
     * @param amountCharged The amount to set, must be non-negative.
     * @throws IllegalArgumentException if amountCharged is negative.
     */
    public void setAmountCharged(double amountCharged) {
        if (amountCharged < 0) {
            throw new IllegalArgumentException("Amount charged cannot be negative.");
        }
        this.amountCharged = amountCharged;
    }
}

