/**
 * 
 */
package com.excilys.cdb.exception;

import java.sql.SQLException;

/**
 * @author excilys
 *
 */
public class DaoException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6622779684936058844L;

	/**
	 * 
	 */
	public DaoException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 */
	public DaoException(String reason) {
		super(reason);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param SQLState
	 */
	public DaoException(String reason, String SQLState) {
		super(reason, SQLState);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param cause
	 */
	public DaoException(String reason, Throwable cause) {
		super(reason, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param SQLState
	 * @param vendorCode
	 */
	public DaoException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param cause
	 */
	public DaoException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param vendorCode
	 * @param cause
	 */
	public DaoException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
		// TODO Auto-generated constructor stub
	}

}
