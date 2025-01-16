package com.asu.EduMentor.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Feedback implements CRUD {
    private long feedbackID;
    private String comment;
    private short rating;
    private boolean isDeleted = false;
    private int sessionId;
    private int menteeId;


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
        String feedbackQuery = "INSERT INTO public.\"Feedback\" (\"Comment\", \"Stars\", \"IsDeleted\") VALUES (?, ?, ?)";
        String fsHasQuery = "INSERT INTO public.\"FS_Has\" (\"FeedbackID\", \"SessionID\") VALUES (?, ?)";
        String fmGivesQuery = "INSERT INTO public.\"FM_Gives\" (\"FeedbackID\", \"MenteeID\") VALUES (?, ?)";

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            long feedbackId;
            try (PreparedStatement feedbackStmt = conn.prepareStatement(feedbackQuery, Statement.RETURN_GENERATED_KEYS)) {
                feedbackStmt.setString(1, this.getComment());
                feedbackStmt.setShort(2, this.getRating());
                feedbackStmt.setBoolean(3, this.isDeleted());

                feedbackStmt.executeUpdate();

                ResultSet rs = feedbackStmt.getGeneratedKeys();
                if (rs.next()) {
                    feedbackId = rs.getLong("FeedbackID");
                    this.setFeedbackID(feedbackId);
                } else {
                    throw new SQLException("Failed to retrieve FeedbackID.");
                }
            }

            try (PreparedStatement fsHasStmt = conn.prepareStatement(fsHasQuery)) {
                fsHasStmt.setLong(1, feedbackId);
                fsHasStmt.setInt(2, this.sessionId);
                fsHasStmt.executeUpdate();
            }

            try (PreparedStatement fmGivesStmt = conn.prepareStatement(fmGivesQuery)) {
                fmGivesStmt.setLong(1, feedbackId);
                fmGivesStmt.setInt(2, this.menteeId);
                fmGivesStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error rolling back transaction", rollbackEx);
            }
            throw new RuntimeException("Error creating feedback and associations", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error resetting autocommit", e);
            }
        }

        return this;
    }

    @Override
    public Object update(Object updatedObject) {

        if (!(updatedObject instanceof Feedback updatedFeedback)) {
            throw new IllegalArgumentException("Invalid object type");
        }

        String sqlQuery = "UPDATE public.\"Feedback\" SET \"Comment\" = ?, \"Stars\" = ? WHERE \"FeedbackID\" = ? AND \"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
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
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
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
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
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
        String feedbackQuery = "UPDATE public.\"Feedback\" SET \"IsDeleted\" = TRUE WHERE \"FeedbackID\" = ?";
        String fsHasQuery = "DELETE FROM public.\"FS_Has\" WHERE \"FeedbackID\" = ?";
        String fmGivesQuery = "DELETE FROM public.\"FM_Gives\" WHERE \"FeedbackID\" = ?";

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement fsHasStmt = conn.prepareStatement(fsHasQuery)) {
                fsHasStmt.setLong(1, id);
                fsHasStmt.executeUpdate();
            }

            try (PreparedStatement fmGivesStmt = conn.prepareStatement(fmGivesQuery)) {
                fmGivesStmt.setLong(1, id);
                fmGivesStmt.executeUpdate();
            }

            int rowsAffected;
            try (PreparedStatement feedbackStmt = conn.prepareStatement(feedbackQuery)) {
                feedbackStmt.setLong(1, id);
                rowsAffected = feedbackStmt.executeUpdate();
            }

            conn.commit();
            return rowsAffected > 0;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error rolling back transaction", rollbackEx);
            }
            throw new RuntimeException("Error deleting feedback", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error resetting autocommit", e);
            }
        }
    }

}
