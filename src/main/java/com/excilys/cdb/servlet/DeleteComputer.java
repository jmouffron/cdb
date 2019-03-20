package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IService;
import com.excilys.cdb.service.ServiceFactory;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet(description = "A Delete endpoint in a CRUD API for computer entities", urlPatterns = { "/DeleteComputer",
		"/deleteComputer" })
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DeleteComputer.class);
	private IService<?> service;

	/**
	 * Default constructor.
	 */
	public DeleteComputer() {
		super();
		try {
			this.service = ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
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
		if (computerName == null) {
			computerName = "";
		}

		Computer computer = null;
		try {
			computer = (Computer) this.service.getOneByName(computerName);
		} catch (BadInputException e) {
			response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
		}

		if (computer == null) {
			response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null computer found!");
		} else {
			HttpSession session = request.getSession(true);
			session.setAttribute("computer", computer);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteComputer");
			dispatcher.forward(request, response);
		}
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
			response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "No correct computer name found!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteComputer");
			dispatcher.forward(request, response);
		}

		Computer computer = null;
		try {
			computer = (Computer) this.service.getOneByName(computerName);
		} catch (BadInputException e) {
			response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
		}

		if (computer == null) {
			response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null computer found!");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteComputer");
			dispatcher.forward(request, response);
		}
		boolean isDeleted = false;
		try {
			isDeleted = this.service.deleteById(computer.getId());
		} catch (BadInputException e) {
			response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Couldn't delete computer!" + e.getMessage());

			RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteComputer");
			dispatcher.forward(request, response);
		}
		if (isDeleted) {
			HttpSession session = request.getSession(true);
			session.setAttribute("success", "The computer " + computer.getName() + " has been correctly deleted!");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard");
			dispatcher.forward(request, response);
		}

	}

}
