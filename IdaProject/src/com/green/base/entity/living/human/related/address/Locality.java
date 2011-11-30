package com.green.base.entity.living.human.related.address;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
		@NamedQuery(name = "Locality.exists", query = "select l.id from Locality l where l.locality like :locality and l.distinguished like :distinguished"),
		@NamedQuery(name = "Locality.existsEditCase", query = "select l.id from Locality l where l.locality like :locality and l.distinguished like :distinguished and l.id != :id") })
public class Locality {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String locality;
	private String distinguished;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private State state;

	public Locality() {

	}

	public Locality(String locality, String distinguished, State state) {
		super();
		this.locality = locality;
		this.distinguished = distinguished;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getDistinguished() {
		return distinguished;
	}

	public void setDistinguished(String distinguished) {
		this.distinguished = distinguished;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getLocalityName() {
		if (locality == null || distinguished == null) {
			return "";
		}
		return locality + " " + distinguished;
	}

	// proxy to satisfy framework needs
	public void setLocalityName(String localityName) {

	}

	@Override
	public String toString() {
		return locality + " " + distinguished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((distinguished == null) ? 0 : distinguished.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((locality == null) ? 0 : locality.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Locality other = (Locality) obj;
		if (distinguished == null) {
			if (other.distinguished != null)
				return false;
		} else if (!distinguished.equals(other.distinguished))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locality == null) {
			if (other.locality != null)
				return false;
		} else if (!locality.equals(other.locality))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

}
