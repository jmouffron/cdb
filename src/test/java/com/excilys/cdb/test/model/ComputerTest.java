package com.excilys.cdb.test.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;

class ComputerTest {

	private static ComputerBuilder computerBuilderMock;
	private static Computer computerDummy;

	static Long id = 1L, badId = -1L;
	static String name = "name", otherName = "other " + name, badName = "";

	static Timestamp introduced = Timestamp.valueOf(LocalDateTime.now().minusDays(3));
	static Timestamp discontinued = Timestamp.valueOf(LocalDateTime.now());
	static Timestamp otherIntroduced = Timestamp.valueOf(LocalDateTime.now().minusDays(2));
	static Timestamp otherDiscontinued = Timestamp.valueOf(LocalDateTime.now().plusDays(7));
	static Timestamp badIntroduced = Timestamp.valueOf(LocalDateTime.now());
	static Timestamp badDiscontinued = Timestamp.valueOf(LocalDateTime.now().minusDays(3));

	static Company company = new Company(id, name);
	static Company badCompany = new Company(badId, badName);
	Company otherCompany = new Company(2L, otherName);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		computerBuilderMock = mock(ComputerBuilder.class);
		computerDummy = new Computer(id, name, introduced, discontinued, company);

		when(computerBuilderMock.setId(anyLong())).thenReturn(computerBuilderMock);
		when(computerBuilderMock.setName(anyString())).thenReturn(computerBuilderMock);
		when(computerBuilderMock.setIntroduced(any())).thenReturn(computerBuilderMock);
		when(computerBuilderMock.setDiscontinued(any())).thenReturn(computerBuilderMock);
		when(computerBuilderMock.setCompany(any())).thenReturn(computerBuilderMock);
		when(computerBuilderMock.build()).thenReturn(computerDummy);

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		computerDummy = null;
		computerBuilderMock = null;
	}

	@Test
	void givenGoodInput_whenCreatingComputer_thenSuceed() {
		Computer computerTested = computerBuilderMock.setId(id).setName(name).setIntroduced(introduced)
				.setDiscontinued(discontinued).setCompany(company).build();

		verify(computerBuilderMock, times(1)).setId(id);
		verify(computerBuilderMock, times(1)).setName(name);
		verify(computerBuilderMock, times(1)).setIntroduced(introduced);
		verify(computerBuilderMock, times(1)).setDiscontinued(discontinued);
		verify(computerBuilderMock, times(1)).setCompany(company);

		assertNotNull(computerTested);
		assertEquals(id, computerTested.getId());
		assertEquals(name, computerTested.getName());
		assertEquals(introduced, computerTested.getIntroduced());
		assertEquals(discontinued, computerTested.getDiscontinued());
		assertEquals(company, computerTested.getCompany());
	}

	@Test
	void givenBadInput_whenCreatingComputer_thenFail() {
		Computer computerTested = new Computer.ComputerBuilder().setId(badId).setName(badName)
				.setIntroduced(badIntroduced).setDiscontinued(badDiscontinued).setCompany(badCompany).build();

		assertFalse( computerTested.getIntroduced().before(computerTested.getDiscontinued()) );

		verify(computerBuilderMock, times(1)).setId(badId);
		verify(computerBuilderMock, times(1)).setName(badName);
		verify(computerBuilderMock, times(1)).setIntroduced(badIntroduced);
		verify(computerBuilderMock, times(1)).setDiscontinued(badDiscontinued);
		verify(computerBuilderMock, times(1)).setCompany(badCompany);
	}

	@Test
	void givenEqualComputers_whenComparingComputers_thenSuceed() {
		Computer computerTested = new Computer(id, name, introduced, discontinued, company);
		Computer otherComputer = new Computer(id, name, introduced, discontinued, company);

		assertEquals(computerTested.getId(), otherComputer.getId());
		assertEquals(computerTested.getName(), otherComputer.getName());
		assertEquals(computerTested.getIntroduced(), otherComputer.getIntroduced());
		assertEquals(computerTested.getDiscontinued(), otherComputer.getDiscontinued());
		assertEquals(computerTested.getCompany(), otherComputer.getCompany());

		assertEquals(computerTested, otherComputer);

		assertEquals(computerTested, computerTested);
		assertEquals(otherComputer, otherComputer);
		assertSame(computerTested, computerTested);
		assertSame(otherComputer, otherComputer);
	}

	@Test
	void givenDifferentComputers_whenComparingComputers_thenFail() {
		Computer computerTested = new Computer(id, name, introduced, discontinued, company);
		Computer otherComputer = new Computer(id + 1L, otherName, otherIntroduced, otherDiscontinued, otherCompany);

		assertNotEquals(computerTested.getId(), otherComputer.getId());
		assertNotEquals(computerTested.getName(), otherComputer.getName());
		assertNotEquals(computerTested.getIntroduced(), otherComputer.getIntroduced());
		assertNotEquals(computerTested.getDiscontinued(), otherComputer.getDiscontinued());
		assertNotEquals(computerTested.getCompany(), otherComputer.getCompany());

		assertNotEquals(computerTested, otherComputer);

		assertEquals(computerTested, computerTested);
		assertEquals(otherComputer, otherComputer);
		assertSame(computerTested, computerTested);
		assertSame(otherComputer, otherComputer);
	}
}
