package com.excilys.cdb.core.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorJson implements Serializable{

	private static final long serialVersionUID = 3183490442643096576L;
	
	@NotBlank
	String message;
	
	@JsonIgnore
	HttpStatus status;
	
	private ErrorJson() {}
	
	private ErrorJson(HttpStatus status) {
		this.status = status;
		this.message = this.status.getReasonPhrase();
	}
	public static ErrorJson of(HttpStatus status) {
		return new ErrorJson(status);
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
