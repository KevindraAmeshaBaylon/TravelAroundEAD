package com.travelaround.controller;

import com.travelaround.config.DBConnection;
import com.travelaround.model.Hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private Connection conn;

    public HotelController() {
        this.conn = DBConnection.getConnection();
    }

    // CREATE: Add a new hotel entry
    public boolean addHotel(String name, String location, double rating, int managerId) {
        String query = "INSERT INTO Hotels (name, location, rating, manager_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, location);
            ps.setDouble(3, rating);
            ps.setInt(4, managerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding hotel: " + e.getMessage());
            return false;
        }
    }

    // READ: Retrieve all hotels to populate our visual tables
    public List<Hotel> getAllHotels() {
        List<Hotel> list = new ArrayList<>();
        String query = "SELECT * FROM Hotels";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                list.add(new Hotel(
                    rs.getInt("hotel_id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getDouble("rating"),
                    rs.getInt("manager_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching hotels: " + e.getMessage());
        }
        return list;
    }

    // UPDATE: Modify an existing hotel entry
    public boolean updateHotel(int hotelId, String name, String location, double rating) {
        String query = "UPDATE Hotels SET name = ?, location = ?, rating = ? WHERE hotel_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, location);
            ps.setDouble(3, rating);
            ps.setInt(4, hotelId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating hotel: " + e.getMessage());
            return false;
        }
    }

    // DELETE: Remove a hotel entry completely
    public boolean deleteHotel(int hotelId) {
        String query = "DELETE FROM Hotels WHERE hotel_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, hotelId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting hotel: " + e.getMessage());
            return false;
        }
    }
}