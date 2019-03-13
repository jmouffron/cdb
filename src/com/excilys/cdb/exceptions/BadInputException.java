package com.excilys.cdb.exceptions;

/**
 * @author excilys
 *
 */
public class BadInputException extends ServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String EXC_MSG = "Bad value inputted.";

	public BadInputException() {
		super(EXC_MSG);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public BadInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(EXC_MSG + message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BadInputException(String message, Throwable cause) {
		super(EXC_MSG + message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public BadInputException(String message) {
		super(EXC_MSG + message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public BadInputException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
