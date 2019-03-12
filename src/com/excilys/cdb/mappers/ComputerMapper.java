package com.excilys.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	static public Computer map(ResultSet rs) throws SQLException {
		
		Long id = rs.getLong("id");
		String name = rs.getString("name");
		Timestamp introduced = rs.getTimestamp("introduced");
		Timestamp discontinued = rs.getTimestamp("discontinued");
		
		Long manuId = rs.getLong("company_id");
		String manuName = rs.getString("manu_name");
		Company company = new Company(manuId, manuName);
		
		Computer computer = new Computer(id, name, introduced, discontinued, company);		
		return computer;
	}

}
