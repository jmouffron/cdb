package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.IDaoFactory;
import com.excilys.cdb.model.Computer;

import org.junit.jupiter.api.Test;

class DaoComputerFactoryTest {
  
  static IDaoFactory<Computer> factoryTested;
  
  @BeforeAll
  static void setUp(DaoComputerFactory factory) {
    factoryTested = factory;
  }
  
  @Test
  void givenFactoryMethod_whenCreatingDao_thenSuceed() {
    
    assertTrue( factoryTested instanceof DaoComputerFactory );
    assertNotNull( factoryTested.getDao() );
  }
  
}
