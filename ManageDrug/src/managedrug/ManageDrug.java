/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedrug;

import DataBase.connectDB;
import Frm.Login;
/**
 *
 * @author phatngy
 */
public class ManageDrug {
       
    public static DataBase.connectDB connect = new DataBase.connectDB();

    public static void main(String[] args) {
        Frm.Login login = new Frm.Login();
        login.show();
    }
    
}
