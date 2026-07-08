package com.travelaround.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Standard XAMPP settings. Note: update port to 3306 or yours if modified
    private static final String URL = "jdbc:mysql://localhost:3306/travelaround_db"; 
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            // FIXED: If connection is null OR if it was previously closed, create a fresh one!
            if (connection == null || connection.isClosed()) {
                // Load the modern MySQL Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(">>> Database Connected Successfully! <<<");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(">>> Database Connection Failed: " + e.getMessage());
        }
        return connection;
    }
}