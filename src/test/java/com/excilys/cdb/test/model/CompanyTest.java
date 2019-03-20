package com.excilys.cdb.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.model.Company;

class CompanyTest {

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
	void givenGoodInput_whenCreatingCompany_thenSuceed() {
		Company companyTested = new Company(1L, "name");
		
		assertNotNull(companyTested);
	}
	
	@Test
	void givenBadInput_whenCreatingCompany_thenFail() {
		Company companyTested = new Company(-1L, null);
		
		assertFalse( companyTested.getId() > 0 );
		assertTrue( companyTested.getName() == null || companyTested.getName().isEmpty() );
	}
	
	@Test
	void givenEqualCompany_whenComparingCompany_thenSuceed() {
		Company companyTested = new Company(1L, "name");
		
		Company otherCompany = new Company(1L, "name");
		
		assertEquals(companyTested, otherCompany);
		assertEquals(companyTested, companyTested);
		assertSame(companyTested, companyTested);
	}
	
	@Test
	void givenDifferentCompany_whenComparingCompany_thenFail() {
		Company companyTested = new Company(1L, "name");
		
		Company otherCompany = new Company(2L, "different name");
		
		assertNotEquals(companyTested, otherCompany);
		assertEquals(companyTested, companyTested);
		assertNotSame(companyTested, otherCompany);
	}
}
