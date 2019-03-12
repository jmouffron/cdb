package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.model.Company;

/**
 * @author excilys
 *
 */
public class DaoCompany extends DaoInstance<Company> {
	private final String INSERT = "INSERT INTO company VALUES(?);";
	private final String SELECT_ALL = "Select "
			+ "pc.name, pc.introduced, pc.discontinued, c.id c.name as manu_name pc " + "from company pc "
			+ "inner join " + "company c " + "on pc.company_id=c.id;";
	private final String SELECT_ID = "Select * from company where id=?;";
	private final String UPDATE = "UPDATE company SET name=? WHERE id=?;";
	private final String DELETE_ID = "DELETE FROM company WHERE id=? ;";
	private final String DELETE_NAME = "DELETE FROM company WHERE name=? ;";

	DaoCompany() {
		super();
	}

	@Override
	List<Company> getAll() {
		List<Company> result = new ArrayList<>();
		try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL);) {
			while (rs.next()) {
				result.add(CompanyMapper.map(rs));
			}
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
		}

		return result;
	}

	@Override
	Company getOneById(Long id) {
		Company result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = this.conn.prepareStatement(SELECT_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			result = CompanyMapper.map(rs);
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	@Override
	boolean create(Company newEntity) {
		PreparedStatement stmt = null;
		int lineAffected = 0;
		try {
			stmt = this.conn.prepareStatement(INSERT);
			stmt.setString(1, newEntity.getName());
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lineAffected > 0 ? true : false;
	}

	@Override
	boolean updateById(Long id, Company newEntity) {
		PreparedStatement stmt = null;
		int lineAffected = 0;
		try {
			stmt = this.conn.prepareStatement(UPDATE);
			stmt.setString(1, newEntity.getName());
			stmt.setLong(2, id);
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
			try {
				stmt.close();
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lineAffected > 0 ? true : false;
	}

	@Override
	boolean deleteById(Long id) {
		PreparedStatement stmt = null;
		int lineAffected = 0;
		try {
			stmt = this.conn.prepareStatement(DELETE_ID);
			stmt.setLong(1, id);
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lineAffected > 0 ? true : false;
	}

	@Override
	boolean deleteByName(String name) {
		PreparedStatement stmt = null;
		int lineAffected = 0;
		try {
			stmt = this.conn.prepareStatement(DELETE_NAME);
			stmt.setString(1, name);
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lineAffected > 0 ? true : false;
	}

}
