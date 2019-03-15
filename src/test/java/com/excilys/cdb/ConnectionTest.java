package com.excilys.cdb;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.persistence.Datasource;

class ConnectionTest {
	
	String badUrl = "jdac:psql://localboast:3302/nodatabase?useSSL=true", 
			badDriver ="com.psql.odbc.Dummy" , 
			badUsername = "fakeRoot", 
			badPassword = "azertyAzerty",
			badPropsPath = "src/java/resource/odbc.properties";
	
	String goodUrl = "jdbc:mysql://localhost:3306/computer-database-db?useSSL=false&autoReconnect=true",
			goodDriver = "com.mysql.jdbc.cj.Driver", 
			goodUsername = "admincdb", 
			goodPassword = "qwerty1234", 
			goodPropsPath = "src/java/resource/jdbc.properties";
	
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
		} catch (ClassNotFoundException e) {}
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
		} catch (SQLException e) {}
	}
}
