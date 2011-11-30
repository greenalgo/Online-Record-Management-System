package com.green.base.entity.animal.medicalcase;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.green.base.entity.living.animal.Animal;
import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.medical.personals.Doctor;

@Entity
@DiscriminatorValue(value = "ANIMAL_CASE")
public class AnimalCase extends GenericCase {

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	protected Animal animal;// dog or cat

	public AnimalCase() {

	}

	public AnimalCase(Doctor doctor, HumanBeing personReportingCase,
			Date admittedOn, Date tentatativeDateOfRelease, Boolean isOpdCase,
			String reasonForAdmissionDetails, String treatmentPrescribed,
			Animal animal) {
		super(doctor, personReportingCase, admittedOn,
				tentatativeDateOfRelease, isOpdCase, reasonForAdmissionDetails,
				treatmentPrescribed);
		this.animal = animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Animal getAnimal() {
		return animal;
	}

}
