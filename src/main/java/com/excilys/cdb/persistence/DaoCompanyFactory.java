package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Company;

public class DaoCompanyFactory implements IDaoFactory<Company> {
	private volatile DaoCompany instance = null;

	@Override
	public DaoInstance<Company> getDao() {
		if (instance == null) {
			synchronized (DaoCompanyFactory.class) {
				if (instance == null) {
					instance = new DaoCompany();
				}
			}
		}
		return instance;
	}
}