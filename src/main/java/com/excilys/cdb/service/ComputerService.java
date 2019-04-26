package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.validator.ServiceValidator;

@Service
public class ComputerService {
	@Autowired
	private DaoComputer dao;
	private ComputerMapper pcMapper;
	private CompanyService corpService;
	private static Logger log = LoggerFactory.getLogger(ComputerService.class);


	public ComputerService(DaoComputer dao, CompanyService service, ComputerMapper mapper) {
		this.dao = dao;
		this.corpService = service;
		this.pcMapper = mapper;
	}

	public Optional<List<ComputerDTO>> getAll() {
		List<ComputerDTO> list = null;
		list = this.dao.getAll().get().stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());

		return Optional.ofNullable(list);
	}

	public Optional<List<ComputerDTO>> orderBy(String name, boolean isDesc) throws ServiceException, DaoException {
		List<ComputerDTO> list = null;
		list = this.dao.getAllOrderedBy(name, isDesc).get().stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());
		return Optional.ofNullable(list);
	}

	public Optional<List<ComputerDTO>> searchByName(String name) {
		String regex = "(?i)(.*)" + name + "(.*)";
		List<ComputerDTO> filteredComputers = null;
		filteredComputers = this.dao.getAll().get().stream()
				.filter(computer -> computer.getName().matches(regex) || computer.getCompany().getName().matches(regex))
				.map(ComputerMapper::mapToDTO).map(Optional::get).collect(Collectors.toList());

		return Optional.ofNullable(filteredComputers);
	}

	public Optional<List<ComputerDTO>> searchByNameOrdered(String name, String order, boolean isDesc)
			throws ServiceException, DaoException {
		String regex = "(?i)(.*)" + name + "(.*)";
		List<ComputerDTO> filteredComputers = null;
		filteredComputers = this.dao.getAllOrderedBy(order, isDesc).get().stream()
				.filter(computer -> computer.getName().matches(regex) || computer.getCompany().getName().matches(regex))
				.map(ComputerMapper::mapToDTO).map(Optional::get).collect(Collectors.toList());

		return Optional.ofNullable(filteredComputers);
	}

	public Optional<ComputerDTO> getOneById(Long id) throws BadInputException {
		return ComputerMapper.mapToDTO(this.dao.getOneById(id).get());
	}
	
	public Optional<ComputerDTO> getOneByName(String name) throws BadInputException {
		return ComputerMapper.mapToDTO(this.dao.getOneByName(name).get());
	}

	public void create(Computer newEntity) throws ServiceException, DaoException {
		this.dao.create(newEntity);
	}
	
	public void updateById(Computer newEntity) throws BadInputException, DaoException {
		this.dao.updateById(newEntity);
	}

	public void deleteById(Long id) throws BadInputException, DaoException {
		this.dao.deleteById(id);
	}

	public void deleteByName(String name) throws ServiceException, DaoException {
		ServiceValidator.nameValidator(name, "Computer");
		this.dao.deleteByName(name);
	}

	public DaoComputer getDao() {
		return this.dao;
	}

	public void setDao(DaoComputer dao) {
		this.dao = dao;
	}
}
