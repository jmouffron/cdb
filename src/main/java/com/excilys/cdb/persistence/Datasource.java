package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author excilys
 *
 */
public class Datasource {
  private static Logger log;

	private static HikariConfig hikariCfg = new HikariConfig("/datasource.properties");
	private static HikariDataSource hikariDs = new HikariDataSource(hikariCfg);

  /**
   * A private Constructor to never allow instantiation
   */
  private Datasource() {
  }

  /**
   * A static way to get a connection to the database
   * 
   * @return Connection
   */
  public static Optional<Connection> getConnection() {
    Connection result = null;
    
    try {
      result = hikariDs.getConnection();
    } catch (SQLException e) {
      log.error(e.getMessage());
    } finally {
      try {
        result.close();
      } catch (SQLException e) {
        log.error(e.getMessage());;
      }
    }
    
    return Optional.ofNullable(result);
  }
  
  public static void killConnections() {
    hikariDs.close();
  }

}
