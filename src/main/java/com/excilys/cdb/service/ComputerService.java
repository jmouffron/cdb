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
	private static Logger log = LoggerFactory.getLogger(ComputerService.class);

	public ComputerService() {
	}

	public ComputerService(DaoComputer dao) {
		super();
		this.dao = dao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.cdb.service.IService#getAll()
	 */

	public Optional<List<ComputerDTO>> getAll() {
		List<ComputerDTO> list = null;
		list = this.dao.getAll().get().stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());

		return Optional.ofNullable(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.excilys.cdb.service.IService#orderBy(java.lang.String, boolean)
	 */

	public Optional<List<ComputerDTO>> orderBy(String name, boolean isDesc) throws ServiceException, DaoException {
		List<ComputerDTO> list = null;
		list = this.dao.getAllOrderedBy(name, isDesc).get().stream().map(ComputerMapper::mapToDTO).map(Optional::get)
				.collect(Collectors.toList());
		return Optional.ofNullable(list);
	}

	/**
	 * @param name
	 * @return
	 * @throws DaoException
	 * @throws ServiceException
	 */

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

	/*
	 * @see com.excilys.cdb.service.IService#getOneById(java.lang.Long)
	 */

	public Optional<ComputerDTO> getOneById(Long id) throws BadInputException {
		ServiceValidator.idValidator(id, "Computer");

		return ComputerMapper.mapToDTO(this.dao.getOneById(id).get());
	}

	/*
	 * @see com.excilys.cdb.service.IService#getOneByName(java.lang.String)
	 */

	public Optional<ComputerDTO> getOneByName(String name) throws BadInputException {
		ServiceValidator.nameValidator(name, "Computer");

		return ComputerMapper.mapToDTO(this.dao.getOneByName(name).get());
	}

	/*
	 * @see com.excilys.cdb.service.IService#create(com.excilys.cdb.model.Entity)
	 */
	public void create(ComputerDTO newEntity) throws ServiceException, DaoException {
		ServiceValidator.computerDTOValidator(newEntity, "Computer");
		Computer computer = ComputerMapper.mapToComputer(newEntity).get();

		this.dao.create(computer);
	}

	/*
	 * @see
	 * com.excilys.cdb.service.IService#updateById(com.excilys.cdb.model.Entity)
	 */

	public void updateById(ComputerDTO newEntity) throws BadInputException, DaoException {
		ServiceValidator.computerDTOValidator(newEntity, "Computer");
		Computer computer = ComputerMapper.mapToComputer(newEntity).get();

		this.dao.updateById(computer);
	}

	/*
	 * @see com.excilys.cdb.service.IService#deleteById(java.lang.Long)
	 */
	public void deleteById(Long id) throws BadInputException, DaoException {
		ServiceValidator.idValidator(id, "Computer");
		this.dao.deleteById(id);
	}

	/*
	 * @see com.excilys.cdb.service.IService#deleteByName(java.lang.String)
	 */

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
