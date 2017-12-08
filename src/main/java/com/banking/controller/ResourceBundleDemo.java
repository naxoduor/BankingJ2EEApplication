/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.controller;

/**
 *
 * @author maradona
 */
import java.util.Locale;
import java.util.ResourceBundle;
public class ResourceBundleDemo {
    
public static void main(String[] args) {

   // create a new ResourceBundle with default locale
   
   System.out.println("Current Locale: " + Locale.getDefault());
   ResourceBundle bundle =
   ResourceBundle.getBundle("Messages");
   
   System.out.println(bundle);

   // print the text assigned to key "hello"
   System.out.println("" + bundle.getString("hello"));

   }
}
