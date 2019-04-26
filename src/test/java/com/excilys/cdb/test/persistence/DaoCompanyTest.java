package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoCompany;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DaoCompanyTest {

  private static final Logger log = LoggerFactory.getLogger(DaoComputerTest.class);
  private static DaoCompany daoTested;
  
  @BeforeAll
  static void setUp() { 

  }
  
  @BeforeAll
  static void tearDown() { }
  
  @Test
  void givenGoodInput_whenCreateUser_thenSuceed() throws DaoException {
    Company companyDummy = new Company(600L, "company");
    
    assertTrue( daoTested.create(companyDummy) );
     
    List<Company> companies = daoTested.getAll().get();
    int computerSize = companies.size() - 1;
        
    assertEquals(companyDummy, companies.get(computerSize));
  }
  
  @Test
  void givenBadInput_whenCreateUser_thenFail() throws DaoException {
    Long id = -100L;
    
    Company companyDummy = new Company(id, "");   
    
    assertFalse(daoTested.create(companyDummy));
    
    List<Company> companies = daoTested.getAll().get();
    int computerSize = companies.size() - 1;

    assertNotEquals(companyDummy.getName(), companies.get(computerSize).getName());
  }
}
