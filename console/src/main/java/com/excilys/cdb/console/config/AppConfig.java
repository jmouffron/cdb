package com.excilys.cdb.console.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.web.context.annotation.RequestScope;

import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.view.IndexPagination;
import com.excilys.cdb.service.view.Pagination;

@Configuration
@PropertySource("classpath:datasource.properties")
@EnableTransactionManagement
@ComponentScan(basePackages = "com.excilys.cdb", includeFilters = @Filter(type = FilterType.REGEX, pattern = "com.excilys.cdb.servlet"))
public class AppConfig implements TransactionManagementConfigurer{

	Environment env;

	public AppConfig(Environment env) {
		this.env = env;
	}

	@Bean(destroyMethod = "close")
	public DataSource Datasource() {
		return DataSourceBuilder.create().driverClassName(env.getProperty("driverClassName"))
				.url(env.getProperty("jdbcUrl")).username(env.getProperty("login"))
				.password(env.getProperty("password")).build();
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

	@Bean
	public LocalSessionFactoryBean SessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(Datasource());
		sessionFactory.setMappingResources(new String[] {"/hbm.cfg.xml"});
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
    private Properties hibernateProperties() {
    	Properties hbmProps = new Properties();
    	try {
			hbmProps.load(getClass().getClassLoader().getResourceAsStream("datasource.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return hbmProps;
	}

	@Bean
    public HibernateTransactionManager getTransactionManager() {
        return new HibernateTransactionManager(SessionFactory().getObject());
    }

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return getTransactionManager();
	}
}
