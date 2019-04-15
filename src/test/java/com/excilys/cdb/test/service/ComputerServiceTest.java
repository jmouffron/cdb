package com.excilys.cdb.test.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Timestamp;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceFactory;
import com.excilys.cdb.utils.DateUtils;

class ComputerServiceTest {
	private static Logger log = LoggerFactory.getLogger(ComputerServiceTest.class);
	static ComputerService service;
	static DaoComputer daoComputerDTO;

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");

	String badIntroduced = DateUtils.getNowTimestamp().toString();
	String badDiscontinued = DateUtils.getBeforeNowTimestamp(Period.ofYears(3)).toString();
	String introduced = DateUtils.getBeforeNowTimestamp(Period.ofYears(3)).toString();
	String discontinued = DateUtils.getNowTimestamp().toString();

	@BeforeAll
	static void setUp(ServiceFactory factory) {
		service = factory.getComputerService();
	}

	@AfterAll
	static void tearDown() {
		service = null;
	}

	@Test
	void givenGoodInput_whenCreateUser_thenSuceed() throws ServiceException {
		Company companyDummy = new Company(600L, "company");

		ComputerDTO computerDummy = new ComputerDTO.ComputerDTOBuilder().setId(600L).setIntroduced(introduced)
				.setDiscontinued(discontinued).setName("New computer").setCompanyId(companyDummy.getId()).build();

		try {
			service.create(computerDummy);
		} catch (BadInputException | DaoException e) {
			log.error("Create Failure, BadInputException launched!");
			fail("BadInputException thrown with good inputs.");
		}
	}

	@Test
	void givenBadInput_whenCreateUser_thenFail() throws ServiceException {
		Company badCompanyDummy = new Company(-1L, null);

		ComputerDTO badComputerDTODummy = new ComputerDTO.ComputerDTOBuilder().setId(-1L).setName(null)
				.setIntroduced(badIntroduced).setDiscontinued(badDiscontinued).setCompanyId(badCompanyDummy.getId())
				.build();

		try {
			service.create(badComputerDTODummy);
			fail("BadInputException non thrown with bad inputs.");
		} catch (BadInputException | DaoException e) {
			log.debug("BadInput Success, Create Exception launched!");
		}
	}

	@Test
	void givenGoodInput_whenUpdateUser_thenSuceed() throws ServiceException {
		Company companyDummy = new Company(600L, "company");
		Company updatedcompany = new Company(700L, "new company");
		String updatedIntroduced = Timestamp.valueOf(introduced).toString();
		String updatedDiscontinued = null;

		ComputerDTO computerDummy = new ComputerDTO.ComputerDTOBuilder().setId(600L).setIntroduced(introduced)
				.setDiscontinued(discontinued).setName("New computer").setCompanyId(companyDummy.getId()).build();

		ComputerDTO computerUpdated = new ComputerDTO.ComputerDTOBuilder().setId(600L).setIntroduced(updatedIntroduced)
				.setDiscontinued(updatedDiscontinued).setName("New name").setCompanyId(updatedcompany.getId()).build();

		try {
			service.create(computerDummy);
			service.updateById(computerUpdated);
		} catch (BadInputException | DaoException e) {
			log.error("Update Failure, BadInputException launched!");
			fail("BadInputException thrown with good inputs.");
		}
	}

	@Test
	void givenBadInput_whenUpdateUser_thenFail() throws ServiceException {
		Company badCompanyDummy = new Company(-1L, null);

		ComputerDTO badComputerDTODummy = new ComputerDTO.ComputerDTOBuilder().setId(-1L).setName(null)
				.setIntroduced(badIntroduced).setDiscontinued(badDiscontinued).setCompanyId(badCompanyDummy.getId())
				.build();

		try {
			service.updateById(badComputerDTODummy);
			fail("BadInputException non thrown with bad inputs.");
		} catch (BadInputException | DaoException e) {
			log.debug("BadInput Success, Update Exception launched!");
		}
	}

	@Test
	void givenGoodInput_whenDeleteUser_thenSuceed() throws ServiceException {

		Company companyDummy = new Company(600L, "company");
		ComputerDTO computerDummy = new ComputerDTO.ComputerDTOBuilder().setId(600L)
				.setDiscontinued(discontinued.toString()).setIntroduced(introduced.toString()).setName("New computer")
				.setCompanyId(companyDummy.getId()).build();

		try {
			service.create(computerDummy);
			service.deleteById(computerDummy.getId());
		} catch (BadInputException | DaoException e) {
			log.error("Delete Failure, BadInputException launched!");
			fail("BadInputException thrown with good inputs.");
		}
	}

	@Test
	void givenBadInput_whenDeleteUser_thenFail() throws ServiceException {
		Company badCompanyDummy = new Company(-1L, null);

		ComputerDTO badComputerDTODummy = new ComputerDTO.ComputerDTOBuilder().setId(-1L).setName(null)
				.setIntroduced(badIntroduced).setDiscontinued(badDiscontinued).setCompanyId(badCompanyDummy.getId())
				.build();

		try {
			service.create(badComputerDTODummy);
			service.deleteById(badComputerDTODummy.getId());
			fail("BadInputException non thrown with bad inputs.");
		} catch (BadInputException | DaoException e) {
			log.debug("BadInput Success, Delete Exception launched!");
		}
	}

}
