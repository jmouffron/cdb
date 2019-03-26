package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DaoCompanyFactory;
import com.excilys.cdb.persistence.IDaoFactory;

class DaoCompanyFactoryTest {

  static IDaoFactory<Company> factoryTested;

  @Test
  void givenFactoryMethod_whenCreatingDao_thenSuceed() {
    factoryTested = DaoCompanyFactory.getCompanyFactory();
    
    assertTrue(factoryTested instanceof DaoCompanyFactory);
    assertNotNull(factoryTested.getDao());
  }

}
