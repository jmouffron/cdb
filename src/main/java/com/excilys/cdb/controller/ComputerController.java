package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.PageException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.view.IndexPagination;
import com.excilys.cdb.view.Pagination;

@Controller
public class ComputerController {
	private static final String DASHBOARD = "dashboard";
	private static final String EDIT_COMPUTER = "editComputer";
	private static final String ADD_COMPUTER = "addComputer";
	private static final String ERROR = "500";

	private final Logger logger = LoggerFactory.getLogger(ComputerController.class);

	ComputerService computerService;

	CompanyService companyService;

	MessageSource msgSrc;

	Pagination pagination;

	public ComputerController(ComputerService computerService, CompanyService companyService, Pagination pagination, MessageSource msg) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.pagination = pagination;
		this.msgSrc = msg;
	}

	private static Map<String, String> columns;

	static {
		columns = new HashMap<>();

		columns.put("0", "pc_name");
		columns.put("1", "introduced");
		columns.put("2", "discontinued");
		columns.put("3", "cp_name");
	}

	@GetMapping({ "/", "/dashboard", "/dashBoard" })
	public String getDashBoard(@RequestParam(required = false) Map<String, String> paths, Model model) {
		logger.info(getClass().getName() + " has been called");

		List<ComputerDTO> computers = null;
		IndexPagination entitiesPerPage;
		int startIndex;
		int page;
		boolean isDesc;

		String success = paths.get("success");
		String danger = paths.get("danger");

		String index = paths.get("startIndex");
		String perPage = paths.get("perPage");
		String newPage = paths.get("page");

		String searchName = paths.get("search");
		String order = paths.get("order");
		String toOrder = paths.get("toOrder");
		String selection = paths.get("selection");

		isDesc = (order == null || order.equals("")) ? false : true;
		entitiesPerPage = (perPage == null || perPage.equals("")) ? IndexPagination.IDX_10
				: IndexPagination.valueOf(perPage);
		startIndex = (index == null) ? 0 : Integer.parseInt(index);
		page = (newPage == null || newPage.equals("0")) ? 1 : Integer.parseInt(newPage);
		toOrder = (toOrder == null || toOrder.equals("") || !columns.containsKey(toOrder)) ? "pc_name"
				: columns.get(toOrder);

		if (searchName == null || searchName.isEmpty()) {
			searchName = "";
			try {
				computers = this.computerService.orderBy(toOrder, isDesc).get();
			} catch (ServiceException | DaoException e) {
				logger.error(e.getMessage());
				model.addAttribute("stackTrace", e.getMessage());
				return ERROR;
			}
		} else {
			try {
				computers = this.computerService.searchByNameOrdered(searchName, toOrder, isDesc).get();
			} catch (ServiceException | DaoException e) {
				logger.error(e.getMessage());
				model.addAttribute("stackTrace", e.getMessage());
				return ERROR;
			}
		}

		model.addAttribute("search", searchName);
		model.addAttribute("index", startIndex);
		model.addAttribute("success", success);
		model.addAttribute("danger", danger);
		model.addAttribute("sel", selection);

		try {
			pagination.setPerPage(entitiesPerPage);
		} catch (PageException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return ERROR;
		}

		pagination.setElements(computers);
		pagination.navigate(page);

		try {
			model.addAttribute("computerNumber", pagination.getSize());
			model.addAttribute("totalPages", pagination.getTotalPages());
			model.addAttribute("pages", pagination.getPages());
			model.addAttribute("computers", pagination.list().get());
			model.addAttribute("currentPage", pagination.getCurrentPage());
		} catch (PageException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return ERROR;
		}
		model.addAttribute("perPage", perPage);

		return DASHBOARD;
	}

	@PostMapping({ "/", "/dashboard", "/dashBoard" })
	public String postDeleteComputer(@RequestParam(required = false) Map<String, String> paths, Model model) {
		logger.info(getClass().getName() + " has been called");

		String success = paths.get("success");
		String danger = paths.get("danger");
		String selection = paths.get("selection");

		if ("".equals(selection)) {
			logger.error("No correct computer name found!");
			model.addAttribute("stackTrace", "No correct computer name found!");
			return ERROR;
		}
		List<Long> computerIdsToDelete = Arrays.asList(selection.split(",")).stream().map(Long::parseLong)
				.collect(Collectors.toList());

		computerIdsToDelete.forEach((Long id) -> {
			ComputerDTO computer = null;
			try {
				computer = this.computerService.getOneById(id).get();
			} catch (BadInputException e) {
				logger.error(e.getMessage());
				model.addAttribute("stackTrace", e.getMessage());
			}

			if (computer == null) {
				logger.error("Null computer found!");
				model.addAttribute("stackTrace", "Null computer found!");
			}

			try {
				this.computerService.deleteById(computer.getId());
			} catch (ServiceException | DaoException e) {
				logger.error(e.getMessage());
				model.addAttribute("stackTrace", e.getMessage());
				model.addAttribute("success", "The computer " + computer.getName() + " has been correctly deleted!");
				model.addAttribute("danger", danger);
			}

			model.addAttribute("success", success);
			model.addAttribute("danger", "The computer" + computer.getName() + "couldn't be deleted!");
		});

		return getDashBoard(paths, model);
	}

	@GetMapping({ "addComputer", "/addcomputer", "/AddComputer", "/Addcomputer" })
	public String getAddComputer(@Validated @RequestParam(required = false) Map<String, String> paths, Model model) {
		logger.info(getClass().getName() + " has been called");

		String computerName = paths.get("computerName");
		computerName = (computerName == null || computerName.equals("")) ? "" : computerName.trim();

		List<CompanyDTO> companies = null;

		try {
			companies = this.companyService.orderBy("id", Boolean.TRUE).get();
		} catch (ServiceException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return ERROR;
		}

		model.addAttribute("companies", companies);

		return ADD_COMPUTER;
	}

	@PostMapping({ "/addComputer", "/addcomputer", "/AddComputer", "/Addcomputer" })
	public String postAddComputer(@RequestParam(required = false) Map<String, String> paths, BindingResult res,
			Model model) {
		logger.info(getClass().getName() + " has been called");

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		String computerName = paths.get("computerName");
		String introduced = paths.get("introduced");
		String discontinued = paths.get("discontinued");
		String companyName = paths.get("companyName");

		if (computerName == null || computerName.equals("")) {
			logger.debug("Empty or null name for computer to be searched.");
			model.addAttribute("stackTrace", "Empty or null name for computer to be searched.");
			return ERROR;
		}

		ComputerDTO computerDto = new ComputerDTO.ComputerDTOBuilder().setName(computerName).setIntroduced(introduced)
				.setCompanyName(companyName).setDiscontinued(discontinued).build();

		try {
			this.computerService.create(computerDto);
		} catch (ServiceException | DaoException e) {
			logger.debug(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			model.addAttribute("danger", "Computer " + computerDto.getName() + " couldn't be created!");
			return ERROR;
		}

		model.addAttribute("success", "Computer " + computerDto.getName() + " successfully created!");

		return getDashBoard(paths, model);
	}

	@GetMapping({ "/editComputer", "/editcomputer", "/EditComputer", "/Editcomputer" })
	public String getEditComputer(@RequestParam(required = false) Map<String, String> paths, Model model) {
		logger.info(getClass().getName() + " has been called");

		String computerId = paths.get("computerId");

		if ("".equals(computerId)) {
			logger.debug("Empty or null id for computer to be searched.");
			model.addAttribute("stackTrace", "Empty or null id for computer to be searched.");
			return ERROR;
		}

		ComputerDTO computer = null;
		try {
			computer = this.computerService.getOneById(Long.parseLong(computerId)).get();
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return ERROR;
		}

		List<CompanyDTO> companies = null;
		try {
			companies = this.companyService.getAll().get();
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return ERROR;
		}

		model.addAttribute("companies", companies);
		model.addAttribute("computer", computer);

		return EDIT_COMPUTER;
	}

	@PostMapping({ "/editComputer", "/editcomputer", "/EditComputer", "/Editcomputer" })
	public String postEditComputer(@RequestParam(required = false) Map<String, String> paths, BindingResult res,
			Model model) {
		logger.info(getClass().getName() + " has been called");

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		String computerId = paths.get("computerId");
		String computerName = paths.get("computerName");
		String computerIntro = paths.get("computerIntro");
		String computerDisco = paths.get("computerDisco");
		String computerComp = paths.get("computerComp");
		String companyId = paths.get("companyId");

		List<String> computerData = new ArrayList<>(
				Arrays.asList(computerId, computerName, computerIntro, computerDisco, computerComp));
		computerData.forEach(param -> {
			if ("".equals(param)) {
				logger.debug("Empty or null id for computer to be searched.");
				model.addAttribute("stackTrace", "Empty or null id for computer to be searched.");
			}
		});

		ComputerDTO computerInDb = null;
		try {
			computerInDb = this.computerService.getOneById(Long.parseLong(computerId)).get();
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return ERROR;
		}

		ComputerDTO computerToSend = new ComputerDTO.ComputerDTOBuilder().setId(Long.parseLong(computerId))
				.setCompanyId(Long.parseLong(companyId)).setCompanyName(computerComp).setIntroduced(computerIntro)
				.setDiscontinued(computerDisco).setName(computerName).build();

		if (computerToSend.getId() != computerInDb.getId()) {
			logger.error("Wrong edit and change of the id!");
			model.addAttribute("stackTrace", "Wrong edit and change of the id!");
			return ERROR;
		}
		
		try {
			this.computerService.updateById(computerToSend);
		} catch (ServiceException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			model.addAttribute("danger", "Computer " + computerToSend.getName() + " not updated!");
			return ERROR;
		}
		model.addAttribute("success", "Computer " + computerToSend.getName() + " successfully updated!");

		return getDashBoard(paths, model);
	}

	private boolean setStackTraceBindingError(Model model, BindingResult res) {
		if (res.hasErrors()) {
			StringBuilder stackTrace = new StringBuilder();
			List<ObjectError> errors = res.getAllErrors();
			errors.forEach(stackTrace::append);
			model.addAttribute("stackTrace", stackTrace);
			return true;
		}
		return false;
	}
}
