package com.excilys.cdb.view;

import java.util.Scanner;

import org.slf4j.Logger;

import com.excilys.cdb.model.Entity;
import com.excilys.cdb.service.IService;

public abstract class Page<T extends Entity> {
	protected Logger logger;
	protected Scanner input;
	protected IService<T> service;
	public abstract String show();
}
