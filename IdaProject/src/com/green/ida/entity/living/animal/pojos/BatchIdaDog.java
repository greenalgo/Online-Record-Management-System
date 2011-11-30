package com.green.ida.entity.living.animal.pojos;

import java.util.Date;

import com.green.base.entity.living.related.enums.Gender;

public class BatchIdaDog extends IdaDog {

	private String tokenNo;

	private String kennelNo;

	private Long[] admissionReasonValues;

	private String admissionReasonValuesOthers;

	private String idaCaseDogGenderValue;

	private String treatmentPrescribed;

	private Date sterlizationDate;

	private String idaCaseType;

	public BatchIdaDog() {
		isBiped = false;
		isPet = false;
		iswild = false;
	}

	public BatchIdaDog(Gender gender, Boolean isBiped, Boolean iswild,
			Date dateOfBirth, String animalDescription, Boolean isPet,
			String name, String physicalDescription, String tokenNo) {
		super(gender, isBiped, iswild, dateOfBirth, animalDescription, isPet,
				name, physicalDescription);
		this.tokenNo = tokenNo;
	}

	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}

	public String getTokenNo() {
		return tokenNo;
	}

	public void setIdaCaseDogGenderValue(String idaCaseDogGenderValue) {
		this.idaCaseDogGenderValue = idaCaseDogGenderValue;
	}

	public String getIdaCaseDogGenderValue() {
		return idaCaseDogGenderValue;
	}

	public void setTreatmentPrescribed(String treatmentPrescribed) {
		this.treatmentPrescribed = treatmentPrescribed;
	}

	public String getTreatmentPrescribed() {
		return treatmentPrescribed;
	}

	public void setIdaCaseType(String idaCaseType) {
		this.idaCaseType = idaCaseType;
	}

	public String getIdaCaseType() {
		return idaCaseType;
	}

	public void setAdmissionReasonValues(Long[] admissionReasonValues) {
		this.admissionReasonValues = admissionReasonValues;
	}

	public Long[] getAdmissionReasonValues() {
		return admissionReasonValues;
	}

	public String getKennelNo() {
		return kennelNo;
	}

	public void setKennelNo(String kennelNo) {
		this.kennelNo = kennelNo;
	}

	public IdaDog getIdaDog() {
		IdaDog dog = new IdaDog(gender, isBiped, iswild, dateOfBirth,
				animalDescription, isPet, name, physicalDescription);
		dog.setAge(getAge());
		return dog;

	}

	public void setAdmissionReasonValuesOthers(
			String admissionReasonValuesOthers) {
		this.admissionReasonValuesOthers = admissionReasonValuesOthers;
	}

	public String getAdmissionReasonValuesOthers() {
		return admissionReasonValuesOthers;
	}

	public void setSterlizationDate(Date sterlizationDate) {
		this.sterlizationDate = sterlizationDate;
	}

	public Date getSterlizationDate() {
		return sterlizationDate;
	}

}
