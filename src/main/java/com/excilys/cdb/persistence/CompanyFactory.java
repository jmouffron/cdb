package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Company;

public class CompanyFactory {
	private static DaoInstance<Company> instance;

	public static DaoInstance<Company> getInstance() {
		return createInstance();
	}

	protected static DaoInstance<Company> createInstance() {
		if (instance == null) {
			synchronized (DaoInstance.class) {
				if (instance == null) {
					instance = new DaoCompany();
				}
			}
		}
		return instance;
	}

}
