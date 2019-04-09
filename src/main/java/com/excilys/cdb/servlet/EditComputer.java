package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    "/EditComputer/", "/editComputer/", "/editcomputer/", "/Editcomputer/" })
public class EditComputer extends HttpServlet {

  private static final long serialVersionUID = -7908163785589368761L;
  private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
  
  private ComputerService computerService;
  private CompanyService companyService;
  
  static ApplicationContext springCtx;
  private static ServiceFactory factory;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    springCtx = DashBoard.springCtx;
    factory = (ServiceFactory) springCtx.getBean("doubleServiceFactory");
    this.computerService = factory.getComputerService();
    this.companyService = factory.getCompanyService();
    logger.info("Initialization of EditComputer Servlet");
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    
    String computerId = request.getParameter("computerId");
    
    if ( "".equals(computerId) ) {
      logger.debug("Empty or null id for computer to be searched.");
      response.sendRedirect("/dashboard");
    }

    ComputerDTO computer = null;
    try {
      computer = this.computerService.getOneById( Long.parseLong(computerId) ).get();
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


    session.setAttribute("companies", companies);
    session.setAttribute("computer", computer);
    
    logger.info(getServletName() + " has been called with URL: " + request.getRequestURI());
    this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    
    String computerId = request.getParameter("computerId");
    String computerName = request.getParameter("computerName");
    String computerIntro = request.getParameter("computerIntro");
    String computerDisco = request.getParameter("computerDisco");
    String computerComp = request.getParameter("computerComp");
    String companyId = request.getParameter("companyId");
    
    List<String> computerData = new ArrayList<>(Arrays.asList(computerId, computerName, computerIntro, computerDisco, computerComp));
    computerData.forEach( param -> {
      if ( "".equals(param) ) {
        logger.debug("Empty or null id for computer to be searched.");
        
        try {
          response.sendRedirect("/dashboard");
        } catch (IOException e) {
          logger.debug(e.getMessage());
          session.setAttribute("stackTrace", e.getMessage());
        }
      }
    });

    ComputerDTO computerInDb = null;
    try {
      computerInDb = this.computerService.getOneById( Long.parseLong(computerId) ).get();
    } catch (BadInputException e) {
      logger.error(e.getMessage());
      session.setAttribute("stackTrace", e.getMessage());
      response.sendRedirect("/dashboard");
    }
    
    ComputerDTO computerToSend = new ComputerDTO.ComputerDTOBuilder()
        .setId(Long.parseLong(computerId))
        .setCompanyId(Long.parseLong(companyId))
        .setCompanyName(computerComp)
        .setIntroduced(computerIntro)
        .setDiscontinued(computerDisco)
        .setName(computerName)
        .build();
    
    if( computerToSend.getId() != computerInDb.getId() ) {
      logger.error("Wrong edit and change of the id!");
      session.setAttribute("stackTrace", "Wrong edit and change of the id!");
      response.sendRedirect("/dashboard");
    }
    
    boolean isUpdated = false;
    try {
      isUpdated = this.computerService.updateById(computerToSend);
    } catch (ServiceException e) {
      logger.error(e.getMessage());
      session.setAttribute("stackTrace", e.getMessage());
    }

    if (isUpdated) {
      session.setAttribute("success", "Computer " + computerToSend.getName() + " successfully updated!");
    }else {
      session.setAttribute("danger", "Computer " + computerToSend.getName() + " not updated!");
    }
    
    logger.info(getServletName() + " has been called with URL: " + request.getRequestURI());
    this.getServletContext().getRequestDispatcher("/dashboard").forward(request, response);
  }

}
