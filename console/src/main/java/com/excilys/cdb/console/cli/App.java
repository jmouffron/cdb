package com.excilys.cdb.console.cli;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * It is the app main entrypoint and Spring Container initializer.
 * 
 * @author excilys
 * 
 */
@ComponentScan({ "com.excilys.cdb.service", "com.excilys.cdb.persistence", "com.excilys.cdb.cli" })
@PropertySource("classpath:datasource.properties")
public class App {
	@Value("${driverClassName}")
	String driver;
	@Value("${jdbcUrl}")
	String url;
	@Value("${login}")
	String user;
	@Value("${password}")
	String pass;

	@Bean(destroyMethod = "close")
	public DataSource Datasource() {
		return DataSourceBuilder.create().driverClassName(driver).url(url).username(user).password(pass).build();
	}

	@Bean
	public JdbcTemplate JdbcTemplate() {
		JdbcTemplate jdbc = new JdbcTemplate();
		jdbc.setDataSource(Datasource());
		return jdbc;
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext cliCtx = new AnnotationConfigApplicationContext();
		cliCtx.register(App.class);
		cliCtx.refresh();

		Controller controller = cliCtx.getBean(Controller.class);
		controller.start();

		((ConfigurableApplicationContext) cliCtx).close();
		System.exit(0);

	}
}
