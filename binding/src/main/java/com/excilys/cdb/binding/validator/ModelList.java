package com.excilys.cdb.binding.validator;

import org.springframework.stereotype.Component;

@Component
public class ModelList {
	protected long[] ids;
	
	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] newIds) {
		this.ids = newIds;
	}
}
