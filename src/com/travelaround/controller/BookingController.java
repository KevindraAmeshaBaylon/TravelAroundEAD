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
        // 1. Check current room status first
        String statusCheckQuery = "SELECT status FROM Rooms WHERE room_id = ?";
        try (PreparedStatement psCheck = conn.prepareStatement(statusCheckQuery)) {
            psCheck.setInt(1, roomId);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next() && "Booked".equalsIgnoreCase(rs.getString("status"))) {
                    // Throw your custom exception if unavailable
                    throw new RoomUnavailableException("Transaction Aborted: This room is already occupied during the requested period.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Status tracking database error: " + e.getMessage());
            return false;
        }

        // 2. Begin Transaction to execute multi-table writes safely
        try {
            conn.setAutoCommit(false);

            // Statement A: Insert the Booking log record
            String insertBookingQuery = "INSERT INTO Bookings (customer_id, room_id, check_in_date, check_out_date, total_cost, booking_status) VALUES (?, ?, ?, ?, ?, 'Confirmed')";
            try (PreparedStatement psBook = conn.prepareStatement(insertBookingQuery)) {
                psBook.setInt(1, customerId);
                psBook.setInt(2, roomId);
                psBook.setDate(3, java.sql.Date.valueOf(checkIn));
                psBook.setDate(4, java.sql.Date.valueOf(checkOut));
                psBook.setDouble(5, cost);
                psBook.executeUpdate();
            }

            // Statement B: Automatically flip Room status condition block
            String updateRoomQuery = "UPDATE Rooms SET status = 'Booked' WHERE room_id = ?";
            try (PreparedStatement psRoom = conn.prepareStatement(updateRoomQuery)) {
                psRoom.setInt(1, roomId);
                psRoom.executeUpdate();
            }

            // Commit transaction modifications safely
            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback if any write crashes to maintain data integrity
                conn.setAutoCommit(true);
            } catch (SQLException rollbackEx) {
                System.out.println("Rollback execution error: " + rollbackEx.getMessage());
            }
            System.out.println("Booking Transaction processing failed: " + e.getMessage());
            return false;
        }
    }
    
       public boolean cancelBooking(int bookingId) {
    // TODO: Connect your DBConnection statement to update the booking status to 'Cancelled'
    // Example: UPDATE booking SET status = 'Cancelled' WHERE booking_id = bookingId;
    System.out.println("Booking ID " + bookingId + " has been requested for cancellation.");
    return true; 
}
        /**
        * Fetches all booking transaction records to display on the master log view.
        */
       public java.util.List<com.travelaround.model.Booking> getAllBookings() {
           java.util.List<com.travelaround.model.Booking> bookingList = new java.util.ArrayList<>();
           String query = "SELECT * FROM Bookings";

           try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
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
       }
}