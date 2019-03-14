package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exception.BadInputException;
import com.excilys.cdb.model.Entity;

public interface IService<T extends Entity> {
	public abstract List<T> getAll();

	public abstract T getOneById(Long id) throws BadInputException;

	public abstract T getOneByName(String name) throws BadInputException;

	public abstract boolean create(Entity newEntity) throws BadInputException;

	public abstract boolean updateById(Long id, T newEntity) throws BadInputException;

	public abstract boolean deleteById(Long id) throws BadInputException;

	public abstract boolean deleteByName(String name) throws BadInputException;
}
