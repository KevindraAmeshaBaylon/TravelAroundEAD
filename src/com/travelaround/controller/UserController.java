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

        // Method to count total rows in any given table dynamically
    public int getSystemCount(String tableName) {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Analytics error for " + tableName + ": " + e.getMessage());
        }
        return 0;
    }

    // Method to sum total booking revenue generated 
    public double getTotalRevenue() {
        String query = "SELECT SUM(total_cost) FROM Bookings WHERE booking_status = 'Confirmed'";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Revenue calculation error: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Registers a new customer into the database.
     * Sets the default role to 'Customer'.
     */
    public boolean registerCustomer(String username, String password, String email, String phone) {
        String query = "INSERT INTO Users (username, password, email, phone, role) VALUES (?, ?, ?, ?, 'Customer')";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, phone);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Registration System Error: " + e.getMessage());
            return false;
        }
    }
}