package com.excilys.cdb.view;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Computer;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.controller.MenuChoiceEnum;

public class EntityDeletePage extends Page<Computer> {

	public EntityDeletePage() throws ServiceException {
		this.logger = LoggerFactory.getLogger(EntityDeletePage.class);
	}

	@Override
	public MenuChoiceEnum show() {
		System.out.println("==============================");
		System.out.print("Entity ");
		data.show();
		System.out.println("deleted .");
		System.out.println("==============================");
		return MenuChoiceEnum.DEFAULT;
	}

}
