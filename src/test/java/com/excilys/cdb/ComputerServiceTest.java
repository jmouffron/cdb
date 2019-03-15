package com.excilys.cdb;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
	
	static IService<Computer> service;
	
	@BeforeAll
	static void setUp() {
		try {
			service = (IService<Computer>) ServiceFactory.getService(ServiceFactory.COMPUTER_SERVICE);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@AfterAll
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
		try {
			assertTrue(service.create(computerDummy));
		} catch (BadInputException e) {
			fail("BadInputException thrown with good inputs.");
		}
	}
	
//	@Test
//	void givenBadInput_whenCreateUser_thenFail() {
//		Company badCompanyDummy = new Company(-1L, null);
//		LocalDateTime dateTime = LocalDateTime.MIN;
//		Timestamp badIntroduced = Timestamp.valueOf(dateTime.plusYears(3));
//		Timestamp badDiscontinued = Timestamp.valueOf(dateTime);
//		
//		Computer badComputerDummy = new Computer.ComputerBuilder()
//				.setId(-1L)
//				.setName(null)
//				.setIntroduced(badIntroduced)
//				.setDiscontinued(badDiscontinued)
//				.setCompany(badCompanyDummy)
//				.build();
//		  
//		try {
//			assertFalse(service.create(badComputerDummy));
//			fail("BadInputException non thrown with bad inputs.");
//		} catch (BadInputException e) {
//			log.debug("BadInputException launched!");
//		}
//	}
	
	@Test
	void givenGoodInput_whenUpdateUser_thenSuceed() {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		  
		try {
			assertTrue(service.updateById(computerDummy.getId(), computerDummy));
		} catch (BadInputException e) {
			log.debug("BadInputException launched!");
			fail("BadInputException thrown with good inputs.");
		}
	}
	
//	@Test
//	void givenBadInput_whenUpdateUser_thenFail() {
//		Company badCompanyDummy = new Company(-1L, null);
//		LocalDateTime dateTime = LocalDateTime.MIN;
//		Timestamp badIntroduced = Timestamp.valueOf(dateTime.plusYears(3));
//		Timestamp badDiscontinued = Timestamp.valueOf(dateTime);
//		
//		Computer badComputerDummy = new Computer.ComputerBuilder()
//				.setId(-1L)
//				.setName(null)
//				.setIntroduced(badIntroduced)
//				.setDiscontinued(badDiscontinued)
//				.setCompany(badCompanyDummy)
//				.build();
//		  
//		try {
//			assertFalse(service.updateById(badComputerDummy.getId(), badComputerDummy));
//			fail("BadInputException non thrown with bad inputs.");
//		} catch (BadInputException e) {
//			log.debug("BadInputException launched!");
//		}
//	}
	
	@Test
	void givenGoodInput_whenDeleteUser_thenSuceed() {
		Timestamp introduced = Timestamp.valueOf(LocalDateTime.now());
		Timestamp discontinued = Timestamp.valueOf(LocalDateTime.now().plusYears(3));
		
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setDiscontinued(discontinued)
				.setIntroduced(introduced)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		
		try {
			service.create(computerDummy);
			assertTrue(service.deleteById(computerDummy.getId()));
		} catch (BadInputException e) {
			log.debug("Delete Succeed BadInputException launched!");
			fail("BadInputException thrown with good inputs.");
		}
	}
	
//	@Test
//	void givenBadInput_whenDeleteUser_thenFail() {
//		Company badCompanyDummy = new Company(-1L, null);
//		LocalDateTime dateTime = LocalDateTime.MIN;
//		Timestamp badIntroduced = Timestamp.valueOf(dateTime.plusYears(3));
//		Timestamp badDiscontinued = Timestamp.valueOf(dateTime);
//		
//		Computer badComputerDummy = new Computer.ComputerBuilder()
//				.setId(-1L)
//				.setName(null)
//				.setIntroduced(badIntroduced)
//				.setDiscontinued(badDiscontinued)
//				.setCompany(badCompanyDummy)
//				.build();
//		  
//		try {
//			service.create(badComputerDummy);
//			assertFalse(service.deleteById(badComputerDummy.getId()));
//			fail("BadInputException non thrown with bad inputs.");
//		} catch (BadInputException e) {
//			log.debug("Delete Fail BadInputException launched!");
//		}
//	}

}
