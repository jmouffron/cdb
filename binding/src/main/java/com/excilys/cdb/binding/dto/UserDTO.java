package com.excilys.cdb.binding.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDTO {
	
	public UserDTO() {
		
	}
	
	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	@Email
	@NotEmpty
	private String email;


	public String getUsername() {
		return username;
	}

	public void setLastName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
