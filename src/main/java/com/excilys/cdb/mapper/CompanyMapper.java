package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@Component
public class CompanyMapper implements RowMapper<Company> {
	private CompanyService companyService;

	static public Company map(ResultSet rs) throws SQLException {

		Long id = rs.getLong("id");
		String name = rs.getString("name");

		Company company = new Company(id, name);

		return company;
	}

	static public CompanyDTO mapToDTO(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}

	public Company mapToCompany(CompanyDTO dto, CompanyService service) throws BadInputException {
		return new Company(dto.getId(), service.getOneByName(dto.getName()).get().getName());
	}

	public Company mapToCompany(ComputerDTO dto, CompanyService service) throws BadInputException {
		System.out.println(dto);
		return new Company(dto.getCompanyId(), service.getOneById(dto.getCompanyId()).get().getName());
	}

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return map(rs);
	}
}
