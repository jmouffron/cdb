package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Entity;

public class EntityListPage extends Page<Entity> {

	public EntityListPage(Data<Entity> data) throws ServiceException {
		this.data = data;
		this.logger = LoggerFactory.getLogger(EntityListPage.class);

	}

	@Override
	public String show() {
		System.out.println("==============================");
		System.out.println(" |          Liste           | ");
		System.out.println("==============================");
		data.list();
		System.out.println("==============================");
		return "default";
	}

}
