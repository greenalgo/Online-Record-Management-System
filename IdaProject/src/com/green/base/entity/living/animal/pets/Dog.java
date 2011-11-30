package com.green.base.entity.living.animal.pets;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.green.base.entity.living.animal.Animal;
import com.green.base.entity.living.animal.pets.related.dog.Breed;
import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.related.enums.Gender;

@Entity
@DiscriminatorValue(value = "DOG")
public class Dog extends Animal {

	protected Boolean isPet;
	protected String name;
	@Size(max = 214000000)
	protected String physicalDescription;

	public Dog() {

	}

	public Dog(Gender gender, Boolean isBiped, Boolean iswild,
			Date dateOfBirth, String animalDescription, Boolean isPet,
			String name, String physicalDescription) {
		super(gender, isBiped, iswild, dateOfBirth, animalDescription);
		this.isPet = isPet;
		this.name = name;
		this.physicalDescription = physicalDescription;
	}

	@OneToOne(cascade = { CascadeType.PERSIST })
	private Breed breed;

	@OneToOne(cascade = { CascadeType.PERSIST })
	private HumanBeing owner;

	public Boolean getIsPet() {
		return isPet;
	}

	public void setIsPet(Boolean isPet) {
		this.isPet = isPet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HumanBeing getOwner() {
		return owner;
	}

	public void setOwner(HumanBeing owner) {
		this.owner = owner;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	public Breed getBreed() {
		return breed;
	}

	public String getPhysicalDescription() {
		return physicalDescription;
	}

	public void setPhysicalDescription(String physicalDescription) {
		this.physicalDescription = physicalDescription;
	}

	@SuppressWarnings("deprecation")
	public Integer getDogAgeFromDateOfBirth() {
                return dateOfBirth == null? 0:new Date().getYear() - dateOfBirth.getYear();
	}

	public String getGenderViewValue() {
		return this.gender.getViewValue();
	}

}
