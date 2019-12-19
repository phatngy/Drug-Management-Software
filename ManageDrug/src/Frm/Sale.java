/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frm;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;



class Sale extends javax.swing.JFrame implements Runnable {
    
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    private Connection conn = managedrug.ManageDrug.connect.conn;
    
    
    private Thread thread;
    private Detail detail;

    public Sale(Detail d) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail = new Detail(d);
        lblStatus.setForeground(Color.red);
        
        setData();
        Start();
        Disabled();
//        checkTable();
    }
    
    private void setData(){
        Disabled();
        lblName.setText(detail.getName());
        lblDate.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
    }
    
    private void Pays(){
        lbltotalMoney.setText("0");
        String sqlPay = "SELECT * FROM Bill";
        try{
            pst = conn.prepareStatement(sqlPay);
            rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("Total").trim();
                double totalMoney = convertedToNumbers(s)+ convertedToNumbers(lbltotalMoney.getText());
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                
                lbltotalMoney.setText(formatter.format(totalMoney));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void Start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }
    
    private void Update(){
        lblTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
    }
    
    
    private void Disabled(){
        cbxComponent.setEnabled(false);
        cbxDrug.setEnabled(false);
        txbAmount.setEnabled(false);
        txbIDBill.setEnabled(false);
        btnCheckIDBill.setEnabled(false);
        btnPay.setEnabled(false);

    }
   
    private boolean CheckBill() {
        boolean kq = true;
        String sqlCheck = "SELECT * FROM Bill";
        try{
            PreparedStatement pstCheck = conn.prepareStatement(sqlCheck);
            ResultSet rsCheck = pstCheck.executeQuery();
            
            while(rsCheck.next()){
                if(this.txbIDBill.getText().equals(rsCheck.getString("ID_Bill").trim())){
                    lblStatus.setText("Đơn Hàng Đã Tồn Tại!! Hãy Nhập Lại!");
                    return false;                                           
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    private boolean CheckBillDetail() {
        boolean kq = true;
        String sqlCheck = "SELECT * FROM BillDetail";
        try{
            PreparedStatement pstCheck = conn.prepareStatement(sqlCheck);
            ResultSet rsCheck = pstCheck.executeQuery();
            
            while(rsCheck.next()){
                if(this.txbIDBill.getText().equals(rsCheck.getString("ID_Bill").trim()) & this.txbIDDrug.getText().equals(rsCheck.getString("ID_Drug").trim())){
                    lblStatus.setText("Đơn Hàng Đã Tồn Tại!! Hãy Nhập Lại!");
                    return false;                                           
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    private boolean checkNull(){
        boolean kq = true;
        if(String.valueOf(this.txbIDDrug.getText()).length()==0){
            lblStatus.setText("Bạn chưa chọn sản phẩm!");
            return false;
        }
        else if(String.valueOf(this.txbAmount.getText()).length()==0){
            lblStatus.setText("Bạn chưa nhập số lượng sản phẩm!");
            return false;
        }
        return kq;
    }
    
    private void Sucessful(){
        btnCheckIDBill.setEnabled(false);
        btnAdd.setEnabled(true);
        btnNew.setEnabled(true);
        btnChange.setEnabled(false);
        btnDelete.setEnabled(false);
    }
       
    private String getID_NV(){
        String sql = "SELECT * FROM Employee WHERE UserName = '"+detail.getUser() +"'";
        String ID_NV = null;
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next())
                ID_NV = rs.getString("ID_NV").trim();
                
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return ID_NV;
        
    }    
//    private void addBill() {
//        
//        String sqlInsert = "INSERT INTO Bill (ID_Bill, ID_NV, Time, Date, Total) VALUES(?,?,?,?,?)";
//        String ID_NV = getID_NV();
//        try{
//
//            pst = conn.prepareStatement(sqlInsert);
//            pst.setString(1, String.valueOf(txbIDBill.getText()));
//            pst.setString(2, ID_NV);
//            pst.setString(3, lblTime.getText());
//            pst.setDate(4,new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(lblDate.getText()).getTime()));
//            pst.setString(5, lbltotalMoney.getText());
//            pst.executeUpdate();
//            lblStatus.setText("Thanh Toán Và Thêm Hoá Đơn Thành Công!");
//
//            Disabled();
//            Sucessful();
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }
//        
//    }

    private void LoadComponent(){
        String sql = "SELECT * FROM MainComponent";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbxComponent.addItem(rs.getString("NameMainComponent").trim());
            }
        }  
        catch (Exception ex) {  
            ex.printStackTrace();  
        }
    }
    
    private void changeDrug() {
        int Click = tableBill.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
        
        model.setValueAt(txbIDDrug.getText(), Click, 0);
        model.setValueAt(cbxDrug.getSelectedItem().toString(), Click, 1);
        model.setValueAt(txbAmount.getText(), Click, 2);
        model.setValueAt(txbIntoMoney.getText(), Click, 3);
        updateMoney();
//        lblStatus.getText();
    }
    
    private void addtoTable(){
        try{
            String [] arr = {"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Tiền"};
            DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
            
            Vector vector = new Vector();
            vector.add(txbIDDrug.getText());
            vector.add(cbxDrug.getSelectedItem().toString());
            vector.add(txbAmount.getText());
            vector.add(txbIntoMoney.getText());
            model.addRow(vector);
            
            tableBill.setModel(model);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        addDrug();
    }
    
    private void consistency(){
        String sqlBill = "SELECT * FROM BillDetail";
        try{
            
            PreparedStatement pstBill = conn.prepareStatement(sqlBill);
            ResultSet rsBillDetail = pstBill.executeQuery();
            
            while(rsBillDetail.next()){
                
                try{
                    String sqlDrug = "SELECT * FROM Drug WHERE ID_Drug ='"+rsBillDetail.getString("ID_Drug")+"'";
                    PreparedStatement pstDrug = conn.prepareStatement(sqlDrug);
                    ResultSet rsDrug = pstDrug.executeQuery();
                    
                    if(rsDrug.next()){
                        
                        String sqlUpdate = "UPDATE Drug SET Quantity=? WHERE ID_Drug='"+rsBillDetail.getString("ID_Drug").trim()+"'";
                        try{
                            pst = conn.prepareStatement(sqlUpdate);
                            pst.setInt(1, rsDrug.getInt("Quantity") - rsBillDetail.getInt("Amount"));
                            pst.executeUpdate();
                        }
                        catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }  
    }
    
    private void checkProducts(){
        String sqlCheck = "SELECT Quantity FROM Drug WHERE ID_Drug='"+txbIDDrug.getText()+"'";
        try{
            pst = conn.prepareCall(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()){
                if(rs.getInt("Quantity")==0){
                    lblStatus.setText("Loại thuốc này đã hết!!");
                    btnCheckIDBill.setEnabled(false);
                    txbAmount.setEnabled(false);
                }
                else{
                    lblStatus.setText("Loại thuốc này còn "+rs.getInt("Quantity")+" sản phẩm!!");
                    btnCheckIDBill.setEnabled(true);
                    txbAmount.setEnabled(true);
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private double convertedToNumbers(String s){
        String number = "";
        String []array = s.replace(","," ").split("\\s");
        for(String i:array){
            number = number.concat(i);
        }
        return Double.parseDouble(number);
    }
    
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }
    
    private void addBill(){
        String sql = "INSERT INTO Bill (ID_Bill) VALUES (?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, String.valueOf(txbIDBill.getText()));
            
            pst.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void addDrug() {
        if(checkNull()){
            String sqlInsert = "INSERT INTO BillDetail (ID_Bill, ID_Drug, Amount) VALUES(?,?,?)";
            try{
                pst=conn.prepareStatement(sqlInsert);
                pst.setString(1, String.valueOf(txbIDBill.getText()));
                pst.setString(2, String.valueOf(txbIDDrug.getText()));
                pst.setInt(3, Integer.parseInt(txbAmount.getText()));

                pst.executeUpdate();
                lblStatus.setText("Thêm sản phẩm thành công!");
                Disabled();
                Sucessful();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    private String TotalMoneyAdd(){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format((convertedToNumbers(txbIntoMoney.getText()) + convertedToNumbers(lbltotalMoney.getText())));
    }
    private String TotalMoneySub(){
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            return formatter.format((convertedToNumbers(lbltotalMoney.getText()) - convertedToNumbers(txbIntoMoney.getText())));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableBill = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbxComponent = new javax.swing.JComboBox<String>();
        cbxDrug = new javax.swing.JComboBox<String>();
        txbIntoMoney = new javax.swing.JTextField();
        txbAmount = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txbIDDrug = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txbPrice = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnChange = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnPay = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnBackHome = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbltotalMoney = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txbIDBill = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        btnCheckIDBill = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        tableBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Thuốc", "Tên Thuốc", "Số lượng", "Tiền"
            }
        ));
        tableBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBill);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Thành Phần Chính:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Tên Thuốc:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Số Lượng:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Thành Tiền:");

        cbxComponent.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbxComponentPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbxComponent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxComponentActionPerformed(evt);
            }
        });

        cbxDrug.setEnabled(false);
        cbxDrug.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbxDrugPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txbIntoMoney.setEnabled(false);

        txbAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txbAmountKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Mã Thuốc:");

        txbIDDrug.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Giá:");

        txbPrice.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txbIDDrug)
                    .addComponent(cbxComponent, 0, 178, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxDrug, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txbAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txbPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(txbIntoMoney))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbxDrug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txbPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txbIDDrug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txbAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txbIntoMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        btnAdd.setText("Thêm Thuốc");
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        btnDelete.setText("Xóa Thuốc");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnChange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        btnChange.setText("Sửa");
        btnChange.setEnabled(false);
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/New.png"))); // NOI18N
        btnNew.setText("Hóa Đơn Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print Sale.png"))); // NOI18N
        btnPrint.setText("Xuất Hóa Đơn");
        btnPrint.setEnabled(false);
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pay.png"))); // NOI18N
        btnPay.setText("Thanh Toán");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblName.setText("Nhân Viên:");

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDate.setText("Date");

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTime.setText("Time");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Giờ:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Ngày:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(lblDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(lblName)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDate)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(116, 116, 116)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChange, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(btnPay, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnBackHome.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBackHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        btnBackHome.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnBackHome.setContentAreaFilled(false);
        btnBackHome.setFocusPainted(false);
        btnBackHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBackHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackHomeMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 28)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Bán Hàng");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setText("Tổng Tiền Hóa Đơn:");

        lbltotalMoney.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbltotalMoney.setText("0");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setText("ID Hoá Đơn");

        txbIDBill.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txbIDBill.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txbIDBillKeyReleased(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Trạng Thái");

        btnCheckIDBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/checkmark_48px.png"))); // NOI18N
        btnCheckIDBill.setAlignmentY(0.0F);
        btnCheckIDBill.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCheckIDBill.setContentAreaFilled(false);
        btnCheckIDBill.setFocusPainted(false);
        btnCheckIDBill.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCheckIDBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckIDBillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbltotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txbIDBill, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCheckIDBill))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(btnBackHome, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBackHome, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txbIDBill, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnCheckIDBill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbltotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatus)
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBillMouseClicked
        int Click = tableBill.getSelectedRow();
        TableModel model = tableBill.getModel();

        txbIDDrug.setText(model.getValueAt(Click,0).toString());
        cbxDrug.addItem(model.getValueAt(Click,1).toString());
        txbAmount.setText(model.getValueAt(Click,2).toString());
        txbIntoMoney.setText(model.getValueAt(Click,3).toString());

        btnChange.setEnabled(true);
        btnDelete.setEnabled(true);
        txbAmount.setEnabled(true);
        
        cbxComponent.setEnabled(false);
        btnAdd.setEnabled(false);
        btnNew.setEnabled(false);
        btnPay.setEnabled(false);
        btnCheckIDBill.setEnabled(false);
        txbIDBill.setEnabled(false);
    }//GEN-LAST:event_tableBillMouseClicked

    private void cbxComponentPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxComponentPopupMenuWillBecomeInvisible
        cbxDrug.removeAllItems();
        String sql = "SELECT * FROM Drug a INNER JOIN MainComponent b on b.ID_Component = a.MainComponent where b.NameMainComponent = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, cbxComponent.getSelectedItem().toString());
            rs = pst.executeQuery();
            while(rs.next()){
                this.cbxDrug.addItem(rs.getString("NameDrug").trim());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if(cbxDrug.getItemCount()!=0) cbxDrug.setEnabled(true);
        else {
            cbxDrug.setEnabled(false);
            txbAmount.setEnabled(false);
            txbIDDrug.setText("");
            txbPrice.setText("");
            txbAmount.setText("");
            txbIntoMoney.setText("");
        }
    }//GEN-LAST:event_cbxComponentPopupMenuWillBecomeInvisible

    private void cbxDrugPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxDrugPopupMenuWillBecomeInvisible
        String sql = "SELECT * FROM Drug where NameDrug=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, this.cbxDrug.getSelectedItem().toString());
            rs = pst.executeQuery();
            while(rs.next()){
                txbIDDrug.setText(rs.getString("ID_Drug").trim());
                txbPrice.setText(rs.getString("Price").trim());
                txbAmount.setEnabled(true);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        checkProducts();
    }//GEN-LAST:event_cbxDrugPopupMenuWillBecomeInvisible

    private void txbAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txbAmountKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        txbAmount.setText(cutChar(txbAmount.getText()));

        if(txbAmount.getText().equals("")){
            txbIntoMoney.setText(txbPrice.getText());
        }
        else{
            String sqlCheck = "SELECT Quantity FROM Drug WHERE ID_Drug='"+txbIDDrug.getText()+"'";
            try{
                pst = conn.prepareStatement(sqlCheck);
                rs = pst.executeQuery();

                while(rs.next()){
                    if((rs.getInt("Quantity")-Integer.parseInt(txbAmount.getText()))<0){
                        txbIntoMoney.setText(txbPrice.getText());

                        lblStatus.setText("Số lượng bán không được vượt quá số lượng hàng trong kho!!");
                        btnCheckIDBill.setEnabled(false);
                    }
                    else{
                        int soluong = Integer.parseInt(txbAmount.getText());
                        txbIntoMoney.setText(formatter.format(convertedToNumbers(txbPrice.getText())*soluong));

                        lblStatus.setText("Số lượng bán hợp lệ!!");
                        btnCheckIDBill.setEnabled(true);
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_txbAmountKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if("".equals(txbIDBill.getText())){
            lblStatus.setText("Chưa Nhập Mã Hoá Đơn");
        }
        else{
            txbIDBill.setEnabled(false);
            if(CheckBillDetail() && checkNull()){
                addtoTable();
                lbltotalMoney.setText(TotalMoneyAdd());
                btnAdd.setEnabled(true);
                btnDelete.setEnabled(false);
                btnChange.setEnabled(false);
                btnPay.setEnabled(true);
                btnNew.setEnabled(false);
                cbxComponent.setEnabled(true);
                
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa loại thuốc này khỏi hóa đơn hay không?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION){
            
            String sqlDelete = "DELETE FROM BillDetail WHERE ID_Drug = ? and ID_Bill = ?";
            try{
                lbltotalMoney.setText(TotalMoneySub());
                pst = conn.prepareStatement(sqlDelete);
                pst.setString(1, String.valueOf(txbIDDrug.getText()));
                pst.setString(2, String.valueOf(txbIDBill.getText()));
                pst.executeUpdate();
                this.lblStatus.setText("Xóa thành công!");
                Sucessful();
                
                DefaultTableModel model = (DefaultTableModel) tableBill.getModel();
                int SelectedRow = tableBill.getSelectedRow();
                model.removeRow(SelectedRow);
                tableBill.clearSelection();
                
                btnAdd.setEnabled(true);
                btnPay.setEnabled(true);
                btnNew.setEnabled(false);
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCheckIDBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckIDBillActionPerformed
        if(CheckBill()){
            
            lblStatus.setText("Hoá Đơn Khả Dụng");
            addBill();
            txbIDBill.setEnabled(false);
            btnCheckIDBill.setEnabled(false);
            
        }
        else{
            lblStatus.setText("Hoá Đơn Tồn Tại! Hãy Nhập Lại!!");
            txbIDBill.setEnabled(true);
            btnCheckIDBill.setEnabled(true);
        }
    }//GEN-LAST:event_btnCheckIDBillActionPerformed

    private String updateMoney(){
        int Click = tableBill.getSelectedRow();
        TableModel model = tableBill.getModel();
        
        double t1 = convertedToNumbers(lbltotalMoney.getText());
        double t2 = convertedToNumbers(model.getValueAt(Click , 3).toString());
        double t3 = convertedToNumbers(txbIntoMoney.getText());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format((t1 - t2 + t3));
    }
    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
       String sqlChange = "UPDATE BillDetail SET Amount=? WHERE ID_Bill = '"+txbIDBill.getText()+"' AND ID_Drug = '" + txbIDDrug.getText()+"'";
        try{
            
            int Click = tableBill.getSelectedRow();
            TableModel model = tableBill.getModel();

            double t1 = convertedToNumbers(lbltotalMoney.getText());
            double t2 = convertedToNumbers(model.getValueAt(Click , 3).toString());
            
            pst = conn.prepareStatement(sqlChange);
            pst.setInt(1, Integer.parseInt(this.txbAmount.getText()));
            pst.executeUpdate();
            lblStatus.setText("Lưu thay đổi thành công!");
            double t3 = convertedToNumbers(txbIntoMoney.getText());
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            changeDrug();
            lbltotalMoney.setText(formatter.format((t1-t2+t3)*1000));
            tableBill.clearSelection();
            
            cbxComponent.setEnabled(true);
//            LoadComponent();
            btnAdd.setEnabled(true);
            btnPay.setEnabled(true);
            btnNew.setEnabled(false);
            btnChange.setEnabled(false);
            btnDelete.setEnabled(false);
            btnCheckIDBill.setEnabled(false);
            txbAmount.setEnabled(true);
           
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnChangeActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn tạo 1 hóa đơn mới hay không?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION){
            try{ 
                lbltotalMoney.setText("0");
                txbAmount.setText("");
                
                clearTable();
                btnAdd.setEnabled(true);
                cbxComponent.removeAllItems();
                cbxComponent.setEnabled(true);
                LoadComponent();
                txbIDBill.setEnabled(true);
                btnCheckIDBill.setEnabled(true);
                txbAmount.setEnabled(true);
                btnPay.setEnabled(true);
                btnNew.setEnabled(false);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        
        JOptionPane.showMessageDialog(null, "Chức Năng Đang Được Hoàn Thiện, Sẽ Có Vào Bản Update Sau!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        this.setVisible(true);
    }//GEN-LAST:event_btnPrintActionPerformed

    private void clearTable(){
        tableBill.setModel(new DefaultTableModel(null, new String []{"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Tiền"}));
        
    }
    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        
        if(tableBill.getRowCount()!=0){
            String sqlInsert = "UPDATE Bill SET ID_NV = ?,  Time = ?, Date = ?, Total = ?  WHERE ID_Bill = ?";
            String ID_NV = getID_NV();
            try{

                pst = conn.prepareStatement(sqlInsert);
                pst.setString(1, ID_NV);
                pst.setString(2, lblTime.getText());
                pst.setDate(3,new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(lblDate.getText()).getTime()));
                pst.setString(4, lbltotalMoney.getText());
                pst.setString(5, String.valueOf(txbIDBill.getText()));
                pst.executeUpdate();
                lblStatus.setText("Thanh Toán Và Thêm Hoá Đơn Thành Công!");

                Disabled();
    
                consistency();
//                clearTable();
                cbxComponent.removeAllItems();
                btnPrint.setEnabled(true);
                btnNew.setEnabled(true);
                btnAdd.setEnabled(false);
                btnPay.setEnabled(false);
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else    lblStatus.setText("Chưa Thêm Đơn Thuốc");
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnBackHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackHomeMouseClicked
        if(this.detail.getUser().toString().equals("Admin")){
            Home home = new Home(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
        else{
            HomeUser home = new HomeUser(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
    }//GEN-LAST:event_btnBackHomeMouseClicked

    private void txbIDBillKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txbIDBillKeyReleased

    }//GEN-LAST:event_txbIDBillKeyReleased

    private void cbxComponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxComponentActionPerformed
        
    }//GEN-LAST:event_cbxComponentActionPerformed

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
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail = new Detail();
                new Sale(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBackHome;
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnCheckIDBill;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox<String> cbxComponent;
    private javax.swing.JComboBox<String> cbxDrug;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lbltotalMoney;
    private javax.swing.JTable tableBill;
    private javax.swing.JTextField txbAmount;
    private javax.swing.JTextField txbIDBill;
    private javax.swing.JTextField txbIDDrug;
    private javax.swing.JTextField txbIntoMoney;
    private javax.swing.JTextField txbPrice;
    // End of variables declaration//GEN-END:variables
    @Override
    public void run() {
        while(true){
        Update();  
            try{
                Thread.sleep(1);  
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
