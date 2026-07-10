package com.travelaround.view;

public class DashboardForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardForm.class.getName());

    private com.travelaround.model.User currentUser;
    private com.travelaround.controller.UserController controller;
    
    private int loggedInCustomerId;
    private String loggedInCustomerName;
    private String loggedInCustomerEmail;
    private String loggedInCustomerPhone;
    public DashboardForm(com.travelaround.model.User user) {
        initComponents();
    this.currentUser = user;
    this.controller = new com.travelaround.controller.UserController();
    this.setLocationRelativeTo(null);
    
    if (currentUser != null) {
        // 1. Set the dynamic welcoming title for BOTH roles
        lblUserWelcome.setText("Welcome back, " + currentUser.getUsername() + " | Account Access: [" + currentUser.getRole() + "]");
        
        // 2. Run UI visibility hiding logic based on role string
        enforceRoleAccessControls(currentUser.getRole());
        
        // 3. Separate the data loading and variable assignments by role
        if (!currentUser.getRole().equalsIgnoreCase("Admin")) {
            // Only assign these if the user is a CUSTOMER
            this.loggedInCustomerId = currentUser.getId();
            this.loggedInCustomerName = currentUser.getCustomerName();
            this.loggedInCustomerEmail = currentUser.getEmail();
            this.loggedInCustomerPhone = currentUser.getPhone();
            
            // Populate customer data grid
            loadCustomerBookingDetails(currentUser.getId()); 
        } else {
            // Clear or safely ignore customer variables for Admin
            this.loggedInCustomerId = -1;
            this.loggedInCustomerName = "System Administrator";
            
            // Populate admin statistics panels
            loadDatabaseAnalytics();
        }
    }
        
    }
    // Add this so the form can be opened with no arguments safely
    public DashboardForm(int id, String name, String email, String phone, String role) {
        initComponents();
        this.controller = new com.travelaround.controller.UserController();
        this.setLocationRelativeTo(null);
        
        // Check if the logging-in user is an Admin
        boolean isAdmin = (role != null && role.equalsIgnoreCase("Admin"));
        
        if (isAdmin) {
            this.loggedInCustomerId = -1;
            // Set a clean name if the database field is blank for admin
            this.loggedInCustomerName = (name != null && !name.trim().isEmpty()) ? name : "System Admin";
        } else {
            this.loggedInCustomerId = id;
            this.loggedInCustomerName = (name != null && !name.trim().isEmpty()) ? name : "Customer Account";
        }
        
        this.loggedInCustomerEmail = email;
        this.loggedInCustomerPhone = phone;
        
        // Dynamically print greeting safely using the corrected name variable!
        lblUserWelcome.setText("Welcome back, " + this.loggedInCustomerName + " | Account Access: [" + role + "]");
        
        enforceRoleAccessControls(role);
        
        if (!isAdmin) {
            // Load their actual live bookings instead of just their profile row!
            loadCustomerBookingDetails(id);
        } else {
            loadDatabaseAnalytics();
        }
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    private void loadCustomerBookingDetails(int customerId) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblCustomers.getModel();
        
        // Match the layout headers exactly
        model.setColumnIdentifiers(new Object[]{"Booking ID", "Hotel Name", "Room Type", "Status"});
        model.setRowCount(0); // Clear old tracking state rows
        
        try {
            com.travelaround.controller.BookingController bookingController = new com.travelaround.controller.BookingController();
            
            // CALL THE NEW METHOD HERE:
            java.util.List<Object[]> customerBookings = bookingController.getBookingsByCustomerId(customerId);
            
            if (customerBookings != null && !customerBookings.isEmpty()) {
                for (Object[] row : customerBookings) {
                    model.addRow(row);
                }
            } else {
                model.addRow(new Object[]{"-", "No booking history found", "-", "-"});
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addRow(new Object[]{"Error", "Failed to query database logs", "-", "-"});
        }
    }
    
    /**
     * Restricts UI button elements depending on user permission hierarchy
     */
    private void enforceRoleAccessControls(String role) {
        if (role == null) return;
        
        boolean isAdmin = role.equalsIgnoreCase("Admin");
        
        // Non-admins shouldn't modify hotel structures or room logs
        btnBookRoom1.setVisible(isAdmin);    // Manage Rooms Button
        btnManageHotels.setVisible(isAdmin); // Manage Hotels Button
        btnViewLogs.setVisible(isAdmin);     // View Logs Button
        btnViewReport.setVisible(isAdmin);   // View Report Button
        
        // Hide Admin Panels / Boxes
        totuserbox.setVisible(isAdmin);
        tothotelbox.setVisible(isAdmin);
        totroomsbox.setVisible(isAdmin);
        totrevenuebox.setVisible(isAdmin);
        searchbox.setVisible(isAdmin);
        
        // Customer view updates
        if (!isAdmin) {
            // Customers only need to see their personal information pane
            jScrollPane1.setVisible(true);
            btnBookRoom.setVisible(true);
        } else {
            // Admins can hide the individual customer single row grid if desired, 
            // or keep it visible for global administration search results
            jScrollPane1.setVisible(true); 
            btnBookRoom.setVisible(false); // Hide simple book room button from admin
        }
    }
    
    private void loadDatabaseAnalytics() {
            if (controller != null) {
                try {
                    lblTotalUsers.setText(String.valueOf(controller.getSystemCount("Users")));
                    lblTotalHotels.setText(String.valueOf(controller.getSystemCount("Hotels")));
                    lblTotalRooms.setText(String.valueOf(controller.getSystemCount("Rooms")));
                    lblTotalRevenue.setText("$" + String.format("%.2f", controller.getTotalRevenue()));
                } catch (Exception e) {
                    logger.log(java.util.logging.Level.SEVERE, "Analytics tracking engine load failure", e);
                }
            }
     }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUserWelcome = new javax.swing.JLabel();
        totuserbox = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTotalUsers = new javax.swing.JLabel();
        tothotelbox = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalHotels = new javax.swing.JLabel();
        totroomsbox = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalRooms = new javax.swing.JLabel();
        totrevenuebox = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTotalRevenue = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnBookRoom1 = new javax.swing.JButton();
        btnViewLogs = new javax.swing.JButton();
        btnManageHotels = new javax.swing.JButton();
        btnViewReport = new javax.swing.JButton();
        searchbox = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnBookRoom = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomers = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lblUserWelcome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUserWelcome.setText("Logged in as: [Role Name Account]");

        totuserbox.setBackground(new java.awt.Color(204, 204, 204));
        totuserbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        totuserbox.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Total Users");

        lblTotalUsers.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalUsers.setText("Value");

        javax.swing.GroupLayout totuserboxLayout = new javax.swing.GroupLayout(totuserbox);
        totuserbox.setLayout(totuserboxLayout);
        totuserboxLayout.setHorizontalGroup(
            totuserboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totuserboxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblTotalUsers)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        totuserboxLayout.setVerticalGroup(
            totuserboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totuserboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(totuserboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblTotalUsers))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tothotelbox.setBackground(new java.awt.Color(204, 204, 204));
        tothotelbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tothotelbox.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Total Hotels");

        lblTotalHotels.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalHotels.setText("Value");

        javax.swing.GroupLayout tothotelboxLayout = new javax.swing.GroupLayout(tothotelbox);
        tothotelbox.setLayout(tothotelboxLayout);
        tothotelboxLayout.setHorizontalGroup(
            tothotelboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tothotelboxLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblTotalHotels)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        tothotelboxLayout.setVerticalGroup(
            tothotelboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tothotelboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tothotelboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblTotalHotels))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        totroomsbox.setBackground(new java.awt.Color(204, 204, 204));
        totroomsbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        totroomsbox.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Total Rooms");

        lblTotalRooms.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalRooms.setText("Value");

        javax.swing.GroupLayout totroomsboxLayout = new javax.swing.GroupLayout(totroomsbox);
        totroomsbox.setLayout(totroomsboxLayout);
        totroomsboxLayout.setHorizontalGroup(
            totroomsboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totroomsboxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblTotalRooms)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        totroomsboxLayout.setVerticalGroup(
            totroomsboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totroomsboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(totroomsboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTotalRooms))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        totrevenuebox.setBackground(new java.awt.Color(204, 204, 204));
        totrevenuebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        totrevenuebox.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Total Revenue");

        lblTotalRevenue.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalRevenue.setText("Value");

        javax.swing.GroupLayout totrevenueboxLayout = new javax.swing.GroupLayout(totrevenuebox);
        totrevenuebox.setLayout(totrevenueboxLayout);
        totrevenueboxLayout.setHorizontalGroup(
            totrevenueboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totrevenueboxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(lblTotalRevenue)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        totrevenueboxLayout.setVerticalGroup(
            totrevenueboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totrevenueboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(totrevenueboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTotalRevenue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TravelAround Management & Analytics Console");

        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setText("LogOut");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addGap(26, 26, 26))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnLogout))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnBookRoom1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBookRoom1.setText("Manage Rooms");
        btnBookRoom1.setActionCommand("Book a Room");
        btnBookRoom1.addActionListener(this::btnBookRoom1ActionPerformed);

        btnViewLogs.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnViewLogs.setText("View Booking Logs");
        btnViewLogs.setActionCommand("Book a Room");
        btnViewLogs.addActionListener(this::btnViewLogsActionPerformed);

        btnManageHotels.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnManageHotels.setText("Manage Hotels");
        btnManageHotels.setActionCommand("Book a Room");
        btnManageHotels.addActionListener(this::btnManageHotelsActionPerformed);

        btnViewReport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnViewReport.setText("Launch Report");
        btnViewReport.setActionCommand("Book a Room");
        btnViewReport.addActionListener(this::btnViewReportActionPerformed);

        searchbox.setBackground(new java.awt.Color(204, 204, 204));
        searchbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        searchbox.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Customers");

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setActionCommand("Book a Room");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        javax.swing.GroupLayout searchboxLayout = new javax.swing.GroupLayout(searchbox);
        searchbox.setLayout(searchboxLayout);
        searchboxLayout.setHorizontalGroup(
            searchboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchboxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        searchboxLayout.setVerticalGroup(
            searchboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBookRoom.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBookRoom.setText("Book A Room");
        btnBookRoom.setActionCommand("Book a Room");
        btnBookRoom.addActionListener(this::btnBookRoomActionPerformed);

        tblCustomers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Email", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCustomers);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBookRoom1)
                                .addGap(19, 19, 19))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBookRoom)
                                .addGap(28, 28, 28))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnViewLogs)
                                .addComponent(btnViewReport)
                                .addComponent(btnManageHotels)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(searchbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(totuserbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tothotelbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(totroomsbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(totrevenuebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblUserWelcome)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserWelcome)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(totrevenuebox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totroomsbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tothotelbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totuserbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(searchbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBookRoom)
                        .addGap(18, 18, 18)
                        .addComponent(btnBookRoom1)
                        .addGap(38, 38, 38)
                        .addComponent(btnManageHotels)
                        .addGap(35, 35, 35)
                        .addComponent(btnViewReport)
                        .addGap(43, 43, 43)
                        .addComponent(btnViewLogs)))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // 1. Close and destroy the current dashboard window completely
        this.dispose(); 

        // 2. Open up ONE fresh copy of the login screen
        com.travelaround.view.LoginForm loginWindow = new com.travelaround.view.LoginForm();
        loginWindow.setLocationRelativeTo(null);
        loginWindow.setVisible(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnBookRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookRoomActionPerformed
        BookingForm bookingScreen = new BookingForm(this.loggedInCustomerId);
        bookingScreen.setVisible(true);
    }//GEN-LAST:event_btnBookRoomActionPerformed

    private void btnBookRoom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookRoom1ActionPerformed
        RoomManagementForm roomScreen = new RoomManagementForm();
        roomScreen.setVisible(true);
    }//GEN-LAST:event_btnBookRoom1ActionPerformed

    private void btnViewLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewLogsActionPerformed
        BookingHistoryForm historyScreen = new BookingHistoryForm();
        historyScreen.setVisible(true);
    }//GEN-LAST:event_btnViewLogsActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // Only refresh analytics if the user is logged in as an admin
        if (currentUser != null && currentUser.getRole().equalsIgnoreCase("Admin")) {
            loadDatabaseAnalytics();
        } else if (this.loggedInCustomerName != null && !totrevenuebox.isVisible()) {
            // If it's a customer, we do nothing so the app doesn't crash trying to update hidden boxes
        }
    
    }//GEN-LAST:event_formWindowGainedFocus

    private void btnManageHotelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageHotelsActionPerformed
        com.travelaround.view.HotelManagementForm hotelForm = new com.travelaround.view.HotelManagementForm();
        hotelForm.setVisible(true);
    }//GEN-LAST:event_btnManageHotelsActionPerformed

    private void btnViewReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewReportActionPerformed
      try {
        // Path to your compiled Jasper design binary file
        String reportPath = "C:\\Users\\kevin\\Documents\\NetBeansProjects\\TravelAround\\src\\com\\travelaround\\model\\report\\BookingSummaryReport.jasper"; 
        
        java.sql.Connection conn = com.travelaround.config.DBConnection.getConnection();
        
        // Fill the compiled report layout blueprint with your live database records
        net.sf.jasperreports.engine.JasperPrint jasperPrint = 
            net.sf.jasperreports.engine.JasperFillManager.fillReport(reportPath, null, conn);
        
        // Launch a clean Swing window to view, print, or export your report
        net.sf.jasperreports.view.JasperViewer viewer = new net.sf.jasperreports.view.JasperViewer(jasperPrint, false);
        viewer.setTitle("TravelAround - Operational Booking Management Report");
        viewer.setVisible(true);
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Error rendering reporting engine views: " + e.getMessage(), 
            "Reporting System Failure", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnViewReportActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch.getText().trim();
    
    // Assume your JTable variable name is tblCustomers
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblCustomers.getModel();
    model.setRowCount(0); // Clear old table contents
    
    if (keyword.isEmpty()) {
        // If search is empty, you could reload all bookings or just stop
        return;
    }
    
    // Call the search through your existing BookingController!
    com.travelaround.controller.BookingController controller = new com.travelaround.controller.BookingController();
    java.util.List<Object[]> results = controller.searchCustomers(keyword);
    
    // Repopulate your JTable grid row by row
    for (Object[] row : results) {
        model.addRow(row);
    }
    }//GEN-LAST:event_btnSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBookRoom;
    private javax.swing.JButton btnBookRoom1;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManageHotels;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewLogs;
    private javax.swing.JButton btnViewReport;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalHotels;
    private javax.swing.JLabel lblTotalRevenue;
    private javax.swing.JLabel lblTotalRooms;
    private javax.swing.JLabel lblTotalRooms2;
    private javax.swing.JLabel lblTotalUsers;
    private javax.swing.JLabel lblUserWelcome;
    private javax.swing.JPanel searchbox;
    private javax.swing.JTable tblCustomers;
    private javax.swing.JPanel tothotelbox;
    private javax.swing.JPanel totrevenuebox;
    private javax.swing.JPanel totroomsbox;
    private javax.swing.JPanel totuserbox;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
