package com.excilys.cdb.core.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.excilys.cdb.core.utils.DateUtils;

@Entity
@Table(name = "computer")
public class Computer implements Serializable {

	private static final long serialVersionUID = 2256380163459893961L;

	public Computer() {

	}

	@Id
	@Min(0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Size(min = 2, max = 255, message = "Should be between 2 and 255 character!")
	@NotNull
	@Basic(optional = false)
	@Column(name = "name")
	private String name;

	private Timestamp introduced;

	private Timestamp discontinued;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
		return Objects.hash(company, discontinued, id, introduced, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Computer)) {
			return false;
		}
		Computer other = (Computer) obj;
		return Objects.equals(company, other.company) && Objects.equals(discontinued, other.discontinued)
				&& id == other.id && Objects.equals(introduced, other.introduced) && Objects.equals(name, other.name);
	}

	/**
	 * @author excilys
	 *
	 *         An inner Builder class for computer entities
	 *
	 */
	public static class ComputerBuilder {
		private long id;
		private String name;
		private Timestamp introduced = DateUtils.getNowTimestamp();
		private Timestamp discontinued = null;
		private Company company;

		public ComputerBuilder() {
			super();
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
