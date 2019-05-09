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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ErrorJson other = (ErrorJson) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message)) {
			return false;
		}
		return status != other.status;
	}
}
