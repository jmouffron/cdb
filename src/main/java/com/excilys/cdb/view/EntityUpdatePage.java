package com.excilys.cdb.view;

import com.excilys.cdb.controller.MenuChoiceEnum;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Computer;
import org.slf4j.LoggerFactory;

public class EntityUpdatePage extends Page<Computer> {

	public EntityUpdatePage() throws ServiceException {
		this.logger = LoggerFactory.getLogger(EntityUpdatePage.class);
	}

	@Override
	public MenuChoiceEnum show() {
		System.out.println("==============================");
		System.out.print("Entity ");
		data.show();
		System.out.println("updated .");
		System.out.println("==============================");
		return MenuChoiceEnum.DEFAULT;
	}

}
