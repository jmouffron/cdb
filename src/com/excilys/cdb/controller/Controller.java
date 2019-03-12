package com.excilys.cdb.controller;

import com.excilys.cdb.exceptions.PageException;
import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Entity;
import com.excilys.cdb.service.ServiceFactory;
import com.excilys.cdb.view.CompanyListPage;
import com.excilys.cdb.view.ComputerAddPage;
import com.excilys.cdb.view.ComputerDeletePage;
import com.excilys.cdb.view.ComputerDetailsPage;
import com.excilys.cdb.view.ComputerListPage;
import com.excilys.cdb.view.ComputerUpdatePage;
import com.excilys.cdb.view.MenuPage;
import com.excilys.cdb.view.Page;

public class Controller<T extends Entity> {
	private Page page;
	private String request;
	
	private Controller(Page page) {
		this.page = page;
	}
	
	private void setPage(Page page) {
		this.page = page;
	}
	
	private void observe() throws PageException, ServiceException {
		while(this.request != "7") {
			this.request = this.page.show();
			
			switch(this.request) {
				case "1":
					this.setPage(new CompanyListPage());	
					break;
				case "2":
					this.setPage(new ComputerListPage());	
					break;
				case "3":
					this.setPage(new ComputerDetailsPage());	
					break;
				case "4":
					this.setPage(new ComputerAddPage());	
					break;
				case "5":
					this.setPage(new ComputerUpdatePage());	
					break;
				case "6":
					this.setPage(new ComputerDeletePage());	
					break;
				case "7":
					break;
				default:
					this.setPage(new MenuPage());
			}
					
		}
	}
	
	public static void main(String[] args) {
		
		Controller<Entity> controller = new Controller<>(new MenuPage());
		
		try {
			controller.observe();
		} catch (PageException e) {
			controller.setPage(new MenuPage());
			e.printStackTrace();
		} catch (ServiceException e) {
			controller.setPage(new MenuPage());
			e.printStackTrace();
		}
		
	}


}
