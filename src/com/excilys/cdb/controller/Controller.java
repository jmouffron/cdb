package com.excilys.cdb.controller;

import com.excilys.cdb.view.MenuPage;
import com.excilys.cdb.view.Page;

public class Controller {
	private Page page;
	private String request;
	
	private Controller(Page page) {
		this.page = page;
		this.request = this.page.show();
	}
	public static void main(String[] args) {
		
		Controller controller = new Controller(new MenuPage());
		
	}
}
