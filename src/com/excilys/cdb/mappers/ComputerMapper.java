package com.excilys.cdb.mappers;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Entity;

public class ComputerMapper {

	static public Entity map(ResultSet rs) throws SQLException {
		
		BigInteger id = new BigInteger(rs.getBigDecimal("id").toString());
		String name = rs.getString("name");
		Date introduced = rs.getDate("introduced");
		Date discontinued = rs.getDate("discontinued");
		
		BigInteger manuId = new BigInteger(rs.getBigDecimal("company_id").toString());
		// TODO: Gérer la récupération d'un resultset pour l'entité company
		String manuName = rs.getString("");
		Company company = new Company(manuId, manuName);
		
		Computer computer = new Computer(id, name, introduced, discontinued, company);		
		return computer;
	}

}
