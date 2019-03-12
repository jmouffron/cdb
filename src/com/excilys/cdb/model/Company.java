package com.excilys.cdb.model;

public class Company extends Entity {
	/**
	 * @param name
	 */
	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name ;
	}

}
