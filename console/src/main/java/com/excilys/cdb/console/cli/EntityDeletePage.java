package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.exception.ServiceException;

public class EntityDeletePage extends Page {

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
