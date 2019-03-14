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
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	public Computer(Long id, String name, Timestamp introduced, Timestamp discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer of id " + this.id + ", of name " + this.name + ", introduced in " + this.introduced
				+ ", discontinued in " + this.discontinued + " manufactur� par " + this.company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		return true;
	}

	static public class ComputerBuilder {
		private Long id;
		private String name;
		private Timestamp introduced = null;
		private Timestamp discontinued = null;
		private Company company;

		public ComputerBuilder() {
		}

		public ComputerBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder setIntroduced(Timestamp introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder setDiscontinued(Timestamp discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerBuilder setCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(id, name, introduced, discontinued, company);
		}
	}
}
