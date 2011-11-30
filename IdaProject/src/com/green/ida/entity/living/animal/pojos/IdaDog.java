package com.green.ida.entity.living.animal.pojos;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.green.base.entity.living.animal.pets.Dog;
import com.green.base.entity.living.related.enums.Gender;

@Entity
@DiscriminatorValue(value = "IDA_DOG")
public class IdaDog extends Dog {

	// private String tokenNo;
	private Integer age;

	public IdaDog() {

	}

	public IdaDog(Gender gender, Boolean isBiped, Boolean iswild,
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
                if(age == null){
                    return;
                }
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.add(Calendar.YEAR, -age);
		dateOfBirth = calendar.getTime();
	}

}
