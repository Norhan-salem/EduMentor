package com.asu.EduMentor.model;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static volatile DBConnection instance;
    private final String url = "jdbc:postgresql://sdp-db.ct0m6ak00bfv.eu-north-1.rds.amazonaws.com/EduMentor";
    private final String username = "";
    private final String password = "";
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