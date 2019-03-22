package com.excilys.cdb.persistence;

import java.util.List;
import java.util.Optional;

/**
 * An interface to allow contract to instantiate Daos to interact with
 * 
 * @author excilys 

 * @param <T>
 */
public interface IDaoInstance<Entity> {
	abstract Optional<List<Entity>> getAll();

	abstract Optional<Entity> getOneById(Long id);

	abstract Optional<Entity> getOneByName(String name);

	abstract boolean create(Entity newEntity);

	abstract boolean updateById(Entity newEntity);

	abstract boolean deleteById(Long id);

	abstract boolean deleteByName(String name);

	abstract boolean createDTO(Entity newEntity);
	
	abstract Optional<List<Entity>> getAllOrderedBy(String orderBy, boolean desc);
}
