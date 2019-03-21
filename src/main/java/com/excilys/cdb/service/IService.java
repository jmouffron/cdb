package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.IDaoInstance;

public interface IService<Entity> {
	/**
	 * Commands the DAO related to the service to retrieve all entities T from the
	 * database
	 * 
	 * @return List<T extends Entity>
	 */
	public abstract Optional<List<Entity>> getAll();

	/**
	 * Commands the DAO related to the service to retrieve an Entity T based on id
	 * 
	 * @param Long id
	 * @return <T extends Entity>
	 * @throws BadInputException
	 */
	public abstract Optional<Entity> getOneById(Long id) throws BadInputException;

	/**
	 * Commands the Dao related to the service to retrieve an Entity T based on name
	 * 
	 * @param String name
	 * @return <T extends Entity>
	 * @throws BadInputException
	 */
	public abstract Optional<Entity> getOneByName(String name) throws BadInputException;

	/**
	 * Commands the Dao related to the service to create a new entity
	 * 
	 * @param <T extends Entity> newEntity
	 * @return boolean
	 * @throws BadInputException
	 */
	public abstract boolean create(Entity newEntity) throws BadInputException;

	/**
	 * Commands the Dao related to the service to update an Entity based on id and
	 * data to update the entity
	 * 
	 * @param <T extends Entity> newEntity
	 * @return boolean
	 * @throws BadInputException
	 */
	public abstract boolean updateById(Entity newEntity) throws BadInputException;

	/**
	 * Commands the Dao related to the service to delete an Entity based on id
	 * 
	 * @param id
	 * @return boolean
	 * @throws BadInputException
	 */
	public abstract boolean deleteById(Long id) throws BadInputException;

	/**
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
	public abstract IDaoInstance<Entity> getDao();

	/**
	 * Sets a new Dao on the service
	 * 
	 * @param DaoInstance<T extends Entity> dao
	 */
	public abstract void setDao(IDaoInstance<Entity> dao);

	/**
	 * Returns all the entities T that match the name in parameter
	 * 
	 * @param String name
	 * @return List<T extends Entity>
	 */
	public abstract Optional<List<Entity>> searchByName(String name);
}
