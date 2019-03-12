package com.excilys.cdb.persistence;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;
import com.excilys.cdb.model.Entity;

public abstract class DaoInstance {
	protected String url;
	protected String driver;
	protected Connection conn;
	protected String username;
	protected String password;
	
	abstract Connection getConnection();
	abstract List<Entity> getAll();
	abstract Entity getOneById(BigInteger id);
	abstract Entity create(Entity newEntity);
	abstract Entity updateById(BigInteger id);
	abstract Entity deleteById(BigInteger id);
	abstract Entity deleteByName(String name);
}
