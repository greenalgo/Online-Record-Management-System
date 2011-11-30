package com.green.ida.entity.living.animal.pojos;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.green.base.entity.living.animal.pets.Dog;
import com.green.base.entity.living.related.enums.Gender;
import com.green.ida.entity.living.human.pojos.IdaResidentDogAdopter;

@Entity
@DiscriminatorValue(value = "IDA_RESIDENT_DOG")
public class IdaResidentDog extends Dog {

	// private String tokenNo;
	private Integer age;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaResidentDogAdopter idaResidentDogAdopter;

	public IdaResidentDog() {

	}

	public IdaResidentDog(Gender gender, Boolean isBiped, Boolean iswild,
			Date dateOfBirth, String animalDescription, Boolean isPet,
			String name, String physicalDescription) {
		super(gender, isBiped, iswild, dateOfBirth, animalDescription, isPet,
				name, physicalDescription);
		// this.tokenNo = tokenNo;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setDogDateOfBirthFromAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.add(Calendar.YEAR, -age);
		dateOfBirth = calendar.getTime();
	}

	public void setIdaResidentDogAdopter(IdaResidentDogAdopter idaResidentDogAdopter) {
		this.idaResidentDogAdopter = idaResidentDogAdopter;
	}

	public IdaResidentDogAdopter getIdaResidentDogAdopter() {
		return idaResidentDogAdopter;
	}

}
