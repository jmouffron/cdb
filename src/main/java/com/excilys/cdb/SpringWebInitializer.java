package com.excilys.cdb;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringWebInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
//    XmlWebApplicationContext context = new XmlWebApplicationContext();
//    context.setConfigLocation("/spring/dispatcher-config.xml");
//    
//    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
//
//    dispatcher.setLoadOnStartup(1);
//    dispatcher.addMapping("/");
  }

}
