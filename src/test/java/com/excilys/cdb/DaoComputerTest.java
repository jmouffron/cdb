package com.excilys.cdb;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.controller.Controller;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Entity;
import com.excilys.cdb.persistence.DaoInstance;
import com.excilys.cdb.persistence.Datasource;
import com.excilys.cdb.service.IService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DaoComputerTest {

	private Connection conn;
	private Logger log = LoggerFactory.getLogger(DaoComputerTest.class);

	private IService<Entity> service;
	
	/**
	 * Setup of the connection 
	 */
	@BeforeEach
	void getConnection() {
		this.conn = Datasource.getConnection();
	}
	
	@AfterEach
	void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			log.debug(e.getMessage());
		}
	}
	
	@BeforeAll
	void setUp() {
		this.service = Controller.getController().getService();
	}
	
	@BeforeAll
	void tearDown() {
		this.service = null;
		this.conn = null;
	}
	
	@Test
	void givenGoodInput_whenCreateUser_thenSuceed() {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		
		DaoInstance daoTested = this.service.getDao();
		
		assertTrue(daoTested.create(computerDummy));
     
        List<Computer> computers = daoTested.getAll();
        int computerSize = computers.size() - 1;
        
		assertEquals(computerDummy.getName(), computers.get(computerSize).getName());
	}
	
	@Test
	void givenBadInput_whenCreateUser_thenFail() {
		Long id = -100L;
		String name = null;
		LocalDateTime dateTime = LocalDateTime.MIN.minusYears(1000);
		Timestamp introduced = Timestamp.valueOf(dateTime);
		Timestamp discontinued = Timestamp.valueOf(dateTime.minusHours(3));
		Company companyDummy = new Company(id, "");
		
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(companyDummy)
				.build();
		
		DaoInstance daoTested = this.service.getDao();
		
		assertFalse(daoTested.create(computerDummy));
		
		List<Entity> computers = daoTested.getAll();
        int computerSize = computers.size() - 1;

        assertNotEquals(computerDummy.getName(), computers.get(computerSize).getName());
	}
}
