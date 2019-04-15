package com.excilys.cdb.cli;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.MenuChoiceEnum;
import com.excilys.cdb.exception.ServiceException;

public class EntityPage extends Page {

	public EntityPage(Data payload) throws ServiceException {
		this.data = payload;
		this.logger = LoggerFactory.getLogger(EntityPage.class);
	}

	@Override
	public MenuChoiceEnum show() {
		System.out.println("==============================");
		System.out.println(" |          Entity           | ");
		System.out.println("==============================");
		data.show();
		System.out.println("==============================");
		return MenuChoiceEnum.DEFAULT;
	}
}
