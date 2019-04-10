package com.excilys.cdb;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.view.IndexPagination;
import com.excilys.cdb.view.Pagination;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.cdb", includeFilters = @Filter(type = FilterType.REGEX, pattern = "com.excilys.cdb.servlet"))
public class AppConfig {

  @Bean
  public HikariConfig HikariConfig() {
    return new HikariConfig("/datasource.properties");
  }

  @Bean
  public HikariDataSource HikariDataSource() {
    return new HikariDataSource(HikariConfig());
  }

  @Bean
  public JdbcTemplate JdbcTemplate() {
    JdbcTemplate jdbc = new JdbcTemplate();
    jdbc.setDataSource(HikariDataSource());
    return jdbc;
  }

  @Bean
  public DaoComputer DaoComputer() {
    return new DaoComputer(JdbcTemplate());
  }

  @Bean
  public DaoCompany DaoCompany() {
    return new DaoCompany(JdbcTemplate());
  }

  @Bean
  public ComputerService ComputerService() {
    return new ComputerService(DaoComputer());
  }

  @Bean
  public CompanyService CompanyService() {
    return new CompanyService(DaoCompany());
  }
  
  @Bean
  @RequestScope
  public Pagination Pagination() {
    return new Pagination(ComputerService(), 0, IndexPagination.IDX_10);
  }
}
