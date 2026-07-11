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
    }

    /**
     * Searches for customers by matching their exact ID or a partial Name string.
     */
    public java.util.List<Object[]> searchCustomers(String keyword) {
        java.util.List<Object[]> customerList = new java.util.ArrayList<>();
        String query = "SELECT id, customer_name, email, phone FROM customers WHERE id = ? OR customer_name LIKE ?";

        try {
            java.sql.Connection liveConn = com.travelaround.config.DBConnection.getConnection();
            java.sql.PreparedStatement stmt = liveConn.prepareStatement(query);
            
            int idSearch = -1;
            try {
                idSearch = Integer.parseInt(keyword);
            } catch (NumberFormatException e) {
                // Safe fallback if it's a textual name string
            }

            stmt.setInt(1, idSearch);
            stmt.setString(2, "%" + keyword + "%"); 

            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customerList.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("customer_name"),
                    rs.getString("email"),
                    rs.getString("phone")
                });
            }
            
            rs.close();
            stmt.close();
            
        } catch (java.sql.SQLException e) {
            System.out.println("Customer search query failure: " + e.getMessage());
        }
        return customerList;
    }

    /**
     * NEW METHOD: Fetches real relational booking information formatted for the customer dashboard columns.
     */
    public java.util.List<Object[]> getBookingsByCustomerId(int customerId) {
        java.util.List<Object[]> bookingDataList = new java.util.ArrayList<>();
        
        String query = "SELECT b.booking_id, h.name AS hotel_name, r.room_type, b.booking_status " +
                        "FROM bookings b " +
                        "JOIN rooms r ON b.room_id = r.room_id " +
                        "JOIN hotels h ON r.hotel_id = h.hotel_id " +
                        "WHERE b.customer_id = ?";
        try {
            Connection liveConn = DBConnection.getConnection();
            PreparedStatement stmt = liveConn.prepareStatement(query);
            
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                bookingDataList.add(new Object[]{
                    rs.getInt("booking_id"),
                    rs.getString("hotel_name"),
                    rs.getString("room_type"),
                    rs.getString("booking_status")
                });
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.out.println("Error pulling dashboard dataset: " + e.getMessage());
        }
        return bookingDataList;
    }
}