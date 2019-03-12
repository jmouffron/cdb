package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.exceptions.BadInputException;

public interface IService<T> {
	public abstract List<T> getAll();
	public abstract T getOneById(Long id) throws BadInputException;
	public abstract boolean create(T newEntity) throws BadInputException;
	public abstract boolean updateById(Long id, T newEntity) throws BadInputException;
	public abstract boolean deleteById(Long id) throws BadInputException;
	public abstract boolean deleteByName(String name) throws BadInputException;
}
