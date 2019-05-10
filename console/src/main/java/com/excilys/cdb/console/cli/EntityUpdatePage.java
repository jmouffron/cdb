package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

public class EntityUpdatePage extends Page {
	public EntityUpdatePage(Data data) {
		this.logger = LoggerFactory.getLogger(EntityUpdatePage.class);
		this.data = data;
	}

	@Override
	public MenuChoiceEnum show() {
		logger.info(DELIMITER);
		logger.info("Entity ");
		data.show();
		logger.info("updated .");
		logger.info(DELIMITER);
		return MenuChoiceEnum.DEFAULT;
	}

}
