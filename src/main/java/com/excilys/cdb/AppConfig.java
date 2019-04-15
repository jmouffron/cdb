package com.excilys.cdb;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.web.context.annotation.RequestScope;

import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.view.IndexPagination;
import com.excilys.cdb.view.Pagination;

@Configuration
@PropertySource("classpath:datasource.properties")
@ComponentScan(
		basePackages = "com.excilys.cdb", 
		includeFilters = @Filter(type = FilterType.REGEX, pattern = "com.excilys.cdb.servlet")
)
public class AppConfig {
  
  @Value( "${driverClassName}" )
  String driver;
  @Value( "${jdbcUrl}" )
  String url;
  @Value( "${login}" )
  String user;
  @Value( "${password}" )
  String pass;
  
  @Bean(destroyMethod="close")
  public DataSource Datasource() {
    return DataSourceBuilder.create()
        .driverClassName(driver)
        .url(url)
        .username(user)
        .password(pass)
        .build();
  }

  @Bean
  public JdbcTemplate JdbcTemplate() {
    JdbcTemplate jdbc = new JdbcTemplate();
    jdbc.setDataSource(Datasource());
    return jdbc;
  }
  
  @Bean
  @RequestScope
  public Pagination pagination(ComputerService service) {
    return new Pagination(service, 0, IndexPagination.IDX_10);
  }
}
