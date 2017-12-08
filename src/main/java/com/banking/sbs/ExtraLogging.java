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
import org.apache.log4j.Logger;
public class ExtraLogging {
    
    public static Logger logger = Logger.getLogger(ExtraLogging.class);

    public void trace (String parameter) {

        if(logger.isDebugEnabled()) {
            logger.debug("check if is debug : " + parameter);
        }

        if(logger.isInfoEnabled()) {
            logger.info("check if is info : " + parameter);
        }

        logger.warn("check if is warn: " + parameter);
        logger.error("check if is error : " + parameter);
        logger.fatal("check if is fatal : " + parameter);

    }
    
}
