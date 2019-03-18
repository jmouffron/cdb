package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

/**
 * @author excilys
 *
 */
public class DaoCompany implements DaoInstance<Company> {
	private final String INSERT = "INSERT INTO company VALUES(?);";
	private final String SELECT_ALL = "SELECT * FROM company;";
	private final String SELECT_ID = "SELECT * FROM company where id=?;";
	private final String SELECT_NAME = "SELECT * FROM company where name=?;";
	private final String UPDATE = "UPDATE company SET name=? WHERE id=?;";
	private final String DELETE_ID = "DELETE FROM company WHERE id=? ;";
	private final String DELETE_NAME = "DELETE FROM company WHERE name=? ;";
	private final Logger log = LoggerFactory.getLogger(DaoCompany.class);
	
	@Override
	public List<Company> getAll() {
		List<Company> result = new ArrayList<>();

		try (Statement stmt = Datasource.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL);) {
			while (rs.next()) {
				result.add(CompanyMapper.map(rs));
			}
		} catch (SQLException sqlex) {
			log.debug(sqlex.getMessage());
		}

		return result;
	}

	@Override
	public Company getOneById(Long id) {
		Company result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = Datasource.getConnection().prepareStatement(SELECT_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			result = CompanyMapper.map(rs);
		} catch (SQLException sqlex) {
			log.debug(sqlex.getMessage());
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				log.debug(e.getMessage());
			}

		}
		return result;
	}

	@Override
	public Company getOneByName(String name) {
		Company result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = Datasource.getConnection().prepareStatement(SELECT_NAME);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			rs.next();
			result = CompanyMapper.map(rs);
		} catch (SQLException sqlex) {
			log.debug(sqlex.getMessage());
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				log.debug(e.getMessage());
			}
		}

		return result;
	}

	@Override
	public boolean create(Company newEntity) {
		int lineAffected = 0;

		try (PreparedStatement stmt = Datasource.getConnection().prepareStatement(INSERT);) {
			stmt.setString(1, newEntity.getName());
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			log.debug(sqlex.getMessage());
		}

		return lineAffected > 0 ? true : false;
	}

	@Override
	public boolean updateById(Company newEntity) {
		int lineAffected = 0;

		try (PreparedStatement stmt = Datasource.getConnection().prepareStatement(UPDATE);) {
			stmt.setString(1, newEntity.getName());
			stmt.setLong(2, newEntity.getId());
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			log.debug(sqlex.getMessage());
		}

		return lineAffected > 0 ? true : false;
	}

	@Override
	public boolean deleteById(Long id) {
		int lineAffected = 0;

		try (PreparedStatement stmt = Datasource.getConnection().prepareStatement(DELETE_ID);) {
			stmt.setLong(1, id);
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			log.debug(sqlex.getMessage());
		}

		return lineAffected > 0 ? true : false;
	}

	@Override
	public boolean deleteByName(String name) {
		int lineAffected = 0;

		try (PreparedStatement stmt = Datasource.getConnection().prepareStatement(DELETE_NAME);) {
			stmt.setString(1, name);
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			log.debug(sqlex.getMessage());
		}

		return lineAffected > 0 ? true : false;
	}

}
