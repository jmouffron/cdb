package com.excilys.cdb.cli;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.MenuChoiceEnum;

public class EntityAddPage extends Page{

	public EntityAddPage(Data payload) {
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
