package com.excilys.cdb.webapp.enums;

public enum HttpCode {
	NOT_FOUND("404"), FORBIDDEN("403"), SERVER_ERROR("500"), EXCEPTION("exception");
	
	private String code;
	
	private HttpCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
}
