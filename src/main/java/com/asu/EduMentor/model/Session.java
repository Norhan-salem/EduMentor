package com.asu.EduMentor.model;

import lombok.NoArgsConstructor;

import com.asu.EduMentor.model.SessionStateDP.CompletedState;
import com.asu.EduMentor.model.SessionStateDP.ISessionState;
import com.asu.EduMentor.model.SessionStateDP.OngoingState;
import com.asu.EduMentor.model.SessionStateDP.ScheduleState;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class Session implements CRUD {

    private long sessionID;
    private Date date;
    private double duration;
    private String name;
    private boolean isDeleted = false;

    ISessionState sessionState;

    public Session(Date date, double duration, String name) {
        this.date = date;
        this.duration = duration;
        this.name = name;
        sessionState = fetchDate(date);
    }

    public String getState() {
        return sessionState.getStateName();
    }

    public void setSessionState(ISessionState sessionState) {
       this.sessionState = sessionState;
    }
    public ISessionState fetchDate(Date currentDate){
        long sessionStartTime = getDate().getTime();
        long sessionEndTime = sessionStartTime + (long) (getDuration() * 3600 * 1000);
        long currentTime = currentDate.getTime();

        if (currentTime < sessionStartTime) {
            return new ScheduleState();
        } else if (currentTime >= sessionStartTime && currentTime < sessionEndTime) {
            return new OngoingState();
        }
        return new CompletedState();
    }
    public static List<Session> findSessionsBySearchTerm(String search) {
        List<Session> sessions = new ArrayList<>();
        String sqlQuery = "SELECT * FROM public.\"Session\" WHERE " +
                "(\"SessionName\" ILIKE ? OR TO_CHAR(\"Date\", 'YYYY-MM-DD') ILIKE ?) AND \"IsDeleted\" = FALSE";

        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            String searchPattern = "%" + search + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long sessionID = rs.getLong("SessionID");
                Date date = rs.getDate("Date");
                double duration = rs.getDouble("Duration");
                String name = rs.getString("SessionName");

                Session session = new Session(date, duration, name);
                session.setSessionID(sessionID);
                sessions.add(session);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching for sessions", e);
        }

        return sessions;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Object create2(int AdminId) {
        String sessionQuery = "INSERT INTO public.\"Session\" (\"Date\", \"Duration\", \"SessionName\", \"IsDeleted\") VALUES (?, ?, ?, ?)";
        String asMakesQuery = "INSERT INTO public.\"AS_Makes\" (\"AdminID\", \"SessionID\") VALUES (?, ?)";

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);
            long generatedSessionId;
            try (PreparedStatement sessionStmt = conn.prepareStatement(sessionQuery, Statement.RETURN_GENERATED_KEYS)) {
                sessionStmt.setDate(1, new java.sql.Date(this.date.getTime()));
                sessionStmt.setDouble(2, this.duration);
                sessionStmt.setString(3, this.name);
                sessionStmt.setBoolean(4, this.isDeleted);

                sessionStmt.executeUpdate();

                ResultSet rs = sessionStmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedSessionId = rs.getLong("SessionID");
                    this.sessionID = generatedSessionId;
                } else {
                    throw new SQLException("Failed to retrieve SessionID");
                }
            }

            try (PreparedStatement asMakesStmt = conn.prepareStatement(asMakesQuery)) {
                asMakesStmt.setInt(1, AdminId);
                asMakesStmt.setLong(2, generatedSessionId);
                asMakesStmt.executeUpdate();
            }
            conn.commit();
            return this;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error rolling back transaction", rollbackEx);
            }
            throw new RuntimeException("Error creating session with admin association", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error resetting autocommit", e);
            }
        }
    }

    @Override
    public Object create() {

        String sqlQuery = "INSERT INTO public.\"Session\" (\"Date\", \"Duration\", \"SessionName\", \"IsDeleted\") VALUES (?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, new Timestamp(this.date.getTime()));
            stmt.setDouble(2, this.duration);
            stmt.setString(3, this.name);
            stmt.setBoolean(4, this.isDeleted);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.sessionID = rs.getLong("SessionID");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error creating session", e);
        }
        return this;
    }

    @Override
    public Object update(Object updatedObject) {

        if (!(updatedObject instanceof Session updatedSession)) {
            throw new IllegalArgumentException("Invalid object type");
        }

        String sqlQuery = "UPDATE public.\"Session\" SET \"Date\" = ?, \"Duration\" = ?, \"SessionName\" = ? WHERE \"SessionID\" = ? AND \"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setTimestamp(1, new Timestamp(updatedSession.getDate().getTime()));
            stmt.setDouble(2, updatedSession.getDuration());
            stmt.setString(3, updatedSession.getName());
            stmt.setLong(4, updatedSession.getSessionID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error updating session", e);
        }
        return updatedSession;
    }

    @Override
    public Object read(int id) {

        String sqlQuery = "SELECT * FROM public.\"Session\" WHERE \"SessionID\" = ? AND \"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                java.util.Date date = rs.getTimestamp("Date");
                double duration = rs.getDouble("Duration");
                String name = rs.getString("SessionName");

                Session s = new Session(date, duration, name);
                s.setSessionID(rs.getLong("SessionID"));
                return s;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading session", e);
        }
        return null;
    }

    @Override
    public List<Object> readAll() {

        List<Object> sessions = new ArrayList<>();
        String sqlQuery = "SELECT * FROM public.\"Session\" WHERE \"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long sessionID = rs.getLong("SessionID");
                java.util.Date date = rs.getTimestamp("Date");
                double duration = rs.getDouble("Duration");
                String name = rs.getString("SessionName");

                Session s = new Session(date, duration, name);
                s.setSessionID(sessionID);
                sessions.add(s);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading all sessions", e);
        }

        return sessions;

    }

    @Override
    public boolean delete(int id) {

        String sqlQuery = "UPDATE public.\"Session\" SET \"IsDeleted\" = TRUE WHERE \"SessionID\" = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error deleting session", e);
        }

    }

    public List<Feedback> getSessionFeedback() {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT f.* FROM public.\"Feedback\" f " +
                "INNER JOIN public.\"FS_Has\" fsh ON f.\"FeedbackID\" = fsh.\"FeedbackID\" " +
                "WHERE fsh.\"SessionID\" = ? AND f.\"IsDeleted\" = false " +
                "ORDER BY f.\"FeedbackID\" ASC";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, this.sessionID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long feedbackID = rs.getLong("FeedbackID");
                String comment = rs.getString("Comment");
                short rating = rs.getShort("Stars");
                Feedback f = new Feedback(comment, rating);
                f.setFeedbackID(feedbackID);
                feedbacks.add(f);
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error retrieving feedbacks", e);
        }
        return feedbacks;
    }

    public List<Mentor> getSessionMentors() {
        List<Mentor> mentors = new ArrayList<>();

        String sqlQuery = "SELECT u.\"UserID\", u.\"FirstName\", u.\"LastName\", u.\"Email\", u.\"Password\"" +
                "FROM public.\"User\" u " +
                "JOIN public.\"Mentor\" m ON u.\"UserID\" = m.\"MentorID\" " +
                "JOIN public.\"SM_Gives\" sg ON m.\"MentorID\" = sg.\"MentorID\" " +
                "WHERE sg.\"SessionID\" = ?;";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setLong(1, this.sessionID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int mentorID = rs.getInt("UserID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");

                Mentor m = new Mentor(firstName, lastName, email, password);
                m.setUserID(mentorID);
                mentors.add(m);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error retrieving mentors", e);
        }
        return mentors;
    }

    public List<Mentee> getSessionMentees() {
        List<Mentee> mentees = new ArrayList<>();
        String sqlQuery = "SELECT u.\"UserID\", u.\"FirstName\", u.\"LastName\", u.\"Email\", u.\"Password\"," +
                "m.\"NumberOfAttandedSessions\", m.\"LearningHours\" " +
                "FROM public.\"User\" u " +
                "JOIN public.\"Mentee\" m ON u.\"UserID\" = m.\"MenteeID\" " +
                "JOIN public.\"SMTT_Takes\" st ON m.\"MenteeID\" = st.\"MenteeID\" " +
                "WHERE st.\"SessionID\" = ?";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setLong(1, this.sessionID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int menteeID = rs.getInt("UserID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                int numberOfAttendedSessions = rs.getInt("NumberOfAttandedSessions");
                double learningHours = rs.getDouble("LearningHours");

                Mentee m = new Mentee(firstName, lastName, email, password, numberOfAttendedSessions, learningHours);
                m.setUserID(menteeID);
                mentees.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving mentees", e);
        }
        return mentees;
    }

    public boolean addMentor(Mentor mentor) {
        String sqlQuery = "INSERT INTO public.\"SM_Gives\" (\"SessionID\", \"MentorID\") VALUES (?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setLong(1, this.sessionID);
            stmt.setInt(2, mentor.getUserID());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error adding mentor to session", e);
        }
    }

    public boolean addMentee(Mentee mentee) {
        String sqlQuery = "INSERT INTO public.\"SMTT_Takes\" (\"SessionID\", \"MenteeID\") VALUES (?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setLong(1, this.sessionID);
            stmt.setInt(2, mentee.getUserID());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error adding mentee to session", e);
        }
    }

}
