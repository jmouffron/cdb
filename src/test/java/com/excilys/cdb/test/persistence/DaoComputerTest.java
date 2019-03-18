package com.excilys.cdb.test.persistence;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoInstance;
import com.excilys.cdb.persistence.Datasource;
import com.excilys.cdb.service.IService;
import com.excilys.cdb.service.ServiceFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DaoComputerTest {

	private static final Logger log = LoggerFactory.getLogger(DaoComputerTest.class);

	private static IService<Computer> service;
	
	@BeforeAll
	static void setUp() {
		try {
			service = (IService<Computer>) ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
		} catch (ServiceException e) {
			log.debug(e.getMessage());
			fail(e.getMessage());
		}
	}
	
	@BeforeAll
	static void tearDown() {
		service = null;
	}
	
	@Test
	void givenGoodInput_whenCreateUser_thenSuceed() {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		
		DaoInstance<Computer> daoTested = service.getDao();
		
		assertTrue(daoTested.create(computerDummy));
     
        List<Computer> computers = daoTested.getAll();
        int computerSize = computers.size() - 1;
        
		assertEquals(computerDummy, computers.get(computerSize));
	}
	
	@Test
	void givenBadInput_whenCreateUser_thenFail() {
		Long id = -100L;
		String name = null;
		LocalDateTime dateTime = LocalDateTime.now().minusYears(1000);
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
		
		DaoInstance<Computer> daoTested = service.getDao();
		
		assertFalse(daoTested.create(computerDummy));
		
		List<Computer> computers = daoTested.getAll();
        int computerSize = computers.size() - 1;

        assertNotEquals(computerDummy.getName(), computers.get(computerSize).getName());
	}
}
