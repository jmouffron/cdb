package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceFactory;

public class ComputerAddPage extends Page<Computer> {
	
	public ComputerAddPage() throws ServiceException{
		this.service = ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
		this.logger = LoggerFactory.getLogger(ComputerAddPage.class);
	}
	
	@Override
	public String show() {
	
		return null;
	}

}
