package com.asu.EduMentor.model;

import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class OnlineDonor extends User {


    private int numberOfDonations;


    public OnlineDonor(String firstName, String lastName, String email) {
        super(firstName, lastName, UserType.ONLINEDONOR, email);
    }

    public OnlineDonor(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, UserType.ONLINEDONOR, email, password);
        this.numberOfDonations = 0;
    }

    public OnlineDonor(String firstName, String lastName, String email, String password, int numberOfDonations) {
        super(firstName, lastName, UserType.ONLINEDONOR, email, password);
        List<OnlineDonation> donations = getDonations();
        this.numberOfDonations = donations.size();
    }

    public int getNumberOfDonations() {
        return getDonations().size();
    }

    public void setNumberOfDonations(int numberOfDonations) {
        this.numberOfDonations = numberOfDonations;
    }

    @Override
    public Object create() {

        String userQuery = "INSERT INTO public.\"User\" (\"FirstName\", \"LastName\", \"Email\", \"Password\", \"Role\") VALUES (?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.getFirstName());
            stmt.setString(2, this.getLastName());
            stmt.setString(3, this.getEmail());
            stmt.setString(4, this.getPassword());
            stmt.setInt(5, UserType.ONLINEDONOR.getRoleId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.setUserID(rs.getInt("UserID"));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error creating donor", e);
        }

        String donorQuery = "INSERT INTO public.\"OnlineDonor\" (\"OnlineDonorID\", \"NumberofDonations\") VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(donorQuery)) {
            stmt.setInt(1, this.getUserID());
            stmt.setInt(2, this.getNumberOfDonations());

            stmt.executeUpdate();

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error creating donor", e);
        }

        return this;

    }

    @Override
    public Object update(Object updatedObject) {

        if (!(updatedObject instanceof User)) {
            throw new IllegalArgumentException("Invalid object type");
        }

        OnlineDonor updatedDonor = (OnlineDonor) updatedObject;

        String userQuery = "UPDATE public.\"User\" SET \"FirstName\" = ?, \"LastName\" = ?, \"Email\" = ?, \"Password\" = ? WHERE \"UserID\" = ? AND \"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(userQuery)) {
            stmt.setString(1, updatedDonor.getFirstName());
            stmt.setString(2, updatedDonor.getLastName());
            stmt.setString(3, updatedDonor.getEmail());
            stmt.setString(4, updatedDonor.getPassword());
            stmt.setInt(5, updatedDonor.getUserID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error updating donor", e);
        }

        String menteeQuery = "UPDATE public.\"OnlineDonor\" SET \"NumberofDonations\" = ? WHERE \"OnlineDonorID\" = ?";
        try (PreparedStatement adminStmt = conn.prepareStatement(menteeQuery)) {
            adminStmt.setInt(1, updatedDonor.getNumberOfDonations());
            adminStmt.setInt(3, updatedDonor.getUserID());

            adminStmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error updating donor", e);
        }

        return updatedDonor;

    }

    @Override
    public Object read(int id) {

        String sqlQuery = "SELECT u.\"UserID\", u.\"FirstName\", u.\"LastName\", u.\"Email\", u.\"Password\", d.\"NumberofDonations\" FROM public.\"OnlineDonor\" d JOIN public.\"User\" u ON d.\"OnlineDonorID\" = u.\"UserID\" WHERE u.\"UserID\" = ? AND u.\"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userID = rs.getInt("UserID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                int donations = rs.getInt("NumberofDonations");

                OnlineDonor od = new OnlineDonor(firstName, lastName, email, password);
                od.setNumberOfDonations(donations);
                od.setUserID(userID);
                return od;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading donor", e);
        }
        return null;

    }

    @Override
    public List<Object> readAll() {

        List<Object> donors = new ArrayList<>();
        String sqlQuery = "SELECT u.\"UserID\", u.\"FirstName\", u.\"LastName\", u.\"Email\", u.\"Password\", d.\"NumberofDonations\" FROM public.\"OnlineDonor\" d JOIN public.\"User\" u ON d.\"OnlineDonorID\" = u.\"UserID\" WHERE u.\"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                int donations = rs.getInt("NumberofDonations");

                OnlineDonor od = new OnlineDonor(firstName, lastName, email, password);
                od.setNumberOfDonations(donations);
                od.setUserID(userID);
                donors.add(od);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading all online donors", e);
        }

        return donors;
    }


    public List<OnlineDonation> getDonations() {
        List<OnlineDonation> donations = new ArrayList<>();

        String sqlQuery = "SELECT o.\"DonationID\", o.\"Amount\", o.\"PaymentType\" " +
                "FROM public.\"OnlineDonation\" o " +
                "JOIN public.\"ODD_Makes\" d ON o.\"DonationID\" = d.\"DonationID\" " +
                "WHERE d.\"OnlineDonorID\" = ? AND o.\"IsDeleted\" = False ";

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setInt(1, this.getUserID());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int donationID = rs.getInt("DonationID");
                double amount = rs.getDouble("Amount");
                int paymentTypeOrdinal = rs.getInt("PaymentType");

                // Convert the int (ordinal) to the PaymentType enum
                PaymentType paymentType = PaymentType.values()[paymentTypeOrdinal];

                OnlineDonation donation = new OnlineDonation(paymentType, amount);
                donation.setDonationID(donationID);
                donations.add(donation);
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException("Error retrieving donations", e);
        }

        return donations;
    }

}
