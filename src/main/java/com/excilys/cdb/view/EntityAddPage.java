package com.excilys.cdb.view;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Entity;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.MenuChoiceEnum;

public class EntityAddPage extends Page<Computer> {

	public EntityAddPage(Data<Entity> data) throws ServiceException {
		this.data = data;
		this.logger = LoggerFactory.getLogger(EntityAddPage.class);
	}

	@Override
	public MenuChoiceEnum show() {
		System.out.println("==============================");
		System.out.print("Entity ");
		data.show();
		System.out.println("created .");
		System.out.println("==============================");
		return MenuChoiceEnum.DEFAULT;
	}

}
