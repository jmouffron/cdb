package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.PageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceFactory;
import com.excilys.cdb.view.IndexPagination;
import com.excilys.cdb.view.Pagination;

/**
 * Servlet implementation class DashBoard
 */
@WebServlet(description = "The main web entry point to the app, a dashboard about the database entities", urlPatterns = {
    "/DashBoard", "/dashBoard", "/", "/home", "/index.html", "/index" })
public class DashBoard extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(DashBoard.class);
  private Pagination pagination;
  private ComputerService computerService;

  /**
   * Default constructor.
   */
  public DashBoard() {
    super();
    this.computerService = ServiceFactory.getComputerService();
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    logger.info("Initialisation de la servlet DashBoard.");
    pagination = new Pagination(this.computerService.getAll().get(), 0, IndexPagination.IDX_10);
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String searchName = request.getParameter("search");
    String index = request.getParameter("startIndex");
    String perPage = request.getParameter("perPage");
    String newPage = request.getParameter("page");

    List<Computer> computers = null;
    IndexPagination entitiesPerPage;
    int startIndex;
    int page;
    
    if (searchName == null || searchName.isEmpty()) {
      searchName = "";
      computers = this.computerService.getAll().get();
    } else {
      computers = this.computerService.searchByName(searchName).get();
    }
    if (perPage == null) {
      entitiesPerPage = IndexPagination.IDX_10;
    } else {
      entitiesPerPage = IndexPagination.valueOf(perPage);
    }
    if (index == null) {
      startIndex = 0;
    } else {
      startIndex = Integer.parseInt(index);
    }
    if (newPage == null || newPage.equals("0")) {
      page = 1;
    } else {
      page = Integer.parseInt(newPage);
    }
    
    HttpSession session = request.getSession(true);
    session.setAttribute("search", searchName);
    session.setAttribute("index", startIndex);
    
    try {
      pagination.setPerPage(entitiesPerPage);
    } catch (PageException e) {
      logger.error(e.getMessage());
      session.setAttribute("stackTrace", e.getStackTrace());
      response.sendError(ErrorCode.SERVER_ERROR.getErrorCode(), e.getStackTrace().toString());
    }
    
    pagination.setElements(computers);
    pagination.navigate(page);
    
    try {
      session.setAttribute("computerNumber", pagination.getSize());
      session.setAttribute("totalPages", pagination.getTotalPages());
      session.setAttribute("pages", pagination.getPages());
      session.setAttribute("computers", pagination.list().get());
      session.setAttribute("currentPage", pagination.getCurrentPage());
    } catch (PageException e) {
      logger.error(e.getMessage());
      session.setAttribute("stackTrace", e.getStackTrace());
      response.sendError(ErrorCode.SERVER_ERROR.getErrorCode(), e.getStackTrace().toString());
    }

    logger.info(getServletName() + " has been called with URL: " + request.getRequestURI());
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
