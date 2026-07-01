package com.travelaround.controller;

import com.travelaround.config.DBConnection;
import com.travelaround.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private Connection conn;

    public UserController() {
        // Automatically fetch our open database connection
        this.conn = DBConnection.getConnection();
    }

    /**
     * Verifies username and password against the MySQL database.
     * Returns a full User object if successful, or null if credentials fail.
     */
    public User loginUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Turn database row data into a clean Java Object
                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Login System Error: " + e.getMessage());
        }
        return null; // Credential verification failed
    }
}