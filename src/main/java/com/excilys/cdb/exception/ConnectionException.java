/**
 * 
 */
package com.excilys.cdb.exception;

/**
 * @author excilys
 *
 *         A database connection in the dao layer
 *
 */
public class ConnectionException extends DaoException {

	private static final long serialVersionUID = -1186885962059451710L;
	private final static String EXC_MSG = "The connection couldn't be established, change connection parameters.";

	public ConnectionException() {
		super();
	}

	/**
	 * @param reason
	 */
	public ConnectionException(String reason) {
		super(EXC_MSG + reason);
	}

	/**
	 * @param cause
	 */
	public ConnectionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param reason
	 * @param SQLState
	 */
	public ConnectionException(String reason, String SQLState) {
		super(EXC_MSG + reason, SQLState);
	}

	/**
	 * @param reason
	 * @param cause
	 */
	public ConnectionException(String reason, Throwable cause) {
		super(EXC_MSG + reason, cause);
	}

	/**
	 * @param reason
	 * @param SQLState
	 * @param vendorCode
	 */
	public ConnectionException(String reason, String SQLState, int vendorCode) {
		super(EXC_MSG + reason, SQLState, vendorCode);
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param cause
	 */
	public ConnectionException(String reason, String sqlState, Throwable cause) {
		super(EXC_MSG + reason, sqlState, cause);
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param vendorCode
	 * @param cause
	 */
	public ConnectionException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(EXC_MSG + reason, sqlState, vendorCode, cause);
	}

}
