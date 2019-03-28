package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
  private ApplicationContext springCtx;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.springCtx = new ClassPathXmlApplicationContext("/spring/beans.xml");
    ServiceFactory factory = (ServiceFactory) springCtx.getBean("doubleServiceFactory");
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
