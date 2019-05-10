package com.excilys.cdb.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.PageException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.persistence.mapper.ComputerMapper;
import com.excilys.cdb.service.view.IndexPagination;
import com.excilys.cdb.service.view.Pagination;
import com.excilys.cdb.binding.validator.ComputerDTOValidator;
import com.excilys.cdb.binding.validator.ModelList;
import com.excilys.cdb.binding.validator.ModelListValidator;
import com.excilys.cdb.core.model.Computer;

@Controller
@RequestMapping("/computer")
public class ComputerController {
	private static final String DASHBOARD = "dashboard";
	private static final String EDIT_COMPUTER = "editComputer";
	private static final String ADD_COMPUTER = "addComputer";
	private static final String ERROR = "500";
	private static final String REDIRECT_ADD = "redirect:addComputer";
	private static final String REDIRECT_EDIT = "redirect:editComputer";
	private static final String REDIRECT_OP = "redirect:/computer";
	private static final String SUCCESS = "success";
	private static final String DANGER = "danger";
	private static final String STACKTRACE = "stackTrace";
	private static final String NULL_COMPUTER = "Null computer of id ";
	private static final String CONTROLLER_LOG = String.format("%s has been called", ComputerController.class.getName());
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

		columns.put("0", "name");
		columns.put("1", "introduced");
		columns.put("2", "discontinued");
		columns.put("3", "company");
	}

	@InitBinder("computer")
	protected void initDTOBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator());
	}

	@InitBinder("computersDestroyed")
	protected void initListDTOBinder(WebDataBinder binder) {
		binder.setValidator(new ModelListValidator());
	}

	@GetMapping({ "","/", "/dashboard", "/dashBoard" })
	public String getDashBoard(
			@RequestParam(required = false) Map<String, String> paths,
			@RequestParam(required = false, name = SUCCESS) String success,
			@RequestParam(required = false, name = DANGER) String danger,
			@RequestParam(required = false, name = "startIndex") String index,
			@RequestParam(required = false, name = "perPage") String perPage,
			@RequestParam(required = false, name = "page") String newPage,
			@RequestParam(required = false, name = "search") String searchName,
			@RequestParam(required = false, name = "toOrder") String toOrder,
			@RequestParam(required = false, name = "order") String order,
			@RequestParam(required = false, name = "selection") String selection,
			Model model
	) {
		logger.info( CONTROLLER_LOG );

		List<ComputerDTO> computers;
		IndexPagination entitiesPerPage;
		int startIndex;
		int page;
		boolean isDesc;

		isDesc = ("".equals(order)) ? Boolean.FALSE : Boolean.TRUE;
		entitiesPerPage = ("".equals(perPage)) ? IndexPagination.IDX_10
				: IndexPagination.valueOf(perPage);
		startIndex = (Objects.isNull(index)) ? 0 : Integer.parseInt(index);
		page = (Objects.isNull(newPage) || newPage.equals("0")) ? 1 : Integer.parseInt(newPage);
		toOrder = ("".equals(toOrder) || !columns.containsKey(toOrder)) ? columns.getOrDefault(0,"name")
				: columns.get(toOrder);

		if ("".equals(searchName)) {
			searchName = "";
			try {
				computers = this.computerService.orderBy(toOrder, isDesc).orElseThrow(ServiceException::new);
			} catch (ServiceException | DaoException e) {
				logger.error(e.getMessage());
				model.addAttribute(STACKTRACE, e.getMessage());
				return ERROR;
			}
		} else {
			try {
				computers = this.computerService
						.searchByNameOrdered(searchName, toOrder, isDesc)
						.orElseThrow(ServiceException::new);
			} catch (ServiceException e) {
				logger.error(e.getMessage());
				model.addAttribute(STACKTRACE, e.getMessage());
				return ERROR;
			}
		}

		model.addAttribute("search", searchName);
		model.addAttribute("index", startIndex);
		model.addAttribute(SUCCESS, success);
		model.addAttribute(DANGER, danger);
		model.addAttribute("sel", selection);

		try {
			pagination.setPerPage(entitiesPerPage);
		} catch (PageException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return ERROR;
		}

		pagination.setElements(computers);
		pagination.navigate(page);

		try {
			model.addAttribute("computerNumber", pagination.getSize());
			model.addAttribute("totalPages", pagination.getTotalPages());
			model.addAttribute("pages", pagination.getPages());
			model.addAttribute("computers", pagination.list().orElseThrow(PageException::new));
			model.addAttribute("currentPage", pagination.getCurrentPage());
		} catch (PageException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
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
		logger.info(CONTROLLER_LOG);

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		StringBuilder success = new StringBuilder(Optional.ofNullable(paths.get(SUCCESS)).orElse(""));
		StringBuilder danger = new StringBuilder(Optional.ofNullable(paths.get(DANGER)).orElse(""));

		LongStream.of(computers.getIds()).forEach( (long id) -> {
			ComputerDTO computer = null;
			try {
				computer = this.computerService.getOneById(id).get();
			} catch (BadInputException e) {
				logger.error(e.getMessage());
				model.addAttribute(STACKTRACE, e.getMessage());
				model.addAttribute(DANGER, e.getMessage());
			}

			if (computer == null) {
				logger.error(NULL_COMPUTER + id);
				model.addAttribute(STACKTRACE, NULL_COMPUTER + id );
				model.addAttribute(DANGER, NULL_COMPUTER + id );
			}

			try {
				this.computerService.deleteById(computer.getId());
			} catch (DaoException e) {
				logger.error(e.getMessage());
				model.addAttribute(STACKTRACE, e.getMessage());
				String message = String.format("The computer %s has been incorrectly deleted!", computer.getName());
				model.addAttribute(DANGER, message);
			}
			success.append("The computer {}" + computer.getName() + " has been correctly deleted!\n");
			
		});
		model.addAttribute(SUCCESS, success);
		model.addAttribute(DANGER, danger);

		return REDIRECT_OP;
	}

	@GetMapping({ "/add" })
	public String getAddComputer(@Validated @RequestParam(required = false) Map<String, String> paths, Model model) {
		logger.info(CONTROLLER_LOG);

		List<CompanyDTO> companies = null;

		try {
			companies = this.companyService.orderBy("id", Boolean.TRUE).orElseThrow(ServiceException::new);
		} catch (ServiceException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return ERROR;
		}

		model.addAttribute("companies", companies);
		model.addAttribute("computer", new ComputerDTO());

		return ADD_COMPUTER;
	}

	@PostMapping({ "/add" })
	public String postAddComputer(@RequestParam(required = false) Map<String, String> paths,
			@Validated @ModelAttribute("computer") ComputerDTO dto, BindingResult res, Model model) {
		logger.info(CONTROLLER_LOG);

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return REDIRECT_ADD;
		}

		try {
			Computer computer = pcMapper.mapToComputer(dto, companyService.getDao()).orElseThrow(ServiceException::new);
			this.computerService.create(computer);
		} catch (ServiceException | DaoException e) {
			logger.debug(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			String message = String.format("Computer %s couldn't be created!", dto.getName());
			model.addAttribute(DANGER, message);
			return ERROR;
		}
		
		String message = String.format( "Computer %s successfully created!",dto.getName());
		model.addAttribute(SUCCESS, message);

		return REDIRECT_OP;
	}

	@GetMapping({ "/edit/{id}" })
	public String getEditComputer(@PathVariable(required = false, value = "id") Long computerId, Model model) {
		logger.info(CONTROLLER_LOG);

		ComputerDTO computer = null;
		try {
			computer = this.computerService.getOneById(computerId).orElseThrow(BadInputException::new);
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return ERROR;
		}

		List<CompanyDTO> companies = null;
		try {
			companies = this.companyService.getAll().orElseThrow(ServiceException::new);
		} catch (ServiceException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return ERROR;
		}
		model.addAttribute("companies", companies);
		model.addAttribute("computer", computer);
		model.addAttribute("dto", new ComputerDTO());

		return EDIT_COMPUTER;
	}

	@PostMapping({ "/edit/{id}" })
	public String postEditComputer(@Validated @ModelAttribute("dto") ComputerDTO computer, BindingResult res,
			Model model) {
		logger.info(CONTROLLER_LOG);

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return REDIRECT_EDIT;
		}

		ComputerDTO computerInDb = null;
		try {
			computerInDb = this.computerService.getOneById(computer.getId()).orElseThrow(BadInputException::new);
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return ERROR;
		}

		if (computer.getId() != computerInDb.getId()) {
			logger.error("Wrong edit and change of the id!");
			model.addAttribute(STACKTRACE, "Wrong edit and change of the id!");
			return ERROR;
		}

		try {
			Computer comp = pcMapper.mapToComputer(computer, companyService.getDao()).orElseThrow(ServiceException::new);
			this.computerService.updateById(comp);
		} catch (ServiceException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			String message = String.format("Computer %s not updated!", computer.getName());
			model.addAttribute(DANGER, message);
			return ERROR;
		}
		String message = String.format("Computer %s successfully updated!", computer.getName());
		model.addAttribute(SUCCESS, message);

		return REDIRECT_OP;
	}

	private boolean setStackTraceBindingError(Model model, BindingResult res) {
		if (res.hasErrors()) {
			StringBuilder stackTrace = new StringBuilder();
			List<ObjectError> errors = res.getAllErrors();
			errors.forEach(stackTrace::append);
			model.addAttribute(STACKTRACE, stackTrace);
			return true;
		}
		return false;
	}
}
