package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Entity;

public interface IDaoFactory<T> {
	abstract DaoInstance<T> getInstance();
}
