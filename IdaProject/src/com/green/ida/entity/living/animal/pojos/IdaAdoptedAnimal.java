package com.green.ida.entity.living.animal.pojos;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.green.base.entity.living.animal.Animal;
import com.green.base.entity.living.human.HumanBeing;

@Entity
@DiscriminatorValue(value = "IDA_ADOPTED_ANIMAL")
public class IdaAdoptedAnimal extends Animal {

	// private String tokenNo;
	private Integer age;

	private String breedType;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private HumanBeing idaAnimalAdopter;

	private String animalType;

	private String name;

	public IdaAdoptedAnimal() {
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

	public HumanBeing getIdaAnimalAdopter() {
		return idaAnimalAdopter;
	}

	public void setIdaAnimalAdopter(HumanBeing idaAnimalAdopter) {
		this.idaAnimalAdopter = idaAnimalAdopter;
	}

	public String getAnimalType() {
		return animalType;
	}

	public void setAnimalType(String animalType) {
		this.animalType = animalType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
