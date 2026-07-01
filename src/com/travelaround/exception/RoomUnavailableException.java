package com.travelaround.exception;
/*
 * Custom Exception thrown when a user attempts to book a room 
 * that is already occupied during the requested dates.
 */
public class RoomUnavailableException extends Exception {
    public RoomUnavailableException(String message) {
        super(message);
    }
}