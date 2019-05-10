package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.BadInputException;
import com.excilys.cdb.binding.exception.DaoException;
import com.excilys.cdb.binding.exception.ServiceException;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.persistence.mapper.ComputerMapper;

@Service
@Transactional
public class ComputerService {
	private DaoComputer dao;
	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);

	public ComputerService(DaoComputer dao) {
		this.dao = dao;
	}

	@Transactional(readOnly = true)
	public Optional<List<ComputerDTO>> getAll() throws DaoException {
		List<ComputerDTO> list = null;
		list = this.dao.getAll().orElseThrow(DaoException::new).stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());

		return Optional.ofNullable(list);
	}
	
	@Transactional(readOnly = true)
	public Optional<List<ComputerDTO>> orderBy(String name, boolean isDesc) throws ServiceException, DaoException {
		List<ComputerDTO> list = null;
		list = this.dao.getAllOrderedBy(name, isDesc).orElseThrow(ServiceException::new).stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());
		return Optional.ofNullable(list);
	}

	@Transactional(readOnly = true)
	public Optional<List<ComputerDTO>> searchByName(String name) throws DaoException {
		String regex = "(?i)(.*)" + name + "(.*)";
		List<ComputerDTO> filteredComputers = null;
		filteredComputers = this.dao.getAll().orElseThrow(DaoException::new).stream()
				.filter(computer -> computer.getName().matches(regex) || computer.getCompany().getName().matches(regex))
				.map(ComputerMapper::mapToDTO).map(Optional::get).collect(Collectors.toList());

		return Optional.ofNullable(filteredComputers);
	}
	
	@Transactional(readOnly = true)
	public Optional<List<ComputerDTO>> searchByNameOrdered(String name, String order, boolean isDesc)
			throws ServiceException {
		List<ComputerDTO> filteredComputers = null;
		filteredComputers = this.dao.searchBy(name).orElseThrow(ServiceException::new).stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());

		return Optional.ofNullable(filteredComputers);
	}

	@Transactional(readOnly = true)
	public Optional<ComputerDTO> getOneById(Long id) throws BadInputException {
		return ComputerMapper.mapToDTO(this.dao.getOneById(id).orElseThrow(BadInputException::new));
	}
	
	@Transactional(readOnly = true)
	public Optional<ComputerDTO> getOneByName(String name) throws BadInputException {
		return ComputerMapper.mapToDTO(this.dao.getOneByName(name).orElseThrow(BadInputException::new));
	}
	
	public void create(Computer newEntity) throws DaoException {
		this.dao.create(newEntity);
	}

	public void updateById(Computer newEntity) throws DaoException {
		this.dao.updateById(newEntity);
	}

	public void deleteById(Long id) throws DaoException {
		this.dao.deleteById(id);
	}

	public void deleteByName(String name) throws DaoException {
		this.dao.deleteByName(name);
	}

	public DaoComputer getDao() {
		return this.dao;
	}

	public void setDao(DaoComputer dao) {
		this.dao = dao;
	}

	public Optional<List<ComputerDTO>> getPage(Long page) throws DaoException {
		List<ComputerDTO> list = null;
		list = this.dao.getAll().orElseThrow(DaoException::new).stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());

		return Optional.ofNullable(list);
	}
}
