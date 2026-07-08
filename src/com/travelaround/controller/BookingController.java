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
     */
    public boolean processRoomBooking(int customerId, int roomId, String checkIn, String checkOut, double cost) throws RoomUnavailableException {
       String checkSql = "SELECT COUNT(*) FROM bookings WHERE room_id = ? AND booking_status != 'Cancelled' "
                        + "AND ((check_in_date <= ? AND check_out_date >= ?) OR (check_in_date <= ? AND check_out_date >= ?))";
        
        String insertSql = "INSERT INTO bookings (customer_id, room_id, check_in_date, check_out_date, total_cost, booking_status) VALUES (?, ?, ?, ?, ?, 'Confirmed')";

        // Fixed: Ensure the shared connection is not placed in try-with-resources (avoids closing it)
        try {
            Connection liveConn = DBConnection.getConnection();
            
            // Check Availability
            try (PreparedStatement checkStmt = liveConn.prepareStatement(checkSql)) {
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
            try (PreparedStatement insertStmt = liveConn.prepareStatement(insertSql)) {
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
        
        try {
            Connection liveConn = DBConnection.getConnection();
            PreparedStatement stmt = liveConn.prepareStatement(sql);
            
            stmt.setInt(1, bookingId);
            int rowsUpdated = stmt.executeUpdate();
            
            stmt.close(); // Close only the statement, keep connection open
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.out.println("Database execution error during cancellation step:");
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

        // Fixed: Removed Connection from try-with-resources statement so it stays alive
        try {
            Connection liveConn = DBConnection.getConnection();
            PreparedStatement stmt = liveConn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
             
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
            
            // Close statement and result set manually, keeping connection alive
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.out.println("Error pulling transaction files: " + e.getMessage());
        }
        return bookingList;
   
}}