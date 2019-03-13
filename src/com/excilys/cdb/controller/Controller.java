package com.excilys.cdb.controller;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.excilys.cdb.exceptions.BadInputException;
import com.excilys.cdb.exceptions.PageException;
import com.excilys.cdb.exceptions.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Entity;
import com.excilys.cdb.service.IService;
import com.excilys.cdb.service.ServiceFactory;
import com.excilys.cdb.view.Data;
import com.excilys.cdb.view.EntityAddPage;
import com.excilys.cdb.view.EntityDeletePage;
import com.excilys.cdb.view.EntityListPage;
import com.excilys.cdb.view.EntityPage;
import com.excilys.cdb.view.EntityUpdatePage;
import com.excilys.cdb.view.MenuPage;
import com.excilys.cdb.view.Page;

public class Controller<T extends Entity> {
	private Page page;
	private String request;
	private Scanner input;
	private IService<T> service;
	private volatile static Controller<Entity> instance;

	private Controller(Page page) {
		setPage(page);
	}

	static Controller<Entity> getController() {
		if (instance == null) {
			synchronized (Controller.class) {
				if (instance == null) {
					instance = new Controller<>(new MenuPage());
				}
			}
		}
		return instance;
	}

	private void setInput() {
		this.input = new Scanner(System.in);
	}

	public static void main(String[] args) {

		Controller<Entity> controller = Controller.getController();

		try {
			controller.observe();
		} catch (PageException | ServiceException e) {
			controller.setPage(new MenuPage());
			e.printStackTrace();
		}

	}

	private void observe() throws PageException, ServiceException {
		while (this.request != "7") {
			this.request = this.page.show();

			switch (this.request) {
			case "1":
				listAllEntity(ServiceFactory.COMPANY_SERVICE);
				break;
			case "2":
				listAllEntity(ServiceFactory.COMPUTER_SERVICE);
				break;
			case "3":
				showEntity(ServiceFactory.COMPUTER_SERVICE);
				break;
			case "4":
				createEntity();
				break;
			case "5":
				updateComputer();
				break;
			case "6":
				deleteComputer();
				break;
			case "7":
				break;
			default:
				setPage(new MenuPage());
			}

		}
	}

	private void listAllEntity(String serviceType) throws ServiceException {
		this.setService(serviceType);
		List<Entity> entities = getAllEntities();
		Data<Entity> payload = new Data<>(entities);
		this.setPage(new EntityListPage(payload));
	}

	private void showEntity(String serviceType) throws ServiceException {
		long value = 0L;
		boolean error;
		this.setService(serviceType);

		do {
			setInput();
			error = false;
			System.out.println("Choisissez un id d'entité supérieur à 0:");
			try {
				value = this.input.nextLong();
			} catch (InputMismatchException e) {
				error = true;
			}
		} while (error || value < 0);

		Entity entity = getEntityById(value);
		Data<Entity> payload = new Data<>(entity);
		this.setPage(new EntityPage(payload));
	}

	private void createEntity() throws ServiceException {
		boolean error;
		this.setService(ServiceFactory.COMPUTER_SERVICE);
		String name, companyName;
		Timestamp introduced, discontinued;
		Long companyId;
		name = ControllerUtils.getStringInput("Veuillez entrer un nom pour l'ordinateur:", value -> {
			return (value != null && value.equals("") && value.length() > 2);
		});
		companyId = ControllerUtils.getLongInput("Veuillez entrer l'id de la companie ayant crée l'ordinateur:",
				value -> value > 0);
		companyName = ControllerUtils.getStringInput("Veuillez entrer le nom de la companie ayant crée l'ordinateur:",
				value -> value != null && value.equals("") && value.length() > 2);
		do {
			introduced = ControllerUtils
					.getTimestampInput("Veuillez entrer la date de mise en fonction de l'ordinateur");
			discontinued = ControllerUtils
					.getTimestampInput("Veuillez entrer la date de fin de fonction de l'ordinateur");
		} while (introduced.after(discontinued));

		Company company = (Company) getEntityById(companyId);
		Entity newEntity = new Computer.ComputerBuilder().setName(name).setIntroduced(introduced)
				.setDiscontinued(discontinued).setCompany(new Company(companyId, companyName)).build();

		Data<Entity> payload = new Data<>(newEntity);
		addEntity(newEntity);
		this.setPage(new EntityAddPage(payload));
	}

	private void updateComputer() throws ServiceException {
		this.setService(ServiceFactory.COMPUTER_SERVICE);

		this.setPage(new EntityUpdatePage());
	}

	private void deleteComputer() throws ServiceException {
		this.setService(ServiceFactory.COMPUTER_SERVICE);

		this.setPage(new EntityDeletePage());
	}
	/*
	 * Service Method calls
	 */

	/**
	 * @return List<Entity>
	 */
	private List<Entity> getAllEntities() {
		// TODO Auto-generated method stub
		return (List<Entity>) this.service.getAll();
	}

	private Entity getEntityById(Long id) {
		try {
			return this.service.getOneById(id);
		} catch (BadInputException e) {
			e.printStackTrace();
		}
		return null;
	}

	private T getEntityByName(String name) {
		try {
			return this.service.getOneByName(name);
		} catch (BadInputException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean addEntity(Entity newEntity) {
		try {
			return this.service.create(newEntity);
		} catch (BadInputException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void setPage(Page page) {
		this.page = page;
	}

	private void setService(String serviceType) {
		try {
			this.service = (IService<T>) ServiceFactory.getService(serviceType);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
