package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

/**
 * @author excilys
 *
 */
public class DaoCompany implements IDaoInstance<Company> {
  private final String INSERT = "INSERT INTO company VALUES(?,?);";
  private final String SELECT_ALL = "SELECT * FROM company;";
  private final String MAX_ID = "SELECT MAX(id)+1 FROM company;";
  private final String SELECT_ID = "SELECT * FROM company where id=?;";
  private final String SELECT_NAME = "SELECT * FROM company where name=?;";
  private final String UPDATE = "UPDATE company SET name=? WHERE id=?;";
  private final String DELETE_ID = "DELETE FROM company WHERE id=? ;";
  private final String DELETE_NAME = "DELETE FROM company WHERE name=? ;";
  
  private final Logger log = LoggerFactory.getLogger(DaoCompany.class);
  private static volatile IDaoInstance<Company> instance = null;

  private DaoCompany() {
  }

  public static IDaoInstance<Company> getDao() {
    if (instance == null) {
      synchronized (DaoCompany.class) {
        if (instance == null) {
          instance = new DaoCompany();
        }
      }
    }
    return instance;
  }

  private Connection getConnection() throws DaoException {
    Connection optConnection = Datasource.getConnection().get();
    if (optConnection == null) {
      log.error("Connection failure in DAO.");
      throw new DaoException("Connection failed and is null");
    }
    return optConnection;
  }

  @Override
  public Optional<List<Company>> getAll() {
    List<Company> result = new ArrayList<>();

    try (Statement stmt = getConnection().createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL);) {
      while (rs.next()) {
        result.add(CompanyMapper.map(rs));
      }
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<Company> getOneById(Long id) {
    Company result = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      stmt = getConnection().prepareStatement(SELECT_ID);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      result = CompanyMapper.map(rs);
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    } finally {
      try {
        rs.close();
        stmt.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<Company> getOneByName(String name) {
    Company result = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      stmt = getConnection().prepareStatement(SELECT_NAME);
      stmt.setString(1, name);
      rs = stmt.executeQuery();
      rs.next();
      result = CompanyMapper.map(rs);
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    } finally {
      try {
        rs.close();
        stmt.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
    }

    return Optional.ofNullable(result);
  }

  @Override
  public boolean create(Company newEntity) {
    int lineAffected = 0;
    ResultSet maxRs = null;

    try (Connection conn = getConnection();
        PreparedStatement maxStmt = conn.prepareStatement(MAX_ID);
        PreparedStatement stmt = conn.prepareStatement(INSERT);) {
      maxRs = maxStmt.executeQuery();
      maxRs.next();
      long maxId = maxRs.getLong(1);
      stmt.setLong(1, maxId);
      stmt.setString(1, newEntity.getName());
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.debug(sqlex.getMessage());
    } finally {
      try {
        maxRs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
    }

    return lineAffected > 0 ? true : false;
  }

  @Override
  public boolean updateById(Company newEntity) {
    int lineAffected = 0;

    try (PreparedStatement stmt = getConnection().prepareStatement(UPDATE);) {
      stmt.setString(1, newEntity.getName());
      stmt.setLong(2, newEntity.getId());
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return lineAffected > 0 ? true : false;
  }

  @Override
  public boolean deleteById(Long id) {
    int lineAffected = 0;

    try (PreparedStatement stmt = getConnection().prepareStatement(DELETE_ID);) {
      stmt.setLong(1, id);
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return lineAffected > 0 ? true : false;
  }

  @Override
  public boolean deleteByName(String name) {
    int lineAffected = 0;

    try (PreparedStatement stmt = getConnection().prepareStatement(DELETE_NAME);) {
      stmt.setString(1, name);
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return lineAffected > 0 ? true : false;
  }

  @Override
  public boolean createDTO(Company newEntity) {
    int lineAffected = 0;

    try (PreparedStatement stmt = getConnection().prepareStatement(INSERT);) {
      stmt.setString(1, newEntity.getName());
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return lineAffected > 0 ? true : false;
  }

}
