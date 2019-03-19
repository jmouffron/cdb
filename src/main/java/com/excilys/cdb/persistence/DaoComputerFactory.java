package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Computer;

public class DaoComputerFactory implements IDaoFactory<Computer> {
	private static volatile IDaoFactory<Computer> instance;
	private static IDaoInstance<Computer> daoComputer;
	
	public static IDaoFactory<Computer> getComputerFactory() {
	  if (instance == null) {
      synchronized (DaoComputerFactory.class) {
        if (instance == null) {
          instance = new DaoComputerFactory();
        }
      }
    }
    return instance;
	}
	
	@Override
	public IDaoInstance<Computer> getDao() {
		if (daoComputer == null) {
			synchronized (DaoComputerFactory.class) {
				if (daoComputer == null) {
					daoComputer = DaoComputer.getDao();
				}
			}
		}
		return daoComputer;
	}
}