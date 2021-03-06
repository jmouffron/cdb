package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

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
