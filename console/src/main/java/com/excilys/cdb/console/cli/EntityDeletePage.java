package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

public class EntityDeletePage extends Page {

	public EntityDeletePage(Data data) {
		this.logger = LoggerFactory.getLogger(EntityDeletePage.class);
		this.data = data;
	}

	@Override
	public MenuChoiceEnum show() {
		logger.info("DELIMITER");
		logger.info("Entity ");
		data.showId();
		logger.info("deleted .");
		logger.info("DELIMITER");
		return MenuChoiceEnum.DEFAULT;
	}

}
