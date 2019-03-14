package com.excilys.cdb;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.model.Entity;
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.Datasource;
import com.excilys.cdb.service.IService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {
	
	@Mock
	DaoComputerFactory factory;
	
	//@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	private Connection conn;
	private Logger log = LoggerFactory.getLogger(ConnectionTest.class);

	private IService<Entity> service;
	
	@BeforeEach
	public void getConnection() {
		this.conn = Datasource.getConnection();
	}
	
	@AfterEach
	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			log.debug(e.getMessage());
		}
	}
	
	@BeforeAll
	public void setUp() {
		this.service = Controller.getController().getService();
	}
	
	@BeforeAll
	public void tearDown() {
		this.service = null;
		this.conn = null;
	}
	
	@Test
	public void shouldUpdateWithGoodInput() {

	}
	
	@Test
	public void shouldNotUpdateWithBadInput() {

	}
	
	@Test
	public void shouldCreateWithGoodInput() {

	}
	
	@Test
	public void shouldNotCreateWithBadInput() {

	}
	
	@Test
	public void shouldDeleteWithGoodInput() {

	}
	
	@Test
	public void shouldNotDeleteWithBadInput() {

	}
}
