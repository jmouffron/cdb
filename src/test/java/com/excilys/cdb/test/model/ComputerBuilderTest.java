package com.excilys.cdb.test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

class ComputerBuilderTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void givenGoodInput_whenBuildingComputer_thenSuceed() {
		Long id = 1L;
		String name = "name";
		Timestamp introduced = Timestamp.valueOf(LocalDateTime.now().minusDays(3));
		Timestamp discontinued = Timestamp.valueOf(LocalDateTime.now());
		Company company = new Company(1L, "name");

		ComputerBuilder computerBuilderTested = new ComputerBuilder()
				.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company);
		
		Computer computer = computerBuilderTested.build();
		
		assertNotNull(computer);
		assertNotEquals(null, computer);
		assertNotSame(null, computer);
	}
	
	@Test
	void givenBadInput_whenBuildingComputer_thenFail() {
		Long id = -1L;
		String name = "";
		Timestamp introduced = Timestamp.valueOf(LocalDateTime.now());
		Timestamp discontinued = Timestamp.valueOf(LocalDateTime.now().minusDays(3));
		Company company = new Company(-1L, "");

		ComputerBuilder computerBuilderTested = new ComputerBuilder()
				.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company);
		
		Computer computer = computerBuilderTested.build();
		
		assertFalse( computer.getIntroduced().before( computer.getDiscontinued() ) );
	}
	
	@Test
	void givenIncompleteGoodInput_whenBuildingComputer_thenSuceed() {
		String name = "name";
		Long id = 600L;
		Company company = new Company(1L, "name");

		ComputerBuilder computerBuilderTested = new ComputerBuilder()
				.setId(id)
				.setName(name)
				.setCompany(company);
		
		Computer computer = computerBuilderTested.build();
		Computer otherComputer = new Computer(0L, name, Timestamp.valueOf(LocalDateTime.now()), null, company);
		
		assertTrue( computer.getIntroduced().before( computer.getDiscontinued() ) );
		assertTrue( computer.getId()>0 );
		assertEquals( otherComputer, computer );
	}
	
	@Test
	void givenIncompleteBadInput_whenBuildingComputer_thenFail() {
		String name = "";
		Long id = -200L;
		Timestamp discontinued = Timestamp.valueOf(LocalDateTime.now().minusDays(3));
		
		Company company = new Company(-1L, "");
		ComputerBuilder computerBuilderTested = new ComputerBuilder()
				.setId(id)
				.setName(name)
				.setDiscontinued(discontinued);
		
		Computer computer = computerBuilderTested.build();
		Computer otherComputer = new Computer(0L, name, Timestamp.valueOf(LocalDateTime.now()), discontinued, company);
		
		assertFalse( computer.getIntroduced().before( computer.getDiscontinued() ) );
		assertFalse( computer.getId()>0 );
		assertNotEquals( otherComputer, computer );
	}
}
