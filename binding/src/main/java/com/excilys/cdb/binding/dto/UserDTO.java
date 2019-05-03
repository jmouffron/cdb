package com.excilys.cdb.binding.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDTO {

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String password;

	@NotEmpty
	private String confirmPassword;

	@Email
	@NotEmpty
	private String email;

	@Email
	@NotEmpty
	private String confirmEmail;
	
	@AssertTrue
    private Boolean terms;
}
