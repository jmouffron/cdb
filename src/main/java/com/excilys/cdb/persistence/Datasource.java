package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author excilys
 *
 */
public class Datasource {
  private static Logger log;

  private HikariDataSource hikariDs;
  private Connection conn;
  
  public Datasource(HikariDataSource ds) {
    this.hikariDs = ds;
  }

  /**
   * A static way to get a connection to the database
   * 
   * @return Connection
   */
  public Optional<Connection> getConnection() {
    this.conn = null;
    
    try {
      this.conn = hikariDs.getConnection();
    } catch (SQLException e) {
      log.error(e.getMessage());
    }

    return Optional.ofNullable(this.conn);
  }

  public void close() {
      this.hikariDs.close();
  }

}
