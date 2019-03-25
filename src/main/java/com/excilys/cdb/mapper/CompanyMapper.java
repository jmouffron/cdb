package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ServiceFactory;

public class CompanyMapper {

	static public Company map(ResultSet rs) throws SQLException {

		Long id = rs.getLong("id");
		String name = rs.getString("name");

		Company company = new Company(id, name);

		return company;
	}
	
	 static public CompanyDTO mapToDTO(Company company){
	    return new CompanyDTO(company.getId(), company.getName());
	  }

  public static Company mapToCompany(long newEntityId) throws BadInputException {
    CompanyService service = ServiceFactory.getCompanyService();
    return new Company(newEntityId, service.getOneById(newEntityId).get().getName());
  }
}
