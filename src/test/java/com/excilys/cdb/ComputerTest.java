package com.excilys.cdb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import java.sql.Timestamp;
import java.time.LocalDateTime;

class ComputerTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void givenNoInput_whenComputerBuild_thenFail() {
		Computer computerTested = new Computer.ComputerBuilder().build();
		
		assertEquals(0L,computerTested.getId());
		assertNull(computerTested.getIntroduced());
		assertNull(computerTested.getDiscontinued());
		assertNull(computerTested.getName());
	}
	
	@Test
	void givenGoodInput_whenComputerBuild_thenSuceed() {
		String name = "name";
		Long id = 700L;
		Company company = new Company(600L, name);
		LocalDateTime dateTime = LocalDateTime.now();
		Timestamp introduced = Timestamp.valueOf(dateTime);
		Timestamp discontinued = Timestamp.valueOf(dateTime.plusHours(3));
		Computer computerTested = new Computer.ComputerBuilder()
				.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company)
				.build();
		
		assertEquals(700L,computerTested.getId());
		assertEquals(introduced, computerTested.getIntroduced());
		assertEquals(discontinued, computerTested.getDiscontinued());
		assertTrue(computerTested.getIntroduced().before(computerTested.getDiscontinued()));
		assertEquals(name, computerTested.getName());
		assertEquals(600L, computerTested.getCompany().getId());
		assertEquals(name, computerTested.getCompany().getName());
	}
	
	@Test
	void givenBadInput_whenComputerBuild_thenFail() {
		String name = "";
		Long id = -100L;
		Company company = new Company(id, name);
		LocalDateTime dateTime = LocalDateTime.now();
		Timestamp introduced = Timestamp.valueOf(dateTime);
		Timestamp discontinued = Timestamp.valueOf(dateTime.minusHours(3));
		Computer computerTested = new Computer.ComputerBuilder()
				.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company)
				.build();
		
		assertNotEquals(700L,computerTested.getId());
		assertNotEquals(introduced, computerTested.getIntroduced());
		assertNotEquals(discontinued, computerTested.getDiscontinued());
		assertFalse(computerTested.getIntroduced().before(computerTested.getDiscontinued()));
		assertNotEquals(name, computerTested.getName());
		assertNotEquals(600L, computerTested.getCompany().getId());
		assertNotEquals(name, computerTested.getCompany().getName());
	}
}
