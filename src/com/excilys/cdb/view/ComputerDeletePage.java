package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceFactory;

public class ComputerDeletePage extends Page<Computer> {
	
	public ComputerDeletePage() throws ServiceException {
		this.service = ServiceFactory.getService(ServiceFactory.COMPANY_SERVICE);
		this.logger = LoggerFactory.getLogger(ComputerDeletePage.class);
	}
	
	@Override
	public String show() {
	
		return null;
	}

}
