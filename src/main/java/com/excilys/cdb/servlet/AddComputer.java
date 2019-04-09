package com.excilys.cdb.servlet;

import com.excilys.cdb.AppConfig;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceFactory;

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
import org.springframework.context.ApplicationContext;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet(description = "A Create endpoint in a CRUD API for computer entities", urlPatterns = { "/AddComputer",
    "/addComputer", "/addcomputer", "/Addcomputer" })
public class AddComputer extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
  
  private ComputerService computerService;
  private CompanyService companyService;

  public AddComputer() {}
  
  public AddComputer(ComputerService pcService, CompanyService compService) {
    this.computerService = pcService;
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

    String computerName = request.getParameter("computerName");
    computerName = (computerName == null || computerName.equals("")) ? "" : computerName.trim();

    List<CompanyDTO> companies = null;

    try {
      companies = this.companyService.orderBy("id", Boolean.TRUE).get();
    } catch (ServiceException | DaoException e) {
      logger.error(e.getMessage());
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), e.getMessage());
    }

    session.setAttribute("companies", companies);
    
    logger.info(getServletName() + " has been called with URL: " + request.getRequestURI());
    this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    
    String computerName = request.getParameter("computerName");
    String introduced = request.getParameter("introduced");
    String discontinued = request.getParameter("discontinued");
    String companyName = request.getParameter("companyName");

    if (computerName == null || computerName.equals("") ) {
      logger.debug("Empty or null name for computer to be searched.");
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), "No Computer Name chosen for this request!");
    }

    ComputerDTO computerDto = new ComputerDTO.ComputerDTOBuilder().setName(computerName).setIntroduced(introduced)
        .setCompanyName(companyName).setDiscontinued(discontinued).build();

    boolean isCreated = false;
    try {
      isCreated = this.computerService.create(computerDto);
    } catch (ServiceException | DaoException e) {
      logger.debug(e.getMessage());
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), e.getMessage());
    }

    if (isCreated) { 
      session.setAttribute("success", "Computer " + computerDto.getName() + " successfully created!"); 
    }else {
      session.setAttribute("danger", "Computer " + computerDto.getName() + " couldn't be created!"); 
    }
    
    logger.info(getServletName() + " has been called with URL: " + request.getRequestURI());
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
  }

}
