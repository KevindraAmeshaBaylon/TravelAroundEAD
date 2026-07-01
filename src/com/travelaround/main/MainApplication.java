package com.travelaround.main;

import com.travelaround.controller.UserController;
import com.travelaround.model.User;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("========== Launching TravelAround App ==========");
        
        // Initialize the controller
        UserController userController = new UserController();
        
        // Test system using our default admin account from the SQL setup script
        System.out.println("\nTesting database connection with account: admin...");
        User testUser = userController.loginUser("admin", "admin123");
        
        if (testUser != null) {
            System.out.println(">>> SUCCESS! Account Found <<<");
            System.out.println("Welcome back: " + testUser.getUsername());
            System.out.println("System Security Clearance Role: " + testUser.getRole());
        } else {
            System.out.println(">>> FAILURE! Could not read test user from the database. <<<");
            System.out.println("Tip: Check that XAMPP is running and database tables were generated.");
        }
        
        System.out.println("\n================================================");
    }
}