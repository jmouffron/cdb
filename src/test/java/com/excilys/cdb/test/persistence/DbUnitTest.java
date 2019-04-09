package com.excilys.cdb.test.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import com.excilys.cdb.exception.PropertyFileIOException;

class DbUnitTest extends DBTestCase {
  
  public DbUnitTest(String name) throws PropertyFileIOException {
    super(name);
    Properties props = new Properties();
    try {
      props.load(new FileInputStream("/datasource.properties"));
    } catch (IOException e) {
      throw new PropertyFileIOException("Couldn't get properties resource.");
    }
    
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, props.getProperty("driverClassName"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,  props.getProperty("jdbcUrl"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,  props.getProperty("username"));
    System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,  props.getProperty("password"));
  }

  protected IDataSet getDataSet() throws Exception {
    return new FlatXmlDataSetBuilder().build(new FileInputStream("dataset.xml"));
  }
}
