package com.excilys.cdb.servlet;

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

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceFactory;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet(description = "An Update endpoint for a CRUD API for computer entities", urlPatterns = {
    "/EditComputer/", "/editComputer" })
public class EditComputer extends HttpServlet {

  private static final long serialVersionUID = -7908163785589368761L;
  private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
  private ComputerService computerService;
  private CompanyService companyService;

  /**
   * Default constructor.
   */
  public EditComputer(ServiceFactory factory) {
    super();
    this.computerService = factory.getComputerService();
    this.companyService = factory.getCompanyService();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String computerName = request.getParameter("computerName");
    
    if ( "".equals(computerName) ) {
      logger.debug("Empty or null name for computer to be searched.");
      response.sendRedirect("/dashboard");
    }

    ComputerDTO computer = null;
    try {
      computer = this.computerService.getOneByName(computerName).get();
    } catch (BadInputException e) {
      logger.error(e.getMessage());
      response.sendRedirect("/dashboard");
    }
    
    List<CompanyDTO> companies = null;
    try {
      companies = this.companyService.getAll().get();
    } catch (ServiceException e) {
      logger.error(e.getMessage());
      response.sendRedirect("/dashboard");
    }

    HttpSession session = request.getSession(true);
    session.setAttribute("companies", companies);
    session.setAttribute("computer", computer);
    
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    
    String computerName = request.getParameter("computerName");
    
    if (computerName == null) {
      logger.debug("Empty or null name for computer to be searched.");
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), "No Computer Name chosen for this request!");
    }

    ComputerDTO computer = null;
    try {
      computer = this.computerService.getOneByName(computerName).get();
    } catch (BadInputException e) {
      logger.debug(e.getMessage());
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), e.getMessage());
    }

    boolean isUpdated = false;
    try {
      isUpdated = this.computerService.updateById(computer);
    } catch ( ServiceException e) {
      logger.error(e.getMessage());
    }

    if (isUpdated) {
      session.setAttribute("success", "Computer " + computer.getName() + " successfully updated!");
    }else {
      session.setAttribute("danger", "Computer " + computer.getName() + " not updated!");
    }
    
    this.getServletContext().getRequestDispatcher("/dashboard").forward(request, response);
  }

}
