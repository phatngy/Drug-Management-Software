/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Windows 10 Version 2
 */
public class connectDB {
    
    private String connectionString = "jdbc:sqlserver://ANONYMOUS\\phatngy;databaseName=KTPMDatabase2;integratedSecurity=true";
    public Connection conn;
    public connectDB(){
       
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(connectionString);
            
            if(conn != null){
                System.out.println("Kết nối CSDL SQL Server thành công!");
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    

    public static void main(String[] args) {
        connectDB connect = new connectDB();
    }
}
