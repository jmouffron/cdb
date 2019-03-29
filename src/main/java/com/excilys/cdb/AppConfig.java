package com.excilys.cdb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.persistence.Datasource;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//@Configuration
public class AppConfig {
  static ApplicationContext springCtx = new ClassPathXmlApplicationContext("/spring/beans.xml");
  
  @Bean
  public HikariConfig getHikariConfig() {
    return new HikariConfig("/datasource.properties");
  }
  
  @Bean
  public HikariDataSource getHikariDataSource() {
    return new HikariDataSource(getHikariConfig());
  }
  
  @Bean(destroyMethod = "close")
  public Datasource getDatasource() {
    return new Datasource(getHikariDataSource());
  }
  
  @Bean
  public DaoComputer getDaoComputer() {
    return new DaoComputer();
  }
  @Bean
  public DaoCompany getDaoCompany() {
    return new DaoCompany();
  }
  
  @Bean
  public ComputerService getComputerService() {
    return new ComputerService(getDaoComputer());
  }
  
  @Bean
  public CompanyService getCompanyService() {
    return new CompanyService(getDaoCompany());
  }
}
