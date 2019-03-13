package com.excilys.cdb.view;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Computer;

public class EntityDeletePage extends Page<Computer> {

	public EntityDeletePage() throws ServiceException {
		this.logger = LoggerFactory.getLogger(EntityDeletePage.class);
	}

	@Override
	public String show() {

		return null;
	}

}
