package com.excilys.cdb.binding.exception;

public class ResourceFileException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ResourceFileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message + "Resource File for jdbc Connection Not Found", cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ResourceFileException(String message, Throwable cause) {
		super(message + "Resource File for jdbc Connection Not Found", cause);
	}

	/**
	 * @param message
	 */
	public ResourceFileException(String message) {
		super(message + "Resource File for jdbc Connection Not Found");
	}

	/**
	 * @param cause
	 */
	public ResourceFileException(Throwable cause) {
		super(cause);
	}

}
