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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public class DateMain {
    
    public static void main(String[] args) {
    
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        
        System.out.println(today);
        System.out.println(tomorrow);
        System.out.println(yesterday);
    }
    
}
