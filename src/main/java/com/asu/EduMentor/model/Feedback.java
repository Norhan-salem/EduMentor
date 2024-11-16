package com.asu.EduMentor.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Feedback implements CRUD {
    private long feedbackID;
    private String comment;
    private short rating;
    private boolean isDeleted = false;


    public Feedback(String comment, short rating) {
        this.comment = comment;
        this.rating = rating;
    }


    public long getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(long feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        this.rating = rating;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public Object create() {

        String sqlQuery = "INSERT INTO public.\"Feedback\" (\"Comment\", \"Stars\", \"IsDeleted\") VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.getComment());
            stmt.setShort(2, this.getRating());
            stmt.setBoolean(3, this.isDeleted());


            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.setFeedbackID(rs.getLong("FeedbackID"));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error creating feedback", e);
        }

        return this;

    }

    @Override
    public Object update(Object updatedObject) {

        if (!(updatedObject instanceof Feedback updatedFeedback)) {
            throw new IllegalArgumentException("Invalid object type");
        }

        String sqlQuery = "UPDATE public.\"Feedback\" SET \"Comment\" = ?, \"Stars\" = ? WHERE \"FeedbackID\" = ? AND \"IsDeleted\" = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setString(1, updatedFeedback.getComment());
            stmt.setShort(2, updatedFeedback.getRating());
            stmt.setLong(3, updatedFeedback.getFeedbackID());


            stmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error updating feedback", e);
        }

        return updatedFeedback;

    }

    @Override
    public Object read(int id) {

        String sqlQuery = "SELECT * FROM public.\"Feedback\" WHERE \"FeedbackID\" = ? AND \"IsDeleted\" = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long FeedbackID = rs.getLong("FeedbackID");
                String comment = rs.getString("Comment");
                short rating = rs.getShort("Stars");

                Feedback f = new Feedback(comment, rating);
                f.setFeedbackID(FeedbackID);
                return f;
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading feedback", e);
        }

        return null;

    }

    @Override
    public List<Object> readAll() {

        List<Object> feedbacks = new ArrayList<>();
        String sqlQuery = "SELECT * FROM public.\"Feedback\" WHERE \"IsDeleted\" = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long FeedbackID = rs.getLong("FeedbackID");
                String comment = rs.getString("Comment");
                short rating = rs.getShort("Stars");


                Feedback f = new Feedback(comment, rating);
                f.setFeedbackID(FeedbackID);
                feedbacks.add(f);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading all feedbacks", e);
        }

        return feedbacks;

    }

    @Override
    public boolean delete(int id) {

        String sqlQuery = "UPDATE public.\"Feedback\" SET \"IsDeleted\" = TRUE WHERE \"FeedbackID\" = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error deleting feedback", e);
        }

    }
}
