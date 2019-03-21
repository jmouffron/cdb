package com.excilys.cdb.service;

import com.excilys.cdb.model.Entity;

public class ServiceFactory<T extends Entity> {

	public static final String COMPUTER_SERVICE = "computer";
	public static final String COMPANY_SERVICE = "company";
	
	private ServiceFactory() { }
	
  public static ComputerService getComputerService(){
		return ComputerService.getService();
	}
  
  public static CompanyService getCompanyService(){
    return CompanyService.getService();
  }
}
