package com.green.base.entity.living.human;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.base.entity.living.related.enums.Gender;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "kind", discriminatorType = DiscriminatorType.STRING)
@NamedQueries( {
		@NamedQuery(name = "HumanBeing.exists", query = "select h.id from HumanBeing h where h.name.firstName like :firstName"
				+ " and h.name.middleName like :middleName and h.name.lastName like :lastName"),
		@NamedQuery(name = "HumanBeing.existsEditCase", query = "select h.id from HumanBeing h where h.name.firstName like :firstName"
				+ " and h.name.middleName like :middleName and h.name.lastName like :lastName and h.id != :id") })
public class HumanBeing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Embedded
	protected Name name = new Name();

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	protected Date dateOfBirth;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Embedded
	protected ContactDetails contactDetails;

	@OneToOne(cascade = { CascadeType.ALL })
	protected Address address;

	protected String simpleAddress;

	protected String identityTag;

	public HumanBeing() {

	}

	public HumanBeing(Name name, Date dateOfBirth,
			ContactDetails contactDetails, Address address) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.contactDetails = contactDetails;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public ContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(ContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Gender getGender() {
		return gender;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setSimpleAddress(String simpleAddress) {
		this.simpleAddress = simpleAddress;
	}

	public String getSimpleAddress() {
		return this.simpleAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		HumanBeing other = (HumanBeing) obj;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getIdentityTag() {
		return identityTag;
	}

	public void setIdentityTag(String identityTag) {
		this.identityTag = identityTag;
	}

}
