/**
 * 
 */
package com.excilys.cdb.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * @author excilys
 *
 */
public class ServiceValidator {
	
	public static final Logger log = LoggerFactory.getLogger(ServiceValidator.class);
	
	public static boolean companyValidator(Company newEntity, String entity) throws BadInputException {
		if (newEntity == null) {
			log.debug("Null NewEntity for update of "+ entity);
			throw new BadInputException();
		}
		if (newEntity.getId() < 0L) {
			log.debug("Bad Id for update of "+ entity);
			throw new BadInputException();
		}
		if (newEntity.getName() == null || newEntity.getName().isEmpty()) {
			log.debug("Null Name on NewEntity for update of "+ entity);
			throw new BadInputException();
		}
		
		return true;
	}
	
	public static boolean computerValidator(Computer newEntity, String entity) throws BadInputException {
		if (newEntity == null) {
			log.debug("Null NewEntity for update of "+ entity);
			throw new BadInputException();
		}
		if (newEntity.getId() < 0) {
			log.debug("Bad Id for update of "+ entity);
			throw new BadInputException();
		}
		if (newEntity.getName() == null || newEntity.getName().equals("")) {
			log.debug("Null Name on NewEntity for update of "+ entity);
			throw new BadInputException();
		} 
		if (newEntity.getId() < 0L) {
			log.debug("Bad Id on NewEntity for update of "+ entity);
			throw new BadInputException();
		} 
		if(newEntity.getIntroduced() == null && newEntity.getDiscontinued() != null ) {
			log.debug("Discontinued Date on NewEntity without Introduced Date for update of "+ entity);
			throw new BadInputException();
		}
		if(newEntity.getIntroduced() != null && newEntity.getDiscontinued() != null) {
			if(newEntity.getIntroduced().after(newEntity.getDiscontinued())) {
				log.debug("Discontinued Date on NewEntity before Introduced Date for update of "+ entity);
				throw new BadInputException();
			}
		}
		if (newEntity.getCompany() == null) {
			log.debug("Null Company on NewEntity for update of "+ entity);
			throw new BadInputException();
		}
		ServiceValidator.companyValidator(newEntity.getCompany(), entity);
		
		return true;
	}
	
	public static boolean idValidator(Long id, String entity) throws BadInputException {
		if (id < 0L) {
			log.debug("Bad Id for deleting of "+ entity);
			throw new BadInputException();
		} 
		if (id > Long.MAX_VALUE) {
			log.debug("Bad Input of id for deleting of "+ entity);
			throw new BadInputException("Id too big inputted!");
		}
		
		return true;
	}
	
	public static boolean nameValidator(String name, String entity) throws BadInputException{
		if (name == null || name.equals("")) {
			log.debug("Bad Name for deleting of "+ entity);
			throw new BadInputException();
		}
		
		return true;
	}
}
