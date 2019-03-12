package com.excilys.cdb.persistence;

import java.util.List;

public interface DaoInstance<T> {
	abstract List<T> getAll();
	abstract T getOneById(Long id);
	abstract boolean create(T newEntity);
	abstract boolean updateById(Long id, T newEntity);
	abstract boolean deleteById(Long id);
	abstract boolean deleteByName(String name);
}
