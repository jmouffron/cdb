package com.excilys.cdb.model;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name="company")
public class Company extends Entity implements Serializable {

  private static final long serialVersionUID = 3942228965283035652L;

  public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
