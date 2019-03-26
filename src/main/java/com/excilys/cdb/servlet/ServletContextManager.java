package com.excilys.cdb.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.persistence.Datasource;

public class ServletContextManager implements ServletContextListener {
  
  private static Logger logger = LoggerFactory.getLogger(ServletContextManager.class);
  
  @Override
  public void contextInitialized(ServletContextEvent sce) {
    logger.debug("Application started!");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
   logger.debug("Application about to shutdown!");
   Datasource.killConnections();
   logger.debug("Application shutdown!");
  }

}
