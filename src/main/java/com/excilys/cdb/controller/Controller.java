package com.excilys.cdb.controller;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.ServiceException;
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

/**
 * A Front Controller class to handle user interaction and view data injection.
 * 
 * @author excilys
 * @param <T> Entity
 */
public class Controller<T extends Entity> {
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
  private Page<T> page;
	private MenuChoiceEnum request;
	private IService<T> service;
	private static volatile Controller<Entity> instance;

	private Controller(Page<T> page) {
		this.page = page;
	}

	public static Controller<Entity> getController() {
		if (instance == null) {
			synchronized (Controller.class) {
				if (instance == null) {
					instance = new Controller<>(new MenuPage());
				}
			}
		}
		return instance;
	}

	/**
	 * Starts the process of listening on user input and launches the application
	 * loop
	 */
	public void start() {
		boolean looper = true;

		while (looper) {
      this.request = this.page.show();

			switch (this.request) {
			case FETCH_ALL_COMPANY:
				listAllEntity(ServiceFactory.COMPANY_SERVICE);
				break;
			case FETCH_ALL_COMPUTER:
				listAllEntity(ServiceFactory.COMPUTER_SERVICE);
				break;
			case FETCH_COMPUTER:
				showComputer();
				break;
			case CREATE_COMPUTER:
				createComputer();
				break;
			case UPDATE_COMPUTER:
				updateComputer();
				break;
			case DELETE_COMPUTER:
				deleteComputer();
				break;
			case EXIT_PROGRAM:
				looper = false;
				break;
			case DEFAULT:
			default:
				setPage(new MenuPage());
			}
		}
	}

	/**
	 * Returns the total number of pages for a view
	 * 
	 * @param totalSize
	 * @return
	 */
	private long getTotalPages(long totalSize) {
		long parts = 0, partsSize = totalSize;
		while (partsSize > 0) {
			partsSize /= 2;
			parts++;
		}
		return parts;
	}

	/**
	 * Returns the number of page to be displayed by page for a view
	 * 
	 * @param totalSize
	 * @param totalPages
	 * @return
	 */
	private long getDataPerPage(long totalSize, long totalPages) {
		return totalSize / totalPages;
	}

	/**
	 * List all instances of an entity in the database
	 * 
	 * @param serviceType
	 * @throws ServiceException
	 */
	private void listAllEntity(String serviceType) {
		try {
      this.setService(serviceType);
    } catch (ServiceException e1) {
      logger.error("Bad service instantiation in controller");
    }

		List<Entity> entities = this.getAllEntities();
		Data<Entity> payload = new Data<Entity>(entities);

		long totalDataSize = entities.size();

		long totalPages = getTotalPages(totalDataSize);
		long dataPerPage = getDataPerPage(totalDataSize, totalPages);

		try {
			this.setPage(new EntityListPage(payload, totalPages, dataPerPage));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the details of a computer
	 * 
	 * @throws ServiceException
	 */
	private void showComputer() {
		long id = 0L;

		try {
      this.setService(ServiceFactory.COMPUTER_SERVICE);
    } catch (ServiceException e1) {
      logger.error("Bad service instantiation in controller");
    }

		id = ControllerUtils.getLongInput("Choisissez un id d'entité supérieur à 0:",
				ControllerUtils.isStrictlyPositive);

		Entity entity = this.getEntityById(id);
		Data<Entity> payload = new Data<Entity>(entity);

		try {
			this.setPage(new EntityPage(payload));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a computer to the database
	 * 
	 * @throws ServiceException
	 */
	private void createComputer() {

		try {
      this.setService(ServiceFactory.COMPUTER_SERVICE);
    } catch (ServiceException e1) {
      logger.error("Bad service instantiation in controller");
    }
		String name;
		Timestamp introduced, discontinued;
		Long companyId;

		name = ControllerUtils.getStringInput("Veuillez entrer un nom pour l'ordinateur:",
				ControllerUtils.isNotNullString);
		companyId = ControllerUtils.getLongInput("Veuillez entrer l'id de la companie ayant crée l'ordinateur:",
				ControllerUtils.isStrictlyPositive);

		do {
			introduced = ControllerUtils
					.getTimestampInput("Veuillez entrer la date de mise en fonction de l'ordinateur");
			discontinued = ControllerUtils
					.getTimestampInput("Veuillez entrer la date de fin de fonction de l'ordinateur");
		} while (introduced.after(discontinued));

		Company company = (Company) getEntityById(companyId);
		Computer newEntity = new Computer.ComputerBuilder().setName(name).setIntroduced(introduced)
				.setDiscontinued(discontinued).setCompany(company).build();

		Data<Entity> payload = new Data<Entity>(newEntity);
		this.addEntity(newEntity);

		this.setPage(new EntityAddPage(payload));

	}

	/**
	 * Updates a Computer in the database
	 * 
	 * @throws ServiceException
	 */
	private void updateComputer() {
		try {
		  this.setService(ServiceFactory.COMPUTER_SERVICE);
			this.setPage(new EntityUpdatePage());
		} catch (ServiceException e) {
		  logger.error("Bad service instantiation in controller");
		}
	}

	/**
	 * Deletes a Computer from the database
	 * 
	 * @throws ServiceException
	 */
	private void deleteComputer() {
		try {
      this.setService(ServiceFactory.COMPUTER_SERVICE);
    } catch (ServiceException e1) {
      logger.error("Bad service instantiation in controller");
    }

		try {
      this.setPage(new EntityDeletePage());
    } catch (ServiceException e) {
      logger.error("Bad Page instantiation in controller");
    }
	}

	/*
	 * Service Method calls
	 */

	/**
	 * Fetches all instance of a certain type of entity from the database
	 * 
	 * @return List<Entity>
	 */
	private List<Entity> getAllEntities() {
		return (List<Entity>) this.service.getAll().get();
	}

	/**
	 * Fetches an Entity based on a given Id.
	 * 
	 * @param id
	 * @return An entity mapped from the database
	 */
	private Entity getEntityById(Long id) {
		try {
			return this.service.getOneById(id).get();
		} catch (BadInputException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Fetches an Entity based on a given Id.
	 * 
	 * @param newEntity
	 * @return true if the call changed the database, else false
	 */
	private boolean addEntity(Entity newEntity) {
		try {
			return this.service.create((T) newEntity);
		} catch (BadInputException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Sets the page to be shown by the controller
	 * 
	 * @param page
	 */
	private void setPage(Page page) {
		this.page = page;
	}

	/**
	 * Returns the service to act on the database
	 * 
	 * @return service of the controller
	 */
	public IService<T> getService() {
		return this.service;
	}

	/**
	 * Sets the service to be used by the controller
	 * 
	 * @param serviceType
	 * @throws ServiceException 
	 */
	private void setService(String serviceType) throws ServiceException {
		switch(serviceType) {
		  case ServiceFactory.COMPUTER_SERVICE:
		    this.service = (IService<T>) ServiceFactory.getComputerService();
		    break;
		  case ServiceFactory.COMPANY_SERVICE:
        this.service = (IService<T>) ServiceFactory.getCompanyService();
        break;
      default:
        logger.error("Wrong type of services asked in controller.");
        throw new ServiceException("Wrong type of services asked in controller.");
		}
	}
}
