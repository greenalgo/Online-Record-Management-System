package com.green.base.entity.animal.medicalcase;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.medical.personals.Doctor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "caseType", discriminatorType = DiscriminatorType.STRING)
public abstract class GenericCase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne()
	protected Doctor doctor;// attending doctor

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	protected HumanBeing personReportingCase;

	@Temporal(TemporalType.DATE)
	protected Date admittedOn;

	@Temporal(TemporalType.DATE)
	protected Date tentatativeDateOfRelease;

	@Temporal(TemporalType.DATE)
	protected Date actualDateOfRelease;// on case closure

	protected Boolean isOpdCase;

	protected String reasonForAdmissionDetails;// detailed description

	@Size(max = 214000000)
	protected String treatmentPrescribed;

	public GenericCase() {

	}

	public GenericCase(Doctor doctor, HumanBeing personReportingCase,
			Date admittedOn, Date tentatativeDateOfRelease, Boolean isOpdCase,
			String reasonForAdmissionDetails, String treatmentPrescribed) {
		super();
		this.doctor = doctor;
		this.admittedOn = admittedOn;
		this.tentatativeDateOfRelease = tentatativeDateOfRelease;
		this.isOpdCase = isOpdCase;
		this.reasonForAdmissionDetails = reasonForAdmissionDetails;
		this.treatmentPrescribed = treatmentPrescribed;
		this.personReportingCase = personReportingCase;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAdmittedOn(Date admittedOn) {
		this.admittedOn = admittedOn;
	}

	public Date getAdmittedOn() {
		return admittedOn;
	}

	public void setIsOpdCase(Boolean isOpdCase) {
		this.isOpdCase = isOpdCase;
	}

	public Boolean getIsOpdCase() {
		return isOpdCase;
	}

	public void setReasonForAdmissionDetails(String reasonForAdmissionDetails) {
		this.reasonForAdmissionDetails = reasonForAdmissionDetails;
	}

	public void setTreatmentPrescribed(String treatmentPrescribed) {
		this.treatmentPrescribed = treatmentPrescribed;
	}

	public String getTreatmentPrescribed() {
		return treatmentPrescribed;
	}

	public String getReasonForAdmissionDetails() {
		return reasonForAdmissionDetails;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public HumanBeing getPersonReportingCase() {
		return personReportingCase;
	}

	public void setPersonReportingCase(HumanBeing personReportingCase) {
		this.personReportingCase = personReportingCase;
	}

	public Date getTentatativeDateOfRelease() {
		return tentatativeDateOfRelease;
	}

	public void setTentatativeDateOfRelease(Date tentatativeDateOfRelease) {
		this.tentatativeDateOfRelease = tentatativeDateOfRelease;
	}

	public Date getActualDateOfRelease() {
		return actualDateOfRelease;
	}

	public void setActualDateOfRelease(Date actualDateOfRelease) {
		this.actualDateOfRelease = actualDateOfRelease;
	}

	public String getAdmissionDate() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		return s.format(admittedOn);

	}

	public String getIsOpdInYesOrNo() {
		return this.isOpdCase ? "Yes" : "No";
	}

}
