package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Computer;

public class DaoComputerFactory implements IDaoFactory<Computer> {
	private volatile DaoInstance<Computer> instance = null;

	@Override
	public DaoInstance<Computer> getDao() {
		if (instance == null) {
			synchronized (DaoComputerFactory.class) {
				if (instance == null) {
					instance = new DaoComputer();
				}
			}
		}
		return instance;
	}
}