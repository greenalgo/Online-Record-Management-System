package com.green.ida.controller.register.animal.medicalcase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.animal.pets.related.dog.Breed;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.base.entity.living.human.related.address.Locality;
import com.green.base.entity.living.related.enums.Gender;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.admission.reason.pojos.IdaCaseAdmissionReason;
import com.green.ida.entity.animal.idacase.pojos.IdaCase;
import com.green.ida.entity.animal.idacase.pojos.IdaCaseActivity;
import com.green.ida.entity.animal.idacase.pojos.IdaCaseType;
import com.green.ida.entity.living.animal.pojos.IdaDog;
import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import com.green.ida.entity.living.human.pojos.IdaCasePerson;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.living.human.pojos.enums.IdaCasePersonType;
import com.green.ida.entity.ngo.Ngo;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterIdaMedicalCaseController {

	private static transient Logger LOGGER = Logger
			.getLogger(RegisterIdaMedicalCaseController.class);
	private IdaCase idaCase;
	private IdaCasePerson idaCasePerson;
	private IdaDog idaDog;
	private IdaCaseDoctor idaCaseDoctor;
	private Map<String, String> localityMap;
	private String broughtFromLocalityValue;
	private String idaCasePersonLocalityValue;
	private Map<String, String> doctorMap;
	private String doctorValue;
	private Map<String, String> idaCaseTypeMap;
	private String idaCaseTypeValue;
	// private Map<String,String> idaCaseResultMap;
	// private String idaCaseResultValue;
	private Map<String, Long> idaCaseActivityMap;
	private Long idaCaseActivityValue;
	private Map<String, String> ngoDriverMap;
	private String ngoDriverValue;
	private Map<String, String> ngoVehicleMap;
	private String ngoVehicleValue;
	private Map<String, String> ngoCatcherMap;
	private String ngoCatcherValue;
	private Map<String, String> genderMap;
	private String idaCaseDogGenderValue;
	private String idaCasePersonGenderValue;
	private Map<String, String> admissionReasonMap;
	private String[] admissionReasonValues;
	private Map<String, String> idaCasePersonTypeMap;
	private String idaCasePersonTypeValue;
	private Integer dogAge;
	private Address dogBroughtFromArea;
	private String personTypeViewValue;
	private String personGenderViewValue;
	// private Boolean isClosure;
	// private Boolean isEdit;
	// private Boolean isReadOnly;
	private Long toEditIdaCaseId;
	private boolean isMobileActivity;
	private Boolean byPassEditGetListener = Boolean.FALSE;
	private Integer page = 0;
	private List<IdaCaseAdmissionReason> idaCaseAdmissionReasonList;
	@Inject
	private GenericDao genericDao;
	@Inject
	private ControllerUtil controllerUtil;
	@Inject
	private FacesUtil facesUtil;

	@PostConstruct
	public void initIdaCaseComponents() {
		this.idaCase = new IdaCase();
		this.idaCasePerson = new IdaCasePerson();
		this.idaCasePerson.setContactDetails(new ContactDetails());
		// this.idaCasePerson.setAddress(new Address(new SimpleLocality()));
		Address address = new Address();
		Locality locality = new Locality();
		address.setLocality(locality);
		this.idaCasePerson.setAddress(address);
		this.idaDog = new IdaDog();
		// this.dogBroughtFromArea = new Address(new SimpleLocality());
		this.dogBroughtFromArea = new Address();
		this.localityMap = this.controllerUtil.initLocalityMap();
		this.doctorMap = this.controllerUtil.initDoctorMap();
		this.admissionReasonMap = this.controllerUtil.initAdmissionReasonMap();
		this.idaCaseTypeMap = this.controllerUtil.initIdaCaseTypeMap();
		this.genderMap = this.controllerUtil.initGenderMap();
		this.idaCasePersonTypeMap = this.controllerUtil
				.initIdaCasePersonTypeMap();
		this.ngoDriverMap = this.controllerUtil.initNgoDriverMap();
		this.ngoVehicleMap = this.controllerUtil.initNgoVehicleMap();
		this.ngoCatcherMap = this.controllerUtil.initNgoCatcherMap();

		this.idaCaseActivityMap = IdaCaseActivity.getIdaCaseActivityMap();

	}

	public String saveIdaCase() {

		LOGGER.info("Saving ida medical case .....");
		try {
			ingestIdaMedicalCase();

			LOGGER.info("Saved ida case with id .... " + this.idaCase.getId());

		} catch (Exception e) {
			LOGGER.error("Problem saving ida case " + e.getMessage(), e);
			return "error";
		}
		return "idaMedicalCaseView?faces-redirect=true&amp;includeViewParams=true";
	}

	private void ingestIdaMedicalCase() throws Exception {
		Locality broughtFromLocality = this.genericDao.getReference(
				Locality.class, Long.valueOf(broughtFromLocalityValue));
		Locality idaCasePersonLocality = this.genericDao.getReference(
				Locality.class, Long.valueOf(idaCasePersonLocalityValue));
		Boolean isReadOnly = Boolean.valueOf(facesUtil.getFromSession(
				"isReadOnly").toString());
		Boolean isCallerCase = Boolean.valueOf(facesUtil.getFromSession(
				"isCallerCase").toString());
		Ngo idaNgo = this.genericDao.findFirstWhereCondition(Ngo.class,
				"ngoName", "IDA");
		Gender dogGender = Gender.getGender(getGenderNameIdaCaseDog());
		Gender idaCasePersonGender = Gender
				.getGender(getGenderNameIdaCasePerson());

		// this.dogBroughtFromArea.setSimpleLocality(new SimpleLocality(
		// broughtFromLocalityValue));

		this.dogBroughtFromArea.setLocality(broughtFromLocality);

		if (isReadOnly != null && isReadOnly) {
			this.idaCasePerson = this.genericDao.find(IdaCasePerson.class,
					this.idaCasePerson.getId());

		}
		if (!isReadOnly || isCallerCase) {
			this.idaCasePerson.setPersonType(IdaCasePersonType
					.getIdaCasePersonType(getIdaCasePersonTypeName()));
			// this.idaCasePerson.getAddress().setSimpleLocality(
			// new SimpleLocality(idaCasePersonLocalityValue));
			this.idaCasePerson.getAddress().setLocality(idaCasePersonLocality);
			this.idaCasePerson.setGender(idaCasePersonGender);
		}
		this.idaDog.setGender(dogGender);
		this.idaDog.setIsBiped(false);
		this.idaDog.setIsPet(false);
		this.idaDog.setIsWild(false);

		Breed breed = this.genericDao.findFirstWhereCondition(Breed.class,
				"name", "Street Dog");
		if (breed == null) {
			throw new Exception("Breed found as null !!!! for Street Dog");
		}
		this.idaDog.setBreed(breed);
		if (dogAge != null && dogAge > 0) {
			this.idaDog.setDateOfBirth(this.controllerUtil
					.getDogDateOfBirthFromAge(dogAge));
		}

		idaCaseAdmissionReasonList = this.genericDao.findListWhereInCondition(
				IdaCaseAdmissionReason.class, "id",
				getAdmissionReasonValuesInLong(admissionReasonValues));

		for (IdaCaseAdmissionReason idaCaseAdmissionReason : idaCaseAdmissionReasonList) {
			this.idaCase.addIdaCaseAdmissionReason(idaCaseAdmissionReason);
		}

		idaCaseDoctor = this.genericDao.getReference(IdaCaseDoctor.class,
				Long.valueOf(doctorValue));
		// driver catcher vehicle entry here
		if ((isReadOnly != null && isReadOnly)
				|| (isCallerCase != null && isCallerCase)) {
			NgoCatcher ngoCatcher = this.genericDao.getReference(
					NgoCatcher.class, Long.valueOf(ngoCatcherValue));
			NgoDriver ngoDriver = this.genericDao.getReference(NgoDriver.class,
					Long.valueOf(ngoDriverValue));
			NgoVehicle ngoVehicle = this.genericDao.getReference(
					NgoVehicle.class, Long.valueOf(ngoVehicleValue));

			idaCase.setNgoCatcher(ngoCatcher);
			idaCase.setNgoDriver(ngoDriver);
			idaCase.setNgoVehicle(ngoVehicle);
		}
		IdaCaseType idaCaseType = IdaCaseType
				.getIdaCaseType(getIdaCaseTypeName());
		this.idaCase.setDoctor(idaCaseDoctor);
		this.idaCase.setIdaCaseType(idaCaseType);
		this.idaCase.setAnimal(idaDog);
		this.idaCase.setBroughtFromArea(dogBroughtFromArea);
		// this.idaCase.setAdmittedOn(new Date());
		this.idaCase.setPersonReportingCase(idaCasePerson);

		this.idaCase.setNgo(idaNgo);

		// this.idaCase.setIdaCaseActivity(IdaCaseActivity
		// .getIdaCaseActivity(getIdaCaseActivityName()));

		this.idaCase = this.genericDao.merge(this.idaCase);
	}

	public void searchForIdaCase() {
		LOGGER.info("Searching entry for ida case with id "
				+ this.idaCase.getId());

		try {
			this.idaCase = this.genericDao.findFirstWhereCondition(
					IdaCase.class, "id", this.idaCase.getId());
			this.isMobileActivity = this.idaCase.getIsMobileActivity();
			this.idaCaseAdmissionReasonList = new ArrayList<IdaCaseAdmissionReason>(
					this.idaCase.getIdaCaseAdmissionReason());
			this.admissionReasonValues = this.idaCase
					.getIdaCaseAdmissionReasonIdList();

			this.idaCaseDoctor = this.idaCase.getIdaDoctor();
			this.dogBroughtFromArea = this.idaCase.getBroughtFromArea();
			this.idaCasePerson = (IdaCasePerson) this.idaCase
					.getPersonReportingCase();
			this.idaDog = this.idaCase.getIdaDog();

			if (this.idaCasePerson != null) {

				// this.idaCasePersonLocalityValue = this.idaCasePerson
				// .getAddress().getSimpleLocality() != null? this.idaCasePerson
				// .getAddress().getSimpleLocality().getLocality() : "";

				this.idaCasePersonLocalityValue = this.idaCasePerson
						.getAddress().getLocality().getId().toString();

				this.idaCasePersonGenderValue = this.genderMap.get(Gender
						.valueOf(this.idaCasePerson.getGender()));

				this.personGenderViewValue = this.idaCasePerson.getGender()
						.getViewValue();

				this.idaCasePersonTypeValue = this.idaCasePersonTypeMap
						.get(IdaCasePersonType.valueOf(this.idaCasePerson
								.getPersonType()));
				this.personTypeViewValue = this.idaCasePerson.getPersonType()
						.getViewValue();
			}
			// this.broughtFromLocalityValue = this.dogBroughtFromArea
			// .getSimpleLocality().getLocality();

			this.idaCaseActivityValue = this.idaCaseActivityMap
					.get(IdaCaseActivity.valueOf(this.idaCase
							.getIdaCaseActivity()));

			this.broughtFromLocalityValue = this.dogBroughtFromArea
					.getLocality().getId().toString();

			this.doctorValue = this.idaCaseDoctor.getId().toString();
			// put none case here
			this.ngoCatcherValue = this.idaCase.getNgoCatcher() == null ? "-1"
					: this.idaCase.getNgoCatcher().getId().toString();
			this.ngoDriverValue = this.idaCase.getNgoDriver() == null ? "-1"
					: this.idaCase.getNgoDriver().getId().toString();
			this.ngoVehicleValue = this.idaCase.getNgoVehicle() == null ? "-1"
					: this.idaCase.getNgoVehicle().getId().toString();

			if (this.idaDog.getDateOfBirth() != null) {
				this.dogAge = this.controllerUtil
						.getDogAgeFromDateOfBirth(this.idaDog.getDateOfBirth());
				this.idaDog.setAge(dogAge);
			}
			this.idaCaseDogGenderValue = this.genderMap.get(Gender
					.valueOf(this.idaDog.getGender()));

			this.idaCaseTypeValue = this.idaCaseTypeMap.get(IdaCaseType
					.valueOf(this.idaCase.getIdaCaseType()));
			this.byPassEditGetListener = Boolean.TRUE;
			checkIfNgo();
			checkIfCaller();
		} catch (Exception e) {
			LOGGER.error(
					"Problem while searching ida case for id "
							+ idaCase.getId() + " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem while searching"
					+ " ida case for id " + idaCase.getId());
		}

		LOGGER.info("Finished searching ida case for id " + idaCase.getId());

	}

	private void checkIfNgo() throws NumberFormatException, Exception {
		if (getIdaCasePersonTypeName().equalsIgnoreCase(
				IdaCasePersonType.NGO.name())) {
			facesUtil.putInSession("isReadOnly", true);
			return;
		}
		facesUtil.putInSession("isReadOnly", false);
	}

	private void checkIfCaller() throws NumberFormatException, Exception {
		if (getIdaCasePersonTypeName().equalsIgnoreCase(
				IdaCasePersonType.CALLER.name())) {
			facesUtil.putInSession("isCallerCase", true);
			return;
		}
		facesUtil.putInSession("isCallerCase", false);
	}

	public void idaCaseForEdit() {
		if (getByPassEditGetListener()) {
			return;
		}
		// this.isEdit = true;
		Long id = this.idaCase.getId();
		LOGGER.info("Fetching ida case details for id " + id);
		if (FacesContext.getCurrentInstance().isValidationFailed()) {
			LOGGER.info("Validation failed by passing search !!!!!!");
			return;
		}

		if (id == null || id <= 0) {
			LOGGER.error("Ida case with null id or negative value cannot be editted");
			facesUtil.addErrorMessage("Ida case cannot be fetched for null Id");
			return;
		}

		searchForIdaCase();
	}

	public String updateIdaCase() {
		LOGGER.info("Updating ida case for " + this.idaCase + " for id "
				+ this.idaCase.getId());
		try {
			String tokenNumberError = controllerUtil.tokenNumberExixts(
					this.idaCase.getTokenNumber(), this.idaCase.getId());
			if (!"".equalsIgnoreCase(tokenNumberError)) {
				facesUtil.addErrorMessage(tokenNumberError);
				return null;
			}
			ingestIdaMedicalCase();
		} catch (Exception e) {
			LOGGER.error(
					"Problem updating ida case " + this.idaCase + " "
							+ e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Updated ida case for id " + this.idaCase.getId());
		return "idaMedicalCaseView?faces-redirect=true&amp;includeViewParams=true";
	}

	private Serializable[] getAdmissionReasonValuesInLong(String[] values) {
		Long[] longValues = new Long[values.length];
		for (int i = 0; i < values.length; ++i) {
			longValues[i] = Long.valueOf(values[i]);
		}

		return longValues;
	}

	public String getLocalityNameIdaCasePerson() {
		return this.controllerUtil.getSelectKeyFromValueFor(localityMap,
				idaCasePersonLocalityValue);
	}

	public String getLocalityNameIdaCaseDog() {
		return this.controllerUtil.getSelectKeyFromValueFor(localityMap,
				broughtFromLocalityValue);
	}

	public String getGenderNameIdaCasePerson() {
		return this.controllerUtil.getSelectKeyFromValueFor(genderMap,
				idaCasePersonGenderValue);
	}

	public String getGenderNameIdaCaseDog() {
		return this.controllerUtil.getSelectKeyFromValueFor(genderMap,
				idaCaseDogGenderValue);
	}

	public String getIdaCaseTypeName() {
		return this.controllerUtil.getSelectKeyFromValueFor(idaCaseTypeMap,
				idaCaseTypeValue);
	}

	public String getIdaCasePersonTypeName() {
		return this.controllerUtil.getSelectKeyFromValueFor(
				idaCasePersonTypeMap, idaCasePersonTypeValue);
	}

	public String getIdaCaseActivityName() {
		return this.controllerUtil.getSelectKeyFromValueFor(idaCaseActivityMap,
				idaCaseActivityValue);
	}

	public String getDoctorName() {
		return this.controllerUtil.getSelectKeyFromValueFor(doctorMap,
				doctorValue);
	}

	public void setControllerUtil(ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
	}

	public ControllerUtil getControllerUtil() {
		return controllerUtil;
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public GenericDao getGenericDao() {
		return genericDao;
	}

	public void setAdmissionReasonValues(String[] admissionReasonValues) {
		this.admissionReasonValues = admissionReasonValues;
	}

	public String[] getAdmissionReasonValues() {
		return admissionReasonValues;
	}

	public void setAdmissionReasonMap(Map<String, String> admissionReasonMap) {
		this.admissionReasonMap = admissionReasonMap;
	}

	public Map<String, String> getAdmissionReasonMap() {
		return admissionReasonMap;
	}

	public void setDoctorValue(String doctorValue) {
		this.doctorValue = doctorValue;
	}

	public String getDoctorValue() {
		return doctorValue;
	}

	public void setDoctorMap(Map<String, String> doctorMap) {
		this.doctorMap = doctorMap;
	}

	public Map<String, String> getDoctorMap() {
		return doctorMap;
	}

	// public void setLocalityMap(Map<String, String> localityMap) {
	// this.localityMap = localityMap;
	// }
	//
	// public Map<String, String> getLocalityMap() {
	// return localityMap;
	// }
	public void setIdaDog(IdaDog idaDog) {
		this.idaDog = idaDog;
	}

	public IdaDog getIdaDog() {
		return idaDog;
	}

	public void setIdaCasePerson(IdaCasePerson idaCasePerson) {
		this.idaCasePerson = idaCasePerson;
	}

	public IdaCasePerson getIdaCasePerson() {
		return idaCasePerson;
	}

	public void setIdaCase(IdaCase idaCase) {
		this.idaCase = idaCase;
	}

	public IdaCase getIdaCase() {
		return idaCase;
	}

	public void setIdaCaseTypeMap(Map<String, String> idaCaseTypeMap) {
		this.idaCaseTypeMap = idaCaseTypeMap;
	}

	public Map<String, String> getIdaCaseTypeMap() {
		return idaCaseTypeMap;
	}

	public void setIdaCaseTypeValue(String idaCaseTypeValue) {
		this.idaCaseTypeValue = idaCaseTypeValue;
	}

	public String getIdaCaseTypeValue() {
		return idaCaseTypeValue;
	}

	public void setIdaCasePersonLocalityValue(String idaCasePersonLocalityValue) {
		this.idaCasePersonLocalityValue = idaCasePersonLocalityValue;
	}

	public String getIdaCasePersonLocalityValue() {
		return idaCasePersonLocalityValue;
	}

	public void setBroughtFromLocalityValue(String broughtFromLocalityValue) {
		this.broughtFromLocalityValue = broughtFromLocalityValue;
	}

	public String getBroughtFromLocalityValue() {
		return broughtFromLocalityValue;
	}

	public void setDogBroughtFromArea(Address dogBroughtFromArea) {
		this.dogBroughtFromArea = dogBroughtFromArea;
	}

	public Address getDogBroughtFromArea() {
		return dogBroughtFromArea;
	}

	public void setGenderMap(Map<String, String> genderMap) {
		this.genderMap = genderMap;
	}

	public Map<String, String> getGenderMap() {
		return genderMap;
	}

	public void setIdaCaseDogGenderValue(String idaCaseDogGenderValue) {
		this.idaCaseDogGenderValue = idaCaseDogGenderValue;
	}

	public String getIdaCaseDogGenderValue() {
		return idaCaseDogGenderValue;
	}

	public void setIdaCasePersonGenderValue(String idaCasePersonGenderValue) {
		this.idaCasePersonGenderValue = idaCasePersonGenderValue;
	}

	public String getIdaCasePersonGenderValue() {
		return idaCasePersonGenderValue;
	}

	public void setDogAge(Integer dogAge) {
		this.dogAge = dogAge;
	}

	public Integer getDogAge() {
		return dogAge;
	}

	public void setToEditIdaCaseId(Long toEditIdaCaseId) {
		this.toEditIdaCaseId = toEditIdaCaseId;
	}

	public Long getToEditIdaCaseId() {
		return toEditIdaCaseId;
	}

	public void setIdaCasePersonTypeMap(Map<String, String> idaCasePersonTypeMap) {
		this.idaCasePersonTypeMap = idaCasePersonTypeMap;
	}

	public Map<String, String> getIdaCasePersonTypeMap() {
		return idaCasePersonTypeMap;
	}

	public void setIdaCasePersonTypeValue(String idaCasePersonTypeValue) {
		this.idaCasePersonTypeValue = idaCasePersonTypeValue;
	}

	public String getIdaCasePersonTypeValue() {
		return idaCasePersonTypeValue;
	}

	public void setFacesUtil(FacesUtil facesUtil) {
		this.facesUtil = facesUtil;
	}

	public FacesUtil getFacesUtil() {
		return facesUtil;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPage() {
		return page;
	}

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}

	public void setNgoDriverMap(Map<String, String> ngoDriverMap) {
		this.ngoDriverMap = ngoDriverMap;
	}

	public Map<String, String> getNgoDriverMap() {
		return ngoDriverMap;
	}

	public void setNgoDriverValue(String ngoDriverValue) {
		this.ngoDriverValue = ngoDriverValue;
	}

	public String getNgoDriverValue() {
		return ngoDriverValue;
	}

	public void setNgoVehicleMap(Map<String, String> ngoVehicleMap) {
		this.ngoVehicleMap = ngoVehicleMap;
	}

	public Map<String, String> getNgoVehicleMap() {
		return ngoVehicleMap;
	}

	public void setNgoVehicleValue(String ngoVehicleValue) {
		this.ngoVehicleValue = ngoVehicleValue;
	}

	public String getNgoVehicleValue() {
		return ngoVehicleValue;
	}

	public void setNgoCatcherMap(Map<String, String> ngoCatcherMap) {
		this.ngoCatcherMap = ngoCatcherMap;
	}

	public Map<String, String> getNgoCatcherMap() {
		return ngoCatcherMap;
	}

	public void setNgoCatcherValue(String ngoCatcherValue) {
		this.ngoCatcherValue = ngoCatcherValue;
	}

	public String getNgoCatcherValue() {
		return ngoCatcherValue;
	}

	public String getPersonTypeViewValue() {
		return personTypeViewValue;
	}

	public void setPersonTypeViewValue(String personTypeViewValue) {
		this.personTypeViewValue = personTypeViewValue;
	}

	public String getPersonGenderViewValue() {
		return personGenderViewValue;
	}

	public void setPersonGenderViewValue(String personGenderViewValue) {
		this.personGenderViewValue = personGenderViewValue;
	}

	public IdaCaseDoctor getIdaCaseDoctor() {
		return idaCaseDoctor;
	}

	public void setIdaCaseDoctor(IdaCaseDoctor idaCaseDoctor) {
		this.idaCaseDoctor = idaCaseDoctor;
	}

	public Map<String, String> getLocalityMap() {
		return localityMap;
	}

	public void setLocalityMap(Map<String, String> localityMap) {
		this.localityMap = localityMap;
	}

	public List<IdaCaseAdmissionReason> getIdaCaseAdmissionReasonList() {
		return idaCaseAdmissionReasonList;
	}

	public void setIdaCaseAdmissionReasonList(
			List<IdaCaseAdmissionReason> idaCaseAdmissionReasonList) {
		this.idaCaseAdmissionReasonList = idaCaseAdmissionReasonList;
	}

	public Map<String, Long> getIdaCaseActivityMap() {
		return idaCaseActivityMap;
	}

	public void setIdaCaseActivityMap(Map<String, Long> idaCaseActivityMap) {
		this.idaCaseActivityMap = idaCaseActivityMap;
	}

	public Long getIdaCaseActivityValue() {
		return idaCaseActivityValue;
	}

	public void setIdaCaseActivityValue(Long idaCaseActivityValue) {
		this.idaCaseActivityValue = idaCaseActivityValue;
	}

	public void setIsMobileActivity(boolean isMobileActivity) {
		this.isMobileActivity = isMobileActivity;
	}

	public boolean getIsMobileActivity() {
		return isMobileActivity;
	}

}
