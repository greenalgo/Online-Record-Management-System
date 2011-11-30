package com.green.ida.entity.living.human.pojos;

import java.util.Date;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.green.base.entity.living.human.Name;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.base.entity.medical.personals.Doctor;
import com.green.ida.entity.animal.idacase.pojos.IdaCase;

@Entity
@DiscriminatorValue(value = "IDA_DOCTOR")
public class IdaCaseDoctor extends Doctor {

	@OneToMany
	private Set<IdaCase> idaCases;

	public IdaCaseDoctor() {
		this.isVeterinary = true;
	}

	public IdaCaseDoctor(Name name, Date dateOfBirth,
			ContactDetails contactDetails, Address address,
			Boolean isVeterinary, Set<IdaCase> idaCases) {
		super(name, dateOfBirth, contactDetails, address, isVeterinary);
		this.setIdaCases(idaCases);
	}

	// @Override
	// public String toString() {
	// return "IdaCaseDoctor [isVeterinary="
	// + isVeterinary + ", address=" + address + ", contactDetails="
	// + contactDetails + ", dateOfBirth=" + dateOfBirth
	// + ", firstName=" + firstName + ", id=" + id + ", lastName="
	// + lastName + ", middleName=" + middleName + "]";
	// }

	public void setIdaCases(Set<IdaCase> idaCases) {
		this.idaCases = idaCases;
	}

	public Set<IdaCase> getIdaCases() {
		return idaCases;
	}

}
