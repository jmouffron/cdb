package com.excilys.cdb.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	private final String DASHBOARD = "dashboard";
	private final String EDIT_COMPANY = "editCompany";
	private final String DELETE_COMPANY = "deleteCompany";
	private final String ERROR = "500";

	CompanyService companyService;
	
	public CompanyController(CompanyService service) {
		this.companyService = service;
	}

	@GetMapping({ "/editCompany", "/editCompany" })
	public String getEditCompany(@RequestParam(required = false) Map<String, String> paths, Model model) {
		String companyName = paths.get("companyName");

		if ("".equals(companyName)) {
			logger.error("Empty or null name for company to be searched.");
			model.addAttribute("stackTrace", "Empty or null name for company to be searched.");
			return DASHBOARD;
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).get();
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return DASHBOARD;
		}

		model.addAttribute("company", company);

		return EDIT_COMPANY;
	}

	@PostMapping({ "/editCompany", "/editCompany" })
	public String postEditCompany(@Validated @RequestParam(required = false) Map<String, String> paths,
			BindingResult res, Model model) {
		String companyName = paths.get("companyName");

		boolean hasError = setStackTraceBindingError(model, res);
		if (hasError) {
			return ERROR;
		}

		if (companyName == null) {
			logger.error("Empty or null name for company to be searched.");
			model.addAttribute("stackTrace", "Empty or null name for company to be searched.");
			return DASHBOARD;
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).get();
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return DASHBOARD;
		}

		boolean isUpdated = false;
		try {
			isUpdated = this.companyService.updateById(company);
		} catch (DaoException | ServiceException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return DASHBOARD;
		}

		if (isUpdated) {
			model.addAttribute("success", "Company " + company.getName() + " successfully updated!");
		} else {
			model.addAttribute("danger", "Company " + company.getName() + " not updated!");
		}

		return getEditCompany(paths, model);
	}

	@GetMapping({ "/deleteCompany", "/deletecompany" })
	public String getRoot(@RequestParam(required = false) Map<String, String> paths, Model model) {
		String companyName = paths.get("companyName");

		if (companyName == null) {
			companyName = "";
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).get();
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return DASHBOARD;
		}

		if (company == null) {
			logger.error("Null company found!");
			model.addAttribute("stackTrace", "Null Company found!");
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

		String success = paths.get("success");
		String danger = paths.get("danger");
		String companyName = paths.get("companyName");

		if (companyName == null) {
			logger.error("No correct company name found!");
			model.addAttribute("stackTrace", "No correct company name found!");
			return DASHBOARD;
		}

		CompanyDTO company = null;
		try {
			company = this.companyService.getOneByName(companyName).get();
		} catch (BadInputException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return DASHBOARD;
		}

		if (company == null) {
			logger.error("Null company found!");
			model.addAttribute("stackTrace", "Null company found!");
			return DASHBOARD;
		}

		boolean isDeleted = false;

		try {
			isDeleted = this.companyService.deleteById(company.getId());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			model.addAttribute("stackTrace", e.getMessage());
			return DASHBOARD;
		}

		if (isDeleted) {
			model.addAttribute("success", "The company " + company.getName() + " has been correctly deleted!");
			model.addAttribute("danger", danger);
		} else {
			model.addAttribute("success", success);
			model.addAttribute("danger", "The company" + company.getName() + "couldn't be deleted!");
		}

		return getRoot(paths, model);
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
