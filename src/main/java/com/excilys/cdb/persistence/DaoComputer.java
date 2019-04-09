package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.Datasource;

public class DaoComputer implements IDaoInstance<Computer> {

  private final String DELETE_ID = "DELETE FROM computer WHERE id=? ";
  private final String DELETE_NAME = "DELETE FROM computer WHERE name=? ";
  private final String DESC = " DESC";
  private final String INSERT = "INSERT INTO computer VALUES(?,?,?,?,?)";
  private final String MAX_ID = "SELECT MAX(id)+1 from computer";
  private final String SELECT_ALL = "SELECT pc.id, pc.name as pc_name, pc.introduced, pc.discontinued, pc.company_id, c.name as cp_name FROM computer pc INNER JOIN company c ON pc.company_id=c.id";
  private final String SELECT_ID = SELECT_ALL + " where pc.id=? ";
  private final String SELECT_NAME = SELECT_ALL + " where pc.name=? ";
  private final String UPDATE = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=? ";
  private final String ORDER_BY = SELECT_ALL + " ORDER BY ";

  private final Logger log = LoggerFactory.getLogger(DaoComputer.class);
  
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  public DaoComputer() { }
  
  public DaoComputer(JdbcTemplate jdbc) {
    this.jdbcTemplate = jdbc;
  }

  @Override
  public Optional<List<Computer>> getAll(){
    List<Computer> computerFetched = jdbcTemplate.query(SELECT_ALL, new ComputerMapper());
    return Optional.ofNullable(computerFetched);
  }

  @Override
  public Optional<List<Computer>> getAllOrderedBy(String orderBy, boolean desc){
    String QUERY = desc ? ORDER_BY + orderBy + DESC : ORDER_BY + orderBy;
    List<Computer> computerFetched = jdbcTemplate.query(QUERY, new ComputerMapper());
    return Optional.ofNullable(computerFetched);
  }

  @Override
  public Optional<Computer> getOneById(Long id) {
    Computer computerFetched = jdbcTemplate.queryForObject(SELECT_ID, new Object[]{id} ,new ComputerMapper());
    return Optional.ofNullable(computerFetched);
  }

  @Override
  public Optional<Computer> getOneByName(String name) {
    Computer computerFetched = jdbcTemplate.queryForObject(SELECT_NAME, new Object[]{name} ,new ComputerMapper());
    return Optional.ofNullable(computerFetched);
  }
  
  @Override
  public boolean create(Computer newEntity) throws DaoException {
    int affected = jdbcTemplate.update(INSERT, new Object[] {newEntity});
    if( affected > 0 ) {
      throw new DaoException("Couldn't insert "+ newEntity.getName() );
    }
    return affected > 0 ? true : false;
  }

  @Override
  public boolean updateById(Computer newEntity) throws DaoException {
    int affected = jdbcTemplate.update(UPDATE, new Object[] {newEntity});
    if( affected > 0 ) {
      throw new DaoException("Couldn't update "+ newEntity.getName() );
    }
    return affected > 0 ? true : false;
  }

  public boolean deleteById(Long id) throws DaoException {
    int affected = jdbcTemplate.update( DELETE_ID , new Object[] {id});
    if( affected > 0 ) {
      throw new DaoException( "Couldn't delete entity" );
    }
    return affected > 0 ? true : false;
  }

  public boolean deleteByName(String name) throws DaoException {
    int affected = jdbcTemplate.update( DELETE_NAME , new Object[] {name});
    if( affected > 0 ) {
      throw new DaoException( "Couldn't delete entity" );
    }
    return affected > 0 ? true : false;
  }

}
