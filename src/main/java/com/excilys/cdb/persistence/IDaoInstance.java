package com.excilys.cdb.persistence;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.exception.DaoException;

/**
 * An interface to allow contract to instantiate Daos to interact with
 * 
 * @author excilys 

 * @param <T>
 */
public interface IDaoInstance<Entity> {
	abstract Optional<List<Entity>> getAll() throws DaoException;
  
  abstract Optional<List<Entity>> getAllOrderedBy(String orderBy, boolean desc) throws DaoException;
  
	abstract Optional<Entity> getOneById(Long id);

	abstract Optional<Entity> getOneByName(String name);

	abstract boolean create(Entity computer) throws DaoException ;

	abstract boolean updateById(Entity newEntity) throws DaoException;

	abstract boolean createDTO(Entity newEntity) throws DaoException;

}
