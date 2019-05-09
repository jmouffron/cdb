package com.excilys.cdb.binding.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ComputerDTO.class)
public class ComputerDTO extends EntityDTO {

	private static final long serialVersionUID = -73180210575560484L;
	@JsonProperty("introduced")
	private String introduced;
	@JsonProperty("discontinued")
	private String discontinued;
	@JsonProperty("companyName")
	private String companyName;
	@JsonProperty("companyId")
	private long companyId;

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	/**
	 * General constructor with all params
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ComputerDTO(@JsonProperty("id") long id, @JsonProperty("companyName") String name,
			@JsonProperty("introduced") String introduced, @JsonProperty("discontinued") String discontinued,
			@JsonProperty("companyName") String companyName, @JsonProperty("companyId") long companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = companyName;
		this.companyId = companyId;
	}

	public ComputerDTO() {
	}

	@Override
	public String toString() {
		return "Computer of id " + this.id + ", of name " + this.name + ", introduced in " + this.introduced
				+ ", discontinued in " + this.discontinued + " manufacturé par " + this.companyName;
	}

	/**
	 * @author excilys
	 *
	 *         An inner Builder class for computer entities
	 *
	 */
	public static class ComputerDTOBuilder {
		private long id;
		private String name;
		private String introduced = Timestamp.valueOf(LocalDateTime.now()).toString();
		private String discontinued = null;
		private String companyName;
		private long companyId;

		public ComputerDTOBuilder() {
			super();
		}

		public ComputerDTOBuilder setId(long id) {
			this.id = id;
			return this;
		}

		public ComputerDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ComputerDTOBuilder setIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerDTOBuilder setDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerDTOBuilder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public ComputerDTOBuilder setCompanyId(long companyId) {
			this.companyId = companyId;
			return this;
		}

		public ComputerDTO build() {
			return new ComputerDTO(id, name, introduced, discontinued, companyName, companyId);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
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
		ComputerDTO other = (ComputerDTO) obj;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (introduced == null || other.introduced != null) {
				return false;
		}
		return !introduced.equals(other.introduced);
	}

}
