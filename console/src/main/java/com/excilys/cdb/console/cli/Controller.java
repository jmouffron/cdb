package com.excilys.cdb.console.cli;

import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.console.resource.CompanyResource;
import com.excilys.cdb.console.resource.ComputerResource;
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
					listAllCompanies();
					break;
				case FETCH_ALL_COMPUTER:
					listAllComputers();
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
	 * List all instances of an entity in the database
	 * 
	 * @param serviceType
	 * @throws DaoException 
	 * @throws ServiceException
	 */
	private void listAllComputers() throws DaoException {
		ComputerResource.getAllComputers().ifPresent(System.out::println);
	}
	
	private void listAllCompanies() throws DaoException {
		CompanyResource.getAllCompanies().ifPresent(System.out::println);
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
		entity = ComputerResource.getComputer(id).get();
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
		company = CompanyResource.getCompany(companyId).get();

		ComputerDTO dto = new ComputerDTO.ComputerDTOBuilder().setName(name).setIntroduced(introduced.toString())
				.setDiscontinued(discontinued.toString()).setCompanyId(company.getId())
				.setCompanyName(company.getName()).build();

		Data payload = new Data(dto);
		ComputerResource.postComputer(dto);

		this.setPage(new EntityAddPage(payload));

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
