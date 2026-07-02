package com.travelaround.model;

public class Hotel {
    private int hotelId;
    private String name;
    private String location;
    private double rating;
    private int managerId;

    // Constructor
    public Hotel(int hotelId, String name, String location, double rating, int managerId) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.managerId = managerId;
    }

    // Getters and Setters (Encapsulation Principles)
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getManagerId() { return managerId; }
    public void setManagerId(int managerId) { this.managerId = managerId; }
}