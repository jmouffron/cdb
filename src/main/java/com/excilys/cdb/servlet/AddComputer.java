package com.excilys.cdb.servlet;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IService;
import com.excilys.cdb.service.ServiceFactory;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(
    description = "A Create endpoint in a CRUD API for computer entities", 
    urlPatterns = { "/AddComputer",
    "/addComputer" })
public class AddComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
  private IService<Computer> computerService;
  private IService<Company> companyService;

	/**
	 * Default constructor.
	 */
	public AddComputer() {
		super();
		try {
			this.computerService = (IService<Computer>) ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
			this.companyService = (IService<Company>) ServiceFactory.getService(ServiceFactory.COMPANY_SERVICE);
		} catch (ServiceException e) {
			logger.debug(e.getMessage());
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		if (computerName.isEmpty() || computerName == null) {
			logger.debug("Empty or null name for computer to be searched.");
			response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), "No Computer Name chosen for this request!");
		}
	
		Computer computer = null;
		try {
			computer = this.computerService.getOneByName(computerName);
		} catch (BadInputException e) {
			logger.debug(e.getMessage());
			response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), e.getMessage());
		}
		List<Company> companies = this.companyService.getAll();
	
		HttpSession session = request.getSession(true);
		session.setAttribute("companies", companies);
		session.setAttribute("computer", computer);
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard");
		dispatcher.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String computerName = request.getParameter("computerName");
		if (computerName == null) {
			logger.debug("Empty or null name for computer to be searched.");
			response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), "No Computer Name chosen for this request!");
		}
	
		Computer computer = null;
		try {
			computer = this.computerService.getOneByName(computerName);
		} catch (BadInputException e) {
			logger.debug(e.getMessage());
			response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), e.getMessage());
		}
	
		boolean isCreated = false;
		try {
			isCreated = this.computerService.create(computer);
		} catch (BadInputException e) {
      e.printStackTrace();
    }

    if (isCreated) {
      HttpSession session = request.getSession(true);
      session.setAttribute("success", "Computer " + computer.getName() + " successfully created!");
      
      RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard");
      dispatcher.forward(request, response);
    }
  }

}
