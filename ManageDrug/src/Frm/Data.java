/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frm;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author phatngy
 */
public class Data extends javax.swing.JFrame {

    private Connection conn = managedrug.ManageDrug.connect.conn;
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Detail detail;
    private boolean Add = false, Change = false;
    
    String sql1 = "SELECT * FROM Position";
    String sql3 = "SELECT * FROM MainComponent";
    /**
     * Creates new form Data
     */
    public Data(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail = new Detail(d);
        lblStatus.setForeground(Color.red);
        loadPosition(sql1);
        loadComponent(sql3);
        DisabledPosition();
        DisabledComponent();
        
    }
    private void loadPosition(String sql){
        tablePosition.removeAll();
        try{
            String [] arr={"Chức Vụ","Lương Cơ Bản"};
            DefaultTableModel modle=new DefaultTableModel(arr,0);
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                
                vector.add(rs.getString("Position").trim());
                vector.add(rs.getString("Payroll").trim());
                
                modle.addRow(vector);
            }
            tablePosition.setModel(modle);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void loadComponent(String sql){
        tableComponent.removeAll();
        try{
            String [] arr = {"Mã Thành Phần","Tên Thành Phần", "Công Dụng"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector = new Vector();
                
                vector.add(rs.getString("ID_Component").trim());
                vector.add(rs.getString("NameMainComponent").trim());
                vector.add(rs.getString("virtue").trim());
                
                model.addRow(vector);
            }
            tableComponent.setModel(model);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void backHome(){
        Home home=new Home(detail);
        this.setVisible(false);
        home.setVisible(true);
    }
    
    private void EnabledPosition(){
        
        txbPosition.setEnabled(true);
        txbPayroll.setEnabled(true);
        lblStatus.setText("Trạng Thái!");
    }
    

    private void EnabledComponent(){
        txbIDComponent.setEnabled(true);
        txbNameComponent.setEnabled(true);
        txbVirtue.setEnabled(true);
        lblStatus.setText("Trạng Thái!");
    }
    
    private void DisabledPosition(){
 
        txbPosition.setEnabled(false);
        txbPayroll.setEnabled(false);
    }
    
    private void DisabledComponent(){
        txbIDComponent.setEnabled(false);
        txbNameComponent.setEnabled(false);
        txbVirtue.setEnabled(false);
    }
    
    private void Refresh(){
        Change=false;
        Add=false;
        txbPosition.setText("");
        txbPayroll.setText("");
        txbIDComponent.setText("");
        txbNameComponent.setText("");
        txbVirtue.setText("");
        
        btnAddPosition.setEnabled(true);
        btnChangePosition.setEnabled(false);
        btnDeletePosition.setEnabled(false);
        btnSavePosition.setEnabled(false);
        btnAddClassify.setEnabled(true);
        btnChangeClassify.setEnabled(false);
        btnDeleteClassify.setEnabled(false);
        btnSaveClassify.setEnabled(false);
        
    }
    
    private boolean CheckPosition(){
        boolean kq=true;
        String sqlCheck="SELECT * FROM Position";
        try{
            pst = conn.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()){
                if(this.txbPosition.getText().equals(rs.getString("Position").toString().trim())){
                    return false;
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    
    
    private boolean CheckComponent(){
        boolean kq=true;
        String sqlCheck="SELECT * FROM MainComponent";
        try{
            pst=conn.prepareStatement(sqlCheck);
            rs=pst.executeQuery();
            while(rs.next()){
                if(this.txbIDComponent.getText().equals(rs.getString("ID_Component").toString().trim())){
                    return false;
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    
    private boolean checkNullPosition(){
        boolean kq=true;
        if(String.valueOf(this.txbPosition.getText()).length()==0){
            lblStatus.setText("Bạn chưa nhập chức vụ!");
            return false;
        }
        if(String.valueOf(this.txbPayroll.getText()).length()==0){
            lblStatus.setText("Bạn chưa nhập lương cơ bản của chức vụ!");
            return false;
        }
        return kq;
    }
    
    
    private boolean checkNullComponent(){
        boolean kq=true;
        if(String.valueOf(this.txbIDComponent.getText()).length()==0){
            lblStatus.setText("Bạn chưa ID cho thành phần thuốc!");
            return false;
        }
        if(String.valueOf(this.txbNameComponent.getText()).length()==0){
            lblStatus.setText("Bạn chưa nhập tên thành phần thuốc!");
            return false;
        } 
        if(String.valueOf(this.txbVirtue.getText()).length()==0){
            lblStatus.setText("Bạn chưa nhập công dụng của thành phần này!");
            return false;
        }
        return kq;
    }
    
    private void addPosition(){
        if(checkNullPosition()){
            String sqlInsert="INSERT INTO Position (Position,Payroll) VALUES(?,?,?)";
            try{
                pst=conn.prepareStatement(sqlInsert);
                
                pst.setString(1, txbPosition.getText());
                pst.setString(2, txbPayroll.getText()+" "+"VND");
                pst.executeUpdate();
                lblStatus.setText("Thêm Chức vụ thành công!");
                DisabledPosition();
                Refresh();
                loadPosition(sql1);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    
    private void addClassify(){
        if(checkNullComponent()){
            String sqlInsert="INSERT INTO MainComponent (ID_Component, NameMainComponent, virtue) VALUES(?,?,?)";
            try{
                pst=conn.prepareStatement(sqlInsert);
                pst.setString(1, txbIDComponent.getText());
                pst.setString(2, txbNameComponent.getText());
                pst.executeUpdate();
                lblStatus.setText("Thêm loại linh kiện thành công!");
                DisabledComponent();
                Refresh();
                loadComponent(sql3);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void changedPosition(){
        int Click=tablePosition.getSelectedRow();
        TableModel model=tablePosition.getModel();
        if(checkNullPosition()){
            String sqlChange="UPDATE Position SET Position=?,Payroll=? WHERE Position='"+model.getValueAt(Click,0).toString().trim()+"'";
            try{
                pst=conn.prepareStatement(sqlChange);
                
                pst.setString(1,txbPosition.getText() );
                pst.setString(2,txbPayroll.getText()+" "+"VND");
                pst.executeUpdate();
                lblStatus.setText("Lưu thay đổi thành công!");
                DisabledPosition();
                Refresh();
                loadPosition(sql1);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void changedClassify(){
        int Click=tableComponent.getSelectedRow();
        TableModel model=tableComponent.getModel();
        if(checkNullComponent()){
            String sqlChange="UPDATE Classify SET ID=?, Classify=? WHERE ID='"+model.getValueAt(Click,0).toString().trim()+"'";;
            try{
                pst=conn.prepareStatement(sqlChange);
                pst.setString(1, txbIDComponent.getText());
                pst.setString(2,txbNameComponent.getText() );
                pst.executeUpdate();
                lblStatus.setText("Lưu thay đổi thành công!");
                DisabledComponent();
                Refresh();
                loadComponent(sql3);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    

    private double convertedToNumbers(String s){
        String number="";
        String []array=s.replace(","," ").split("\\s");
        for(String i:array){
            number=number.concat(i);
        }
        return Double.parseDouble(number);
    }
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableComponent = new javax.swing.JTable();
        txbIDComponent = new javax.swing.JTextField();
        txbNameComponent = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        btnRefreshClassify = new javax.swing.JButton();
        btnAddClassify = new javax.swing.JButton();
        btnChangeClassify = new javax.swing.JButton();
        btnDeleteClassify = new javax.swing.JButton();
        btnSaveClassify = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txbVirtue = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePosition = new javax.swing.JTable();
        txbPayroll = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txbPosition = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        btnRefreshPosition = new javax.swing.JButton();
        btnAddPosition = new javax.swing.JButton();
        btnChangePosition = new javax.swing.JButton();
        btnDeletePosition = new javax.swing.JButton();
        btnSavePosition = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        btnBackHome = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        tableComponent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableComponent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableComponentMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableComponent);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel13.setText("Tên Thành Phần:");

        btnRefreshClassify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        btnRefreshClassify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshClassifyMouseClicked(evt);
            }
        });

        btnAddClassify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        btnAddClassify.setText("Thêm");
        btnAddClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClassifyActionPerformed(evt);
            }
        });

        btnChangeClassify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        btnChangeClassify.setText("Sửa");
        btnChangeClassify.setEnabled(false);
        btnChangeClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeClassifyActionPerformed(evt);
            }
        });

        btnDeleteClassify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        btnDeleteClassify.setText("Xóa");
        btnDeleteClassify.setEnabled(false);
        btnDeleteClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClassifyActionPerformed(evt);
            }
        });

        btnSaveClassify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        btnSaveClassify.setText("Lưu");
        btnSaveClassify.setEnabled(false);
        btnSaveClassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveClassifyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(btnRefreshClassify, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddClassify, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChangeClassify, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteClassify, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveClassify, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRefreshClassify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddClassify, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnSaveClassify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChangeClassify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteClassify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setText("Mã Thành Phần:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel15.setText("Công Dụng:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txbVirtue, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txbNameComponent, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txbIDComponent, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txbIDComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txbNameComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txbVirtue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(24, 24, 24)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Thành Phần Chính", jPanel3);

        tablePosition.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablePosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePositionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePosition);

        txbPayroll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txbPayrollKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setText("Lương Cơ Bản:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setText("Chức Vụ:");

        btnRefreshPosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        btnRefreshPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRefreshPositionMouseClicked(evt);
            }
        });

        btnAddPosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        btnAddPosition.setText("Thêm");
        btnAddPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPositionActionPerformed(evt);
            }
        });

        btnChangePosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        btnChangePosition.setText("Sửa");
        btnChangePosition.setEnabled(false);
        btnChangePosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePositionActionPerformed(evt);
            }
        });

        btnDeletePosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        btnDeletePosition.setText("Xóa");
        btnDeletePosition.setEnabled(false);
        btnDeletePosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePositionActionPerformed(evt);
            }
        });

        btnSavePosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        btnSavePosition.setText("Lưu");
        btnSavePosition.setEnabled(false);
        btnSavePosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePositionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(btnRefreshPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChangePosition, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeletePosition, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSavePosition, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRefreshPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddPosition, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnSavePosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChangePosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeletePosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txbPayroll, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txbPayroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Chức Vụ", jPanel1);

        btnBackHome.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBackHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        btnBackHome.setContentAreaFilled(false);
        btnBackHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackHomeMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 28)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Cập Nhật Thông Tin");

        lblStatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Trạng Thái");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBackHome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnBackHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(16, 16, 16)))
                .addComponent(jTabbedPane1)
                .addGap(30, 30, 30))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(571, Short.MAX_VALUE)
                    .addComponent(lblStatus)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableComponentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableComponentMouseClicked
        int Click=tableComponent.getSelectedRow();
        TableModel model=tableComponent.getModel();

        txbIDComponent.setText(model.getValueAt(Click,0).toString());
        txbNameComponent.setText(model.getValueAt(Click,1).toString());
        txbVirtue.setText(model.getValueAt(Click, 2).toString());

        btnChangeClassify.setEnabled(true);
        btnDeleteClassify.setEnabled(true);
    }//GEN-LAST:event_tableComponentMouseClicked

    private void btnRefreshClassifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshClassifyMouseClicked
        Refresh();
    }//GEN-LAST:event_btnRefreshClassifyMouseClicked

    private void btnAddClassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClassifyActionPerformed
        Refresh();
        Add=true;
        btnAddClassify.setEnabled(false);
        btnSaveClassify.setEnabled(true);
        EnabledComponent();
    }//GEN-LAST:event_btnAddClassifyActionPerformed

    private void btnChangeClassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeClassifyActionPerformed
        Add=false;
        Change=true;
        btnAddClassify.setEnabled(false);
        btnChangeClassify.setEnabled(false);
        btnDeleteClassify.setEnabled(false);
        btnSaveClassify.setEnabled(true);
        EnabledComponent();
    }//GEN-LAST:event_btnChangeClassifyActionPerformed

    private void btnDeleteClassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteClassifyActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa loại linh kiện hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM Classify WHERE ID=? AND Classify=?";
            try{
                pst=conn.prepareStatement(sqlDelete);
                pst.setString(1, txbIDComponent.getText());
                pst.setString(2,txbNameComponent.getText() );
                pst.executeUpdate();
                lblStatus.setText("Xóa loại nhà sản xuất thành công!");
                DisabledComponent();
                Refresh();
                loadComponent(sql3);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteClassifyActionPerformed

    private void btnSaveClassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveClassifyActionPerformed
        if(Add==true)
        if(CheckComponent())
        addClassify();
        else    lblStatus.setText("Mã loại linh kiện bạn nhập đã tồn tại!");
        else{
            if(Change==true)
            changedClassify();
        }
    }//GEN-LAST:event_btnSaveClassifyActionPerformed

    private void tablePositionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePositionMouseClicked
        int Click=tablePosition.getSelectedRow();
        TableModel model=tablePosition.getModel();

        txbPosition.setText(model.getValueAt(Click,0).toString());
        String []s=model.getValueAt(Click,1).toString().split("\\s");
        txbPayroll.setText(s[0]);

        btnChangePosition.setEnabled(true);
        btnDeletePosition.setEnabled(true);
    }//GEN-LAST:event_tablePositionMouseClicked

    private void txbPayrollKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txbPayrollKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        txbPayroll.setText(cutChar(txbPayroll.getText()));
        if(txbPayroll.getText().equals("")){
            return;
        }
        else{
            txbPayroll.setText(formatter.format(convertedToNumbers(txbPayroll.getText())));
        }
    }//GEN-LAST:event_txbPayrollKeyReleased

    private void btnRefreshPositionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRefreshPositionMouseClicked
        Refresh();
    }//GEN-LAST:event_btnRefreshPositionMouseClicked

    private void btnAddPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPositionActionPerformed
        Refresh();
        Add=true;
        btnAddPosition.setEnabled(false);
        btnSavePosition.setEnabled(true);
        EnabledPosition();
    }//GEN-LAST:event_btnAddPositionActionPerformed

    private void btnChangePositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePositionActionPerformed
        Add=false;
        Change=true;
        btnAddPosition.setEnabled(false);
        btnChangePosition.setEnabled(false);
        btnDeletePosition.setEnabled(false);
        btnSavePosition.setEnabled(true);
        EnabledPosition();
    }//GEN-LAST:event_btnChangePositionActionPerformed

    private void btnDeletePositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePositionActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa chức vụ hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM Position WHERE ID=? AND Position=? AND Payroll=?";
            try{
                pst=conn.prepareStatement(sqlDelete);
                
                pst.setString(1,txbPosition.getText() );
                pst.setString(2,txbPayroll.getText()+" "+"VND");
                pst.executeUpdate();
                lblStatus.setText("Xóa loại chức vụ thành công!");
                DisabledPosition();
                Refresh();
                loadPosition(sql1);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeletePositionActionPerformed

    private void btnSavePositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePositionActionPerformed
        if(Add==true)
        if(CheckPosition())
        addPosition();
        else    lblStatus.setText("Mã chức vụ bạn nhập đã tồn tại!");
        else{
            if(Change==true)
            changedPosition();
        }
    }//GEN-LAST:event_btnSavePositionActionPerformed

    private void btnBackHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackHomeMouseClicked
        backHome();
    }//GEN-LAST:event_btnBackHomeMouseClicked

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
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail=new Detail();
                new Data(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddClassify;
    private javax.swing.JButton btnAddPosition;
    private javax.swing.JButton btnBackHome;
    private javax.swing.JButton btnChangeClassify;
    private javax.swing.JButton btnChangePosition;
    private javax.swing.JButton btnDeleteClassify;
    private javax.swing.JButton btnDeletePosition;
    private javax.swing.JButton btnRefreshClassify;
    private javax.swing.JButton btnRefreshPosition;
    private javax.swing.JButton btnSaveClassify;
    private javax.swing.JButton btnSavePosition;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tableComponent;
    private javax.swing.JTable tablePosition;
    private javax.swing.JTextField txbIDComponent;
    private javax.swing.JTextField txbNameComponent;
    private javax.swing.JTextField txbPayroll;
    private javax.swing.JTextField txbPosition;
    private javax.swing.JTextField txbVirtue;
    // End of variables declaration//GEN-END:variables
}
