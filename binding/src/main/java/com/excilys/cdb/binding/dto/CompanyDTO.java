package com.excilys.cdb.binding.dto;

public class CompanyDTO extends EntityDTO {

  private static final long serialVersionUID = 6001033716863818434L;

  public CompanyDTO(long id, String name) {
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
