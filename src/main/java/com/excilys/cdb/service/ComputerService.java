package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DaoComputer;
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.IDaoInstance;
import com.excilys.cdb.validator.ServiceValidator;

public class ComputerService implements IService<ComputerDTO> {
  private DaoComputer dao;
  private static Logger log = LoggerFactory.getLogger(ComputerService.class);

  private ComputerService() {
  }

  private ComputerService(DaoComputer dao) {
    super();
    this.dao = dao;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.excilys.cdb.service.IService#getAll()
   */
  @Override
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
  @Override
  public Optional<List<ComputerDTO>> orderBy(String name, boolean isDesc) throws ServiceException {
    List<ComputerDTO> list = null;
    try {
      list = this.dao.getAllOrderedBy(name, isDesc).get().stream().map(ComputerMapper::mapToDTO).map(Optional::get)
          .collect(Collectors.toList());
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException(e.getMessage());
    }
    return Optional.ofNullable(list);
  }

  /**
   * @param name
   * @return
   * @throws DaoException
   * @throws ServiceException
   */
  @Override
  public Optional<List<ComputerDTO>> searchByName(String name) {
    String regex = "(?i)(.*)" + name + "(.*)";
    List<ComputerDTO> filteredComputers = null;
    filteredComputers = this.dao.getAll().get().stream()
        .filter(computer -> computer.getName().matches(regex) || computer.getCompany().getName().matches(regex))
        .map(ComputerMapper::mapToDTO).map(Optional::get).collect(Collectors.toList());

    return Optional.ofNullable(filteredComputers);
  }

  public Optional<List<ComputerDTO>> searchByNameOrdered(String name, String order, boolean isDesc) throws ServiceException {
    String regex = "(?i)(.*)" + name + "(.*)";
    List<ComputerDTO> filteredComputers = null;
    try {
      filteredComputers = this.dao.getAllOrderedBy(order, isDesc).get().stream()
          .filter(computer -> computer.getName().matches(regex) || computer.getCompany().getName().matches(regex))
          .map(ComputerMapper::mapToDTO).map(Optional::get).collect(Collectors.toList());
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException(e.getMessage());
    }

    return Optional.ofNullable(filteredComputers);
  }

  /*
   * @see com.excilys.cdb.service.IService#getOneById(java.lang.Long)
   */
  @Override
  public Optional<ComputerDTO> getOneById(Long id) throws BadInputException {
    ServiceValidator.idValidator(id, "Computer");

    return ComputerMapper.mapToDTO(this.dao.getOneById(id).get());
  }

  /*
   * @see com.excilys.cdb.service.IService#getOneByName(java.lang.String)
   */
  @Override
  public Optional<ComputerDTO> getOneByName(String name) throws BadInputException {
    ServiceValidator.nameValidator(name, "Computer");

    return ComputerMapper.mapToDTO(this.dao.getOneByName(name).get());
  }

  /*
   * @see com.excilys.cdb.service.IService#create(com.excilys.cdb.model.Entity)
   */
  @Override
  public boolean create(ComputerDTO newEntity) throws ServiceException {
    ServiceValidator.computerDTOValidator(newEntity, "Computer");
    Computer computer = ComputerMapper.mapToComputer(newEntity).get();
    return this.dao.create(computer);
  }

  /*
   * @see
   * com.excilys.cdb.service.IService#updateById(com.excilys.cdb.model.Entity)
   */
  @Override
  public boolean updateById(ComputerDTO newEntity) throws BadInputException {
    ServiceValidator.computerDTOValidator(newEntity, "Computer");
    Computer computer = ComputerMapper.mapToComputer(newEntity).get();

    return this.dao.updateById(computer);
  }

  /*
   * @see com.excilys.cdb.service.IService#deleteById(java.lang.Long)
   */
  @Override
  public boolean deleteById(Long id) throws BadInputException {
    ServiceValidator.idValidator(id, "Computer");

    return this.dao.deleteById(id);
  }

  /*
   * @see com.excilys.cdb.service.IService#deleteByName(java.lang.String)
   */
  @Override
  public boolean deleteByName(String name) throws ServiceException {
    ServiceValidator.nameValidator(name, "Computer");

    return this.dao.deleteByName(name);
  }

  public DaoComputer getDao() {
    return this.dao;
  }

  public void setDao(DaoComputer dao) {
    this.dao = dao;
  }
}
