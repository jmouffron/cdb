package com.excilys.cdb.model;

public abstract class EntityList<T> {
	protected long[] ids;
	
	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] newIds) {
		this.ids = newIds;
	}
}
