package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Computer;

public class DaoComputerFactory implements IDaoFactory<Computer> {
	private DaoInstance<Computer> instance;
	
	public DaoInstance<Computer> getInstance() {
		if(instance == null) {
			synchronized(DaoComputerFactory.class) {
				if(instance == null) {
					instance = new DaoComputer();
				}
			}
		}
		return instance;
	}	
}
