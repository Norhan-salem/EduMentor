package com.asu.EduMentor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Invoice {

    private int invoiceID;

    /**
     * Constructs an Invoice with a specified invoice ID.
     *
     * @param invoiceID The unique ID of the invoice.
     */
    public Invoice(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    /**
     * Gets the invoice ID.
     *
     * @return The unique ID of this invoice.
     */
    public int getInvoiceID() {
        return invoiceID;
    }

    /**
     * Sets the invoice ID.
     *
     * @param invoiceID The unique ID to assign to this invoice.
     */
    public void setInvoiceID(int invoiceID) {
        if (this.invoiceID != 0) {
            throw new IllegalArgumentException("Invoice ID is already set and cannot be modified.");
        }
        this.invoiceID = invoiceID;
    }

    /**
     * Gets the total amount associated with this invoice by retrieving the 'Amount'
     * from the OnlineDonation table where the 'InvoiceID' matches the current invoice ID.
     *
     * @return The amount associated with this invoice.
     */
    public double getTotal() {
        String query = "SELECT \"Amount\" FROM public.\"OnlineDonation\" WHERE \"InvoiceID\" = ? AND \"IsDeleted\" = false";
        double total = 0.0;

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invoiceID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("Amount");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching amount for invoice", e);
        }

        return total;
    }
}

