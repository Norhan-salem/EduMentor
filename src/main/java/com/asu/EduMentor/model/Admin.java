package com.asu.EduMentor.model;

import com.asu.EduMentor.model.profile.template.AdminProfileTemplate;
import com.asu.EduMentor.model.profile.template.IProfileTemplate;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class Admin extends User {

    private boolean status;

    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, UserType.ADMIN, email, password);
        this.status = false;
    }

    public Admin(String firstName, String lastName, String email, String password, boolean status) {
        super(firstName, lastName, UserType.ADMIN, email, password);
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public IProfileTemplate getProfileTemplate() {
        return new AdminProfileTemplate();
    }

    @Override
    public Map<String, Object> getProfileData() {
        IProfileTemplate template = getProfileTemplate();
        Map<String, Object> profileData = template.setupProfile(this.getUserID());
        return profileData;
    }

    @Override
    public Admin create() {

        String userQuery = "INSERT INTO public.\"User\" (\"FirstName\", \"LastName\", \"Email\", \"Password\", \"Role\") VALUES (?, ?, ?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.getFirstName());
            stmt.setString(2, this.getLastName());
            stmt.setString(3, this.getEmail());
            stmt.setString(4, this.getPassword());
            stmt.setInt(5, UserType.ADMIN.getRoleId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.setUserID(rs.getInt("UserID"));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error creating user", e);
        }

        String adminQuery = "INSERT INTO public.\"Admin\" (\"AdminID\", \"Status\") VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(adminQuery)) {
            stmt.setInt(1, this.getUserID());
            stmt.setBoolean(2, this.status);

            stmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error creating admin", e);
        }

        return this;
    }

    @Override
    public Object update(Object updatedObject) {
        if (!(updatedObject instanceof User)) {
            throw new IllegalArgumentException("Invalid object type");
        }

        Admin updatedAdmin = (Admin) updatedObject;

        String userQuery = "UPDATE public.\"User\" SET \"FirstName\" = ?, \"LastName\" = ?, \"Email\" = ?, \"Password\" = ? WHERE \"UserID\" = ? AND \"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(userQuery)) {
            stmt.setString(1, updatedAdmin.getFirstName());
            stmt.setString(2, updatedAdmin.getLastName());
            stmt.setString(3, updatedAdmin.getEmail());
            stmt.setString(4, updatedAdmin.getPassword());
            stmt.setInt(5, updatedAdmin.getUserID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error updating admin", e);
        }

        String adminQuery = "UPDATE public.\"Admin\" SET \"Status\" = ? WHERE \"AdminID\" = ?";
        try (PreparedStatement adminStmt = conn.prepareStatement(adminQuery)) {
            adminStmt.setBoolean(1, updatedAdmin.isStatus());
            adminStmt.setInt(2, updatedAdmin.getUserID());

            adminStmt.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error updating admin", e);
        }

        return updatedAdmin;
    }

    @Override
    public Object read(int id) {
        String sqlQuery = "SELECT u.\"UserID\", u.\"FirstName\", u.\"LastName\", u.\"Email\", u.\"Password\", a.\"Status\" FROM public.\"Admin\" a JOIN public.\"User\" u ON a.\"AdminID\" = u.\"UserID\" WHERE u.\"UserID\" = ? AND u.\"IsDeleted\" = FALSE";
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
                boolean status = rs.getBoolean("Status");

                Admin a = new Admin(firstName, lastName, email, password, status);
                a.setUserID(userID);
                return a;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading admin", e);
        }
        return null;
    }

    @Override
    public List<Object> readAll() {
        List<Object> admins = new ArrayList<>();
        String sqlQuery = "SELECT u.\"UserID\", u.\"FirstName\", u.\"LastName\", u.\"Email\", u.\"Password\", a.\"Status\" FROM public.\"Admin\" a JOIN public.\"User\" u ON a.\"AdminID\" = u.\"UserID\" WHERE u.\"IsDeleted\" = FALSE";
        Connection conn = DBConnection.getInstance().getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                boolean status = rs.getBoolean("Status");


                Admin admin = new Admin(firstName, lastName, email, password, status);
                admin.setUserID(userID);
                admins.add(admin);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Error reading all admins", e);
        }

        return admins;
    }

}
