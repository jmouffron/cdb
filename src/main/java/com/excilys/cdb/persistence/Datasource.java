package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author excilys
 *
 */
public class Datasource {
	private static String url;
	private static String driver;
	private static String username;
	private static String password;
	private static Logger log;

	static {
	  log = LoggerFactory.getLogger(Datasource.class);
	  
		Properties props = new Properties();
		try {
			props.load( new FileInputStream( "/home/excilys/eclipse-workspace/cdb/src/main/webapp/resource/jdbc.properties" ) );
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
		  log.error(e.getMessage());
		}
		
		driver = props.getProperty("jdbc.driver");
		url = props.getProperty("jdbc.url");
		username = props.getProperty("jdbc.username");
		password = props.getProperty("jdbc.password");

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
		  log.error(e.getMessage());
		}
	}

	/**
	 * A private Constructor to never allow instantiation
	 */
	private Datasource() { }

	/**
	 * A static way to get a connection to the database
	 * 
	 * @return Connection
	 */
	public static Optional<Connection> getConnection() {
	  Connection result = null;
	  
		try {
		  result = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
		  log.error(e.getMessage());
		}
		
		return Optional.ofNullable(result);
	}

}
