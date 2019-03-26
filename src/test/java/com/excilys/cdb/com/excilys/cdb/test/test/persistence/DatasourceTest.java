package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.Test;

class DatasourceTest {
	
	String badUrl = "jdac:psql://localboast:3302/nodatabase?useSSL=true"; 
	String badDriver = "com.psql.odbc.Dummy" ;
	String badUsername = "fakeRoot";
	String badPassword = "azertyAzerty";
	String badPropsPath = "src/java/resource/odbc.properties";
	
	String goodUrl = "jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false";
	String goodDriver = "com.mysql.cj.jdbc.Driver"; 
	String goodUsername = "admincdb"; 
	String goodPassword = "qwerty1234"; 
	String goodPropsPath = "src/main/java/resource/jdbc.properties";
	
	@Test
	void givenGoodFilePath_whenLoadingProperties_thenSuceed() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(goodPropsPath));
		} catch (IOException e) {
			fail("Good propsFilePath failed !");
		}
		
		assertEquals(this.goodDriver, props.getProperty("jdbc.driver"));
		assertEquals(this.goodUrl, props.getProperty("jdbc.url"));
		assertEquals(this.goodUsername, props.getProperty("jdbc.username"));
		assertEquals(this.goodPassword, props.getProperty("jdbc.password"));
	}
	
	@Test
	void givenBadDriver_whenClassImport_thenFail() {
		try {
			Class.forName(badDriver);
			fail("Bad driver launched");
		} catch (ClassNotFoundException e) { }
	}
	
	@Test
	void givenGoodDriver_whenClassImport_thenSuceed() {
		try {
			Class.forName(goodDriver);
		} catch (ClassNotFoundException e) {
			fail("Good driver failed.");
		}
	}
	
	@Test
	void givenGoodConfig_whenConnect_thenSuceed() {
		try {
			Class.forName(goodDriver);
		} catch (ClassNotFoundException e) {
			fail("Good driver failed.");
		}
		try {
			assertNotNull(DriverManager.getConnection(goodUrl, goodUsername, goodPassword));
		} catch (SQLException e) {
			fail("Good config failed.");
		}
	}
	
	@Test
	void givenBadConfig_whenConnect_thenSuceed() {
		try {
			Class.forName(goodDriver);
		} catch (ClassNotFoundException e) {
			fail("Good driver failed.");
		}
		try {
			assertNotNull(DriverManager.getConnection(badUrl, badUsername, badPassword));
			fail("Bad config suceeded ?!");
		} catch (SQLException e) { }
	}
}
