package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Entity;

public interface DaoInstance<T extends Entity> {
	abstract List<T> getAll();

	abstract T getOneById(Long id);

	abstract T getOneByName(String name);

	abstract boolean create(Entity newEntity);

	abstract boolean updateById(Long id, T newEntity);

	abstract boolean deleteById(Long id);

	abstract boolean deleteByName(String name);
}
