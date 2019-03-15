package com.excilys.cdb;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.model.Entity;

/**
 * @author excilys
 *	
 *	It is the app
 *         main entrypoint.
 *
 */
public class App {
	public static void main(String[] args) {

		Controller<Entity> controller = Controller.getController();
		controller.start();
		System.exit(0);

	}
}
