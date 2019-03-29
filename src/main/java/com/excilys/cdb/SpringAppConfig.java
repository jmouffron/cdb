package com.excilys.cdb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.service.ComputerService;

public class SpringApp {

  private static Logger logger = LoggerFactory.getLogger(SpringApp.class);

  public static void main(String[] args) {
    ApplicationContext springCtx = new ClassPathXmlApplicationContext("spring/beans.xml");

    ComputerService computerService = springCtx.getBean(ComputerService.class);
    
    List<ComputerDTO> computers = null;
    try {
      computers = computerService.orderBy("pc_name", Boolean.TRUE ).get();
    } catch (ServiceException e) {
      logger .error(e.getMessage());
    }
    
    computers.forEach(System.out::println);
    
    ((ConfigurableApplicationContext) springCtx).close();
    System.exit(0);
  }

}
