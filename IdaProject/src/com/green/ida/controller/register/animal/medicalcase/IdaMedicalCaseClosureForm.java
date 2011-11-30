/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.green.ida.controller.register.animal.medicalcase;

import java.util.Map;

import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.adoption.IdaAdoption;
import com.green.ida.entity.animal.idacase.resident.IdaResident;
import com.green.ida.entity.animal.idacase.result.pojos.IdaAdoptedResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaBuriedInCenterResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaCaseResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaKoraKendaraResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaReleasedResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaResidentResult;
import com.green.ida.entity.living.animal.pojos.IdaAdoptedAnimal;
import com.green.ida.entity.living.animal.pojos.IdaResidentAnimal;

/**
 * 
 * @author gaurav
 */

public class IdaMedicalCaseClosureForm {

	private ControllerUtil controllerUtil;

	private IdaAdoption idaAdoption;

	private Map<String, String> centerMap;
	private String centerValue;

	private Map<String, String> genderMap;
	private String adopterGenderValue;
	private String dogGenderValue;

	private Map<String, String> animalTypeMap;
	private String animalTypeValue;

	private Map<String, String> allCatcherMap;
	private String catcherValue;

	private Map<String, String> allDriverMap;
	private String driverValue;

	private Map<String, String> allVanMap;
	private IdaResident idaResident;
	private String vanValue;

	private Map<String, String> allDoctorMap;
	private String doctorValue;

	private IdaReleasedResult idaReleasedResult;
	private IdaAdoptedResult idaAdoptedResult;
	private IdaResidentResult idaResidentResult;
	private IdaBuriedInCenterResult idaBuriedInCenterResult;
	private IdaKoraKendaraResult idaKoraKendaraResult;

	public void init() {
		setIdaAdoption(new IdaAdoption());
		HumanBeing idaAnimalAdopter = new HumanBeing();
		idaAnimalAdopter.setAddress(new Address());
		idaAnimalAdopter.setContactDetails(new ContactDetails());
		getIdaAdoption().setIdaAnimalAdopter(idaAnimalAdopter);
		getIdaAdoption().setIdaAdoptedAnimal(new IdaAdoptedAnimal());

		genderMap = this.getControllerUtil().initGenderMap();
		animalTypeMap = this.getControllerUtil().initIdaAdoptedAnimalTypeMap();
		centerMap = this.getControllerUtil().initIdaCenterMap();

		allCatcherMap = this.controllerUtil.initNgoCatcherMap();
		allDriverMap = this.controllerUtil.initNgoDriverMap();
		allVanMap = this.controllerUtil.initNgoVehicleMap();
		allDoctorMap = this.controllerUtil.initDoctorMap();

		idaReleasedResult = new IdaReleasedResult();
		idaAdoptedResult = new IdaAdoptedResult();
		idaResidentResult = new IdaResidentResult();
		idaResident = new IdaResident();
		idaResident.setIdaResidentAnimal(new IdaResidentAnimal());
		idaResidentResult.setIdaResident(idaResident);
		idaBuriedInCenterResult = new IdaBuriedInCenterResult();
		idaKoraKendaraResult = new IdaKoraKendaraResult();
	}

	public void setIdaResultAccordingToCaseType(IdaCaseResult idaCaseResult) {
		if (idaCaseResult instanceof IdaAdoptedResult) {
			this.idaAdoptedResult = (IdaAdoptedResult) idaCaseResult;
		} else if (idaCaseResult instanceof IdaResidentResult) {
			this.idaResidentResult = (IdaResidentResult) idaCaseResult;
		} else if (idaCaseResult instanceof IdaReleasedResult) {
			this.idaReleasedResult = (IdaReleasedResult) idaCaseResult;
		} else if (idaCaseResult instanceof IdaBuriedInCenterResult) {
			this.idaBuriedInCenterResult = (IdaBuriedInCenterResult) idaCaseResult;
		} else if (idaCaseResult instanceof IdaKoraKendaraResult) {
			this.idaKoraKendaraResult = (IdaKoraKendaraResult) idaCaseResult;
		}

	}

	public String getCenterName() {
		return this.controllerUtil.getSelectKeyFromValueFor(this.centerMap,
				this.centerValue);
	}

	public ControllerUtil getControllerUtil() {
		return controllerUtil;
	}

	public void setControllerUtil(ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
	}

	public IdaAdoption getIdaAdoption() {
		return idaAdoption;
	}

