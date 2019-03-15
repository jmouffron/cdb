package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Entity;
import com.excilys.cdb.persistence.DaoInstance;

public interface IService<T extends Entity> {
	public abstract List<T> getAll();

	/**
	 * Commands the DAO related to the service to 
	 * retrieve an Entity based on id
	 * 
	 * @param id
	 * @return
	 * @throws BadInputException
	 */
	public abstract T getOneById(Long id) throws BadInputException;

	/**
	 * Commands the Dao related to the service to
	 * retrieve an Entity based on name
	 *  
	 * @param name
	 * @return
	 * @throws BadInputException
	 */
	public abstract T getOneByName(String name) throws BadInputException;

	/**
	 * Commands the Dao related to the service to
	 * create a new entity
	 * 
	 * @param newEntity
	 * @return
	 * @throws BadInputException
	 */
	public abstract boolean create(T newEntity) throws BadInputException;

	/**
	 * 
	 * Commands the Dao related to the service to
	 * update an Entity based on id and data to update the entity
	 * 
	 * @param id
	 * @param newEntity
	 * @return
	 * @throws BadInputException
	 */
	public abstract boolean updateById(Long id, T newEntity) throws BadInputException;

	/**
	 * Commands the Dao related to the service to
	 * delete an Entity based on id
	 * 
	 * @param id
	 * @return
	 * @throws BadInputException
	 */
	public abstract boolean deleteById(Long id) throws BadInputException;

	/**
	 * 
	 * Commands the Dao related to the service to
	 * delete an Entity based on name
	 * 
	 * @param name
	 * @return
	 * @throws BadInputException
	 */
	public abstract boolean deleteByName(String name) throws BadInputException;

	/**
	 * Returns the dao used by the service
	 * 
	 * @return DaoInstance<T extends Entity>
	 */
	public abstract DaoInstance<T> getDao();
}
