package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.persistence.DaoCompany;
import com.excilys.cdb.core.utils.DateUtils;

@Component
public class ComputerMapper implements RowMapper<Computer>{
  private CompanyMapper corpMapper;
  private static final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
  
  public ComputerMapper(CompanyMapper mapper) {
	  this.corpMapper = mapper;
  }
  
  public static Computer map(ResultSet rs) throws SQLException {

    Long id = rs.getLong("id");
    String name = rs.getString("pc_name");
    Timestamp introduced = rs.getTimestamp("introduced");
    Timestamp discontinued = rs.getTimestamp("discontinued");

    Long companyId = rs.getLong("company_id");
    String companyName = rs.getString("cp_name");

    Company company = new Company(companyId, companyName);

    return new Computer.ComputerBuilder().setId(id).setName(name).setIntroduced(introduced)
        .setDiscontinued(discontinued).setCompany(company).build();
  }

  public static Optional<ComputerDTO> mapToDTO(Computer computer) {
	boolean isCompanyNull = computer.getCompany() == null;
    ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder().setId(computer.getId()).setName(computer.getName())
        .setIntroduced(computer.getIntroduced() == null ? null : DateUtils.stringToDateString( computer.getIntroduced() ) )
        .setDiscontinued(computer.getDiscontinued() == null ? null : DateUtils.stringToDateString( computer.getDiscontinued() ) )
        .setCompanyId(isCompanyNull ? 0 : computer.getCompany().getId())
        .setCompanyName(isCompanyNull ? null : computer.getCompany().getName()).build();

    return Optional.ofNullable(computerDTO);
  }

  public Optional<Computer> mapToComputer(ComputerDTO dto, DaoCompany dao) throws BadInputException, DaoException {
    Optional<Company> company = corpMapper.mapToCompany(dto, dao);
    Computer computer = new Computer.ComputerBuilder()
        .setId( dto.getId())
        .setName( dto.getName())
        .setIntroduced( dto.getIntroduced().isEmpty() ? null : DateUtils.stringToTimestamp( dto.getIntroduced() ) )
        .setDiscontinued( dto.getDiscontinued().isEmpty() ? null :DateUtils.stringToTimestamp( dto.getDiscontinued())  )
        .setCompany(company.orElseThrow(BadInputException::new))
        .build();

    return Optional.ofNullable(computer);
  }

  public static Optional<List<ComputerDTO>> mapToDTOList(List<Computer> computers) {
    return Optional.ofNullable(computers.stream().map(computer -> new ComputerDTO.ComputerDTOBuilder().setId(computer.getId()).setName(computer.getName())
          .setIntroduced(computer.getIntroduced() == null ? null : computer.getIntroduced().toString())
          .setDiscontinued(computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString())
          .setCompanyName(computer.getCompany().getName()).build()
    ).collect(Collectors.toList()));
  }

  @Override
  public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
    return map(rs);
  }

}
