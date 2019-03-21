package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	static public Computer map(ResultSet rs) throws SQLException {

		Long id = rs.getLong("id");
		String name = rs.getString("name");
		Timestamp introduced = rs.getTimestamp("introduced");
		Timestamp discontinued = rs.getTimestamp("discontinued");

		Long companyId = rs.getLong("company_id");
		String companyName = rs.getString("c.name");

		Company company = new Company(companyId, companyName);

		Computer computer = new Computer.ComputerBuilder().setId(id).setName(name).setIntroduced(introduced)
				.setDiscontinued(discontinued).setCompany(company).build();

		return computer;
	}
	
	static public Optional<ComputerDTO> mapDTO(Computer computer) {
	  
	  ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder()
	      .setId(computer.getId())
	      .setName(computer.getName())
	      .setIntroduced(computer.getIntroduced() == null ? null:computer.getIntroduced().toString())
	      .setDiscontinued(computer.getDiscontinued() == null ? null:computer.getDiscontinued().toString())
	      .setCompanyId(computer.getCompany().getId())
	      .build();

    return Optional.ofNullable(computerDTO);
  }
	
	 static public Optional<List<ComputerDTO>> mapDTOList(List<Computer> computers) {
	    return Optional.ofNullable(computers
          	      .stream()
          	      .map(computer -> {
          	        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder()
          	            .setId(computer.getId())
          	            .setName(computer.getName())
          	            .setIntroduced(computer.getIntroduced() == null ? null:computer.getIntroduced().toString())
          	            .setDiscontinued(computer.getDiscontinued() == null ? null:computer.getDiscontinued().toString())
          	            .setCompanyId(computer.getCompany().getId())
          	            .build();
          	       return computerDTO;
          	      })
          	      .collect(Collectors.toList())
    	      );
	  }

}
