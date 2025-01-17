package com.asu.EduMentor.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OnlineDonation implements CRUD {
    private int donationID;
    private PaymentType paymentType;
    private double amount;
    private boolean isDeleted;

    /**
     * Constructs an OnlineDonation with a specified payment type and amount.
     *
     * @param paymentType The type of payment used for the donation.
     * @param amount      The donation amount.
     */
    public OnlineDonation(PaymentType paymentType, double amount) {
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
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the payment type.
     *
     * @param paymentType The payment type to assign to this donation.
     */
    public void setPaymentType(PaymentType paymentType) {
        if (paymentType == null) {
            throw new IllegalArgumentException("Payment type cannot be null.");
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

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, donationID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int invoiceID = rs.getInt("InvoiceID");
                    return new InvoiceDetails(invoiceID, 0);
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
     * @param paymentType The type of payment used for the donation (Visa, MasterCard, etc.).
     * @param od The donor who made the donation.
     */
    public boolean makeDonation(OnlineDonor od, PaymentType paymentType) {
        String donationQuery = "INSERT INTO public.\"OnlineDonation\" (\"Amount\", \"PaymentType\", \"AmountCharged\", \"IsDeleted\") " +
                "VALUES (?, ?, ?, ?) RETURNING \"DonationID\"";

        String donorUpdateQuery = "UPDATE public.\"OnlineDonor\" SET \"NumberofDonations\" = \"NumberofDonations\" + 1 " +
                "WHERE \"OnlineDonorID\" = ?";

        String donationLinkQuery = "INSERT INTO public.\"ODD_Makes\" (\"OnlineDonorID\", \"DonationID\", \"Date\") " +
                "VALUES (?, ?, CURRENT_TIMESTAMP)";

        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(donationQuery, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, paymentType.ordinal());
                stmt.setDouble(3, amount);
                stmt.setBoolean(4, false);

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.donationID = generatedKeys.getInt(1);
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(donorUpdateQuery)) {
                stmt.setInt(1, od.getUserID());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(donationLinkQuery)) {
                stmt.setInt(1, od.getUserID());
                stmt.setInt(2, this.donationID);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error rolling back transaction", rollbackEx);
            }
            throw new RuntimeException("Error making donation", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error resetting auto-commit", e);
            }
        }
    }

    /**
     * Creates a new donation entry in the database.
     *
     * @return The created OnlineDonation object.
     */
    @Override
    public Object create() {
        makeDonation(null, this.paymentType);
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

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, updatedDonation.getAmount());
            stmt.setInt(2, updatedDonation.getPaymentType().ordinal());  // Store the enum ordinal
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

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OnlineDonation donation = new OnlineDonation(PaymentType.values()[rs.getInt("PaymentType")], rs.getDouble("Amount"));
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

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OnlineDonation donation = new OnlineDonation(PaymentType.values()[rs.getInt("PaymentType")], rs.getDouble("Amount"));
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
        String findDonorQuery = "SELECT \"OnlineDonorID\" FROM public.\"ODD_Makes\" WHERE \"DonationID\" = ?";
        String donationQuery = "UPDATE public.\"OnlineDonation\" SET \"IsDeleted\" = TRUE WHERE \"DonationID\" = ?";
        String donorUpdateQuery = "UPDATE public.\"OnlineDonor\" SET \"NumberofDonations\" = \"NumberofDonations\" - 1 " +
                "WHERE \"OnlineDonorID\" = ?";
        String oddMakesQuery = "UPDATE public.\"ODD_Makes\" SET \"IsDeleted\" = TRUE WHERE \"DonationID\" = ?";

        Connection conn = DBConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);

            int donorId;
            try (PreparedStatement stmt = conn.prepareStatement(findDonorQuery)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    throw new RuntimeException("No donor found for donation ID: " + id);
                }
                donorId = rs.getInt("OnlineDonorID");
            }

            try (PreparedStatement stmt = conn.prepareStatement(donationQuery)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(donorUpdateQuery)) {
                stmt.setInt(1, donorId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(oddMakesQuery)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error rolling back transaction", rollbackEx);
            }
            throw new RuntimeException("Error deleting donation", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error resetting auto-commit", e);
            }
        }
    }
}



