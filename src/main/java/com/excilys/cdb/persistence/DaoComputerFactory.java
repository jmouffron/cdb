package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Computer;

public class DaoComputerFactory implements IDaoFactory<Computer> {
	private DaoComputer daoComputer;
	
	private DaoComputerFactory() {}
	
	private DaoComputerFactory(DaoComputer dao) {
	  super();
	  this.daoComputer = dao;
	}

	public DaoComputer getDao() {
		return this.daoComputer;
	}
}