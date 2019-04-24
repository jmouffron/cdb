package com.excilys.cdb.binding.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.binding.utils.DateUtils;

/**
 * @author excilys
 *
 */
public class ServiceValidator {

	public static final Logger log = LoggerFactory.getLogger(ServiceValidator.class);

	public static boolean companyValidator(Company newEntity, String entity) throws BadInputException {
		if (newEntity == null) {
			log.error("Null NewEntity for " + entity);
			throw new BadInputException();
		}
		if (newEntity.getId() <= 0L) {
			log.error("Bad Id for " + entity);
			throw new BadInputException();
		}
		if (newEntity.getName() == null || newEntity.getName().isEmpty()) {
			log.error("Null Name on NewEntity for " + entity);
			throw new BadInputException();
		}

		return true;
	}
	
  public static boolean companyDTOValidator(CompanyDTO newEntity) throws BadInputException {
    if (newEntity == null) {
      log.error("Null NewEntity for company");
      throw new BadInputException();
    }
    if (newEntity.getId() <= 0L) {
      log.error("Bad Id for company");
      throw new BadInputException();
    }
    if (newEntity.getName() == null || newEntity.getName().isEmpty()) {
      log.error("Null Name on NewEntity for company");
      throw new BadInputException();
    }
    
    return true;
  }
	
	public static boolean computerValidator(Computer newEntity, String entity) throws BadInputException {
		if (newEntity == null) {
			log.error("Null NewEntity for " + entity);
			throw new BadInputException();
		}
    if (newEntity.getId() <= 0L) {
      log.error("Bad Id on NewEntity for " + entity);
      throw new BadInputException();
    }
		if (newEntity.getName() == null || newEntity.getName().equals("")) {
			log.error("Null Name on NewEntity for " + entity);
			throw new BadInputException();
		}
		if (newEntity.getIntroduced() == null && newEntity.getDiscontinued() != null) {
			log.error("Discontinued Date on NewEntity without Introduced Date for " + entity);
			throw new BadInputException();
		}
		if (newEntity.getIntroduced() != null && newEntity.getDiscontinued() != null) {
			if (newEntity.getIntroduced().compareTo(newEntity.getDiscontinued()) > 0) {
				log.error("Discontinued Date on NewEntity before Introduced Date for " + entity);
				throw new BadInputException();
			}
		}
		if (newEntity.getCompany() == null) {
			log.error("Null Company on NewEntity for " + entity);
			throw new BadInputException();
		}
		ServiceValidator.companyValidator(newEntity.getCompany(), entity);

		return true;
	}
	
  public static boolean computerDTOValidator(ComputerDTO newEntity, String entity) throws BadInputException {
    if (newEntity == null) {
      log.error("Null NewEntity for " + entity);
      throw new BadInputException();
    }
    if (newEntity.getId() <= 0L) {
      log.error("Bad Id on NewEntity for " + entity);
      throw new BadInputException();
    }
    if (newEntity.getName() == null || newEntity.getName().equals("")) {
      log.error("Null Name on NewEntity for " + entity);
      throw new BadInputException();
    }
    if (newEntity.getIntroduced() == null && newEntity.getDiscontinued() != null) {
      log.error("Discontinued Date on NewEntity without Introduced Date for " + entity);
      throw new BadInputException();
    }
    if (newEntity.getIntroduced() != null && newEntity.getDiscontinued() != null) {
      if (DateUtils.stringToTimestamp( newEntity.getIntroduced() ).compareTo( DateUtils.stringToTimestamp( newEntity.getIntroduced() ) ) > 0 ) {
        log.error("Discontinued Date on NewEntity before Introduced Date for " + entity);
        throw new BadInputException();
      }
    }
    if (newEntity.getCompanyName() == null || newEntity.getCompanyName().equals("") ) {
      log.error("Null Company on NewEntity for " + entity);
      throw new BadInputException();
    }

    return true;
    
  }

	public static boolean idValidator(Long id, String entity) throws BadInputException {
		if (id <= 0L) {
			log.error("Bad Id for " + entity);
			throw new BadInputException();
		}
		if (id > Long.MAX_VALUE) {
			log.error("Bad Input of id for " + entity);
			throw new BadInputException("Id too big inputted!");
		}

		return true;
	}

	public static boolean nameValidator(String name, String entity) throws BadInputException {
		if (name == null || name.equals("")) {
			log.error("Bad Name for " + entity);
			throw new BadInputException();
		}

		return true;
	}

}
