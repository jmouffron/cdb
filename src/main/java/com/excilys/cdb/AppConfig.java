package com.excilys.cdb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
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
}
