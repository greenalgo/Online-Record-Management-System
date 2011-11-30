package com.green.base.entity.medical.personals;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.Name;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;

@Entity
@DiscriminatorValue(value = "DOCTOR")
public class Doctor extends HumanBeing {

	protected Boolean isVeterinary;

	public Doctor() {

	}

	public Doctor(Name name, Date dateOfBirth, ContactDetails contactDetails,
			Address address, Boolean isVeterinary) {
		super(name, dateOfBirth, contactDetails, address);
		this.isVeterinary = isVeterinary;
	}

	public void setIsVeterinary(Boolean isVeterinary) {
		this.isVeterinary = isVeterinary;
	}

	public Boolean getIsVeterinary() {
		return isVeterinary;
	}

}
