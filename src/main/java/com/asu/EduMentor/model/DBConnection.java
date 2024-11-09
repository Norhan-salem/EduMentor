package com.asu.EduMentor.model;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static volatile DBConnection instance;
    private final String url = "jdbc:postgresql://localhost:5432/EDUMentor"; // Update with your database
    private final String username = "admin"; // Update with your username
    private final String password = "admin"; // Update with your password
    // Method to get the database connection
    //  variables
    @Getter
    private final Connection connection;

    private DBConnection() {
        // Initialize the database connection
        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            // Establish the connection
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new RuntimeException("PostgreSQL Driver not found.", e);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }


}