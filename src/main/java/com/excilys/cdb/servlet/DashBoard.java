package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
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
    "/DashBoard", "/dashBoard", "/", "/home", "/index.html", "/index", "/dashboard", "/Dashboard" })
public class DashBoard extends HttpServlet {
  
  private static final long serialVersionUID = 3558156539176540043L;
  private static final Logger logger = LoggerFactory.getLogger(DashBoard.class);
  
  private Pagination pagination;
  private static Map<String, String> columns;
  
  private ComputerService computerService;

  static ApplicationContext springCtx;
  private static ServiceFactory factory;
  
  static {
    columns = new HashMap<>();

    columns.put("0", "pc_name");
    columns.put("1", "introduced");
    columns.put("2", "discontinued");
    columns.put("3", "cp_name"); 
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    springCtx = new ClassPathXmlApplicationContext("/spring/beans.xml");
    factory = (ServiceFactory) springCtx.getBean("computerServiceFactory");
    this.computerService = factory.getComputerService();
    this.pagination = new Pagination(this.computerService.getAll().get(), 0, IndexPagination.IDX_10);
    logger.info("Initialisation de la servlet DashBoard.");
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
    String selection = request.getParameter("selection");
    String strCache = request.getParameter("cache");
    
    isDesc = (order == null || order.equals("")) ? false : true;
    entitiesPerPage = (perPage == null) ? IndexPagination.IDX_10 : IndexPagination.valueOf(perPage);
    startIndex = (index == null) ? 0 : Integer.parseInt(index);
    page = (newPage == null || newPage.equals("0")) ? 1 : Integer.parseInt(newPage);
    toOrder = (toOrder == null || toOrder.equals("") || !columns.containsKey(toOrder)) ? "pc_name"
        : columns.get(toOrder);
    
    if (searchName == null || searchName.isEmpty()) {
      searchName = "";
      try {
        computers = this.computerService.orderBy(toOrder, isDesc).get();
      } catch (ServiceException e) {
        logger.error(e.getMessage());
        session.setAttribute("stackTrace", e.getMessage());
        response.sendError(ErrorCode.SERVER_ERROR.getErrorCode(), e.getMessage());
      }
    } else {
       try {
        computers = this.computerService.searchByNameOrdered(searchName, toOrder, isDesc).get();
      } catch (ServiceException e) {
        logger.error(e.getMessage());
        session.setAttribute("stackTrace", e.getMessage());
        response.sendError(ErrorCode.SERVER_ERROR.getErrorCode(), e.getMessage());
      }
    }

    session.setAttribute("search", searchName);
    session.setAttribute("index", startIndex);
    session.setAttribute("success", success);
    session.setAttribute("danger", danger);
    session.setAttribute("sel", selection);

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
    HttpSession session = request.getSession(true);
    
    String success = request.getParameter("success");
    String danger = request.getParameter("danger");
    String selection = request.getParameter("selection");
    
    logger.error(selection);

    if ( "".equals(selection) ) {
      logger.error("No correct computer name found!");
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "No correct computer name found!");
      this.getServletContext().getRequestDispatcher("/views/deleteComputer.jsp").forward(request, response);
    }
    List<Long> computerIdsToDelete = Arrays.asList(selection.split(",")).stream().map(Long::parseLong).collect(Collectors.toList());
    
    computerIdsToDelete.forEach( id -> {
      ComputerDTO computer = null;
      try {
        computer = this.computerService.getOneById(id).get();
      } catch (BadInputException e) {
        try {
          response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
        } catch (IOException e1) {
          session.setAttribute("stackTrace", e.getMessage());
        }
      }
  
      if (computer == null) {
        logger.error("Null computer found!");
        try {
          response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null computer found!");
        } catch (IOException e) {
          logger.error(e.getMessage());
          session.setAttribute("stackTrace", e.getMessage());
        }
      }
      
      boolean isDeleted = false;
      
      try {
        isDeleted = this.computerService.deleteById(computer.getId());
      } catch (ServiceException e) {
        logger.error(e.getMessage());
        session.setAttribute("stackTrace", e.getMessage());
        try {
          response.sendRedirect("/dashBoard");
        } catch (IOException e1) {
          logger.error(e.getMessage());
          session.setAttribute("stackTrace", e.getMessage());
        }
      }
      
      if (isDeleted) {
        session.setAttribute("success", "The computer " + computer.getName() + " has been correctly deleted!");
        session.setAttribute("danger", danger);
      }else {
        session.setAttribute("success", success);
        session.setAttribute("danger", "The computer" + computer.getName() + "couldn't be deleted!");
      }
    });
    
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
  }
}
