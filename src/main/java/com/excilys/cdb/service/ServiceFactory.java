package com.excilys.cdb.service;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Entity;

public class ServiceFactory<T extends Entity> {

	public static final String COMPUTER_SERVICE = "computer";
	public static final String COMPANY_SERVICE = "company";
	
	private ServiceFactory() { }
	
	public static IService<?> getService(String serviceType) throws ServiceException {
		IService<?> service;
		switch (serviceType) {
		case COMPUTER_SERVICE:
			service = new ComputerService();
			break;
		case COMPANY_SERVICE:
			service = new CompanyService();
			break;
		default:
			throw new ServiceException("Problems creating the Services!");
		}

		return service;
	}

}
