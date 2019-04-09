package com.excilys.cdb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.model.Entity;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.service.ServiceFactory;
import com.excilys.cdb.controller.Controller;

/**
 * It is the app main entrypoint and Spring Container initializer.
 * 
 * @author excilys
 * 
 */
@ComponentScan("com.excilys.cdb")
public class App {
  
  public static void main(String[] args) {
    AnnotationConfigApplicationContext cliCtx = new AnnotationConfigApplicationContext();
    cliCtx.register(App.class);
    cliCtx.refresh();
    
    ServiceFactory factory = cliCtx.getBean(ServiceFactory.class);
    
		Controller<Entity> controller = Controller.getController(factory);
		controller.start();
		
		((ConfigurableApplicationContext) cliCtx).close();
		System.exit(0);
		
  }
}
