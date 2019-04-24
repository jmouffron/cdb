package com.excilys.cdb.core.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.PastOrPresent;

import com.excilys.cdb.core.utils.DateUtils;

@Table(name="computer")
public class Computer extends Entity implements Serializable {

	private static final long serialVersionUID = 2256380163459893961L;
	
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp introduced;
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp discontinued;

	@ManyToOne
	@JoinColumn(name = "company", referencedColumnName = "company_id")
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
	public Computer(long id, String name, Timestamp introduced, Timestamp discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer of id " + this.id + ", of name " + this.name + ", introduced in " + this.introduced
				+ ", discontinued in " + this.discontinued + " manufacturé par " + this.company;
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

	/**
	 * @author excilys
	 *
	 *         An inner Builder class for computer entities
	 *
	 */
	static public class ComputerBuilder {
		private long id;
		private String name;
		private Timestamp introduced = DateUtils.getNowTimestamp();
		private Timestamp discontinued = null;
		private Company company;

		public ComputerBuilder() {
		}

		public ComputerBuilder setId(long id) {
			this.id = id;
			return this;
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
