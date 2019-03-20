package com.excilys.cdb.service;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Entity;

public class ServiceFactory<T extends Entity> {

	public static final String COMPUTER_SERVICE = "computer";
	public static final String COMPANY_SERVICE = "company";
	
	private ServiceFactory() { }
	
	public static IService getService(String serviceType) throws ServiceException {
		IService service = null;
		switch (serviceType) {
  		case COMPUTER_SERVICE:
  			service = ComputerService.getService();
  			break;
  		case COMPANY_SERVICE:
  			service = CompanyService.getService();
  			break;
  		default:
  			throw new ServiceException("Problems creating the Services!");
		}

		return service;
	}

}
