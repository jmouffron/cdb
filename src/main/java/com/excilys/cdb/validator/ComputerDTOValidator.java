package com.excilys.cdb.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.utils.DateUtils;

@Component
public class ComputerDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dtopc.name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "introduced", "dtopc.introduced.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "discontinued", "dtopc.discontinued.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "dtopc.companyName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyId", "dtopc.companyId.empty");

		ComputerDTO dto = (ComputerDTO) obj;

		Pattern pattern = Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{4}$", Pattern.CASE_INSENSITIVE);
		if ( dto.getName().length() > 255 ) {
			errors.rejectValue("name", "dtopc.name.invalid");
		}else if (!(pattern.matcher(dto.getIntroduced()).matches())) {
			errors.rejectValue("introduced", "dtopc.introduced.invalid");
		} else if (!(pattern.matcher(dto.getDiscontinued()).matches())) {
			errors.rejectValue("discontinued", "dtopc.introduced.invalid");
		} else if ( dto.getIntroduced() == null && dto.getDiscontinued() != null ) {
			errors.rejectValue("discontinued", "dtopc.introduced.invalid");
		} else if ( DateUtils.stringToTimestamp( dto.getIntroduced() ).after( DateUtils.stringToTimestamp( dto.getDiscontinued() ) ) ) {
			errors.rejectValue("introduced", "dtopc.introduced.invalid");
			errors.rejectValue("discontinued", "dtopc.introduced.invalid");
		}

	}
}
