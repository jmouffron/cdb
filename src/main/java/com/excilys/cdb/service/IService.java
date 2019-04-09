package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dto.EntityDTO;
import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.exception.DaoException;
import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.persistence.IDaoInstance;

public interface IService<EntityDTO> {
  /**
   * Commands the DAO related to the service to retrieve all entities T from the
   * database
   * 
   * @return List<T extends EntityDTO>
   */
  public abstract Optional<List<EntityDTO>> getAll() throws ServiceException;

  /**
   * Commands the DAO related to the service to retrieve all entities T from the
   * database ordered By a String name and a boolean isDesc
   * 
   * @return List<T extends EntityDTO>
   * @throws DaoException 
   */
  public abstract Optional<List<EntityDTO>> orderBy(String name, boolean isDesc) throws ServiceException, DaoException;

  /**
   * Returns all the entities T that match the name in parameter
   * 
   * @param String name
   * @return List<T extends EntityDTO>
   * @throws ServiceException
   */
  public abstract Optional<List<EntityDTO>> searchByName(String name) throws ServiceException;

  /**
   * Commands the DAO related to the service to retrieve an EntityDTO T based on id
   * 
   * @param Long id
   * @return <T extends EntityDTO>
   * @throws BadInputException
   */
  public abstract Optional<EntityDTO> getOneById(Long id) throws BadInputException, ServiceException;

  /**
   * Commands the Dao related to the service to retrieve an EntityDTO T based on name
   * 
   * @param String name
   * @return <T extends EntityDTO>
   * @throws BadInputException
   */
  public abstract Optional<EntityDTO> getOneByName(String name) throws BadInputException, ServiceException;

  /**
   * Commands the Dao related to the service to create a new entity
   * 
   * @param <T extends EntityDTO> newEntityDTO
   * @return boolean
   * @throws BadInputException
   * @throws ServiceException
   * @throws DaoException 
   */
  public abstract boolean create(EntityDTO newEntity) throws ServiceException, DaoException;

  /**
   * Commands the Dao related to the service to update an EntityDTO based on id and
   * data to update the entity
   * 
   * @param <T extends EntityDTO> newEntityDTO
   * @return boolean
   * @throws BadInputException
   * @throws ServiceException
   * @throws DaoException 
   */
  public abstract boolean updateById(EntityDTO newEntity) throws ServiceException, DaoException;

  /**
   * Commands the Dao related to the service to delete an EntityDTO based on id
   * 
   * @param id
   * @return boolean
   * @throws BadInputException
   * @throws ServiceException
   * @throws DaoException 
   */
  public abstract boolean deleteById(Long id) throws BadInputException, ServiceException, DaoException;

  /**
   * Commands the Dao related to the service to delete an EntityDTO based on name
   * 
   * @param String name
   * @return boolean
   * @throws BadInputException
   * @throws DaoException 
   */
  public abstract boolean deleteByName(String name) throws BadInputException, ServiceException, DaoException;

}
