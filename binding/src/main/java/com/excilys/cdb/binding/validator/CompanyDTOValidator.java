package com.excilys.cdb.binding.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

import com.excilys.cdb.binding.dto.CompanyDTO;

public class CompanyDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CompanyDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(target == null) {
			errors.reject("500", "Company is null!");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dtocompany.name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "dtocompany.id.empty");
		
		CompanyDTO dto = (CompanyDTO) target;
		
		if ( dto.getId() < 1 ) {
			errors.rejectValue("id", "dtocompany.id.invalid");
		} else if ( dto.getName().length() > 255 ) {
			errors.rejectValue("name", "dtocompany.name.invalid");
		}
	}

}
