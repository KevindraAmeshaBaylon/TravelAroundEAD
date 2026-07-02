package com.travelaround.controller;

import com.travelaround.config.DBConnection;
import com.travelaround.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private Connection conn;

    public RoomController() {
        this.conn = DBConnection.getConnection();
    }

    // CREATE: Insert a brand new room structure
    public boolean addRoom(int hotelId, String roomType, double price, int roomNo) {
        String query = "INSERT INTO Rooms (hotel_id, room_number, room_type, price_per_night, status) VALUES (?, ?, ?, ?, 'Available')";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, hotelId);
            ps.setInt(2, roomNo);
            ps.setString(3, roomType);
            ps.setDouble(4, price);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creating room slot: " + e.getMessage());
            return false;
        }
    }

    // READ: Get all tracking numbers to populate UI listings
    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM Rooms";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                roomList.add(new Room(
                    rs.getInt("room_id"),
                    rs.getInt("hotel_id"),
                    rs.getString("room_number"),
                    rs.getString("room_type"),
                    rs.getDouble("price_per_night"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room database files: " + e.getMessage());
        }
        return roomList;
    }
}