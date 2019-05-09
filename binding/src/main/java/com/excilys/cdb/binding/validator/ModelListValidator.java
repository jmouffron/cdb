package com.excilys.cdb.binding.validator;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ModelListValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ModelList.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if ( obj == null) {
			errors.reject("500", "ModelList is null!");
		}
		
		Optional<ModelList> computers = Optional.ofNullable((ModelList) obj);
		
		if( !computers.isPresent() || computers.get().getIds() == null || computers.get().getIds().length == 0) {
			errors.reject("500", "ModelList ids is empty!");
		}
	}
}
