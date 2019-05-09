package com.excilys.cdb.console.cli;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.exception.ServiceException;

/**
 * It is the app main entrypoint and Spring Container initializer.
 * 
 * @author excilys
 * 
 */
public class App extends Application {
	private static Logger logger = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
		Controller controller = Controller.of();
		try {
			controller.start();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
		}
		System.exit(0);
	}
}
