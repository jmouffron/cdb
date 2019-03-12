package com.excilys.cdb.view;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.BadInputException;
import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceFactory;

public class ComputerDetailsPage extends Page<Computer> {

	public ComputerDetailsPage() throws ServiceException {
		this.service = ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
		this.logger = LoggerFactory.getLogger(ComputerDetailsPage.class);
	}
	@Override
	public String show() {
		String value = "";
		System.out.println("==============================");
		System.out.println(" |  Affichage d'ordinateur  |");
		System.out.println("==============================");
		do{
			System.out.println("Choissez un id d'ordinateur supérieur à 0, ou tapez exit:");
			value = this.input.next();
		}while(Long.parseLong(value)<0);
		try {
			Computer companyList = this.service.getOneById(Long.parseLong(value));
		} catch (NumberFormatException | BadInputException e) {
			e.printStackTrace();
		}
		System.out.println("==============================");
		return "default";
	}


}
