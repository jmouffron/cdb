package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.PageException;
import com.excilys.cdb.exception.ServiceException;
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

  private static final long serialVersionUID = 3558156539176540043L;
  private static final Logger logger = LoggerFactory.getLogger(DashBoard.class);
  private Pagination pagination;
  private ServiceFactory factory;
  private ComputerService computerService;
  private static Map<String, String> columns;

  static {
    columns = new HashMap<>();

    columns.put("0", "pc_name");
    columns.put("1", "introduced");
    columns.put("2", "discontinued");
    columns.put("3", "company_name");
  }

  /**
   * Default constructor.
   */
  public DashBoard(ServiceFactory factory) {
    super();
    this.factory = factory;
    this.computerService = this.factory.getComputerService();
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
    HttpSession session = request.getSession(true);

    List<ComputerDTO> computers = null;
    IndexPagination entitiesPerPage;
    int startIndex;
    int page;
    boolean isDesc;

    String success = request.getParameter("success");
    String danger = request.getParameter("danger");

    String index = request.getParameter("startIndex");
    String perPage = request.getParameter("perPage");
    String newPage = request.getParameter("page");

    String searchName = request.getParameter("search");
    String order = request.getParameter("order");
    String toOrder = request.getParameter("toOrder");

    isDesc = (order == null || order.equals("")) ? false : true;
    entitiesPerPage = (perPage == null) ? IndexPagination.IDX_10 : IndexPagination.valueOf(perPage);
    startIndex = (index == null) ? 0 : Integer.parseInt(index);
    page = (newPage == null || newPage.equals("0")) ? 1 : Integer.parseInt(newPage);

    if (searchName == null || searchName.isEmpty()) {
      searchName = "";
      toOrder = (toOrder == null || toOrder.equals("") || !columns.containsKey(toOrder)) ? "pc_name"
          : columns.get(toOrder);
      try {
        computers = this.computerService.orderBy(toOrder, isDesc).get();
      } catch (ServiceException e) {
        logger.error(e.getMessage());
        session.setAttribute("stackTrace", e.getMessage());
        response.sendError(ErrorCode.SERVER_ERROR.getErrorCode(), e.getMessage());
      }
    } else {
       computers = this.computerService.searchByName(searchName).get();
    }

    session.setAttribute("search", searchName);
    session.setAttribute("index", startIndex);
    session.setAttribute("success", success);
    session.setAttribute("danger", danger);

    try {
      pagination.setPerPage(entitiesPerPage);
    } catch (PageException e) {
      logger.error(e.getMessage());
      session.setAttribute("stackTrace", e.getMessage());
      response.sendError(ErrorCode.SERVER_ERROR.getErrorCode(), e.getMessage());
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
      session.setAttribute("stackTrace", e.getMessage());
      response.sendError(ErrorCode.SERVER_ERROR.getErrorCode(), e.getMessage());
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
