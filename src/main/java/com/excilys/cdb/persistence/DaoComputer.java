package com.excilys.cdb.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Entity;

public class DaoComputer implements DaoInstance<Computer> {

	private final String INSERT = "INSERT INTO computer VALUES(?,?,?,?);";
	private final String SELECT_ALL = "SELECT pc.*, c.id, c.name FROM computer pc "
			+ "INNER JOIN company c ON pc.company_id=c.id";
	private final String SELECT_ID = SELECT_ALL + " where pc.id=? ;";
	private final String SELECT_NAME = SELECT_ALL + " where pc.name=? ;";
	private final String UPDATE = "UPDATE computer SET name=?,introduced=?,discontinued=?,company_id=? WHERE id=? ;";
	private final String DELETE_ID = "DELETE FROM computer WHERE id=? ;";
	private final String DELETE_NAME = "DELETE FROM computer WHERE name=? ;";

	@Override
	public List<Computer> getAll() {
		List<Computer> result = new ArrayList<>();

		try (Statement stmt = Datasource.getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL);) {

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
			stmt = Datasource.getConnection().prepareStatement(SELECT_ID);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			rs.next();
			result = ComputerMapper.map(rs);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
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
	public Computer getOneByName(String name) {
		Computer result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = Datasource.getConnection().prepareStatement(SELECT_NAME);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			rs.next();
			result = ComputerMapper.map(rs);
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
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
	public boolean create(Entity newEntity) {
		int linesAffected = 0;

		try (PreparedStatement stmt = Datasource.getConnection().prepareStatement(INSERT);) {
			stmt.setString(1, ((Computer) newEntity).getName());
			stmt.setTimestamp(2, ((Computer) newEntity).getIntroduced());
			stmt.setTimestamp(3, ((Computer) newEntity).getDiscontinued());
			stmt.setLong(4, ((Computer) newEntity).getCompany().getId());
			linesAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
		}

		return linesAffected > 0 ? true : false;
	}

	@Override
	public boolean updateById(Long id, Computer newEntity) {
		int lineAffected = 0;

		try (PreparedStatement stmt = Datasource.getConnection().prepareStatement(UPDATE);) {
			stmt.setString(1, newEntity.getName());
			stmt.setTimestamp(2, newEntity.getIntroduced());
			stmt.setTimestamp(3, newEntity.getDiscontinued());
			stmt.setLong(4, newEntity.getCompany().getId());
			lineAffected = stmt.executeUpdate();
		} catch (SQLException sqlex) {
			System.out.println(sqlex.getMessage());
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
			System.out.println(sqlex.getMessage());
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
			System.out.println(sqlex.getMessage());
		}

		return lineAffected > 0 ? true : false;
	}

}
