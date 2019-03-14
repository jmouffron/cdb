package com.excilys.cdb.view;

import com.excilys.cdb.model.Entity;
import org.slf4j.Logger;
import com.excilys.cdb.controller.MenuChoiceEnum;

public abstract class Page<T extends Entity> {
	protected Logger logger;
	protected Data<Entity> data;

	public abstract MenuChoiceEnum show();
}
