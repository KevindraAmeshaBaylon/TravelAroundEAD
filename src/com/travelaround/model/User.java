package com.travelaround.model;

public class User {
    // 1. Existing fields (keep whatever you already had here, like username/password/role)
    private String username;
    private String password;
    private String role;
    
    // 2. ADD THESE NEW FIELDS to hold the customer table details!
    private int id;
    private String customerName;
    private String email;
    private String phone;

    // Default Constructor
    public User() {
    }

    // --- ADD THESE SETTERS AND GETTERS FOR THE NEW FIELDS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // --- Keep your existing getters/setters below for username, password, role ---
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}