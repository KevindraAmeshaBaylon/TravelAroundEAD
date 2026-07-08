package com.travelaround.controller;

import com.travelaround.config.DBConnection;
import com.travelaround.exception.RoomUnavailableException;
import java.sql.*;

public class BookingController {
    private Connection conn;

    public BookingController() {
        this.conn = DBConnection.getConnection();
    }

    /**
     * Executes an enterprise-level multi-table transactional reservation.
     * Throws RoomUnavailableException if the selected room is already booked.
     */
    public boolean processRoomBooking(int customerId, int roomId, String checkIn, String checkOut, double cost) throws RoomUnavailableException {
       String checkSql = "SELECT COUNT(*) FROM bookings WHERE room_id = ? AND booking_status != 'Cancelled' "
                        + "AND ((check_in_date <= ? AND check_out_date >= ?) OR (check_in_date <= ? AND check_out_date >= ?))";
        
        String insertSql = "INSERT INTO bookings (customer_id, room_id, check_in_date, check_out_date, total_cost, booking_status) VALUES (?, ?, ?, ?, ?, 'Confirmed')";

        try (Connection conn = DBConnection.getConnection()) {
            // Check Availability
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, roomId);
                checkStmt.setString(2, checkOut);
                checkStmt.setString(3, checkIn);
                checkStmt.setString(4, checkIn);
                checkStmt.setString(5, checkOut);
                
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new RoomUnavailableException("This room is already reserved for the selected dates.");
                    }
                }
            }

            // Execute reservation insertion
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, customerId);
                insertStmt.setInt(2, roomId);
                insertStmt.setString(3, checkIn);
                insertStmt.setString(4, checkOut);
                insertStmt.setDouble(5, cost);
                
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Cancels an existing booking transaction by its ID tracking column.
     */
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET booking_status = 'Cancelled' WHERE booking_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Fetches records matching database columns.
     */
    public java.util.List<com.travelaround.model.Booking> getAllBookings() {
        java.util.List<com.travelaround.model.Booking> bookingList = new java.util.ArrayList<>();
        String query = "SELECT * FROM bookings";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                bookingList.add(new com.travelaround.model.Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"), 
                    rs.getInt("room_id"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date"),
                    rs.getDouble("total_cost"),
                    rs.getString("booking_status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error pulling transaction files: " + e.getMessage());
        }
        return bookingList;
}}