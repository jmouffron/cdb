package com.excilys.cdb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.model.Entity;
import com.excilys.cdb.service.ServiceFactory;
import com.excilys.cdb.controller.Controller;

/**
 * It is the app main entrypoint and Spring Container initializer.
 * 
 * @author excilys
 * 
 */
public class App {
  
  public static void main(String[] args) {
    ApplicationContext cliCtx = new ClassPathXmlApplicationContext("beans.xml");
    
    ServiceFactory factory = cliCtx.getBean(ServiceFactory.class);
    
		Controller<Entity> controller = Controller.getController(factory);
		controller.start();
		
		((ConfigurableApplicationContext) cliCtx).close();
		System.exit(0);
		
  }
}
