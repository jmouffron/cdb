/**
 * 
 */
package com.excilys.cdb.exception;

/**
 * @author excilys
 *
 */
public class CreationException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8201183813945204038L;

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CreationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public CreationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public CreationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
