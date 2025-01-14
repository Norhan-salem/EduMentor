package com.asu.EduMentor.model;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final Logger log = LoggerFactory.getLogger(DBConnection.class);
    private static volatile DBConnection instance;

    private static final Dotenv dotenv = Dotenv.load();

    private final String url = dotenv.get("DB_URL");
    private final String username = dotenv.get("DB_USERNAME");
    private final String password = dotenv.get("DB_PASSWORD");
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
            log.info(url);
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