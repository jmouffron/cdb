package com.excilys.cdb.servlet;

public enum ErrorCode {
	FORBIDDEN_REQUEST(403), FILE_NOT_FOUND(404), SERVER_ERROR(500);

	private int errorCode;

	private ErrorCode(int errorCode) {
		this.setErrorCode(errorCode);
	}

	public int getErrorCode() {
		return errorCode;
	}

	private void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
