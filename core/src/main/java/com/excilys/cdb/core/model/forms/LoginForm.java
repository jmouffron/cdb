package com.excilys.cdb.core.model.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginForm {
	
	@NotEmpty
	@Size(min = 2, max = 255)
	private String login;
	
	@NotEmpty
	@Size(min = 2, max = 255)
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
