package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.*;

import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.IDaoFactory;
import com.excilys.cdb.model.Computer;

import org.junit.jupiter.api.Test;

class DaoComputerFactoryTest {
  
  static IDaoFactory<Computer> factoryTested;

  @Test
  void givenFactoryMethod_whenCreatingDao_thenSuceed() {
    factoryTested = DaoComputerFactory.getComputerFactory();
    
    assertTrue(factoryTested instanceof DaoComputerFactory);
    assertNotNull(factoryTested.getDao());
  }
  
}
