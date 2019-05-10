package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

public class EntityPage extends Page {
	public EntityPage(Data payload) {
		this.data = payload;
		this.logger = LoggerFactory.getLogger(EntityPage.class);
	}

	@Override
	public MenuChoiceEnum show() {
		logger.info(DELIMITER);
		logger.info(" |          Entity           | ");
		logger.info(DELIMITER);
		data.show();
		logger.info(DELIMITER);
		return MenuChoiceEnum.DEFAULT;
	}
}
