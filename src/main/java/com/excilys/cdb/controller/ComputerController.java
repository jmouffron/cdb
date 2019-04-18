package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.PageException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.ModelList;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.CompanyDTOValidator;
import com.excilys.cdb.validator.ComputerDTOValidator;
import com.excilys.cdb.validator.ModelListValidator;
import com.excilys.cdb.view.IndexPagination;
import com.excilys.cdb.view.Pagination;

@Controller
public class ComputerController {
	private static final String DASHBOARD = "dashboard";
	private static final String EDIT_COMPUTER = "editComputer";
	private static final String ADD_COMPUTER = "addComputer";
	private static final String ERROR = "500";
	private static final String REDIRECT_DASHBOARD = "redirect:dashboard";

	private final Logger logger = LoggerFactory.getLogger(ComputerController.class);

	private ComputerService computerService;

	private CompanyService companyService;

	private ComputerMapper pcMapper;

	private Pagination pagination;

	public ComputerController(ComputerService computerService, CompanyService companyService, Pagination pagination,
			ComputerMapper mapper) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.pagination = pagination;
		this.pcMapper = mapper;
	}

	private static Map<String, String> columns;

	static {
		columns = new HashMap<>();

		columns.put("0", "pc_name");
		columns.put("1", "introduced");
		columns.put("2", "discontinued");
		columns.put("3", "cp_name");
	}

	@InitBinder("computer")
	protected void initDTOBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}

	@InitBinder("computersDestroyed")
	protected void initListDTOBinder(WebDataBinder binder) {
		binder.setValidator(new ModelListValidator());
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
		model.addAttribute("toOrder", toOrder);
		model.addAttribute("computersDestroyed", new ModelList());

		return DASHBOARD;
	}

	@PostMapping({ "/", "/dashboard", "/dashBoard" })
	public String postDeleteComputer(@RequestParam(required = false) Map<String, String> paths,
			@Validated @ModelAttribute("computersDestroyed") ModelList computers, BindingResult res, Model model) {
		logger.info(getClass().getName() + " has been called");

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		StringBuilder success = new StringBuilder(Optional.ofNullable(paths.get("success")).orElse(""));
		StringBuilder danger = new StringBuilder(Optional.ofNullable(paths.get("danger")).orElse(""));

		LongStream.of(computers.getIds()).forEach( (long id) -> {
			ComputerDTO computer = null;
			try {
				computer = this.computerService.getOneById(id).get();
			} catch (BadInputException e) {
				logger.error(e.getMessage());
				model.addAttribute("stackTrace", e.getMessage());
				model.addAttribute("danger", e.getMessage());
			}

			if (computer == null) {
				logger.error("Null computer of id " + id);
				model.addAttribute("stackTrace", "Null computer of id " + id );
				model.addAttribute("danger", "Null computer of id " + id );
			}

			try {
				this.computerService.deleteById(computer.getId());
			} catch (ServiceException | DaoException e) {
				logger.error(e.getMessage());
				model.addAttribute("stackTrace", e.getMessage());
				model.addAttribute("danger", "The computer " + computer.getName() + " has been incorrectly deleted!");
			}
			success.append("The computer " + computer.getName() + " has been correctly deleted!\n");
			
		});
		model.addAttribute("success", success);
		model.addAttribute("danger", danger);

		return REDIRECT_DASHBOARD;
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
		model.addAttribute("computer", new ComputerDTO());

		return ADD_COMPUTER;
	}

	@PostMapping({ "/addComputer", "/addcomputer", "/AddComputer", "/Addcomputer" })
	public String postAddComputer(@RequestParam(required = false) Map<String, String> paths,
			@Validated @ModelAttribute("computer") ComputerDTO computer, BindingResult res, Model model) {
		logger.info(getClass().getName() + " has been called");

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		try {
			this.computerService.create(pcMapper.mapToComputer(computer, companyService).get());
		} catch (ServiceException | DaoException e) {
			logger.debug(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			model.addAttribute("danger", "Computer " + computer.getName() + " couldn't be created!");
			return ERROR;
		}

		model.addAttribute("success", "Computer " + computer.getName() + " successfully created!");

		return REDIRECT_DASHBOARD;
	}

	@GetMapping({ "/editComputer", "/editcomputer", "/EditComputer", "/Editcomputer" })
	public String getEditComputer(@RequestParam(required = false, value = "computerId") long computerId, Model model) {
		logger.info(getClass().getName() + " has been called");

		ComputerDTO computer = null;
		try {
			computer = this.computerService.getOneById(computerId).get();
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
		model.addAttribute("dto", new ComputerDTO());

		return EDIT_COMPUTER;
	}

	@PostMapping({ "/editComputer", "/editcomputer", "/EditComputer", "/Editcomputer" })
	public String postEditComputer(@Validated @ModelAttribute("dto") ComputerDTO computer, BindingResult res,
			Model model) {
		logger.info(getClass().getName() + " has been called");

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		ComputerDTO computerInDb = null;
		try {
			computerInDb = this.computerService.getOneById(computer.getId()).get();
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return ERROR;
		}

		if (computer.getId() != computerInDb.getId()) {
			logger.error("Wrong edit and change of the id!");
			model.addAttribute("stackTrace", "Wrong edit and change of the id!");
			return ERROR;
		}

		try {
			this.computerService.updateById(pcMapper.mapToComputer(computer, companyService).get());
		} catch (ServiceException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			model.addAttribute("danger", "Computer " + computer.getName() + " not updated!");
			return ERROR;
		}
		model.addAttribute("success", "Computer " + computer.getName() + " successfully updated!");

		return REDIRECT_DASHBOARD;
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
