package com.excilys.cdb.view;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceFactory;

public class ComputerListPage extends Page<Computer>{

	public ComputerListPage() throws ServiceException {
		this.service = ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
		this.logger = LoggerFactory.getLogger(CompanyListPage.class);
	}

	@Override
	public String show() {
		System.out.println("==============================");
		System.out.println(" | Liste des ordinateurs |");
		System.out.println("==============================");
		List<Computer> companyList = this.service.getAll();
		companyList.forEach(System.out::println);
		System.out.println("==============================");
		return "default";
	}


}
