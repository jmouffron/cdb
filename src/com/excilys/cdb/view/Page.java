package com.excilys.cdb.view;

import java.util.Scanner;

import org.slf4j.Logger;

public abstract class Page {
	protected Logger logger;
	protected Scanner input;
	public abstract String show();
}
