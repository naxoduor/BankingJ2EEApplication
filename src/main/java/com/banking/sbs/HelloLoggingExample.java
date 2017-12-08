/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.sbs;

/**
 *
 * @author maradona
 */
import com.banking.entities.Owner;
import com.banking.entities.Credit;
import com.banking.sbs.ExtraLogging;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import java.util.Map;
import java.util.HashMap;

public class HelloLoggingExample extends ExtraLogging {
    
     final static Logger logger = Logger.getLogger(HelloLoggingExample.class);

    public static void main(String[] args) {

        HelloLoggingExample obj = new HelloLoggingExample();



        obj.runMe("naxoduor");

    }

    public void runMe(String parameter) {
        

        if(logger.isDebugEnabled()) {
            logger.debug("This is debug : " + parameter);
        }
        
        DataStore dataStore = new DataStore();
        
        
        String squery = "SELECT c FROM Credit c WHERE c.date <= :dateToday";
        
        HashMap<String, Object>params = new HashMap<String, Object>();
        
        params.put("dateToday", LocalDate.now());
        
        List<Credit> items = dataStore.findEntries(squery, params);
        
        logger.info("log values from the database");
        
        for(Credit credit : items) {
            logger.info(credit.getAmount());
            logger.info(credit.getDate());
            logger.info(credit.getDescription());
        }

        if(logger.isInfoEnabled()) {
            logger.info("This is info : " + parameter);
        }

        logger.warn("This is warn: " + parameter);
        logger.error("This is error : " + parameter);
        logger.fatal("This is fatal : " + parameter);
        trace("ronnyodero1");

       

    }
    
}
