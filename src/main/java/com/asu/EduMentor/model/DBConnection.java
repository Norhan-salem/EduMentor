package com.asu.EduMentor.model;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static volatile DBConnection instance;
    private final String url = System.getenv("DB_URL");
    private final String username = System.getenv("DB_USERNAME");;
    private final String password = System.getenv("DB_PASSWORD");
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
            synchronized (DBConnection.class) {//by using synchronized we garantee that no 2
                                               //threads can enter the method at the same time
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

}