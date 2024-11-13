package com.asu.EduMentor.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents an online donation in the system, allowing CRUD operations on donation records.
 * Implements the CRUD interface for standard create, read, update, and delete functionality.
 */
public class OnlineDonation implements CRUD {
    private int donationID;
    private int paymentType;
    private double amount;
    private boolean isDeleted;

    /**
     * Constructs an OnlineDonation with a specified payment type and amount.
     *
     * @param paymentType The type of payment used for the donation.
     * @param amount      The donation amount.
     */
    public OnlineDonation(int paymentType, double amount) {
        setPaymentType(paymentType);
        setAmount(amount);
        this.isDeleted = false;
    }

    /**
     * Gets the donation ID.
     *
     * @return The unique ID of this donation.
     */
    public int getDonationID() {
        return donationID;
    }

    /**
     * Sets the donation ID. Ensures it is only set once.
     *
     * @param donationID The unique ID to assign to this donation.
     */
    public void setDonationID(int donationID) {
        if (this.donationID != 0) {
            throw new IllegalArgumentException("Donation ID is already set and cannot be modified.");
        }
        this.donationID = donationID;
    }

    /**
     * Gets the payment type.
     *
     * @return The payment type used for this donation.
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the payment type with validation.
     *
     * @param paymentType The payment type to assign to this donation.
     */
    public void setPaymentType(int paymentType) {
        if (paymentType <= 0) {
            throw new IllegalArgumentException("Payment type must be a positive integer.");
        }
        this.paymentType = paymentType;
    }

    /**
     * Gets the amount of the donation.
     *
     * @return The donation amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the donation with validation.
     *
     * @param amount The donation amount to assign.
     */
    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        this.amount = amount;
    }

    /**
     * Checks if the donation has been marked as deleted.
     *
     * @return True if the donation is deleted, otherwise false.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Sets the deletion status of the donation.
     *
     * @param deleted True to mark as deleted, false otherwise.
     */
    public void setDeleted(boolean deleted) {
        if (this.isDeleted && !deleted) {
            throw new IllegalArgumentException("Cannot un-delete a donation.");
        }
        this.isDeleted = deleted;
    }

    /**
     * Retrieves the associated InvoiceDetails object for this donation.
     *
     * @return The InvoiceDetails associated with this donation or null if not found.
     */
    public Invoice getInvoice() {
        String query = "SELECT \"InvoiceID\" FROM public.\"OnlineDonation\" WHERE \"DonationID\" = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, donationID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int invoiceID = rs.getInt("InvoiceID");
                    return new InvoiceDetails(invoiceID);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching invoice", e);
        }

        return null;
    }

    /**
     * Processes the donation by inserting a new entry in the OnlineDonation table.
     * Generates a unique donation ID and links the donation to an Invoice.
     *
     * @param od The donor who made the donation.
     */
    public boolean makeDonation(OnlineDonor od) {
        String query = "INSERT INTO public.\"OnlineDonation\" (\"Amount\", \"PaymentType\", \"InvoiceID\", \"AmountCharged\", \"IsDeleted\") " +
                "VALUES (?, ?, ?, ?, ?) RETURNING \"DonationID\"";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, paymentType);
            stmt.setInt(3, getInvoice().getInvoiceID());
            stmt.setDouble(4, amount);
            stmt.setBoolean(5, false);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.donationID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error making donation", e);
        }
        return true;
    }

    /**
     * Creates a new donation entry in the database.
     *
     * @return The created OnlineDonation object.
     */
    @Override
    public Object create() {
        makeDonation(null);
        return this;
    }

    /**
     * Updates an existing donation record in the database.
     *
     * @param updatedObject The OnlineDonation object with updated details.
     * @return The updated OnlineDonation object if successful, otherwise null.
     */
    @Override
    public Object update(Object updatedObject) {
        OnlineDonation updatedDonation = (OnlineDonation) updatedObject;
        String query = "UPDATE public.\"OnlineDonation\" SET \"Amount\" = ?, \"PaymentType\" = ?, \"IsDeleted\" = ? WHERE \"DonationID\" = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, updatedDonation.getAmount());
            stmt.setInt(2, updatedDonation.getPaymentType());
            stmt.setBoolean(3, updatedDonation.isDeleted());
            stmt.setInt(4, updatedDonation.getDonationID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? updatedDonation : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating donation", e);
        }
    }

    /**
     * Reads a donation record from the database based on the specified ID.
     *
     * @param id The ID of the donation to read.
     * @return An OnlineDonation object if found, otherwise null.
     */
    @Override
    public Object read(int id) {
        String query = "SELECT * FROM public.\"OnlineDonation\" WHERE \"DonationID\" = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OnlineDonation donation = new OnlineDonation(rs.getInt("PaymentType"), rs.getDouble("Amount"));
                    donation.setDonationID(rs.getInt("DonationID"));
                    donation.setDeleted(rs.getBoolean("IsDeleted"));
                    return donation;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading donation", e);
        }

        return null;
    }

    /**
     * Reads all donation records from the database.
     *
     * @return A list of OnlineDonation objects.
     */
    @Override
    public List<Object> readAll() {
        List<Object> donations = new ArrayList<>();
        String query = "SELECT * FROM public.\"OnlineDonation\"";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OnlineDonation donation = new OnlineDonation(rs.getInt("PaymentType"), rs.getDouble("Amount"));
                donation.setDonationID(rs.getInt("DonationID"));
                donation.setDeleted(rs.getBoolean("IsDeleted"));
                donations.add(donation);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading all donations", e);
        }

        return donations;
    }

    /**
     * Marks a donation as deleted in the database by setting the IsDeleted flag to true.
     *
     * @param id The ID of the donation to delete.
     * @return True if the deletion was successful, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        String query = "UPDATE public.\"OnlineDonation\" SET \"IsDeleted\" = true WHERE \"DonationID\" = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting donation", e);
        }
    }
}

