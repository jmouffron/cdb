package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IService;
import com.excilys.cdb.service.ServiceFactory;

/**
 * Servlet implementation class DashBoard
 */
@WebServlet(
  description = "The main web entry point to the app, a dashboard about the database entities",
  urlPatterns = {
	  "/DashBoard", "/dashBoard", "/", "/home", "/index.html", "/index"
})
public class DashBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DashBoard.class);
	private IService<?> computerService;

	/**
	 * Default constructor.
	 */
	public DashBoard() {
		super();
		try {
			this.computerService = ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
		} catch (ServiceException e) {
			logger.debug(e.getMessage());
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logger.info("initialisation de la servlet DashBoard.");
	}

	private List<Computer> searchByName(String name) {
		List<Computer> computers = (List<Computer>) this.computerService.getAll();
		List<Computer> filteredComputers = computers.stream()
				.filter(computer -> computer.getName().matches(name) || computer.getCompany().getName().matches(name))
				.collect(Collectors.toList());
		return filteredComputers;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchName = request.getParameter("search");
		String entitiesPerPage = request.getParameter("entitiesPerPage");
		
		List<Computer> computers;
		if (searchName == null) {
			searchName = "";
			computers = (List<Computer>) this.computerService.getAll();
		} else {
			computers = this.searchByName(searchName);
		}
		int computerNumber = computers.size();
    HttpSession session = request.getSession(true);
    session.setAttribute("search", searchName);
    session.setAttribute("computers", computers);
    session.setAttribute("computerNumber", computerNumber);
    
		if ( !entitiesPerPage.isEmpty() || entitiesPerPage != null ) {
		  int[] pages = paginate(computerNumber, Integer.parseInt( entitiesPerPage ));
	    session.setAttribute("pages", pages);
		}

		logger.info(getServletName() + " has been called.");

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
		doGet(request, response);
	}
	
	private int[] paginate(int numberTotal, int numberPerPage) {
	  int pageNumber = numberPerPage / numberTotal;
	  int[] pages = IntStream.rangeClosed(1, pageNumber).toArray();
	  
	  return pages;
	}
}
