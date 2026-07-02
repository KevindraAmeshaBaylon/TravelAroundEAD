package com.travelaround.view;

public class RoomManagementForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RoomManagementForm.class.getName());
    private com.travelaround.controller.RoomController roomController;
    public RoomManagementForm() {
        initComponents();
        roomController = new com.travelaround.controller.RoomController();
        refreshRoomTable();
    }

    private void refreshRoomTable() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblRooms.getModel();
        model.setRowCount(0); // Wipe stale data rows
        
        java.util.List<com.travelaround.model.Room> list = roomController.getAllRooms();
        for (com.travelaround.model.Room rm : list) {
            Object[] targetRow = { rm.getRoomId(), rm.getHotelId(), rm.getRoomNumber(), rm.getRoomType(), rm.getPricePerNight(), rm.getStatus() };
            model.addRow(targetRow);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtResortID = new javax.swing.JTextField();
        btnAddRoom = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRooms = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cmbRoomType = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtRoomNo = new javax.swing.JTextField();
        txtRoomPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(0, 0, 51));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Room Management");

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addGap(25, 25, 25))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Room Number :");

        btnAddRoom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddRoom.setText("Add Room Entry");
        btnAddRoom.addActionListener(this::btnAddRoomActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Price");

        tblRooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Room ID", "Hotel ID", "Room Number", "Type", "Price", "Current Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblRooms);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Hotel ID :");

        cmbRoomType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Single", "Double", "Suite", "Deluxe" }));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Type");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbRoomType, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtRoomPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtResortID, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(btnAddRoom)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtResortID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtRoomNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtRoomPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRoomType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(btnAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtResortID.setText("");
        txtRoomNo.setText("");
        txtRoomPrice.setText("");
        cmbRoomType.setSelectedIndex(0); // Resets dropdown to "Single"
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRoomActionPerformed
       String hotelIdStr = txtResortID.getText().trim(); 
        String roomNoStr = txtRoomNo.getText().trim();
        String priceStr = txtRoomPrice.getText().trim();
        String selectedType = cmbRoomType.getSelectedItem().toString();

        if (hotelIdStr.isEmpty() || roomNoStr.isEmpty() || priceStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please supply all setup properties before adding a room.", "Missing Data", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int hotelId = Integer.parseInt(hotelIdStr);
            int roomNo = Integer.parseInt(roomNoStr); 
            double price = Double.parseDouble(priceStr);

            if (price <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Room price must be a valid positive numerical amount.", "Invalid Price Metric", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // FIXED PARAMETER ORDER HERE: Passed selectedType (String) before roomNo (int)
            if (roomController.addRoom(hotelId, selectedType, price, roomNo)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Room added successfully!");
                refreshRoomTable();

                txtRoomNo.setText("");
                txtRoomPrice.setText("");
            }

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Hotel ID, Room Number, and Price must contain numbers only.", "Parsing Inputs Mismatch Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddRoomActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> {
        new RoomManagementForm().setVisible(true);
    });
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRoom;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JComboBox<String> cmbRoomType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRooms;
    private javax.swing.JTextField txtResortID;
    private javax.swing.JTextField txtRoomNo;
    private javax.swing.JTextField txtRoomPrice;
    // End of variables declaration//GEN-END:variables
}
