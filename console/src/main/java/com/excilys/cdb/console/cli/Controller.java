package com.excilys.cdb.console.cli;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.persistence.mapper.ComputerMapper;

/**
 * A Front Controller class to handle user interaction and view data injection.
 * 
 * @author excilys
 * @param <T> Entity
 */
@Component
public class Controller {
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	private Page page;
	private MenuChoiceEnum request;
	
	private ComputerService computerService;
	private CompanyService companyService;
	private ComputerMapper pcMapper;

	private Controller(ComputerService pcService, CompanyService corpService, ComputerMapper pcMapper) {
		this.page = new MenuPage();
		this.computerService = pcService;
		this.companyService = corpService;
		this.pcMapper = pcMapper;
	}

	/**
	 * Starts the process of listening on user input and launches the application
	 * loop
	 * @throws DaoException 
	 * 
	 * @throws ServiceException
	 */
	public void start() throws DaoException {
		boolean looper = true;

		while (looper) {
			this.request = this.page.show();

			switch (this.request) {
				case FETCH_ALL_COMPANY:
					listAllEntity(CompanyService.class);
					break;
				case FETCH_ALL_COMPUTER:
					listAllEntity(ComputerService.class);
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
				case DELETE_COMPANY:
					deleteCompany();
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
	 * @throws DaoException 
	 * @throws ServiceException
	 */
	private void listAllEntity(Class clazz) throws DaoException {

		List entities = null;
		try {
			entities = this.getAllEntities(clazz);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
		}
		Data payload = new Data(entities);

		long totalDataSize = entities.size();

		long totalPages = getTotalPages(totalDataSize);
		long dataPerPage = getDataPerPage(totalDataSize, totalPages);

		try {
			this.setPage(new EntityListPage(payload, totalPages, dataPerPage));
		} catch (ServiceException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Displays the details of a computer
	 * 
	 * @throws ServiceException
	 */
	private void showComputer() {
		long id = 0L;
		id = ControllerUtils.getLongInput("Choisissez un id d'entité supérieur à 0:",
				ControllerUtils.isStrictlyPositive);

		ComputerDTO entity = null;
		try {
			entity = this.getEntityById(id);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
		}
		Data payload = new Data(entity);

		try {
			this.setPage(new EntityPage(payload));
		} catch (ServiceException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Adds a computer to the database
	 * 
	 * @throws ServiceException
	 */
	private void createComputer() {

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

		CompanyDTO company = null;
		company = getCompanyById(companyId);

		ComputerDTO newEntity = new ComputerDTO.ComputerDTOBuilder().setName(name).setIntroduced(introduced.toString())
				.setDiscontinued(discontinued.toString()).setCompanyId(company.getId())
				.setCompanyName(company.getName()).build();

		Data payload = new Data(newEntity);
		this.addEntity(newEntity);

		this.setPage(new EntityAddPage(payload));

	}

	private CompanyDTO getCompanyById(Long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Updates a Computer in the database
	 * 
	 * @throws ServiceException
	 */
	private void updateComputer() {
		try {
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
			this.setPage(new EntityDeletePage());
		} catch (ServiceException e) {
			logger.error("Bad Page instantiation in controller");
		}
	}

	/**
	 * Deletes a Company from the database
	 * 
	 * @throws ServiceException
	 */
	private void deleteCompany() {
		try {
			this.setPage(new EntityDeletePage());
		} catch (ServiceException e) {
			logger.error("Bad Page instantiation in controller");
		}
	}

	/**
	 * Fetches all instance of a certain type of entity from the database
	 * 
	 * @return List<Entity>
	 * @throws ServiceException
	 * @throws DaoException 
	 */
	private List getAllEntities(Class clazz) throws ServiceException, DaoException {
		List dto = null;
		switch (clazz.getSimpleName()) {
			case "ComputerService":
				dto = this.computerService.getAll().get();
				break;
			case "CompanyService":
				dto = this.companyService.getAll().get();
				break;
		}
		return dto;
	}

	/**
	 * Fetches an Entity based on a given Id.
	 * 
	 * @param id
	 * @return An entity mapped from the database
	 * @throws ServiceException
	 */
	private ComputerDTO getEntityById(Long id) throws ServiceException {
		try {
			return this.computerService.getOneById(id).get();
		} catch (BadInputException e) {
			logger.error("Couldn't get entity by id!");
		}
		return null;
	}

	/**
	 * Fetches an Entity based on a given Id.
	 * 
	 * @param newEntity
	 * @return true if the call changed the database, else false
	 */
	private void addEntity(ComputerDTO newEntity) {
		try {
			this.computerService.create(pcMapper.mapToComputer(newEntity, companyService.getDao()).get());
		} catch (ServiceException | DaoException e) {
			logger.error("Couldn't add entity" + newEntity.getName() + "!");
		}
	}

	/**
	 * Sets the page to be shown by the controller
	 * 
	 * @param page
	 */
	private void setPage(Page page) {
		this.page = page;
	}

	public CompanyService getCompanyService() {
		return this.companyService;
	}

	public ComputerService getComputerService() {
		return this.computerService;
	}

}
