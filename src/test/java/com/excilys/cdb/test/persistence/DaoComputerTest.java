package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.IDaoInstance;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.IService;
import com.excilys.cdb.service.ServiceFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
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
		
		assertFalse(daoTested.create(computerDummy));
		
		List<Computer> computers = daoTested.getAll();
    int computerSize = computers.size() - 1;

    assertNotEquals(computerDummy.getName(), computers.get(computerSize).getName());
	}
}
