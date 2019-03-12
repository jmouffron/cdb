package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Company;

public class DaoCompanyFactory implements IDaoFactory<Company> {
	private DaoInstance<Company> instance;
	
	public DaoInstance<Company> getInstance() {
		if(instance == null) {
			synchronized(DaoComputerFactory.class) {
				if(instance == null) {
					instance = new DaoCompany();
				}
			}
		}
		return instance;
	}	
}