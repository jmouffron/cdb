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
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.Datasource;

public class DaoComputer implements IDaoInstance<Computer> {

  private final String DELETE_ID = "DELETE FROM computer WHERE id=? ";
  private final String DELETE_NAME = "DELETE FROM computer WHERE name=? ";
  private final String DESC = "DESC";
  private final String INSERT = "INSERT INTO computer VALUES(?,?,?,?,?)";
  private final String MAX_ID = "SELECT MAX(id)+1 from computer";
  private final String SELECT_ALL = "SELECT pc.id, pc.name as pc_name, pc.introduced, pc.discontinued, pc.company_id, c.name as cp_name FROM computer pc INNER JOIN company c ON pc.company_id=c.id";
  private final String SELECT_ID = SELECT_ALL + " where pc.id=? ";
  private final String SELECT_NAME = SELECT_ALL + " where pc.name=? ";
  private final String UPDATE = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=? ";
  private final String ORDER_BY = SELECT_ALL + " ORDER BY ";

  private final Logger log = LoggerFactory.getLogger(DaoComputer.class);
  private static volatile IDaoInstance<Computer> instance = null;

  private DaoComputer() {
  }

  public static IDaoInstance<Computer> getDao() {
    if (instance == null) {
      synchronized (DaoComputer.class) {
        if (instance == null) {
          instance = new DaoComputer();
        }
      }
    }
    return instance;
  }

  private Connection getConnection() throws DaoException {
    try( Connection optConnection = Datasource.getConnection().get(); ){
        return optConnection;
    }catch( SQLException e) {
      log.error(e.getMessage());
      throw new DaoException(e.getMessage());
    }
  }

  @Override
  public Optional<List<Computer>> getAll() {
    List<Computer> result = new ArrayList<>();

    try (Connection conn = getConnection();Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL);) {

      while (rs.next()) {
        result.add(ComputerMapper.map(rs));
      }

    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<List<Computer>> getAllOrderedBy(String orderBy, boolean desc) throws DaoException {
    List<Computer> result = new ArrayList<>();
    String QUERY = desc ? ORDER_BY + orderBy + DESC : ORDER_BY + orderBy;

    try (Connection conn = getConnection();Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(QUERY);) {

      while (rs.next()) {
        result.add(ComputerMapper.map(rs));
      }

    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());

      throw new DaoException(sqlex.getMessage());
    }

    return Optional.ofNullable(result);
  }

  @Override
  public Optional<Computer> getOneById(Long id) {
    Computer result = null;
    ResultSet rs = null;

    try(Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_ID);){
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      rs.next();
      result = ComputerMapper.map(rs);
    } catch (SQLException sqlex) {
      log.debug(sqlex.getMessage());
      try {
        rs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
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
  public Optional<Computer> getOneByName(String name) {
    Computer result = null;
    ResultSet rs = null;

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_NAME);){
      stmt.setString(1, name);
      rs = stmt.executeQuery();
      rs.next();
      result = ComputerMapper.map(rs);
    } catch (SQLException sqlex) {
      log.debug(sqlex.getMessage());
      try {
        rs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
    }finally{
      try {
        rs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
    }

    return Optional.ofNullable(result);
  }

  @Override
  public boolean create(Computer newEntity) {
    int linesAffected = 0;

    Long maxId = 0L;

    try (Connection conn = getConnection();
        PreparedStatement maxStmt = conn.prepareStatement(MAX_ID);
        PreparedStatement stmt = conn.prepareStatement(INSERT);) {
      ResultSet maxIdRs = maxStmt.executeQuery();

      while (maxIdRs.next()) {
        maxId = maxIdRs.getLong(1);
      }

      stmt.setLong(1, maxId);
      stmt.setString(2, newEntity.getName());
      stmt.setTimestamp(3, newEntity.getIntroduced());
      stmt.setTimestamp(4, newEntity.getDiscontinued());
      stmt.setLong(5, newEntity.getCompany().getId());

      linesAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.debug(sqlex.getMessage());
    }

    return linesAffected > 0 ? true : false;
  }

  @Override
  public boolean updateById(Computer newEntity) {
    int lineAffected = 0;

    try (Connection conn = getConnection();PreparedStatement stmt = conn.prepareStatement(UPDATE);) {
      stmt.setString(1, newEntity.getName());
      stmt.setTimestamp(2, newEntity.getIntroduced());
      stmt.setTimestamp(3, newEntity.getDiscontinued());
      stmt.setLong(4, newEntity.getCompany().getId());
      stmt.setLong(5, newEntity.getId());
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return lineAffected > 0 ? true : false;
  }

  @Override
  public boolean deleteById(Long id) {
    int lineAffected = 0;

    try (Connection conn = getConnection();PreparedStatement stmt = conn.prepareStatement(DELETE_ID);) {
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

    try (Connection conn = getConnection();PreparedStatement stmt = conn.prepareStatement(DELETE_NAME);) {
      stmt.setString(1, name);
      lineAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return lineAffected > 0 ? true : false;
  }

  @Override
  public boolean createDTO(Computer newEntity) {
    int linesAffected = 0;

    Long maxId = 0L;

    try (Connection conn = getConnection();
        PreparedStatement maxStmt = conn.prepareStatement(MAX_ID);
        PreparedStatement stmt = conn.prepareStatement(INSERT);
        ResultSet maxIdRs = maxStmt.executeQuery();) {

      while (maxIdRs.next()) {
        maxId = maxIdRs.getLong(1);
      }

      stmt.setLong(1, maxId);
      stmt.setString(2, newEntity.getName());
      stmt.setTimestamp(3, newEntity.getIntroduced());
      stmt.setTimestamp(4, newEntity.getDiscontinued());
      stmt.setLong(5, newEntity.getCompany().getId());

      linesAffected = stmt.executeUpdate();
    } catch (SQLException sqlex) {
      log.error(sqlex.getMessage());
    }

    return linesAffected > 0 ? true : false;
  }

}
