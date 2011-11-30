package com.green.ida.controller.search.ida.medicalcase;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;

@Named
public class IdaMedicalCaseSearchForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8444237750947216076L;
	private Long id;
	private String tokenNo;
	private Date admissionDate;
	private String personFirstName;
	private String personLastName;
	private String locality;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTokenNo() {
		return tokenNo;
	}

	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getPersonFirstName() {
		return personFirstName;
	}

	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}

	public String getPersonLastName() {
		return personLastName;
	}

	public void setPersonLastName(String personLastName) {
		this.personLastName = personLastName;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public void reset() {
		this.admissionDate = null;
		this.id = null;
		this.personFirstName = null;
		this.personLastName = null;
		this.tokenNo = null;
		this.locality = null;
	}

	// public String[][] getQueryParameters

}
