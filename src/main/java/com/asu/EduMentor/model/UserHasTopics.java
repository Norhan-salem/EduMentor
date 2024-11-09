package com.asu.EduMentor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the relationship between a {@link User} and {@link Topics}.
 * It provides methods to add, delete, and retrieve topics associated with a specific user
 * in the database.
 */
public class UserHasTopics {

    /**
     * Adds a topic to the specified user's list of topics in the database.
     *
     * @param topic The {@link Topics} instance to be associated with the user.
     * @param user The {@link User} instance to which the topic will be added.
     * @return True if the topic was successfully added, false otherwise.
     * @throws RuntimeException if a database error occurs while adding the topic.
     */
    public boolean addTopic(Topics topic, User user) {
        String sqlQuery = "INSERT INTO public.\"UserTopics\" (\"UserID\", \"TopicsID\") VALUES (?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, topic.getTopicID());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding topic to user", e);
        }
    }

    /**
     * Deletes a topic from the specified user's list of topics in the database.
     *
     * @param topic The {@link Topics} instance to be removed from the user's topics.
     * @param user The {@link User} instance from whom the topic will be removed.
     * @return True if the topic was successfully deleted, false otherwise.
     * @throws RuntimeException if a database error occurs while deleting the topic.
     */
    public boolean deleteTopic(Topics topic, User user) {
        String sqlQuery = "DELETE FROM public.\"UserTopics\" WHERE \"UserID\" = ? AND \"TopicsID\" = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, topic.getTopicID());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting topic from user", e);
        }
    }

    /**
     * Retrieves all topics associated with a specific user from the database.
     *
     * @param user The {@link User} instance whose topics are to be retrieved.
     * @return A list of {@link Topics} associated with the specified user.
     * @throws RuntimeException if a database error occurs while retrieving topics.
     */
    public List<Topics> getUserTopics(User user) {
        List<Topics> topics = new ArrayList<>();
        String sqlQuery = "SELECT t.\"TopicsID\", t.\"TopicsName\" " +
                "FROM public.\"Topics\" t " +
                "JOIN public.\"UserTopics\" ut ON t.\"TopicsID\" = ut.\"TopicsID\" " +
                "WHERE ut.\"UserID\" = ? AND t.\"isDeleted\" = FALSE";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setInt(1, user.getUserID());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int topicID = rs.getInt("TopicsID");
                String topicsName = rs.getString("TopicsName");

                Topics topic = new Topics(topicsName);
                topic.setTopicID(topicID);
                topics.add(topic);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user topics", e);
        }

        return topics;
    }
}
