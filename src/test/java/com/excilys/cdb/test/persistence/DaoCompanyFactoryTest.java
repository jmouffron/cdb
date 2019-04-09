package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoCompanyFactory;
import com.excilys.cdb.persistence.IDaoFactory;

class DaoCompanyFactoryTest {

  static IDaoFactory<Company> factoryTested;
  private static DaoCompanyFactory companyFactory;
  
  @BeforeAll
  static void setUp(DaoCompanyFactory factory) {
    companyFactory = factory;
  }
  
  @Test
  void givenFactoryMethod_whenCreatingDao_thenSuceed() {
    
    assertTrue( companyFactory instanceof DaoCompanyFactory );
    assertNotNull( companyFactory.getDao() );
  }

}
