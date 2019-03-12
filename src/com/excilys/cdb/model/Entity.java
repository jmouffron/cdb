package com.excilys.cdb.model;

public abstract class Entity {
	protected Long id;
	protected String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	abstract public String toString();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
