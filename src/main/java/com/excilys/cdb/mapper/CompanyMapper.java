package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

public class CompanyMapper implements RowMapper<Company> {
  private static CompanyService companyService;
  
	static public Company map(ResultSet rs) throws SQLException {

		Long id = rs.getLong("id");
		String name = rs.getString("name");

		Company company = new Company(id, name);

		return company;
	}
	
	 static public CompanyDTO mapToDTO(Company company){
	    return new CompanyDTO(company.getId(), company.getName());
	  }

  public static Company mapToCompany(CompanyDTO dto) throws BadInputException {
    CompanyService service = companyService;
    return new Company( dto.getId(), service.getOneByName(dto.getName()).get().getName());
  }
  
  public static Company mapToCompany(ComputerDTO dto) throws BadInputException {
    CompanyService service = companyService;
    return new Company( dto.getCompanyId(), service.getOneByName(dto.getCompanyName()).get().getName());
  }

  @Override
  public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
    return map(rs);
  }
}
