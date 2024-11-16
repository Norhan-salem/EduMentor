package com.asu.EduMentor.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Topics implements CRUD {

    private int topicID;
    private String topicsName;
    private boolean isDeleted = false;

    public Topics(String topicsName) {
        this.topicsName = topicsName;
    }


    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicsName() {
        return topicsName;
    }

    public void setTopicsName(String topicsName) {
        this.topicsName = topicsName;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public Object create() {

        String sqlQuery = "INSERT INTO public.\"Topics\" (\"TopicsName\", \"IsDeleted\") VALUES (?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.getTopicsName());
            stmt.setBoolean(2, this.isDeleted());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.setTopicID(rs.getInt("TopicsID"));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error creating topic", e);
        }

        return this;

    }

    @Override
    public Object update(Object updatedObject) {

        if (!(updatedObject instanceof Topics updatedTopic)) {
            throw new IllegalArgumentException("Invalid object type");
        }

        String sqlQuery = "UPDATE public.\"Topics\" SET \"TopicsName\" = ? WHERE \"TopicsID\" = ? AND \"IsDeleted\" = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setString(1, updatedTopic.getTopicsName());
            stmt.setInt(2, updatedTopic.getTopicID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error updating topic", e);
        }

        return updatedTopic;

    }

    @Override
    public Object read(int id) {

        String sqlQuery = "SELECT * FROM public.\"Topics\" WHERE \"TopicsID\" = ? AND \"IsDeleted\" = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int topicID = rs.getInt("TopicsID");
                String name = rs.getString("TopicsName");

                Topics t = new Topics(name);
                t.setTopicID(topicID);
                return t;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading topic", e);
        }
        return null;

    }

    @Override
    public List<Object> readAll() {

        List<Object> topics = new ArrayList<>();
        String sqlQuery = "SELECT * FROM public.\"Topics\" WHERE \"IsDeleted\" = FALSE";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int topicID = rs.getInt("TopicsID");
                String name = rs.getString("TopicsName");

                Topics t = new Topics(name);
                t.setTopicID(topicID);
                topics.add(t);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading all topics", e);
        }

        return topics;

    }

    @Override
    public boolean delete(int id) {

        String sqlQuery = "UPDATE public.\"Topics\" SET \"IsDeleted\" = TRUE WHERE \"TopicsID\" = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error deleting topic", e);
        }

    }
}
