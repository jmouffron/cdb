package com.excilys.cdb.persistence;

import com.excilys.cdb.model.Entity;

/**
 * @author excilys
 * 
 * A DaoFactory Interface to establish contract and abstraction
 * for factories to generate Daos
 * 
 * @param <T>
 */
public interface IDaoFactory<T extends Entity> {
	/**
	 * Returns a DaoInstance to access the database
	 * 
	 * @return DaoInstance<T extends Entity>
	 */
	public DaoInstance<T> getDao();
}
