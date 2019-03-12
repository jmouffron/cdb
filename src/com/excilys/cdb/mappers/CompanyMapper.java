package com.excilys.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;

public class CompanyMapper {

	static public Company map(ResultSet rs) throws SQLException {
		
		Long id = rs.getLong("id");
		String name = rs.getString("name");
		
		Company company = new Company(id, name);
		
		return company;
	}

}
