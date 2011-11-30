package com.green.base.entity.living.human.related.address;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String lineOne;
	private String lineTwo;
	private String zip;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Locality locality;

	@Embedded
	@Column(name = "simpleLocality")
	private SimpleLocality simpleLocality;

	public Address() {

	}

	public Address(String lineOne, String lineTwo, String zip, Locality locality) {
		super();
		this.lineOne = lineOne;
		this.lineTwo = lineTwo;
		this.zip = zip;
		this.locality = locality;
	}

	public Address(SimpleLocality simpleLocality) {
		this.simpleLocality = simpleLocality;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLineOne() {
		return lineOne;
	}

	public void setLineOne(String lineOne) {
		this.lineOne = lineOne;
	}

	public String getLineTwo() {
		return lineTwo;
	}

	public void setLineTwo(String lineTwo) {
		this.lineTwo = lineTwo;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	// @Override
	// public String toString() {
	// return "Address [id=" + id + ", lineOne=" + lineOne + ", lineTwo="
	// + lineTwo + ", locality=" + locality + ", zip=" + zip + "]";
	// }

	public SimpleLocality getSimpleLocality() {
		return simpleLocality;
	}

	public void setSimpleLocality(SimpleLocality simpleLocality) {
		this.simpleLocality = simpleLocality;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lineOne == null) ? 0 : lineOne.hashCode());
		result = prime * result + ((lineTwo == null) ? 0 : lineTwo.hashCode());
		result = prime * result
				+ ((locality == null) ? 0 : locality.hashCode());
		result = prime * result
				+ ((simpleLocality == null) ? 0 : simpleLocality.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		Address other = (Address) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lineOne == null) {
			if (other.lineOne != null)
				return false;
		} else if (!lineOne.equals(other.lineOne))
			return false;
		if (lineTwo == null) {
			if (other.lineTwo != null)
				return false;
		} else if (!lineTwo.equals(other.lineTwo))
			return false;
		if (locality == null) {
			if (other.locality != null)
				return false;
		} else if (!locality.equals(other.locality))
			return false;
		if (simpleLocality == null) {
			if (other.simpleLocality != null)
				return false;
		} else if (!simpleLocality.equals(other.simpleLocality))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

}
