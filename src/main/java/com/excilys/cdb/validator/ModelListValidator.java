package com.excilys.cdb.validator;

import java.awt.List;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.ModelList;
import com.excilys.cdb.utils.DateUtils;

public class ModelListValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		if(clazz.getSuperclass().getSimpleName().equals("EntityList")) {
			return true;
		}
		return List.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if ( obj == null) {
			errors.reject("500", "ModelList is null!");
		}
		
		ModelList computers = (ModelList) obj;
		
		if( computers.getIds().length == 0) {
			errors.reject("500", "ModelList ids is empty!");
		}
		System.out.println("ModelListValidator: " + computers.getIds().toString());
	}
}
