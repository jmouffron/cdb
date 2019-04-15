package com.excilys.cdb.cli;

import org.slf4j.Logger;

import com.excilys.cdb.controller.MenuChoiceEnum;

/**
 * @author excilys
 * 
 *         A page abstract class contract for data display in views
 *
 * @param <T>
 */
public abstract class Page {
	/**
	 * Logging object
	 */
	protected Logger logger;
	/**
	 * Data encapsulator object for the view
	 */
	protected Data data;

	/**
	 * Returns a MenuChoiceEnum that defines a user choice for the controller
	 * 
	 * @return MenuChoiceEnum
	 */
	public abstract MenuChoiceEnum show();
}
