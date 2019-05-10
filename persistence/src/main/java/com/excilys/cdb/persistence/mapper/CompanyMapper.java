package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.persistence.DaoCompany;

@Component
public class CompanyMapper implements RowMapper<Company> {

	public static Company map(ResultSet rs) throws SQLException {

		Long id = rs.getLong("id");
		String name = rs.getString("name");

		return new Company(id, name);
	}

	public static CompanyDTO mapToDTO(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}

	public Optional<Company> mapToCompany(CompanyDTO dto, DaoCompany dao) throws BadInputException, DaoException {
		return Optional.ofNullable(new Company(dto.getId(), dao.getOneByName(dto.getName()).orElseThrow(BadInputException::new).getName()));
	}

	public Optional<Company> mapToCompany(ComputerDTO dto, DaoCompany dao) throws BadInputException, DaoException {
		return Optional.ofNullable(new Company(dto.getCompanyId(), dao.getOneById(dto.getCompanyId()).orElseThrow(BadInputException::new).getName()));
	}

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return map(rs);
	}
}
