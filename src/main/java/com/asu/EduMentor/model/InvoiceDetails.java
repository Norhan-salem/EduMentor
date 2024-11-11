package com.asu.EduMentor.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A decorator class for the Invoice that adds specific charges to the total invoice amount.
 */
public class InvoiceDetails extends InvoiceDecorator {

    private double amountCharged;

    /**
     * Constructs an InvoiceDetails with a specified base invoice and additional charge.
     *
     * @param invoice The base invoice being decorated.
     * @throws IllegalArgumentException if amountCharged is negative.
     */
    public InvoiceDetails(Invoice invoice) {
        super(invoice);
        this.amountCharged = getAmountChargedFromDB(invoice.getInvoiceID());
    }

    /**
     * Returns the total amount of the invoice including the additional charge.
     *
     * @return Total amount of the invoice with additional charge.
     */
    @Override
    public double getTotal() {
        // Get the total amount from the base invoice and add the amountCharged.
        return super.getTotal() + amountCharged;
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
     * Retrieves the amount charged from the OnlineDonation table for a specific InvoiceID.
     *
     * @param invoiceID The InvoiceID to search for.
     * @return The amount charged.
     */
    private double getAmountChargedFromDB(int invoiceID) {
        String query = "SELECT \"AmountCharged\" FROM public.\"OnlineDonation\" WHERE \"InvoiceID\" = ? AND \"IsDeleted\" = false";
        double amountCharged = 0.0;

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, invoiceID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    amountCharged = rs.getDouble("AmountCharged");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching amount charged for invoice", e);
        }

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
