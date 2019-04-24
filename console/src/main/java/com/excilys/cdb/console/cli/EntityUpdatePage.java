package com.excilys.cdb.console.cli;

import com.excilys.cdb.binding.exception.ServiceException;
import org.slf4j.LoggerFactory;

public class EntityUpdatePage extends Page {

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
