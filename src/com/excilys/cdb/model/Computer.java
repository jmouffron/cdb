package com.excilys.cdb.model;

import java.sql.Timestamp;

public class Computer extends Entity {
	
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * General constructor with all params
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	public Computer(Long id,String name, Timestamp introduced, Timestamp discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer of id "+ this.id + ", of name" + this.name +" ,introduced in "+ this.introduced.toString()+ " ,discontinued in"+ this.discontinued.toString() + " " + this.company.toString();
	}

}
