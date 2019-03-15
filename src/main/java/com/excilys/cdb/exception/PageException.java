/**
 * 
 */
package com.excilys.cdb.exception;

/**
 * @author excilys
 *
 * A generic exception for the presentation layer
 * 
 */
public class PageException extends Exception {

	private static final long serialVersionUID = -3827766013553488295L;

	/**
	 * 
	 */
	public PageException() {
		super();
	}

	/**
	 * @param message
	 */
	public PageException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PageException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PageException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
