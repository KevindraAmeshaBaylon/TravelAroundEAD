package com.travelaround.main;

import com.travelaround.controller.UserController;
import com.travelaround.model.User;

public class MainApplication {
    public static void main(String[] args) {
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Theme application failed: " + e.getMessage());
        }

        // Run the visual Login window interface
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                com.travelaround.view.LoginForm loginWindow = new com.travelaround.view.LoginForm();
                loginWindow.setLocationRelativeTo(null); // Centers the frame perfectly on screen
                loginWindow.setVisible(true);
            }
        });
    }
}
