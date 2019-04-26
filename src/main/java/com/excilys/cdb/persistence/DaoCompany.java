package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

@Repository
public class DaoCompany {

	private final String DELETE_ID = "DELETE FROM company WHERE id=? ";
	private final String COMPUTER_CASCADE_DELETE_ID = "DELETE FROM computers WHERE company_id=? ";
	private final String DELETE_NAME = "DELETE FROM company WHERE name=? ";
	private final String COMPUTER_CASCADE_DELETE_NAME = "DELETE FROM computers LEFT JOIN company ON computer.company_id = company.id WHERE company.name = ? ";
	private final String DESC = " DESC;";
	private final String INSERT = "INSERT INTO company VALUES(?,?)";
	private final String SELECT_ALL = "SELECT id, name FROM company ";
	private final String SELECT_ID = "SELECT id, name FROM company where id=?";
	private final String SELECT_NAME = "SELECT id, name FROM company where name=?";
	private final String UPDATE = "UPDATE company SET name=? WHERE id=?";
	private final String ORDER_BY = SELECT_ALL + "ORDER BY ";

	private final Logger log = LoggerFactory.getLogger(DaoCompany.class);
	private Datasource datasource;

	JdbcTemplate jdbcTemplate;
	private CompanyMapper corpMapper;

	public DaoCompany(JdbcTemplate jdbc, CompanyMapper mapper) {
		this.jdbcTemplate = jdbc;
		this.corpMapper = mapper;
	}

	private Connection getConnection() throws DaoException {
		return this.datasource.getConnection().get();
	}
	
	@Transactional(readOnly=true)
	public Optional<List<Company>> getAll() {
		List<Company> companyFetched = jdbcTemplate.query(SELECT_ALL, corpMapper);
		return Optional.ofNullable(companyFetched);
	}
	
	@Transactional(readOnly=true)
	public Optional<List<Company>> getAllOrderedBy(String orderBy, boolean desc) {
		String QUERY = desc ? ORDER_BY + orderBy + DESC : ORDER_BY + orderBy;
		List<Company> companyFetched = jdbcTemplate.query(QUERY, corpMapper);
		return Optional.ofNullable(companyFetched);
	}
	
	@Transactional(readOnly=true)
	public Optional<Company> getOneById(Long id) {
		Company companyFetched = jdbcTemplate.queryForObject(SELECT_ID, new Object[] { id }, corpMapper);
		return Optional.ofNullable(companyFetched);
	}
	
	@Transactional(readOnly=true)
	public Optional<Company> getOneByName(String name) {
		log.error(name);
		Company companyFetched = jdbcTemplate.queryForObject(SELECT_NAME, new Object[] { name }, corpMapper);
		return Optional.ofNullable(companyFetched);
	}

	@Transactional
	public boolean create(Company newEntity) throws DaoException {
		int affected = jdbcTemplate.update(INSERT, new Object[] { newEntity });
		if (affected > 0) {
			throw new DaoException("Couldn't insert " + newEntity.getName());
		}
		return affected > 0 ? true : false;
	}
	
	@Transactional
	public boolean updateById(Company newEntity) throws DaoException {
		int affected = jdbcTemplate.update(UPDATE, new Object[] { newEntity });
		if (affected > 0) {
			throw new DaoException("Couldn't update " + newEntity.getName());
		}
		return affected > 0 ? true : false;
	}
	
	@Transactional
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
	
	@Transactional
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
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				log.error(e.getMessage());
				throw new DaoException(e.getMessage());
			}
		}

		return lineAffected > 0 ? true : false;
	}

}
