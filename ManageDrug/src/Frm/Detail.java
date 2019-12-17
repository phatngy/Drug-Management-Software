/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frm;

/**
 *
 * @author phatngy
 */
public class Detail {
    private String user;
    private String name;
    
    public Detail(){
        user="";
        name="";
    }
    
    public Detail(String us, String na){
        this.user=us;
        this.name=na;
    }

    public Detail(Detail detail){
        this.user=detail.user;
        this.name=detail.name;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
