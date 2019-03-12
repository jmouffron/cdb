package com.excilys.cdb.persistence;

public class ComputerFactory {
	private static DaoInstance instance;
	
	public static DaoInstance getInstance() {
		return createInstance();
	}

	protected static DaoInstance createInstance() {
		if(instance == null) {
			synchronized(DaoInstance.class) {
				if(instance == null) {
					instance = new DaoComputer();
				}
			}
		}
		return instance;
	}
	
}
