package com.excilys.cdb.binding.validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.core.model.User;

public class UserValidator implements Validator {
	
	public static final String USERNAME = "username";
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(User.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if ( obj == null) {
			errors.reject("500", "User is null!");
		}
		Optional<User> dto = Optional.ofNullable((User) obj);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, USERNAME, "user.username.empty" );
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.empty" );
		
		String regex = "((?=.*[a-z])(?=.*\\\\d)(?=.*[A-Z]).{8,255})";
		Pattern rule = Pattern.compile(regex);

		String password = dto.orElseThrow(RuntimeException::new).getPassword();
		
		Matcher regexMatcher = rule.matcher(password);
		
		if ( dto.orElseThrow(RuntimeException::new).getUsername().length() < 2 || dto.orElseThrow(RuntimeException::new).getUsername().length() > 255 ) {
			errors.rejectValue(USERNAME, "user.username.invalid");
		} else if (!regexMatcher.matches()) {
			errors.rejectValue("password", "user.password.invalid");
		} 
	}

}
