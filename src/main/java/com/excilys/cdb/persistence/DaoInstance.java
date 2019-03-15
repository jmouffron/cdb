package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Entity;

/**
 * @author excilys
 * 
 * An interface to allow contract to instantiate Daos
 * to interact with
 * 
 * @param <T>
 */
public interface DaoInstance<T extends Entity> {
	abstract List<T> getAll();

	abstract T getOneById(Long id);

	abstract T getOneByName(String name);

	abstract boolean create(T newEntity);

	abstract boolean updateById(Long id, T newEntity);

	abstract boolean deleteById(Long id);

	abstract boolean deleteByName(String name);
}
