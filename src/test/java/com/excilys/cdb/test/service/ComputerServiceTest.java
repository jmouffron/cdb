package com.excilys.cdb.test.service;

//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//import java.sql.Timestamp;
//import java.time.Period;
//import java.time.format.DateTimeFormatter;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.excilys.cdb.exception.BadInputException;
//import com.excilys.cdb.exception.ServiceException;
//import com.excilys.cdb.model.Company;
//import com.excilys.cdb.model.Computer;
//import com.excilys.cdb.persistence.IDaoInstance;
//import com.excilys.cdb.service.ComputerService;
//import com.excilys.cdb.service.IService;
//import com.excilys.cdb.service.ServiceFactory;
//import com.excilys.cdb.utils.DateUtils;

class ComputerServiceTest {
//  private static Logger log = LoggerFactory.getLogger(ComputerServiceTest.class);
//
//  static ComputerService service;
//  static IDaoInstance<Computer> daoComputer;
//
//  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
//
//  Timestamp badIntroduced = DateUtils.getNowTimestamp();
//  Timestamp badDiscontinued = DateUtils.getBeforeNowTimestamp(Period.ofYears(3));
//  Timestamp introduced = DateUtils.getBeforeNowTimestamp(Period.ofYears(3));
//  Timestamp discontinued = DateUtils.getNowTimestamp();
//
//  @BeforeAll
//  static void setUp() {
//      service = ServiceFactory.getComputerService();
//  }
//
//  @AfterAll
//  static void tearDown() {
//    service = null;
//  }
//
//  @Test
//  void givenGoodInput_whenCreateUser_thenSuceed() throws ServiceException {
//    Company companyDummy = new Company(600L, "company");
//
//    Computer computerDummy = new Computer.ComputerBuilder().setId(600L).setIntroduced(introduced)
//        .setDiscontinued(discontinued).setName("New computer").setCompany(companyDummy).build();
//
//    try {
//      assertTrue(service.create(computerDummy));
//    } catch (BadInputException e) {
//      log.error("Create Failure, BadInputException launched!");
//      fail("BadInputException thrown with good inputs.");
//    }
//  }
//
//  @Test
//  void givenBadInput_whenCreateUser_thenFail() throws ServiceException {
//    Company badCompanyDummy = new Company(-1L, null);
//
//    Computer badComputerDummy = new Computer.ComputerBuilder().setId(-1L).setName(null).setIntroduced(badIntroduced)
//        .setDiscontinued(badDiscontinued).setCompany(badCompanyDummy).build();
//
//    try {
//      assertFalse(service.create(badComputerDummy));
//      fail("BadInputException non thrown with bad inputs.");
//    } catch (BadInputException e) {
//      log.debug("BadInput Success, Create Exception launched!");
//    }
//  }
//
//  @Test
//  void givenGoodInput_whenUpdateUser_thenSuceed() throws ServiceException {
//    Company companyDummy = new Company(600L, "company");
//    Company updatedcompany = new Company(700L, "new company");
//    Timestamp updatedIntroduced = Timestamp.valueOf( introduced.toLocalDateTime().minusDays(4) );
//    Timestamp updatedDiscontinued = null ;
//    
//    Computer computerDummy = new Computer.ComputerBuilder().setId(600L).setIntroduced(introduced)
//        .setDiscontinued(discontinued).setName("New computer").setCompany(companyDummy).build();
//
//    Computer computerUpdated = new Computer.ComputerBuilder().setId(600L).setIntroduced( updatedIntroduced )
//        .setDiscontinued( updatedDiscontinued ).setName("New name").setCompany(updatedcompany).build();
//
//    try {
//      assertTrue(service.create(computerDummy));
//      assertTrue(service.updateById(computerUpdated));
//    } catch (BadInputException e) {
//      log.error("Update Failure, BadInputException launched!");
//      fail("BadInputException thrown with good inputs.");
//    }
//  }
//
//  @Test
//  void givenBadInput_whenUpdateUser_thenFail() throws ServiceException {
//    Company badCompanyDummy = new Company(-1L, null);
//
//    Computer badComputerDummy = new Computer.ComputerBuilder().setId(-1L).setName(null).setIntroduced(badIntroduced)
//        .setDiscontinued(badDiscontinued).setCompany(badCompanyDummy).build();
//
//    try {
//      assertFalse(service.updateById(badComputerDummy));
//      fail("BadInputException non thrown with bad inputs.");
//    } catch (BadInputException e) {
//      log.debug("BadInput Success, Update Exception launched!");
//    }
//  }
//
//  @Test
//  void givenGoodInput_whenDeleteUser_thenSuceed() throws ServiceException {
//
//    Company companyDummy = new Company(600L, "company");
//    Computer computerDummy = new Computer.ComputerBuilder().setId(600L).setDiscontinued(discontinued)
//        .setIntroduced(introduced).setName("New computer").setCompany(companyDummy).build();
//
//    try {
//      assertTrue(service.create(computerDummy));
//      assertTrue(service.deleteById(computerDummy.getId()));
//    } catch (BadInputException e) {
//      log.error("Delete Failure, BadInputException launched!");
//      fail("BadInputException thrown with good inputs.");
//    }
//  }
//
//  @Test
//  void givenBadInput_whenDeleteUser_thenFail() throws ServiceException {
//    Company badCompanyDummy = new Company(-1L, null);
//
//    Computer badComputerDummy = new Computer.ComputerBuilder().setId(-1L).setName(null).setIntroduced(badIntroduced)
//        .setDiscontinued(badDiscontinued).setCompany(badCompanyDummy).build();
//
//    try {
//      assertFalse(service.create(badComputerDummy));
//      assertFalse(service.deleteById(badComputerDummy.getId()));
//      fail("BadInputException non thrown with bad inputs.");
//    } catch (BadInputException e) {
//      log.debug("BadInput Success, Delete Exception launched!");
//    }
//  }

}
