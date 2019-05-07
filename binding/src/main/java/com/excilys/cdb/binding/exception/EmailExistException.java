package com.excilys.cdb.binding.exception;

public class EmailExistException extends Exception {

	private static final long serialVersionUID = 4951412957170065014L;
	
	public EmailExistException() {
		super();
	}
	
	public EmailExistException(String message) {
		super(message);
	}
	
	public EmailExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
