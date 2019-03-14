/**
 * 
 */
package com.excilys.cdb.exception;

/**
 * @author excilys
 *
 */
public class ConnectionException extends DaoException {
	private final static String EXC_MSG = "The connection couldn't be established, change connection parameters.";

	/**
	 * 
	 */
	public ConnectionException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 */
	public ConnectionException(String reason) {
		super(EXC_MSG + reason);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ConnectionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param SQLState
	 */
	public ConnectionException(String reason, String SQLState) {
		super(EXC_MSG + reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param cause
	 */
	public ConnectionException(String reason, Throwable cause) {
		super(EXC_MSG + reason, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param SQLState
	 * @param vendorCode
	 */
	public ConnectionException(String reason, String SQLState, int vendorCode) {
		super(EXC_MSG + reason, SQLState, vendorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param cause
	 */
	public ConnectionException(String reason, String sqlState, Throwable cause) {
		super(EXC_MSG + reason, sqlState, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param vendorCode
	 * @param cause
	 */
	public ConnectionException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(EXC_MSG + reason, sqlState, vendorCode, cause);
		// TODO Auto-generated constructor stub
	}

}
