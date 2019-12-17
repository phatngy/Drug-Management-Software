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
public class test 
{
    public static void main(String[] args){
        
        String number = "", s = "1,000 VND";
        String []array = s.replace(","," ").split("\\s");
        System.out.println(array[2]);
        for(String i:array){
            number = number.concat(i);
        }
        System.out.println(number);   
    }
}
