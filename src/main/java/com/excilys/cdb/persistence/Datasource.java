package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author excilys
 *
 */
public class Datasource {
	private static String url;
	private static String driver;
	private static String username;
	private static String password;

	static {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("src/resource/jdbc.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		driver = props.getProperty("jdbc.driver");
		url = props.getProperty("jdbc.url");
		username = props.getProperty("jdbc.username");
		password = props.getProperty("jdbc.password");

		// Récupération du driver dans le contexte
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * A private Constructor to never allow instantiation
	 */
	private Datasource() {}

	/**
	 * A static way to get a connection to the database
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		// Récupération de la connexion
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
