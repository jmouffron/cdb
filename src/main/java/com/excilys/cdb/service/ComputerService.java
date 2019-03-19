package com.excilys.cdb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.IDaoInstance;
import com.excilys.cdb.validator.ServiceValidator;

public class ComputerService implements IService<Computer> {
	private IDaoInstance<Computer> dao;
	private Logger log;

	public ComputerService() {
		this.dao = DaoComputerFactory.getComputerFactory().getDao();
		this.log = LoggerFactory.getLogger(ComputerService.class);
	}

	@Override
	public List<Computer> getAll() {
		return this.dao.getAll();
	}

	/**
	 * @param name
	 * @return
	 */
	@Override
	public List<Computer> searchByName(String name) {
		List<Computer> filteredComputers = this.dao.getAll().stream()
				.filter(computer -> computer.getName().matches(name) || computer.getCompany().getName().matches(name))
				.collect(Collectors.toList());
		return filteredComputers;
	}

	/*
	 * @see com.excilys.cdb.service.IService#getOneById(java.lang.Long)
	 */
	@Override
	public Computer getOneById(Long id) throws BadInputException {
		ServiceValidator.idValidator(id, "Computer");

		return this.dao.getOneById(id);
	}

	/*
	 * @see com.excilys.cdb.service.IService#getOneByName(java.lang.String)
	 */
	@Override
	public Computer getOneByName(String name) throws BadInputException {
		ServiceValidator.nameValidator(name, "Computer");

		return this.dao.getOneByName(name);
	}

	/*
	 * @see com.excilys.cdb.service.IService#create(com.excilys.cdb.model.Entity)
	 */
	@Override
	public boolean create(Computer newEntity) throws BadInputException {
		ServiceValidator.computerValidator(newEntity, "Computer");

		return this.dao.create(newEntity);
	}

	/*
	 * @see
	 * com.excilys.cdb.service.IService#updateById(com.excilys.cdb.model.Entity)
	 */
	@Override
	public boolean updateById(Computer newEntity) throws BadInputException {
		ServiceValidator.computerValidator(newEntity, "Computer");

		return this.dao.updateById(newEntity);
	}

	/*
	 * @see com.excilys.cdb.service.IService#deleteById(java.lang.Long)
	 */
	@Override
	public boolean deleteById(Long id) throws BadInputException {
		ServiceValidator.idValidator(id, "Computer");

		return this.dao.deleteById(id);
	}

	/*
	 * @see com.excilys.cdb.service.IService#deleteByName(java.lang.String)
	 */
	@Override
	public boolean deleteByName(String name) throws BadInputException {
		ServiceValidator.nameValidator(name, "Computer");

		return this.dao.deleteByName(name);
	}

	/*
	 * @see com.excilys.cdb.service.IService#getDao()
	 */
	@Override
	public IDaoInstance<Computer> getDao() {
		return this.dao;
	}

	/*
	 * @see com.excilys.cdb.service.IService#setDao(com.excilys.cdb.persistence.
	 * DaoInstance)
	 */
	@Override
	public void setDao(IDaoInstance<Computer> dao) {
		this.dao = dao;
	}
}
