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
 * in the database based on their role.
 */
public class UserTopics {

    /**
     * Adds a topic to the specified user's list of topics in the database based on their role.
     * If the user is a mentor, adds to the MentorTopics table.
     * If the user is a mentee, adds to the MenteeTopics table.
     *
     * @param topic The {@link Topics} instance to be associated with the user.
     * @param user  The {@link User} instance to which the topic will be added.
     * @return True if the topic was successfully added, false otherwise.
     * @throws RuntimeException if a database error occurs while adding the topic.
     */
    public boolean addTopic(Topics topic, User user) {
        String roleBasedInsertQuery;

        if (user.getRole() == UserType.MENTOR) {
            roleBasedInsertQuery = "INSERT INTO public.\"MT_InterestedIn\" (\"MentorID\", \"TopicsID\", \"IsDeleted\") VALUES (?, ?, FALSE)";
        } else if (user.getRole() == UserType.MENTEE) {
            roleBasedInsertQuery = "INSERT INTO public.\"MTT_InterestedIn\" (\"MenteeID\", \"TopicsID\", \"IsDeleted\") VALUES (?, ?, FALSE)";
        } else {
            throw new IllegalArgumentException("User must be a mentor or mentee to add a topic.");
        }

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(roleBasedInsertQuery)) {

            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, topic.getTopicID());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error adding topic to user", e);
        }
    }

    /**
     * Soft-deletes a topic from the specified user's list of topics in the database based on their role.
     * If the user is a mentor, updates the isDeleted flag in the MentorTopics table.
     * If the user is a mentee, updates the isDeleted flag in the MenteeTopics table.
     *
     * @param topic The {@link Topics} instance to be removed from the user's topics.
     * @param user  The {@link User} instance from whom the topic will be removed.
     * @return True if the topic was successfully soft-deleted, false otherwise.
     * @throws RuntimeException if a database error occurs while deleting the topic.
     */
    public boolean deleteTopic(Topics topic, User user) {
        String roleBasedDeleteQuery;

        if (user.getRole() == UserType.MENTOR) {
            roleBasedDeleteQuery = "UPDATE public.\"MT_InterestedIn\" SET \"IsDeleted\" = TRUE WHERE \"MentorID\" = ? AND \"TopicsID\" = ?";
        } else if (user.getRole() == UserType.MENTEE) {
            roleBasedDeleteQuery = "UPDATE public.\"MTT_InterestedIn\" SET \"IsDeleted\" = TRUE WHERE \"MenteeID\" = ? AND \"TopicsID\" = ?";
        } else {
            throw new IllegalArgumentException("User must be a mentor or mentee to delete a topic.");
        }

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(roleBasedDeleteQuery)) {

            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, topic.getTopicID());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error soft-deleting topic from user", e);
        }
    }

    /**
     * Retrieves all non-deleted topics associated with a specific user from the database based on their role.
     * If the user is a mentor, retrieves topics from the MentorTopics table.
     * If the user is a mentee, retrieves topics from the MenteeTopics table.
     *
     * @param user The {@link User} instance whose topics are to be retrieved.
     * @return A list of {@link Topics} associated with the specified user.
     * @throws RuntimeException if a database error occurs while retrieving topics.
     */
    public List<Topics> getUserTopics(User user) {
        List<Topics> topics = new ArrayList<>();

        String roleBasedQuery;
        if (user.getRole() == UserType.MENTOR) {
            roleBasedQuery = "SELECT t.\"TopicsID\", t.\"TopicsName\" " +
                    "FROM public.\"Topics\" t " +
                    "JOIN public.\"MT_InterestedIn\" mt ON t.\"TopicsID\" = mt.\"TopicsID\" " +
                    "WHERE mt.\"MentorID\" = ? AND mt.\"IsDeleted\" = FALSE AND t.\"IsDeleted\" = FALSE";
        } else if (user.getRole() == UserType.MENTEE) {
            roleBasedQuery = "SELECT t.\"TopicsID\", t.\"TopicsName\" " +
                    "FROM public.\"Topics\" t " +
                    "JOIN public.\"MTT_InterestedIn\" mt ON t.\"TopicsID\" = mt.\"TopicsID\" " +
                    "WHERE mt.\"MenteeID\" = ? AND mt.\"IsDeleted\" = FALSE AND t.\"IsDeleted\" = FALSE";
        } else {
            throw new IllegalArgumentException("User must be a mentor or mentee to retrieve topics.");
        }

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(roleBasedQuery)) {

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


