package com.excilys.cdb.model;

import java.math.BigInteger;
import java.util.Date;

public class Computer extends Entity {
	
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date discontinued) {
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
	 * @param introduced2
	 * @param discontinued2
	 * @param company
	 */
	public Computer(BigInteger id,String name, Date introduced2, Date discontinued2, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced2;
		this.discontinued = discontinued2;
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer of id "+ this.id + ", of name" + this.name +" ,introduced in "+ this.introduced.toString()+ " ,discontinued in"+ this.discontinued.toString() + " " + this.company.toString();
	}

}
