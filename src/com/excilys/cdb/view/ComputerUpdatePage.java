package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceFactory;

public class ComputerUpdatePage extends Page<Computer> {
	
	public ComputerUpdatePage() throws ServiceException {
		this.service = ServiceFactory.getService(ServiceFactory.COMPANY_SERVICE);
		this.logger = LoggerFactory.getLogger(ComputerUpdatePage.class);
	}
	
	@Override
	public String show() {
	
		return null;
	}

}
