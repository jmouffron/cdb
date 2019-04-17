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
		return ComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (obj == null) {
			errors.reject("500", "Computer is null!");
		}
		ComputerDTO dto = (ComputerDTO) obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "dtopc.name.empty");

		if (!"".equals(dto.getDiscontinued())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "introduced", "dtopc.introduced.empty");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyId", "dtopc.companyId.empty");

		Pattern pattern = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$", Pattern.CASE_INSENSITIVE);

		if (dto.getName().length() > 255 || dto.getName().length() == 0) {
			errors.rejectValue("name", "dtopc.name.invalid");
		}

		if (!"".equals(dto.getIntroduced()) && "".equals(dto.getDiscontinued())) {
			if (!(pattern.matcher(dto.getIntroduced()).matches())) {
				errors.rejectValue("introduced", "dtopc.introduced.invalid");
			}
		} else if (!"".equals(dto.getIntroduced()) && !"".equals(dto.getDiscontinued())) {
			if (!(pattern.matcher(dto.getIntroduced()).matches())) {
				errors.rejectValue("introduced", "dtopc.introduced.invalid");
			}
			if (!(pattern.matcher(dto.getDiscontinued()).matches())) {
				errors.rejectValue("discontinued", "dtopc.introduced.invalid");
			}
			if (DateUtils.stringToTimestamp(dto.getIntroduced())
					.compareTo(DateUtils.stringToTimestamp(dto.getDiscontinued())) > 0) {
				errors.rejectValue("introduced", "dtopc.introduced.invalid");
				errors.rejectValue("discontinued", "dtopc.introduced.invalid");
			}
		}
	}
}
