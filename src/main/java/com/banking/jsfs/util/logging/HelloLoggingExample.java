/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banking.jsfs.util.logging;

/**
 *
 * @author maradona
 */
import org.apache.log4j.Logger;
public class HelloLoggingExample {
    
final static Logger logger = Logger.getLogger(HelloLoggingExample.class);

	public static void main(String[] args) {

		HelloLoggingExample obj = new HelloLoggingExample();
		obj.runMe("mkyong");

	}

	private void runMe(String parameter){

		if(logger.isDebugEnabled()){
			logger.debug("This is debug : " + parameter);
		}

		if(logger.isInfoEnabled()){
			logger.info("This is info : " + parameter);
		}

		logger.warn("This is warn : " + parameter);
		logger.error("This is error : " + parameter);
		logger.fatal("This is fatal : " + parameter);

	}

}
