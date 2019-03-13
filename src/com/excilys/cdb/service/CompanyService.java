package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exceptions.BadInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Entity;
import com.excilys.cdb.persistence.DaoCompanyFactory;
import com.excilys.cdb.persistence.DaoInstance;

public class CompanyService implements IService<Company> {
	private DaoInstance<Company> dao;

	public CompanyService() {
		this.dao = new DaoCompanyFactory().getDao();
	}

	@Override
	public List<Company> getAll() {
		return dao.getAll();
	}

	@Override
	public Company getOneById(Long id) throws BadInputException {
		if (id < 0) {
			throw new BadInputException();
		}
		return dao.getOneById(id);
	}

	@Override
	public Company getOneByName(String name) throws BadInputException {
		if (name == null || name.equals("")) {
			throw new BadInputException("Bad name inputted!");
		}
		return this.dao.getOneByName(name);
	}

	@Override
	public boolean create(Entity newEntity) throws BadInputException {
		if (newEntity == null || newEntity.getName() == null || newEntity.getName().equals("")) {
			throw new BadInputException("Cannot create entity!");
		}
		return false;
	}

	@Override
	public boolean updateById(Long id, Company newEntity) throws BadInputException {
		if (id < 0) {
			throw new BadInputException();
		}
		return this.dao.updateById(id, newEntity);
	}

	@Override
	public boolean deleteById(Long id) throws BadInputException {
		if (id < 0) {
			throw new BadInputException();
		}
		return false;
	}

	@Override
	public boolean deleteByName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

}
