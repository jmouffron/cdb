package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

public class EntityAddPage extends Page{

	public EntityAddPage(Data payload) {
		this.data = payload;
		this.logger = LoggerFactory.getLogger(EntityAddPage.class);
	}

  @Override
	public MenuChoiceEnum show() {
		logger.info(DELIMITER);
		logger.info("Entity ");
		data.show();
		logger.info("created .");
		logger.info(DELIMITER);
		return MenuChoiceEnum.DEFAULT;
	}

}
