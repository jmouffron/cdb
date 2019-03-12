package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class DaoComputer implements DaoInstance<Computer> {
	private final String INSERT = "INSERT INTO computer VALUES(?,?,?,?);";
	private final String SELECT_ALL = "Select pc.*, c.id, c.name from computer pc "
			+ "INNER JOIN company c ON pc.company_id=c.id";
	private final String SELECT_ID = SELECT_ALL + " where pc.id=? ;";
	private final String UPDATE = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=? ;";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=? ;";
	private final String DELETE_NAME = "DELETE FROM computer WHERE name=? ;";
	
	private Connection conn;
	
	DaoComputer() {
		this.conn = Datasource.getConnection();
	}

	@Override
	public List<Computer> getAll() {
		List<Computer> result = new ArrayList<>();
		try (Statement stmt = this.conn.createStatement(); ResultSet rs = stmt.executeQuery(SELECT_ALL);) {
			while (rs.next()) {
				result.add(ComputerMapper.map(rs));
			}
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
		}

		return result;
	}

	@Override
	public Computer getOneById(Long id) {
		Computer result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = this.conn.prepareStatement(SELECT_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			result = ComputerMapper.map(rs);
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
	public boolean create(Computer newEntity) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int linesAffected = 0;
		try {
			stmt = this.conn.prepareStatement(INSERT);
			stmt.setString(1, newEntity.getName());
			stmt.setTimestamp(2, newEntity.getIntroduced());
			stmt.setTimestamp(3, newEntity.getDiscontinued());
			stmt.setLong(4, newEntity.getCompany().getId());
			linesAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return linesAffected > 0 ? true : false;
	}

	@Override
	public boolean updateById(Long id, Computer newEntity) {
		PreparedStatement stmt = null;
		int lineAffected = 0;
		try {
			stmt = this.conn.prepareStatement(UPDATE);
			stmt.setString(1, newEntity.getName());
			stmt.setTimestamp(2, newEntity.getIntroduced());
			stmt.setTimestamp(3, newEntity.getDiscontinued());
			stmt.setLong(4, newEntity.getCompany().getId());
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
	public boolean deleteById(Long id) {
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
	public boolean deleteByName(String name) {
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
