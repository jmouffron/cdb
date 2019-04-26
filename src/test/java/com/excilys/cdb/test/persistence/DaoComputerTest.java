package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.utils.DateUtils;

import java.sql.Timestamp;
import java.time.Period;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoComputerTest {

	private static final Logger log = LoggerFactory.getLogger(DaoComputerTest.class);
	private static DaoComputer daoTested;

	@BeforeAll
	static void setUp(DaoComputer dao) {
		daoTested = dao;
	}

	@BeforeAll
	static void tearDown() {
	}

	@Test
	void givenGoodInput_whenCreateUser_thenSuceed() throws DaoException {
		Company companyDummy = new Company(600L, "company");
		Computer computerDummy = new Computer.ComputerBuilder().setId(600L).setName("New computer")
				.setCompany(companyDummy).build();

		daoTested.create(computerDummy);

		List<Computer> computers = daoTested.getAll().get();
		int computerSize = computers.size() - 1;

		assertEquals(computerDummy, computers.get(computerSize));
	}

	@Test
	void givenBadInput_whenCreateUser_thenFail() throws DaoException {
		Long id = -100L;
		String name = null;
		Timestamp introduced = DateUtils.getBeforeNowTimestamp(Period.ofYears(1000));
		Timestamp discontinued = DateUtils.getBeforeNowTimestamp(Period.ofYears(1003));

		Company companyDummy = new Company(id, "");
		Computer computerDummy = new Computer.ComputerBuilder().setId(id).setName(name).setIntroduced(introduced)
				.setDiscontinued(discontinued).setCompany(companyDummy).build();

		daoTested.create(computerDummy);

		List<Computer> computers = daoTested.getAll().get();
		int computerSize = computers.size() - 1;

		assertNotEquals(computerDummy.getName(), computers.get(computerSize).getName());
	}
}
