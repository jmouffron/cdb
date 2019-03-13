package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Entity;

public class EntityAddPage extends Page<Computer> {

	public EntityAddPage(Data<Entity> data) throws ServiceException {
		this.data = data;
		this.logger = LoggerFactory.getLogger(EntityAddPage.class);
	}

	@Override
	public String show() {
		System.out.println("==============================");
		System.out.print("Entity ");
		data.show();
		System.out.println("created .");
		System.out.println("==============================");
		return "default";
	}

}
