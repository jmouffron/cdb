package com.excilys.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.DaoInstance;

public class ComputerService implements IService<Computer> {
	private DaoInstance<Computer> dao;
	private Logger log;

	public ComputerService() {
		this.dao = new DaoComputerFactory().getDao();
		this.log = LoggerFactory.getLogger(ComputerService.class);
	}

	@Override
	public List<Computer> getAll() {
		return this.dao.getAll();
	}

	@Override
	public Computer getOneById(Long id) throws BadInputException {
		if (id < 0) {
			log.debug("Bad id for fetching Computer!");
			throw new BadInputException("Id chosen inferior to 0!");
		}else if(id > Long.MAX_VALUE) {
			log.debug("Bad id for fetching Computer!");
			throw new BadInputException("Id chosen too big!");
		}
		return this.dao.getOneById(id);
	}

	@Override
	public Computer getOneByName(String name) throws BadInputException {
		if (name == null || name.equals("")) {
			log.debug("Bad name for fetching Computer!");
			throw new BadInputException("Bad name inputted!");
		}
		return this.dao.getOneByName(name);
	}

	@Override
	public boolean create(Computer newEntity) throws BadInputException {
		if(newEntity == null) {
			log.debug("Null NewEntity for update of Computer");
			throw new BadInputException();
		}else if (newEntity.getId() < 0) {
			log.debug("Bad Id for update of Computer");
			throw new BadInputException();
		}else if(newEntity.getName() == null || newEntity.getName().equals("")) {
			log.debug("Null Name on NewEntity for update of Computer");
			throw new BadInputException();
		}else if(newEntity.getId() < 0) {
			log.debug("Bad Id on NewEntity for update of Computer");
			throw new BadInputException();
		}else if(newEntity.getIntroduced().after(newEntity.getDiscontinued())) {
			log.debug("Discontinued Date on NewEntity before Introduced Date for update of Computer");
			throw new BadInputException();
		}
		
		return this.dao.create(newEntity);
	}

	@Override
	public boolean updateById(Long id, Computer newEntity) throws BadInputException {
		if (id < 0) {
			log.debug("Bad Id for update of Computer");
			throw new BadInputException();
		}else if(newEntity == null) {
			log.debug("Null NewEntity for update of Computer");
			throw new BadInputException();
		}else if(newEntity.getName() == null || newEntity.getName().equals("")) {
			log.debug("Null Name on NewEntity for update of Computer");
			throw new BadInputException();
		}else if(newEntity.getId() < 0) {
			log.debug("Bad Id on NewEntity for update of Computer");
			throw new BadInputException();
		}else if(newEntity.getIntroduced().after(newEntity.getDiscontinued())) {
			log.debug("Discontinued Date on NewEntity before Introduced Date for update of Computer");
			throw new BadInputException();
		}
		
		return this.dao.updateById(id, newEntity);
	}

	@Override
	public boolean deleteById(Long id) throws BadInputException {
		if (id < 0) {
			log.debug("Bad Id for deleting of Computer");
			throw new BadInputException();
		}else if(id > Long.MAX_VALUE) {
			log.debug("Bad Input of id for deleting of Computer");
			throw new BadInputException("Id too big inputted!");
		}
		
		return this.dao.deleteById(id);
	}

	@Override
	public boolean deleteByName(String name) throws BadInputException {
		if (name == null || name.equals("")) {
			log.debug("Bad Name for deleting of Computer");
			throw new BadInputException();
		}
		return this.dao.deleteByName(name);
	}

	@Override
	public DaoInstance<Computer> getDao() {
		return getDao();
	}
}
