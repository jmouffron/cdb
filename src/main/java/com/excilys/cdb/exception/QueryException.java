/**
 * 
 */
package com.excilys.cdb.exception;

/**
 * @author excilys
 * 
 *         A query exception in the dao layer
 * 
 */
public class QueryException extends DaoException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public QueryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public QueryException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public QueryException(Throwable cause) {
		super(cause);
	}

}
