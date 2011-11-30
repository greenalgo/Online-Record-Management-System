package com.green.ida.entity.living.human.pojos;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.Name;

@Entity
@DiscriminatorValue(value = "IDA_RESIDENT_DOG_ADOPTER")
public class IdaResidentDogAdopter extends HumanBeing {

	@Embedded
	private Name careTaker;

	public void setCareTaker(Name careTaker) {
		this.careTaker = careTaker;
	}

	public Name getCareTaker() {
		return careTaker;
	}

}
