package com.green.ida.entity.living.animal.pojos;

import java.util.Calendar;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.green.base.entity.living.animal.Animal;

@Entity
@DiscriminatorValue(value = "IDA_RESIDENT_ANIMAL")
public class IdaResidentAnimal extends Animal {

	// private String tokenNo;
	private Integer age;
	private String caseHistory;
	private String breedType;
	private String animalType;
	private String name;
	private String physicalDescription;

	public IdaResidentAnimal() {
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

	/**
	 * @return the caseHistory
	 */
	public String getCaseHistory() {
		return caseHistory;
	}

	/**
	 * @param caseHistory
	 *            the caseHistory to set
	 */
	public void setCaseHistory(String caseHistory) {
		this.caseHistory = caseHistory;
	}

	/**
	 * @return the breedType
	 */
	public String getBreedType() {
		return breedType;
	}

	/**
	 * @param breedType
	 *            the breedType to set
	 */
	public void setBreedType(String breedType) {
		this.breedType = breedType;
	}

	public String getAnimalType() {
		return animalType;
	}

	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhysicalDescription() {
		return physicalDescription;
	}

	public void setPhysicalDescription(String physicalDescription) {
		this.physicalDescription = physicalDescription;
	}

}
