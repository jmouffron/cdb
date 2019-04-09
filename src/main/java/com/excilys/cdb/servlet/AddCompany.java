package com.excilys.cdb.servlet;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceFactory;

import java.io.IOException;
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

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(description = "A Create endpoint in a CRUD API for company entities", urlPatterns = { "/AddCompany",
    "/addCompany", "/addcompany", "/Addcompany" })
public class AddCompany extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(AddCompany.class);
  
  private CompanyService companyService;
  
  public AddCompany() {}
  
  public AddCompany(CompanyService compService) {
    this.companyService = compService;
    logger.info("Initialisation de la servlet " + getServletName() );
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);

    String companyName = request.getParameter("companyName");
    companyName = (companyName == null || companyName.equals("")) ? "" : companyName.trim();

    session.setAttribute("companyName",companyName);

    logger.info(getServletName() + " has been called with URL: " + request.getRequestURI());
    this.getServletContext().getRequestDispatcher("/views/addCompany.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    
    String companyName = request.getParameter("companyName");
    String companyId = request.getParameter("companyId");

    if (companyName == null || companyName.equals("") ) {
      logger.debug("Empty or null name for company to be searched.");
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), "No Company Name chosen for this request!");
    }

    CompanyDTO companyDto = new CompanyDTO(Long.parseLong(companyId),companyName);

    boolean isCreated = false;
    try {
      isCreated = this.companyService.create(companyDto);
    } catch (ServiceException | DaoException e) {
      logger.debug(e.getMessage());
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), e.getMessage());
    }

    if (isCreated) { 
      session.setAttribute("success", "Company " + companyDto.getName() + " successfully created!"); 
    }else {
      session.setAttribute("danger", "Company " + companyDto.getName() + " couldn't be created!"); 
    }
    
    logger.info(getServletName() + " has been called with URL: " + request.getRequestURI());
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
  }

}
