package com.excilys.cdb.view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;

import com.excilys.cdb.model.Entity;

public abstract class Page<T extends Entity> {
	protected Logger logger;
	protected Scanner input = new Scanner(System.in);
	protected Data data;
	protected List[] errors;

	public abstract String show();
}
