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
import com.excilys.cdb.service.ComputerService;

/**
 * Dao for company related entities fetching
 * 
 * @author excilys
 *
 */
public class DaoCompany implements IDaoInstance<Company> {

  private final String DELETE_ID = "DELETE FROM company WHERE id=? ";
  private final String COMPUTER_CASCADE_DELETE_ID = "DELETE FROM computers WHERE company_id=? ";
  private final String DELETE_NAME = "DELETE FROM company WHERE name=? ";
  private final String COMPUTER_CASCADE_DELETE_NAME = "DELETE FROM computers LEFT JOIN company ON computer.company_id = company.id WHERE company.name = ? ";
  private final String DESC = " DESC;";
  private final String INSERT = "INSERT INTO company VALUES(?,?)";
  private final String MAX_ID = "SELECT MAX(id)+1 FROM company;";
  private final String SELECT_ALL = "SELECT id, name FROM company ";
  private final String SELECT_ID = "SELECT id, name FROM company where id=?";
  private final String SELECT_NAME = "SELECT id, name FROM company where name=?";
  private final String UPDATE = "UPDATE company SET name=? WHERE id=?";
  private final String ORDER_BY = SELECT_ALL + "ORDER BY ";

  private final Logger log = LoggerFactory.getLogger(DaoCompany.class);
  private Datasource datasource;

  public DaoCompany() {
  }

  public DaoCompany(Datasource ds) {
    super();
    this.datasource = ds;
  }

  private Connection getConnection() throws DaoException {
    return this.datasource.getConnection().get();
  }

  @Override
  public Optional<List<Company>> getAll() {
    List<Company> result = new ArrayList<>();

    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(SELECT_ALL);) {
      ;
      while (rs.next()) {
        result.add(CompanyMapper.map(rs));
      }
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<List<Company>> getAllOrderedBy(String orderBy, boolean desc) throws DaoException {
    List<Company> result = new ArrayList<>();
    String QUERY = desc ? ORDER_BY + orderBy + DESC : ORDER_BY + orderBy;

    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);) {
      ;

      while (rs.next()) {
        result.add(CompanyMapper.map(rs));
      }

    } catch (SQLException sqlex) {
      log.error(QUERY + sqlex.getMessage());
      throw new DaoException(sqlex.getMessage());
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<Company> getOneById(Long id) {
    Company result = null;
    ResultSet rs = null;

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_ID);) {
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      result = CompanyMapper.map(rs);
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    } finally {
      try {
        rs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<Company> getOneByName(String name) {
    Company result = null;

    ResultSet rs = null;

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_NAME);) {
      stmt.setString(1, name);
      rs = stmt.executeQuery();
      rs.next();
      result = CompanyMapper.map(rs);
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    } finally {
      try {
        rs.close();
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
      log.error(sqlex.getMessage());
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

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE);) {
      stmt.setString(1, newEntity.getName());
      stmt.setLong(2, newEntity.getId());
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return lineAffected > 0 ? true : false;
  }

  public boolean deleteById(Long id) throws DaoException {
    int lineAffected = 0;

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(DELETE_ID);
        PreparedStatement computerStmt = conn.prepareStatement(COMPUTER_CASCADE_DELETE_ID);) {
      conn.setAutoCommit(false);

      stmt.setLong(1, id);

      lineAffected = stmt.executeUpdate();

      if (lineAffected > 0) {
        computerStmt.setLong(1, id);
        lineAffected = computerStmt.executeUpdate();
      } else {
        throw new DaoException("Couldn't rollback from delete transaction !");
      }

      conn.commit();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
      throw new DaoException("Couldn't rollback from delete transaction !");
    }

    return lineAffected > 0 ? true : false;
  }

  public boolean deleteByName(String name, ComputerService service) throws DaoException {
    int lineAffected = 0;
    Connection conn = getConnection();
    
    try (PreparedStatement stmt = conn.prepareStatement(DELETE_NAME);
        PreparedStatement computerStmt = conn.prepareStatement(COMPUTER_CASCADE_DELETE_NAME);) {
      conn.setAutoCommit(false);
      stmt.setString(1, name);

      lineAffected = stmt.executeUpdate();

      if (lineAffected > 0) {
        computerStmt.setString(1, name);
        lineAffected = computerStmt.executeUpdate();
      } else {
        conn.rollback();
        throw new DaoException("Couldn't rollback from delete transaction !");
      }
      conn.commit();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
      try {
        conn.rollback();
        conn.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
        throw new DaoException(e.getMessage());
      }
      throw new DaoException("Couldn't rollback from delete transaction !");
    }finally {
      try {
        conn.setAutoCommit(true);
      } catch (SQLException e) {
        log.error(e.getMessage());
        throw new DaoException(e.getMessage());
      }
    }

    return lineAffected > 0 ? true : false;
  }

  @Override
  public boolean createDTO(Company newEntity) throws DaoException {
    int lineAffected = 0;

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT);) {
      stmt.setString(1, newEntity.getName());
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
      throw new DaoException("Couldn't rollback from delete transaction !");
    }

    return lineAffected > 0 ? true : false;
  }

}
