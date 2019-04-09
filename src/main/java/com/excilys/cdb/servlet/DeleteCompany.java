package com.excilys.cdb.servlet;

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
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ServiceFactory;

/**
 * Servlet implementation class DeleteCompany
 */
@WebServlet(description = "A Delete endpoint in a CRUD API for company entities", urlPatterns = { "/DeleteCompany",
    "/deleteCompany" })
public class DeleteCompany extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(DeleteCompany.class);
  private CompanyService service;

  static ApplicationContext springCtx;
  private static ServiceFactory factory;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    springCtx = DashBoard.springCtx;
    factory = (ServiceFactory) springCtx.getBean("companyServiceFactory");
    this.service = factory.getCompanyService();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);

    String companyName = request.getParameter("companyName");
    
    if (companyName == null) {
      companyName = "";
    }

    CompanyDTO company = null;
    try {
      company = this.service.getOneByName(companyName).get();
    } catch (BadInputException e) {
      logger.error(e.getMessage());
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
    }

    if (company == null) {
      logger.error("Null company found!");
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null company found!");
    } else {
      session.setAttribute("company", company);
      this.getServletContext().getRequestDispatcher("/deleteCompany").forward(request, response);
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
    String companyName = request.getParameter("companyName");
    
    if (companyName == null) {
      logger.error("No correct company name found!");
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "No correct company name found!");
      this.getServletContext().getRequestDispatcher("/views/deleteCompany.jsp").forward(request, response);
    }

    CompanyDTO company = null;
    try {
      company = this.service.getOneByName(companyName).get();
    } catch (BadInputException e) {
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), e.getMessage());
    }

    if (company == null) {
      logger.error("Null company found!");
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Null company found!");
      this.getServletContext().getRequestDispatcher("/views/deleteCompany.jsp").forward(request, response);
    }
    
    boolean isDeleted = false;
    
    try {
      isDeleted = this.service.deleteById(company.getId());
    } catch (ServiceException e) {
      logger.error(e.getMessage());
      response.sendError(ErrorCode.FILE_NOT_FOUND.getErrorCode(), "Couldn't delete company!" + e.getMessage());
      this.getServletContext().getRequestDispatcher("/views/deleteCompany.jsp").forward(request, response);
    }
    
    if (isDeleted) {
      session.setAttribute("success", "The company " + company.getName() + " has been correctly deleted!");
      session.setAttribute("danger", danger);
    }else {
      session.setAttribute("success", success);
      session.setAttribute("danger", "The company" + company.getName() + "couldn't be deleted!");
    }
    this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
  }

}
