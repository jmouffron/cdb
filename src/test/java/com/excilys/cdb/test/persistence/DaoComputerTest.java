package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.Datasource;
import com.excilys.cdb.persistence.IDaoInstance;
import com.excilys.cdb.utils.DateUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoComputerTest {

	private static final Logger log = LoggerFactory.getLogger(DaoComputerTest.class);
	private static IDaoInstance<Computer> daoTested;
	
	@BeforeAll
	static void setUp() {	
	  daoTested = DaoComputerFactory.getComputerFactory().getDao();
	}
	
	@BeforeAll
	static void tearDown() { }
	
	@Test
	void givenGoodInput_whenCreateUser_thenSuceed() {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(600L)
				.setName("New computer")
				.setCompany(companyDummy)
				.build();
		
		assertTrue( daoTested.create(computerDummy) );
     
    List<Computer> computers = daoTested.getAll();
    int computerSize = computers.size() - 1;
        
		assertEquals(computerDummy, computers.get(computerSize));
	}
	
	@Test
	void givenBadInput_whenCreateUser_thenFail() {
		Long id = -100L;
		String name = null;
		Timestamp introduced = DateUtils.getBeforeNowTimestamp(Period.ofYears(1000));
		Timestamp discontinued = DateUtils.getBeforeNowTimestamp(Period.ofYears(1003));
		
		Company companyDummy = new Company(id, "");		
		Computer computerDummy = new Computer.ComputerBuilder()
				.setId(id)
				.setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(companyDummy)
				.build();
		
		assertFalse(daoTested.create(computerDummy));
		
		List<Computer> computers = daoTested.getAll();
    int computerSize = computers.size() - 1;

    assertNotEquals(computerDummy.getName(), computers.get(computerSize).getName());
	}
}
