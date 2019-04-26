package com.excilys.cdb.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		return ComputerDTO.class.isAssignableFrom(clazz) ;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (obj == null) {
			errors.reject("500", "Computer is null!");
		}
		
		if(obj instanceof List) {
			List<ComputerDTO> computers = (List<ComputerDTO>) obj;
			computers.forEach( pc -> verif(pc, errors));
		}else {
			verif(obj, errors);
		}
		
	}
	
	private void verif(Object obj, Errors errors) {
		ComputerDTO dto = (ComputerDTO) obj;
		System.out.println();
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
			if (DateUtils.stringToTimestamp(dto.getIntroduced() + " 00:00:00")
					.compareTo(DateUtils.stringToTimestamp(dto.getDiscontinued() + " 00:00:00")) > 0) {
				errors.rejectValue("introduced", "dtopc.introduced.invalid");
				errors.rejectValue("discontinued", "dtopc.introduced.invalid");
			}
		}
	}
}
