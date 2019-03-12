package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exceptions.BadInputException;
import com.excilys.cdb.exceptions.CreationException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.DaoInstance;

public class ComputerService implements IService<Computer> {
	private DaoInstance<Computer> dao;
	
	public ComputerService(){
		this.dao = new DaoComputerFactory().getDao();
	}

	@Override
	public List<Computer> getAll() {
		return this.dao.getAll();
	}

	@Override
	public Computer getOneById(Long id) throws BadInputException {
		if(id<0) {
			throw new BadInputException();
		}
		return this.dao.getOneById(id);
	}

	@Override
	public boolean create(Computer newEntity) throws BadInputException {
		if(newEntity == null || newEntity.getName() == null || newEntity.getName().equals("")) {
			throw new BadInputException();
		}
		return this.dao.create(newEntity);
	}

	@Override
	public boolean updateById(Long id, Computer newEntity) throws BadInputException {
		if(id<0) {
			throw new BadInputException();
		}
		return this.dao.updateById(id, newEntity);
	}

	@Override
	public boolean deleteById(Long id) throws BadInputException {
		if(id<0) {
			throw new BadInputException();
		}
		return this.dao.deleteById(id);
	}

	@Override
	public boolean deleteByName(String name) throws BadInputException {
		if(name == null || name.equals("")) {
			throw new BadInputException();
		}
		return this.dao.deleteByName(name);
	}
}
