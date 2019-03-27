package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Company;

public class DaoCompanyFactory implements IDaoFactory<Company> {
  private DaoCompany daoCompany;
  
  private DaoCompanyFactory() {}
  
  private DaoCompanyFactory(DaoCompany dao) {
    super();
    this.daoCompany = dao;
  }

  public DaoCompany getDao() {
    return this.daoCompany;
  }
}