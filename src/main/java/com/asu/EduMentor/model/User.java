package com.asu.EduMentor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements CRUD {

    private int userID;
    private String firstName;
    private String lastName;
    private UserType role;
    private String email;
    private String password;
    private boolean isDeleted = false;

    public User(String firstName, String lastName, UserType role, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public static User findByEmail(String email) {
        String sql = "SELECT * FROM public.\"User\" WHERE \"Email\" = ? AND \"IsDeleted\" = FALSE";
        try (PreparedStatement stmt = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email", e);
        }
        return null;
    }

    public static List<User> findUsersBySearchTerm(String search) {
        List<User> userList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM public.\"User\" WHERE " +
                "(\"FirstName\" ILIKE ? OR \"LastName\" ILIKE ? OR \"Email\" ILIKE ?) AND \"IsDeleted\" = FALSE";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            String searchPattern = "%" + search + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                userList.add(createUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching for users", e);
        }

        return userList;
    }

    private static User createUserFromResultSet(ResultSet rs) throws SQLException {
        UserType role = UserType.fromRoleId(Integer.parseInt(rs.getString("Role")));

        User user = switch (role) {
            case ADMIN -> new Admin(rs.getString("FirstName"), rs.getString("LastName"),
                    rs.getString("Email"), rs.getString("Password"), rs.getBoolean("Status"));
            case MENTOR -> new Mentor(rs.getString("FirstName"), rs.getString("LastName"),
                    rs.getString("Email"), rs.getString("Password"), 0);
            case MENTEE -> new Mentee(rs.getString("FirstName"), rs.getString("LastName"),
                    rs.getString("Email"), rs.getString("Password"),
                    0, 0);
            case ONLINEDONOR -> new OnlineDonor(rs.getString("FirstName"), rs.getString("LastName"),
                    rs.getString("Email"), rs.getString("Password"), 0);
            default -> throw new IllegalArgumentException("Invalid role in database");
        };

        user.setUserID(rs.getInt("UserID"));
        return user;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public abstract Object create();

    @Override
    public abstract Object update(Object updatedObject);

    @Override
    public abstract Object read(int id);

    @Override
    public abstract List<Object> readAll();

    @Override
    public boolean delete(int id) {
        String sqlQuery = "UPDATE public.\"User\" SET \"IsDeleted\" = TRUE WHERE \"UserID\" = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error deleting admin", e);
        }
    }

}
