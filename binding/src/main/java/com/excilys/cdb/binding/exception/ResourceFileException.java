package com.excilys.cdb.binding.exception;

public class ResourceFileException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String MSG = "Resource File for jdbc Connection Not Found";
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ResourceFileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message + MSG, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ResourceFileException(String message, Throwable cause) {
		super(message + MSG, cause);
	}

	/**
	 * @param message
	 */
	public ResourceFileException(String message) {
		super(message + MSG);
	}

	/**
	 * @param cause
	 */
	public ResourceFileException(Throwable cause) {
		super(cause);
	}

}
