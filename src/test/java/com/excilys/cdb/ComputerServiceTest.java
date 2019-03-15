package com.excilys.cdb;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IService;
import com.excilys.cdb.service.ServiceFactory;

class ComputerServiceTest {
	Logger log = LoggerFactory.getLogger(ComputerServiceTest.class);
	
	IService<Computer> service;
	
	@BeforeAll
	void setUp() {
		try {
			this.service = (IService<Computer>) ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
	void tearDown() {
		this.service = null;
	}
	
	@Test
	void givenGoodInput_whenCreateUser_thenSuceed() {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		try {
			assertTrue(this.service.create(computerDummy));
		} catch (BadInputException e) {
			fail("BadInputException thrown with good inputs.");
		}
	}
	
	@Test
	void givenBadInput_whenCreateUser_thenFail() {
		Company badCompanyDummy = new Company(-1L, null);
		LocalDateTime dateTime = LocalDateTime.MIN;
		Timestamp badIntroduced = Timestamp.valueOf(dateTime);
		Timestamp badDiscontinued = Timestamp.valueOf(dateTime.minusYears(3));
		
		Computer badComputerDummy = new Computer.ComputerBuilder()
				.setId(-1L)
				.setName(null)
				.setIntroduced(badIntroduced)
				.setDiscontinued(badDiscontinued)
				.setCompany(badCompanyDummy)
				.build();
		  
		try {
			assertFalse(this.service.create(badComputerDummy));
			fail("BadInputException non thrown with bad inputs.");
		} catch (BadInputException e) {
			log.debug("BadInputException launched!");
		}
	}
	
	@Test
	void givenGoodInput_whenUpdateUser_thenSuceed() {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		  
		try {
			assertTrue(this.service.updateById(computerDummy.getId(), computerDummy));
		} catch (BadInputException e) {
			log.debug("BadInputException launched!");
			fail("BadInputException thrown with good inputs.");
		}
	}
	
	@Test
	void givenBadInput_whenUpdateUser_thenFail() {
		Company badCompanyDummy = new Company(-1L, null);
		LocalDateTime dateTime = LocalDateTime.MIN;
		Timestamp badIntroduced = Timestamp.valueOf(dateTime);
		Timestamp badDiscontinued = Timestamp.valueOf(dateTime.minusYears(3));
		
		Computer badComputerDummy = new Computer.ComputerBuilder()
				.setId(-1L)
				.setName(null)
				.setIntroduced(badIntroduced)
				.setDiscontinued(badDiscontinued)
				.setCompany(badCompanyDummy)
				.build();
		  
		try {
			assertFalse(this.service.updateById(badComputerDummy.getId(), badComputerDummy));
			fail("BadInputException non thrown with bad inputs.");
		} catch (BadInputException e) {
			log.debug("BadInputException launched!");
		}
	}
	
	@Test
	void givenGoodInput_whenDeleteUser_thenSuceed() {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		
		try {
			this.service.create(computerDummy);
			assertTrue(this.service.deleteById(computerDummy.getId()));
		} catch (BadInputException e) {
			log.debug("Delete Succeed BadInputException launched!");
			fail("BadInputException thrown with good inputs.");
		}
	}
	
	@Test
	void givenBadInput_whenDeleteUser_thenFail() {
		Company badCompanyDummy = new Company(-1L, null);
		LocalDateTime dateTime = LocalDateTime.MIN;
		Timestamp badIntroduced = Timestamp.valueOf(dateTime);
		Timestamp badDiscontinued = Timestamp.valueOf(dateTime.minusYears(3));
		
		Computer badComputerDummy = new Computer.ComputerBuilder()
				.setId(-1L)
				.setName(null)
				.setIntroduced(badIntroduced)
				.setDiscontinued(badDiscontinued)
				.setCompany(badCompanyDummy)
				.build();
		  
		try {
			this.service.create(badComputerDummy);
			assertFalse(this.service.deleteById(badComputerDummy.getId()));
			fail("BadInputException non thrown with bad inputs.");
		} catch (BadInputException e) {
			log.debug("Delete Fail BadInputException launched!");
		}
	}

}
