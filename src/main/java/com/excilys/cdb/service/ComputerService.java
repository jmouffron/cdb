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
import com.excilys.cdb.persistence.DaoComputerFactory;
import com.excilys.cdb.persistence.IDaoInstance;
import com.excilys.cdb.validator.ServiceValidator;

public class ComputerService implements IService<ComputerDTO> {
  private static ComputerService instance;
	private IDaoInstance<Computer> dao;
	private Logger log;

	private ComputerService() {
		this.dao = DaoComputerFactory.getComputerFactory().getDao();
		this.log = LoggerFactory.getLogger(ComputerService.class);
	}
	
	public static ComputerService getService() {
	  if ( instance == null ) {
	    synchronized( ComputerService.class ) {
	      if (instance == null ) {
	        return new ComputerService();
	      }
	    }
	  }
	  return instance;
	}

	/* (non-Javadoc)
	 * @see com.excilys.cdb.service.IService#getAll()
	 */
	@Override
	public Optional<List<ComputerDTO>> getAll() throws ServiceException {
	  List<ComputerDTO> list = null;
    try {
      list = this.dao
          .getAll()
          .get().stream()
          .map(ComputerMapper::mapToDTO)
          .map(Optional::get)
          .collect(Collectors.toList());
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException("Couldn't order by in computers !");
    }

    return Optional.ofNullable(list);
	}
	
	/* (non-Javadoc)
	 * @see com.excilys.cdb.service.IService#orderBy(java.lang.String, boolean)
	 */
	@Override
  public Optional<List<ComputerDTO>> orderBy(String name, boolean isDesc) throws ServiceException {
      List<ComputerDTO> list = null;
      try {
        list = this.dao.getAllOrderedBy(name, isDesc).get().stream()
            .map(ComputerMapper::mapToDTO)
            .map(Optional::get)
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
	public Optional<List<ComputerDTO>> searchByName(String name) throws ServiceException {
	  String regex = "(?i)(.*)" + name + "(.*)";
		List<ComputerDTO> filteredComputers = null;
    try {
      filteredComputers = this.dao.getAll().get()
            .stream()
        		.filter(computer -> computer.getName().matches(regex) || computer.getCompany().getName().matches(regex) )
        		.map(ComputerMapper::mapToDTO)
        		.map(Optional::get)
        		.collect(Collectors.toList());
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException("Couldn't search in computers !");
    }
		
		return Optional.ofNullable(filteredComputers);
	}

	/*
	 * @see com.excilys.cdb.service.IService#getOneById(java.lang.Long)
	 */
	@Override
	public Optional<ComputerDTO> getOneById(Long id) throws BadInputException {
		ServiceValidator.idValidator(id, "Computer");

		return ComputerMapper.mapToDTO( this.dao.getOneById(id).get() );
	}

	/*
	 * @see com.excilys.cdb.service.IService#getOneByName(java.lang.String)
	 */
	@Override
	public Optional<ComputerDTO> getOneByName(String name) throws BadInputException {
		ServiceValidator.nameValidator(name, "Computer");

		return ComputerMapper.mapToDTO( this.dao.getOneByName(name).get() );
	}

	/*
	 * @see com.excilys.cdb.service.IService#create(com.excilys.cdb.model.Entity)
	 */
	@Override
	public boolean create(ComputerDTO newEntity) throws ServiceException {
		ServiceValidator.computerDTOValidator(newEntity, "Computer");
		Computer computer = ComputerMapper.mapToComputer(newEntity).get();
		try {
      return this.dao.create(computer);
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException("Couldn't create Computer");
    }
	}

	/*
	 * @see
	 * com.excilys.cdb.service.IService#updateById(com.excilys.cdb.model.Entity)
	 */
	@Override
	public boolean updateById(ComputerDTO newEntity) throws ServiceException {
		ServiceValidator.computerDTOValidator(newEntity, "Computer");
    Computer computer = ComputerMapper.mapToComputer(newEntity).get();
    
		try {
      return this.dao.updateById(computer);
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException("Couldn't update entity!");
    }
	}

	/*
	 * @see com.excilys.cdb.service.IService#deleteById(java.lang.Long)
	 */
	@Override
	public boolean deleteById(Long id) throws ServiceException {
		ServiceValidator.idValidator(id, "Computer");

		try {
      return this.dao.deleteById(id);
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException("Couldn't delete by id!");
    }
	}

	/*
	 * @see com.excilys.cdb.service.IService#deleteByName(java.lang.String)
	 */
	@Override
	public boolean deleteByName(String name) throws ServiceException {
		ServiceValidator.nameValidator(name, "Computer");

		try {
      return this.dao.deleteByName(name);
    } catch (DaoException e) {
      log.error(e.getMessage());
      throw new ServiceException("Couldn't delete by id!");
    }
	}

	/*
	 * @see com.excilys.cdb.service.IService#getDao()
	 */
	@Override
	public IDaoInstance getDao() {
		return this.dao;
	}

  @Override
  public void setDao(IDaoInstance dao) {
    this.dao = dao;    
  }
}
