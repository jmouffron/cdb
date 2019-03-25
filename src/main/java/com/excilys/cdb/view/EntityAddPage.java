package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.MenuChoiceEnum;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Entity;

public class EntityAddPage extends Page<Computer> {

	public EntityAddPage(Data<Entity> payload) {
		this.data = payload;
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
