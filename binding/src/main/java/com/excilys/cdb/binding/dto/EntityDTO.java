package com.excilys.cdb.binding.dto;

import java.io.Serializable;

public abstract class EntityDTO implements Serializable{

  private static final long serialVersionUID = 6131697093554583309L;
  protected long id;
	protected String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	abstract public String toString();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    EntityDTO other = (EntityDTO) obj;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
