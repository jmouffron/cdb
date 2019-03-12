package com.excilys.cdb.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Entity;

public class CompanyMapper {

	static public Entity map(ResultSet rs) throws SQLException {
		
		BigInteger id = new BigInteger(rs.getBigDecimal("id").toString());
		String name = rs.getString("name");
		
		Company company = new Company(id, name);
		
		return company;
	}

}
