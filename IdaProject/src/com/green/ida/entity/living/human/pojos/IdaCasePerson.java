package com.green.ida.entity.living.human.pojos;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.Name;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.ida.entity.living.human.pojos.enums.IdaCasePersonType;

@Entity
@DiscriminatorValue(value = "IDA_CASE_PERSON")
public class IdaCasePerson extends HumanBeing {

	public IdaCasePerson() {

	}

	public IdaCasePerson(Name name, Date dateOfBirth,
			ContactDetails contactDetails, Address address,
			IdaCasePersonType personType) {
		super(name, dateOfBirth, contactDetails, address);
		this.personType = personType;

	}

	@Enumerated(EnumType.STRING)
	private IdaCasePersonType personType;

	public IdaCasePersonType getPersonType() {
		return personType;
	}

	public void setPersonType(IdaCasePersonType personType) {
		this.personType = personType;
	}

	public void setHumanData(HumanBeing humanBeing) {
		this.address = humanBeing.getAddress();
		this.contactDetails = humanBeing.getContactDetails();
		this.name = humanBeing.getName();
		this.dateOfBirth = humanBeing.getDateOfBirth();
		this.id = humanBeing.getId();
	}

}
