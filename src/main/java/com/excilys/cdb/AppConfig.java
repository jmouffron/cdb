package com.excilys.cdb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.persistence.Datasource;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys.cdb"})
public class AppConfig {
  public static ApplicationContext springCtx = new ClassPathXmlApplicationContext("/spring/beans.xml");
  
  @Bean
  public HikariConfig HikariConfig() {
    return new HikariConfig("/datasource.properties");
  }
  @Bean
  public HikariDataSource HikariDataSource() {
    return new HikariDataSource(HikariConfig());
  }
  @Bean(destroyMethod = "close")
  public Datasource Datasource() {
    return new Datasource(HikariDataSource());
  }
  
  @Bean
  public DaoComputer DaoComputer() {
    return new DaoComputer(Datasource());
  }
  @Bean
  public DaoCompany DaoCompany() {
    return new DaoCompany(Datasource());
  }
  
  @Bean
  public ComputerService ComputerService() {
    return new ComputerService(DaoComputer());
  }
  
  @Bean
  public CompanyService CompanyService() {
    return new CompanyService(DaoCompany());
  }
}
