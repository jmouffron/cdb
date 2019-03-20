package com.excilys.cdb.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Computer.ComputerBuilder;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



class ComputerTest {

	private static ComputerBuilder computerBuilderMock;
	private static ComputerBuilder computerBuilderBadMock;
	private static Computer computerDummy;
	private static Computer computerBad;
	
	static Long id = 1L;
	static Long badId = -1L;
	static String name = "name";
	static String otherName = "other " + name;
	static String badName = "";

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
	  computerBuilderBadMock = mock(ComputerBuilder.class);
	  computerDummy = new Computer(id, name, introduced, discontinued, company);
	  computerBad = new Computer(badId, badName, badIntroduced, badDiscontinued, badCompany);
	    
	  when(computerBuilderMock.setId(any())).thenReturn(computerBuilderMock);
	  when(computerBuilderMock.setName(any())).thenReturn(computerBuilderMock);
	  when(computerBuilderMock.setIntroduced(any())).thenReturn(computerBuilderMock);
	  when(computerBuilderMock.setDiscontinued(any())).thenReturn(computerBuilderMock);
	  when(computerBuilderMock.setCompany(any())).thenReturn(computerBuilderMock);
	  when(computerBuilderMock.build()).thenReturn(computerDummy);
	    
	  when(computerBuilderBadMock.setId(any())).thenReturn(computerBuilderBadMock);
	  when(computerBuilderBadMock.setName(any())).thenReturn(computerBuilderBadMock);
	  when(computerBuilderBadMock.setIntroduced(any())).thenReturn(computerBuilderBadMock);
	  when(computerBuilderBadMock.setDiscontinued(any())).thenReturn(computerBuilderBadMock);
	  when(computerBuilderBadMock.setCompany(any())).thenReturn(computerBuilderBadMock);
	  when(computerBuilderBadMock.build()).thenReturn(computerBad);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
    computerDummy = null;
    computerBuilderMock = null;
	}

	@Test
	void givenGoodInput_whenCreatingComputer_thenSuceed() {
		Computer computerTested = computerBuilderMock
		    .setId(id)
		    .setName(name)
		    .setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(company).build();

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
		Computer computerTested = computerBuilderBadMock
		    .setId(badId)
		    .setName(badName)
				.setIntroduced(badIntroduced)
				.setDiscontinued(badDiscontinued)
				.setCompany(badCompany)
				.build();
		
    verify(computerBuilderBadMock, times(1)).setId(badId);
    verify(computerBuilderBadMock, times(1)).setName(badName);
    verify(computerBuilderBadMock, times(1)).setIntroduced(badIntroduced);
    verify(computerBuilderBadMock, times(1)).setDiscontinued(badDiscontinued);
    verify(computerBuilderBadMock, times(1)).setCompany(badCompany);
    
		
    assertNotNull(computerTested);
    assertFalse( computerTested.getId() > 0 );
    assertFalse( !computerTested.getName().isEmpty() );
    assertFalse( computerTested.getCompany().getId() > 0 );
    assertFalse( !computerTested.getCompany().getName().isEmpty() );
    assertFalse( computerTested.getIntroduced().before( computerTested.getDiscontinued() ) );

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
