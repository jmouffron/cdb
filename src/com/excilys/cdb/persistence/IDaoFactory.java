package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Computer;

public interface IDaoFactory<T> {
	public DaoInstance<T> getDao();
}