	public void setIdaAdoption(IdaAdoption idaAdoption) {
		this.idaAdoption = idaAdoption;
	}

	public Map<String, String> getCenterMap() {
		return centerMap;
	}

	public void setCenterMap(Map<String, String> centerMap) {
		this.centerMap = centerMap;
	}

	public String getCenterValue() {
		return centerValue;
	}

	public void setCenterValue(String centerValue) {
		this.centerValue = centerValue;
	}

	public Map<String, String> getGenderMap() {
		return genderMap;
	}

	public void setGenderMap(Map<String, String> genderMap) {
		this.genderMap = genderMap;
	}

	public String getAdopterGenderValue() {
		return adopterGenderValue;
	}

	public void setAdopterGenderValue(String adopterGenderValue) {
		this.adopterGenderValue = adopterGenderValue;
	}

	public String getDogGenderValue() {
		return dogGenderValue;
	}

	public void setDogGenderValue(String dogGenderValue) {
		this.dogGenderValue = dogGenderValue;
	}

	public Map<String, String> getAnimalTypeMap() {
		return animalTypeMap;
	}

	public void setAnimalTypeMap(Map<String, String> animalTypeMap) {
		this.animalTypeMap = animalTypeMap;
	}

	public String getAnimalTypeValue() {
		return animalTypeValue;
	}

	public void setAnimalTypeValue(String animalTypeValue) {
		this.animalTypeValue = animalTypeValue;
	}

	public Map<String, String> getAllCatcherMap() {
		return allCatcherMap;
	}

	public void setAllCatcherMap(Map<String, String> allCatcherMap) {
		this.allCatcherMap = allCatcherMap;
	}

	public String getCatcherValue() {
		return catcherValue;
	}

	public void setCatcherValue(String catcherValue) {
		this.catcherValue = catcherValue;
	}

	public Map<String, String> getAllDriverMap() {
		return allDriverMap;
	}

	public void setAllDriverMap(Map<String, String> allDriverMap) {
		this.allDriverMap = allDriverMap;
	}

	public String getDriverValue() {
		return driverValue;
	}

	public void setDriverValue(String driverValue) {
		this.driverValue = driverValue;
	}

	public Map<String, String> getAllVanMap() {
		return allVanMap;
	}

	public void setAllVanMap(Map<String, String> allVanMap) {
		this.allVanMap = allVanMap;
	}

	public String getVanValue() {
		return vanValue;
	}

	public void setVanValue(String vanValue) {
		this.vanValue = vanValue;
	}

	public IdaReleasedResult getIdaReleasedResult() {
		return idaReleasedResult;
	}

	public void setIdaReleasedResult(IdaReleasedResult idaReleasedResult) {
		this.idaReleasedResult = idaReleasedResult;
	}

	public IdaAdoptedResult getIdaAdoptedResult() {
		return idaAdoptedResult;
	}

	public void setIdaAdoptedResult(IdaAdoptedResult idaAdoptedResult) {
		this.idaAdoptedResult = idaAdoptedResult;
	}

	public IdaResidentResult getIdaResidentResult() {
		return idaResidentResult;
	}

	public void setIdaResidentResult(IdaResidentResult idaResidentResult) {
		this.idaResidentResult = idaResidentResult;
	}

	public IdaBuriedInCenterResult getIdaBuriedInCenterResult() {
		return idaBuriedInCenterResult;
	}

	public void setIdaBuriedInCenterResult(
			IdaBuriedInCenterResult idaBuriedInCenterResult) {
		this.idaBuriedInCenterResult = idaBuriedInCenterResult;
	}

	public IdaKoraKendaraResult getIdaKoraKendaraResult() {
		return idaKoraKendaraResult;
	}

	public void setIdaKoraKendaraResult(
			IdaKoraKendaraResult idaKoraKendaraResult) {
		this.idaKoraKendaraResult = idaKoraKendaraResult;
	}

	public Map<String, String> getAllDoctorMap() {
		return allDoctorMap;
	}

	public void setAllDoctorMap(Map<String, String> allDoctorMap) {
		this.allDoctorMap = allDoctorMap;
	}

	public String getDoctorValue() {
		return doctorValue;
	}

	public void setDoctorValue(String doctorValue) {
		this.doctorValue = doctorValue;
	}

	public IdaResident getIdaResident() {
		return idaResident;
	}

	public void setIdaResident(IdaResident idaResident) {
		this.idaResident = idaResident;
	}

}
