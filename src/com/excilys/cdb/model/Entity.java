package com.excilys.cdb.model;

import java.math.BigInteger;

public abstract class Entity {
	protected BigInteger id;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	abstract public String toString();
}
