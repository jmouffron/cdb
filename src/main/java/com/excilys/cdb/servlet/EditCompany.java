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
@WebServlet(description = "An Update endpoint for a CRUD API for company entities", urlPatterns = {
    "/EditCompany/", "/editCompany", "/editcompany", "/Editcompany" })
public class EditCompany extends HttpServlet {

  private static final long serialVersionUID = -7908163785589368761L;
  private static final Logger logger = LoggerFactory.getLogger(AddComputer.class);
  private CompanyService companyService;
  private ApplicationContext springCtx;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.springCtx = new ClassPathXmlApplicationContext("/spring/beans.xml");
    ServiceFactory factory = (ServiceFactory) springCtx.getBean("companyServiceFactory");
    this.companyService = factory.getCompanyService();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    String companyName = request.getParameter("companyName");
    
    if ("".equals(companyName) ) {
      logger.debug("Empty or null name for company to be searched.");
      response.sendRedirect("/dashboard");
    }

    CompanyDTO company = null;
    try {
      company = this.companyService.getOneByName(companyName).get();
    } catch (BadInputException e) {
      logger.error(e.getMessage());
      response.sendRedirect("/dashboard");
    }
   
    session.setAttribute("company", company);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    
    String companyName = request.getParameter("companyName");
    
    if (companyName == null) {
      logger.debug("Empty or null name for company to be searched.");
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), "No Computer Name chosen for this request!");
    }

    CompanyDTO company = null;
    try {
      company = this.companyService.getOneByName(companyName).get();
    } catch (BadInputException e) {
      logger.debug(e.getMessage());
      response.sendError(ErrorCode.FORBIDDEN_REQUEST.getErrorCode(), e.getMessage());
    }

    boolean isUpdated = false;
    try {
      isUpdated = this.companyService.updateById(company);
    } catch ( ServiceException e ) {
      logger.error(e.getMessage());
    }

    if (isUpdated) {
      session.setAttribute("success", "Company " + company.getName() + " successfully updated!");
    }else {
      session.setAttribute("danger", "Company " + company.getName() + " not updated!");
    }
    
    this.getServletContext().getRequestDispatcher("/dashboard").forward(request, response);
  }

}
