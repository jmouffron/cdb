package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Computer;

public class ComputerFactory {
	private static DaoInstance<Computer> instance;

	public static DaoInstance<Computer> getInstance() {
		return createInstance();
	}

	protected static DaoInstance<Computer> createInstance() {
		if (instance == null) {
			synchronized (DaoInstance.class) {
				if (instance == null) {
					instance = new DaoComputer();
				}
			}
		}
		return instance;
	}

}
