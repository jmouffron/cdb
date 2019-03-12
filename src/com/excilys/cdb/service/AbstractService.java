package com.excilys.cdb.service;

import java.math.BigInteger;
import java.util.List;

import com.excilys.cdb.model.Entity;
import com.excilys.cdb.persistence.IDaoFactory;

public abstract class AbstractService {
	protected IDaoFactory factory;
	
	abstract List<Entity> getAll();
	abstract Entity getOneById(BigInteger id);
	abstract Entity create(Entity newEntity);
	abstract Entity updateById(BigInteger id);
	abstract Entity deleteById(BigInteger id);
	abstract Entity deleteByName(String name);
}
