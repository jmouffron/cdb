package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoCompanyFactory;
import com.excilys.cdb.persistence.DaoInstance;

public class CompanyService implements IService<Company> {
	private DaoInstance<Company> dao;
	private Logger log;

	public CompanyService() {
		this.dao = new DaoCompanyFactory().getDao();
		this.log = LoggerFactory.getLogger(CompanyService.class);
	}

	@Override
	public List<Company> getAll() {
		return dao.getAll();
	}

	@Override
	public Company getOneById(Long id) throws BadInputException {
		if (id <= 0) {
			log.debug("Bad Input of id for fetching of Company");
			throw new BadInputException("Non strictly positive id inputted!");
		}else if(id > Long.MAX_VALUE) {
			log.debug("Bad Input of id for fetching of Company");
			throw new BadInputException("Id too big inputted!");
		}
		
		return dao.getOneById(id);
	}

	@Override
	public Company getOneByName(String name) throws BadInputException {
		if (name == null || name.equals("")) {
			log.debug("Bad Input of id for fetching of Company");
			throw new BadInputException("Bad name inputted!");
		}
		
		return this.dao.getOneByName(name);
	}

	@Override
	public boolean create(Company newEntity) throws BadInputException {
		if(newEntity == null) {
			log.debug("Null NewEntity for update of Company");
			throw new BadInputException();
		}else if (newEntity.getId() < 0) {
			log.debug("Bad Id for update of Company");
			throw new BadInputException();
		}else if(newEntity.getName() == null || newEntity.getName().equals("")) {
			log.debug("Null Name on NewEntity for update of Company");
			throw new BadInputException();
		}else if(newEntity.getId() < 0) {
			log.debug("Bad Id on NewEntity for update of Company");
			throw new BadInputException();
		}
		
		return this.dao.create(newEntity);
	}

	/* (non-Javadoc)
	 * @see com.excilys.cdb.service.IService#updateById(java.lang.Long, com.excilys.cdb.model.Entity)
	 */
	@Override
	public boolean updateById(Long id, Company newEntity) throws BadInputException {
		if (id < 0) {
			log.debug("Bad Id for update of Company");
			throw new BadInputException();
		}else if(newEntity == null) {
			log.debug("Null NewEntity for update of Company");
			throw new BadInputException();
		}else if(newEntity.getName() == null || newEntity.getName().equals("")) {
			log.debug("Null Name on NewEntity for update of Company");
			throw new BadInputException();
		}else if(newEntity.getId() < 0) {
			log.debug("Bad Id on NewEntity for update of Company");
			throw new BadInputException();
		}
		
		return this.dao.updateById(id, newEntity);
	}

	@Override
	public boolean deleteById(Long id) throws BadInputException {
		if (id < 0) {
			log.debug("Bad Id for deleting of Company");
			throw new BadInputException();
		}else if(id > Long.MAX_VALUE) {
			log.debug("Bad Input of id for deleting of Company");
			throw new BadInputException("Id too big inputted!");
		}
		
		return this.dao.deleteById(id);
	}

	@Override
	public boolean deleteByName(String name) throws BadInputException {
		if (name == null || name.equals("")) {
			log.debug("Bad Name for deleting of Company");
			throw new BadInputException("Bad Name inputted");
		}		
		
		return this.dao.deleteByName(name);
	}

	@Override
	public DaoInstance<Company> getDao() {
		return getDao();
	}

}
