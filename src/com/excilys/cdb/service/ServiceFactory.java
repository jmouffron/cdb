package com.excilys.cdb.service;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Entity;

public class ServiceFactory<T> {
	
	public static final String COMPUTER_SERVICE = "computer";
	public static final String COMPANY_SERVICE = "company";
	
	public static <T> IService<T> getService(String serviceType) throws ServiceException {
		IService<T> service;
		switch(serviceType) {
			case COMPUTER_SERVICE:
				service =  (IService<T>) new ComputerService();
				break;
			case COMPANY_SERVICE:
				service = (IService<T>) new CompanyService();
				break;
			default:
				throw new ServiceException("Problems creating the Services!");
		}
		
		return service;
	}

}
