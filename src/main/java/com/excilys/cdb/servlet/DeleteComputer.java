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
import com.excilys.cdb.model.Computer;
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
    String computerName = request.getParameter("computerName");
    if (computerName == null) {
      computerName = "";
    }

    Computer computer = null;
    try {
      computer = (Computer) this.service.getOneByName(computerName).get();
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
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String computerName = request.getParameter("computerName");
    if (computerName == null) {
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "No correct computer name found!");
      this.getServletContext().getRequestDispatcher("/views/deleteComputer.jsp").forward(request, response);
    }

    Computer computer = null;
    try {
      computer = (Computer) this.service.getOneByName(computerName).get();
    } catch (BadInputException e) {
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
    }

    if (computer == null) {
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null computer found!");

      this.getServletContext().getRequestDispatcher("/views/deleteComputer.jsp").forward(request, response);
    }
    boolean isDeleted = false;
    try {
      isDeleted = this.service.deleteById(computer.getId());
    } catch (BadInputException e) {
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Couldn't delete computer!" + e.getMessage());
      this.getServletContext().getRequestDispatcher("/views/deleteComputer.jsp").forward(request, response);
    }
    if (isDeleted) {
      HttpSession session = request.getSession(true);
      session.setAttribute("success", "The computer " + computer.getName() + " has been correctly deleted!");

      this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }

  }

}
