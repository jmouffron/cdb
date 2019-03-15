package com.excilys.cdb.view;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Entity;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.controller.MenuChoiceEnum;

public class EntityPage extends Page<Entity> {

	public EntityPage(Data<Entity> data) throws ServiceException {
		this.data = data;
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
