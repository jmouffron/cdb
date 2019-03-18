package com.excilys.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoCompanyFactory;
import com.excilys.cdb.persistence.DaoInstance;
import com.excilys.cdb.validator.ServiceValidator;

public class CompanyService implements IService<Company> {
	private DaoInstance<Company> dao;
	private Logger log;

	public CompanyService() {
		this.dao = new DaoCompanyFactory().getDao();
		this.log = LoggerFactory.getLogger(CompanyService.class);
	}

	/*
	 * @see com.excilys.cdb.service.IService#getAll()
	 */
	@Override
	public List<Company> getAll() {
		return this.dao.getAll();
	}
	
	/*
	 * @see com.excilys.cdb.service.IService#searchByName(java.lang.String)
	 */
	@Override
	public List<Company> searchByName(String name) {
		List<Company> filteredCompanies = this.dao.getAll().stream()
				.filter(company -> company.getName().matches(name))
				.collect(Collectors.toList());
		return filteredCompanies;
	}
	
	/* 
	 * @see com.excilys.cdb.service.IService#getOneById(java.lang.Long)
	 */
	@Override
	public Company getOneById(Long id) throws BadInputException {
		if (id <= 0) {
			log.debug("Bad Input of id for fetching of Company");
			throw new BadInputException("Non strictly positive id inputted!");
		} else if (id > Long.MAX_VALUE) {
			log.debug("Bad Input of id for fetching of Company");
			throw new BadInputException("Id too big inputted!");
		}

		return dao.getOneById(id);
	}

	/* 
	 * @see com.excilys.cdb.service.IService#getOneByName(java.lang.String)
	 */
	@Override
	public Company getOneByName(String name) throws BadInputException {
		if (name == null || name.equals("")) {
			log.debug("Bad Input of id for fetching of Company");
			throw new BadInputException("Bad name inputted!");
		}

		return this.dao.getOneByName(name);
	}

	/* 
	 * @see com.excilys.cdb.service.IService#create(com.excilys.cdb.model.Entity)
	 */
	@Override
	public boolean create(Company newEntity) throws BadInputException {
		if (newEntity == null) {
			log.debug("Null NewEntity for update of Company");
			throw new BadInputException();
		} else if (newEntity.getId() < 0) {
			log.debug("Bad Id for update of Company");
			throw new BadInputException();
		} else if (newEntity.getName() == null || newEntity.getName().equals("")) {
			log.debug("Null Name on NewEntity for update of Company");
			throw new BadInputException();
		} else if (newEntity.getId() < 0) {
			log.debug("Bad Id on NewEntity for update of Company");
			throw new BadInputException();
		}

		return this.dao.create(newEntity);
	}

	/* 
	 * @see com.excilys.cdb.service.IService#updateById(java.lang.Long,
	 * com.excilys.cdb.model.Entity)
	 */
	@Override
	public boolean updateById(Company newEntity) throws BadInputException {
		ServiceValidator.companyValidator(newEntity, "Company");

		return this.dao.updateById(newEntity);
	}

	/* 
	 * @see com.excilys.cdb.service.IService#deleteById(java.lang.Long)
	 */
	@Override
	public boolean deleteById(Long id) throws BadInputException {
		ServiceValidator.idValidator(id, "Company");

		return this.dao.deleteById(id);
	}

	/* 
	 * @see com.excilys.cdb.service.IService#deleteByName(java.lang.String)
	 */
	@Override
	public boolean deleteByName(String name) throws BadInputException {
		ServiceValidator.nameValidator(name, "Company");

		return this.dao.deleteByName(name);
	}

	/* 
	 * @see com.excilys.cdb.service.IService#getDao()
	 */
	@Override
	public DaoInstance<Company> getDao() {
		return getDao();
	}

	/* 
	 * @see com.excilys.cdb.service.IService#setDao(com.excilys.cdb.persistence.DaoInstance)
	 */
	@Override
	public void setDao(DaoInstance<Company> dao) {
		this.dao = dao;
	}

}
