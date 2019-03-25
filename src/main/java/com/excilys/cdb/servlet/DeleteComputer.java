package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceFactory;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet(description = "A Delete endpoint in a CRUD API for computer entities", urlPatterns = { "/DeleteComputer",
    "/deleteComputer" })
public class DeleteComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(DeleteComputer.class);
  private ComputerService service;

  /**
   * Default constructor.
   */
  public DeleteComputer() {
    super();
    this.service = ServiceFactory.getComputerService();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);

    String computerName = request.getParameter("computerName");
    
    if (computerName == null) {
      computerName = "";
    }

    ComputerDTO computer = null;
    try {
      computer = this.service.getOneByName(computerName).get();
    } catch (BadInputException e) {
      logger.error(e.getMessage());
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
    }

    if (computer == null) {
      logger.error("Null computer found!");
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null computer found!");
    } else {
      session.setAttribute("computer", computer);
      this.getServletContext().getRequestDispatcher("/deleteComputer").forward(request, response);
    }
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
    String computerName = request.getParameter("computerName");
    
    if (computerName == null) {
      logger.error("No correct computer name found!");
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "No correct computer name found!");
      this.getServletContext().getRequestDispatcher("/views/deleteComputer.jsp").forward(request, response);
    }

    ComputerDTO computer = null;
    try {
      computer = this.service.getOneByName(computerName).get();
    } catch (BadInputException e) {
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
    }

    if (computer == null) {
      logger.error("Null computer found!");
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null computer found!");
      this.getServletContext().getRequestDispatcher("/views/deleteComputer.jsp").forward(request, response);
    }
    
    boolean isDeleted = false;
    
    try {
      isDeleted = this.service.deleteById(computer.getId());
    } catch (ServiceException e) {
      logger.error(e.getMessage());
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Couldn't delete computer!" + e.getMessage());
      this.getServletContext().getRequestDispatcher("/views/deleteComputer.jsp").forward(request, response);
    }
    
    if (isDeleted) {
      session.setAttribute("success", "The computer " + computer.getName() + " has been correctly deleted!");
      session.setAttribute("danger", danger);
    }else {
      session.setAttribute("success", success);
      session.setAttribute("danger", "The computer" + computer.getName() + "couldn't be deleted!");
    }
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
  }

}
