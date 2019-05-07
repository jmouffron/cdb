package com.excilys.cdb.core.model.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignupForm {
	
	@NotEmpty
	@Size(min = 2, max = 255)
	private String firstName;
	
	@NotEmpty
	@Size(min = 2, max = 255)
	private String lastName;
	
	@NotEmpty
	@Size(min = 2, max = 255)
	private String username;
	
	@NotEmpty
	@Size(min = 8, max = 255)
	private String password;
	
	@NotEmpty
	@Size(min = 4, max = 255)
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
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
