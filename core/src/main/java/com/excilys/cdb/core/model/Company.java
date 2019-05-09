package com.excilys.cdb.core.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@javax.persistence.Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = 3942228965283035652L;

	@Id
	@Min(0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected long id;

	@Size(min = 2, max = 255, message = "Should be between 2 and 255 character!")
	@NotNull
	@Basic(optional = false)
	@Column(name = "name")
	protected String name;

	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Computer> computers;

	@Override
	public String toString() {
		return this.name;
	}

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

	public Set<Computer> getComputers() {
		return computers;
	}

	public void setComputers(Set<Computer> computers) {
		this.computers = computers;
	}

	public void addComputer(Computer computer) {
		computers.add(computer);
		computer.setCompany(this);
	}

	public void removeComputer(Computer computer) {
		computers.remove(computer);
		computer.setCompany(null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((computers == null) ? 0 : computers.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (computers == null) {
			if (other.computers != null) {
				return false;
			}
		} else if (!computers.equals(other.computers)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}