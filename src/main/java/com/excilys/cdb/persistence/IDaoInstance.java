package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Computer;

/**
 * An interface to allow contract to instantiate Daos to interact with
 * 
 * @author excilys 

 * @param <T>
 */
public interface IDaoInstance<Entity> {
	abstract List<Entity> getAll();

	abstract Entity getOneById(Long id);

	abstract Entity getOneByName(String name);

	abstract boolean create(Entity newEntity);

	abstract boolean updateById(Entity newEntity);

	abstract boolean deleteById(Long id);

	abstract boolean deleteByName(String name);

	public abstract boolean createDTO(Entity newEntity);
}
