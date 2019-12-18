/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author phatngy
 */
public class Home extends javax.swing.JFrame implements Runnable {
    
    private Detail detail;
    private Thread thread;
    
    public Home(Detail d) {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        lblSoftwareName.setForeground(Color.GREEN);
        lblRun.setForeground(Color.GREEN);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail = new Detail(d);
    }
    private void Start(){
        if(thread==null){
            thread= new Thread(this);
            thread.start();
        }
    }
    
    private void Update(){
        lblRun.setForeground(Color.GREEN);
        lblRun.setLocation(lblRun.getX()-1, lblRun.getY());
        if(lblRun.getX()+lblRun.getWidth()<0){
            lblRun.setLocation(this.getWidth(), lblRun.getY());
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

        jPanel1 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("src/Image/Background.png");
            public void paintComponent(Graphics g){

                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, this);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        lblSoftwareName = new javax.swing.JLabel();
        lblRun = new javax.swing.JLabel();
        btnEmployeesManagement = new javax.swing.JButton();
        btnProduct = new javax.swing.JButton();
        btnData = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnLogOut = new javax.swing.JButton();
        btnSale = new javax.swing.JButton();
        btnBill = new javax.swing.JButton();
        btnRevenue1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        lblSoftwareName.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        lblSoftwareName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoftwareName.setText("Phần Mềm Quản Lý Cửa Hàng Thuốc");

        lblRun.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        lblRun.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRun.setText("Cửa Hàng Thuốc KSTN");

        btnEmployeesManagement.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeesManagement.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Account.png"))); // NOI18N
        btnEmployeesManagement.setText("Cập Nhật Nhân Viên");
        btnEmployeesManagement.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmployeesManagement.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnEmployeesManagement.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmployeesManagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeesManagementActionPerformed(evt);
            }
        });

        btnProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Product.png"))); // NOI18N
        btnProduct.setText("Cập Nhật Thuốc");
        btnProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductActionPerformed(evt);
            }
        });

        btnData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Database.png"))); // NOI18N
        btnData.setText("Cập Nhật Thông Tin");
        btnData.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnData.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnData.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataActionPerformed(evt);
            }
        });

        btnFind.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Search.png"))); // NOI18N
        btnFind.setText("Tìm Kiếm");
        btnFind.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFind.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnFind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LogOut.png"))); // NOI18N
        btnLogOut.setText("Đăng Xuất");
        btnLogOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogOut.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnLogOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        btnSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Sale.png"))); // NOI18N
        btnSale.setText("Bán Hàng");
        btnSale.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSale.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnSale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleActionPerformed(evt);
            }
        });

        btnBill.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bill_100px.png"))); // NOI18N
        btnBill.setText("Kiểm Kê");
        btnBill.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBill.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnBill.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillActionPerformed(evt);
            }
        });

        btnRevenue1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRevenue1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Payroll.png"))); // NOI18N
        btnRevenue1.setText("Thống Kê");
        btnRevenue1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRevenue1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnRevenue1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRevenue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevenue1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSoftwareName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnData, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRevenue1))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSale, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(btnEmployeesManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFind, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSoftwareName)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEmployeesManagement, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFind, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRevenue1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lblRun)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmployeesManagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeesManagementActionPerformed
        EmployeesManagement account = new EmployeesManagement(detail);
        this.setVisible(false);
        account.setVisible(true);
    }//GEN-LAST:event_btnEmployeesManagementActionPerformed

    private void btnProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductActionPerformed
        ListDrug drug = new ListDrug(detail);
        this.setVisible(false);
        drug.setVisible(true);
    }//GEN-LAST:event_btnProductActionPerformed

    private void btnDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataActionPerformed
        Data data = new Data(detail);
        this.setVisible(false);
        data.setVisible(true);
    }//GEN-LAST:event_btnDataActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        Find find = new Find(detail);
        this.setVisible(false);
        find.setVisible(true);
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất tài khoản khỏi hệ thống hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            Login login = new Login();
            this.setVisible(false);
            login.setVisible(true);
        }
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleActionPerformed
        Sale sale = new Sale(detail);
        this.setVisible(false);
        sale.setVisible(true);
    }//GEN-LAST:event_btnSaleActionPerformed

    private void btnBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillActionPerformed
        Bill b = new Bill();
        this.setVisible(true);
        b.setVisible(true);
    }//GEN-LAST:event_btnBillActionPerformed

    private void btnRevenue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevenue1ActionPerformed
        JOptionPane.showMessageDialog(null, "Chức Năng Đang Được Hoàn Thiện, Sẽ Có Vào Bản Update Sau!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        this.setVisible(true);
    }//GEN-LAST:event_btnRevenue1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail=new Detail();
                new Home(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBill;
    private javax.swing.JButton btnData;
    private javax.swing.JButton btnEmployeesManagement;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnProduct;
    private javax.swing.JButton btnRevenue1;
    private javax.swing.JButton btnSale;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblRun;
    private javax.swing.JLabel lblSoftwareName;
    // End of variables declaration//GEN-END:variables
    @Override
    public void run() {
        long FPS=80;
        long period=1000*1000000/FPS;
        long beginTime,sleepTime;
        
        beginTime=System.nanoTime();
        while(true){
            
            Update();
            
            long deltaTime=System.nanoTime()-beginTime;
            sleepTime=period-deltaTime;
            try{
                if(sleepTime>0)
                    Thread.sleep(sleepTime/1000000);
                else    Thread.sleep(period/2000000);
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
            beginTime=System.nanoTime();
        }
    }

}
