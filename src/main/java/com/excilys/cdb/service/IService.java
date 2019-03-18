package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Entity;
import com.excilys.cdb.persistence.DaoInstance;

public interface IService<T extends Entity> {
	/**
	 * Commands the DAO related to the service to retrieve all entities T from the database
	 * 
	 * @return List<T extends Entity>
	 */
	public abstract List<T> getAll();

	/**
	 * Commands the DAO related to the service to retrieve an Entity T based on id
	 * 
	 * @param Long id
	 * @return <T extends Entity>
	 * @throws BadInputException
	 */
	public abstract T getOneById(Long id) throws BadInputException;

	/**
	 * Commands the Dao related to the service to retrieve an Entity T based on name
	 * 
	 * @param String name
	 * @return <T extends Entity>
	 * @throws BadInputException
	 */
	public abstract T getOneByName(String name) throws BadInputException;

	/**
	 * Commands the Dao related to the service to create a new entity
	 * 
	 * @param <T extends Entity> newEntity
	 * @return boolean
	 * @throws BadInputException
	 */
	public abstract boolean create(T newEntity) throws BadInputException;

	/**
	 * 
	 * Commands the Dao related to the service to update an Entity based on id and
	 * data to update the entity
	 * 
	 * @param <T extends Entity> newEntity
	 * @return boolean
	 * @throws BadInputException
	 */
	public abstract boolean updateById(T newEntity) throws BadInputException;

	/**
	 * Commands the Dao related to the service to delete an Entity based on id
	 * 
	 * @param id
	 * @return boolean
	 * @throws BadInputException
	 */
	public abstract boolean deleteById(Long id) throws BadInputException;

	/**
	 * 
	 * Commands the Dao related to the service to delete an Entity based on name
	 * 
	 * @param String name
	 * @return boolean
	 * @throws BadInputException
	 */
	public abstract boolean deleteByName(String name) throws BadInputException;

	/**
	 * Returns the dao used by the service
	 * 
	 * @return DaoInstance<T extends Entity>
	 */
	public abstract DaoInstance<T> getDao();

	/**
	 * Sets a new Dao on the service
	 * 
	 * @param DaoInstance<T extends Entity> dao
	 */
	public abstract void setDao(DaoInstance<T> dao);

	/**
	 * Returns all the entities T that match the name in parameter
	 * 
	 * @param String name
	 * @return List<T extends Entity> 
	 */
	public abstract List<T> searchByName(String name);
}
