package com.excilys.cdb.webapp.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;

@Controller
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	private static final String DASHBOARD = "dashboard";
	private static final String EDIT_COMPANY = "editCompany";
	private static final String DELETE_COMPANY = "deleteCompany";
	private static final String ERROR = "500";
	private static final String COMPANY_NAME = "companyName";
	private static final String EMPTY_OR_NULL = "Empty or null name for company to be searched.";
	private static final String STACKTRACE = "stackTrace";
	private static final String SUCCESS = "success";
	private static final String DANGER = "danger";
	
	private CompanyService companyService;
	
	public CompanyController(CompanyService service) {
		this.companyService = service;
	}

	@GetMapping({ "/editCompany", "/editCompany" })
	public String getEditCompany(@RequestParam(required = false) Map<String, String> paths, Model model) {
		String companyName = paths.get(COMPANY_NAME);

		if ("".equals(companyName)) {
			logger.error(EMPTY_OR_NULL);
			model.addAttribute(STACKTRACE, EMPTY_OR_NULL);
			return DASHBOARD;
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).orElseThrow(DaoException::new);
		} catch (BadInputException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return DASHBOARD;
		}

		model.addAttribute("company", company);

		return EDIT_COMPANY;
	}

	@PostMapping({ "/editCompany", "/editCompany" })
	public String postEditCompany(@Validated @RequestParam(required = false) Map<String, String> paths,
			BindingResult res, Model model) {
		String companyName = paths.get(COMPANY_NAME);

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		if (companyName == null) {
			logger.error(EMPTY_OR_NULL);
			model.addAttribute(STACKTRACE, EMPTY_OR_NULL);
			return DASHBOARD;
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).orElseThrow(DaoException::new);
		} catch (BadInputException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return DASHBOARD;
		}

		
		try {
			this.companyService.updateById(company);
		} catch (DaoException | ServiceException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			model.addAttribute(DANGER, "Company " + company.getName() + " not updated!");
			return DASHBOARD;
		}
		model.addAttribute(SUCCESS, "Company " + company.getName() + " successfully updated!");

		return getEditCompany(paths, model);
	}

	@GetMapping({ "/deleteCompany", "/deletecompany" })
	public String getRoot(@RequestParam(required = false) Map<String, String> paths, Model model) {
		String companyName = paths.get(COMPANY_NAME);

		if (companyName == null) {
			companyName = "";
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).orElseThrow(DaoException::new);
		} catch (BadInputException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return DASHBOARD;
		}

		if (company == null) {
			logger.error(EMPTY_OR_NULL);
			model.addAttribute(STACKTRACE, EMPTY_OR_NULL);
			return DASHBOARD;
		} else {
			model.addAttribute("company", company);
		}

		return DELETE_COMPANY;
	}

	@PostMapping({ "/deleteCompany", "/deletecompany" })
	public String postRoot(@Validated @RequestParam(required = false) Map<String, String> paths, BindingResult res, Model model) {

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		String success = paths.get(SUCCESS);
		String danger = paths.get(DANGER);
		String companyName = paths.get(COMPANY_NAME);

		if (companyName == null) {
			logger.error("No correct company name found!");
			model.addAttribute(STACKTRACE, "No correct company name found!");
			return DASHBOARD;
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).orElseThrow(DaoException::new);
		} catch (BadInputException | DaoException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			return DASHBOARD;
		}

		if (company == null) {
			logger.error(EMPTY_OR_NULL);
			model.addAttribute(STACKTRACE, EMPTY_OR_NULL);
			return DASHBOARD;
		}

		try {
			this.companyService.deleteById(company.getId());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			model.addAttribute(STACKTRACE, e.getMessage());
			model.addAttribute(SUCCESS, success);
			model.addAttribute(DANGER, "The company" + company.getName() + "couldn't be deleted!");
			return DASHBOARD;
		}
		
		model.addAttribute(SUCCESS, "The company " + company.getName() + " has been correctly deleted!");
		model.addAttribute(DANGER, danger);

		return getRoot(paths, model);
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
