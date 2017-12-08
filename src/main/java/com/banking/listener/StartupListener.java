/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.listener;

/**
 *
 * @author maradona
 */
import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.io.InputStream;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

@WebListener
public class StartupListener implements ServletContextListener {
    
private static final long serialVersionUID = 1L;

    public void contextInitialized(ServletContextEvent sce) {
        
        ServletContext context = sce.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
        System.out.println("The path is" + fullPath);
        
        File sfile =new File(fullPath);
        
        InputStream in = getClass().getResourceAsStream(fullPath);
        
        if(sfile.exists()) {
        System.out.println("a logger has been found");
        PropertyConfigurator.configure(fullPath);
        System.out.println("This deal is done");
        }
        else {
        System.out.println("Basic configurator is used");
        BasicConfigurator.configure();
        }
        
        
      
    }

    public void contextDestroyed(ServletContextEvent sce) {
        
    }


	
}

