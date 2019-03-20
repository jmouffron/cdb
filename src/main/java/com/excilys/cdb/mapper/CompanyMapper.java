package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyMapper {

	static public Company map(ResultSet rs) throws SQLException {

		Long id = rs.getLong("id");
		String name = rs.getString("name");

		Company company = new Company(id, name);

		return company;
	}
	
	 static public CompanyDTO mapDTO(Company company) throws SQLException {
	    return new CompanyDTO(company.getId(), company.getName());
	  }
}
