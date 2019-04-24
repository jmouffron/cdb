package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.exception.ServiceException;

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
