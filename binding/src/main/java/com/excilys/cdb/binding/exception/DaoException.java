package com.excilys.cdb.binding.exception;

import java.sql.SQLException;

/**
 * @author excilys
 * 
 *         A generic exception for the dao layer exceptions
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

	}

	/**
	 * @param reason
	 */
	public DaoException(String reason) {
		super(reason);

	}

	/**
	 * @param cause
	 */
	public DaoException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param reason
	 * @param sqlState
	 */
	public DaoException(String reason, String sqlState) {
		super(reason, sqlState);

	}

	/**
	 * @param reason
	 * @param cause
	 */
	public DaoException(String reason, Throwable cause) {
		super(reason, cause);

	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param vendorCode
	 */
	public DaoException(String reason, String sqlState, int vendorCode) {
		super(reason, sqlState, vendorCode);

	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param cause
	 */
	public DaoException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);

	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param vendorCode
	 * @param cause
	 */
	public DaoException(String reason, String sqlState, int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);

	}

}
