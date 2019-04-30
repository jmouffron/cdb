package com.excilys.cdb.binding.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.cdb.binding.validator.ModelList;

public class ModelListValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.getSimpleName().equals("ModelList");
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
