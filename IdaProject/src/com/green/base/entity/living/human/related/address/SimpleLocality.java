package com.green.base.entity.living.human.related.address;

import javax.persistence.Embeddable;

@Embeddable
public class SimpleLocality {

	private String locality;

	public SimpleLocality() {

	}

	public SimpleLocality(String locality) {
		this.locality = locality;

	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((locality == null) ? 0 : locality.hashCode());
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
		SimpleLocality other = (SimpleLocality) obj;
		if (locality == null) {
			if (other.locality != null)
				return false;
		} else if (!locality.equals(other.locality))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleLocality [locality=" + locality + "]";
	}

}
