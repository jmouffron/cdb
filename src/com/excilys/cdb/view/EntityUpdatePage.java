package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;

public class EntityUpdatePage extends Page<Computer> {

	public EntityUpdatePage() throws ServiceException {
		this.logger = LoggerFactory.getLogger(EntityUpdatePage.class);
	}

	@Override
	public String show() {

		return null;
	}

}
