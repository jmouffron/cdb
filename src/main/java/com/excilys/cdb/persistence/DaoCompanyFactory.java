package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Company;

public class DaoCompanyFactory implements IDaoFactory<Company> {
  private static volatile IDaoFactory<Company> instance;
  private static IDaoInstance<Company> daoCompany;
  
  private DaoCompanyFactory() {  }
  
  public static IDaoFactory<Company> getCompanyFactory() {
    if (instance == null) {
      synchronized (DaoCompanyFactory.class) {
        if (instance == null) {
          instance = new DaoCompanyFactory();
        }
      }
    }
    return instance;
  }
  
	@Override
	public IDaoInstance<Company> getDao() {
		if (daoCompany == null) {
			synchronized (DaoCompanyFactory.class) {
				if (daoCompany == null) {
				  daoCompany = DaoCompany.getDao();
				}
			}
		}
		return daoCompany;
	}
}