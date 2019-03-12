package com.excilys.cdb.view;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Company;

import com.excilys.cdb.service.ServiceFactory;

public class CompanyListPage extends Page<Company> {
	
	public CompanyListPage() throws ServiceException{
		this.service = ServiceFactory.getService(ServiceFactory.COMPANY_SERVICE);
		this.logger = LoggerFactory.getLogger(CompanyListPage.class);
	}
	
	@Override
	public String show() {
		System.out.println("==============================");
		System.out.println(" | Liste des manufactureurs |");
		System.out.println("==============================");
		List<Company> companyList = this.service.getAll();
		companyList.forEach(System.out::println);
		System.out.println("==============================");
		return "default";
	}

}
