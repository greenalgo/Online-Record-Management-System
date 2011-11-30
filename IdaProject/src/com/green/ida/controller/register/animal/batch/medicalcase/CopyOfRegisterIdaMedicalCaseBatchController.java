package com.green.ida.controller.register.animal.batch.medicalcase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.animal.pets.related.dog.Breed;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.base.entity.living.human.related.address.Locality;
import com.green.base.entity.living.related.enums.Gender;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.admission.reason.pojos.IdaCaseAdmissionReason;
import com.green.ida.entity.animal.idacase.call.register.pojos.PhoneCall;
import com.green.ida.entity.animal.idacase.pojos.IdaCase;
import com.green.ida.entity.animal.idacase.pojos.IdaCaseActivity;
import com.green.ida.entity.animal.idacase.pojos.IdaCaseType;
import com.green.ida.entity.living.animal.pojos.BatchIdaDog;
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
@Component
public class CopyOfRegisterIdaMedicalCaseBatchController {

	private static transient Logger LOGGER = Logger
			.getLogger(CopyOfRegisterIdaMedicalCaseBatchController.class);
	private IdaCasePerson idaCasePerson;
	private String idaCaseDogGenderValue;
	private HtmlDataTable idaBatchDogHtmlDataTable;
	private List<BatchIdaDog> batchDogs;
	private List<IdaCase> ingestedBatchCase = new ArrayList<IdaCase>();
	private Integer batchCount;
	private Map<String, String> ngoMap;
	private String ngoValue;
	private Map<String, String> ngoDriverMap;
	private String ngoDriverValue;
	private Map<String, String> ngoVehicleMap;
	private String ngoVehicleValue;
	private Map<String, String> ngoCatcherMap;
	private String ngoCatcherValue;
	private List<IdaCaseAdmissionReason> idaCaseAdmissionReasonList;
	private Map<String, String> genderMap;
	private Map<String, String> personGenderMap;
	private Map<String, String> admissionReasonMap;
	private Map<String, String> doctorMap;
	private String doctorValue;
	private Map<String, String> localityMap;
	private String broughtFromLocalityValue;
	private String idaCasePersonLocalityValue;
	private Map<String, String> idaCaseTypeMap;
	private Map<String, Long> idaCaseActivityMap;
	private Long idaCaseActivityValue;
	private IdaCase idaCase;
	private IdaCaseDoctor idaCaseDoctor;
	private NgoDriver ngoDriver;
	private Address dogBroughtFromArea;
	@Inject
	private GenericDao genericDao;
	@Inject
	private ControllerUtil controllerUtil;
	@Inject
	private FacesUtil facesUtil;
	private String batchIdaCaseCsvList;
	private Boolean isRequired = true;
	private String isRequiredString;
	private Map<String, String> idaCasePersonTypeMap;
	private String idaCasePersonTypeValue;
	private String idaCasePersonGenderValue;
	private Boolean isNoneCase;// is person type individual where none comes in
	// driver catcher vehicle
	private Ngo ngo;
	private String ngoChairPersonLocalityViewValue;
	private PhoneCall phoneCall;

	@PostConstruct
	public void initBatchList() throws NumberFormatException, Exception {
		this.idaCase = new IdaCase();
		this.phoneCall = new PhoneCall();
		setUpIdaCasePerson();
		this.doctorMap = this.controllerUtil.initDoctorMap();
		this.admissionReasonMap = this.controllerUtil.initAdmissionReasonMap();
		this.genderMap = this.controllerUtil.initShortenGenderMap();
		this.personGenderMap = this.controllerUtil.initGenderMap();
		this.ngoDriver = new NgoDriver();
		this.ngoMap = controllerUtil.initNgoMap();
		this.ngoCatcherMap = controllerUtil.initNgoCatcherMap();
		this.localityMap = controllerUtil.initLocalityMap();
		this.idaCaseTypeMap = this.controllerUtil.initShortenIdaCaseTypeMap();
		this.batchDogs = new ArrayList<BatchIdaDog>();
		this.dogBroughtFromArea = new Address();

		this.idaCasePersonTypeMap = this.controllerUtil
				.initIdaCasePersonTypeMap();
		this.idaCaseActivityMap = IdaCaseActivity.getIdaCaseActivityMap();

		Object sessionNgoObject = facesUtil.getFromSession("ngoValue");
		Object sessionCountObject = facesUtil.getFromSession("batchCount");
		Object sessionIsNoneCaseObject = facesUtil.getFromSession("isNoneCase");
		Object sessionIdaCaseActivityObject = facesUtil
				.getFromSession("idaCaseActivityValue");
		String sessionNgoValue = null;
		Integer sessionBatchCount = null;

		if (sessionNgoObject != null) {
			sessionNgoValue = sessionNgoObject.toString();
		}

		if (sessionCountObject != null) {
			sessionBatchCount = (Integer) sessionCountObject;
		}
		if (sessionIsNoneCaseObject != null) {
			this.isNoneCase = (Boolean) sessionIsNoneCaseObject;
		}
		if (sessionIdaCaseActivityObject != null) {
			this.idaCaseActivityValue = (Long) sessionIdaCaseActivityObject;
		}

		if (sessionNgoValue != null && !sessionNgoValue.equals("")) {// extract
			this.ngoDriverMap = controllerUtil
					.initNgoDriverMapForNgo(getNgoName(sessionNgoValue));
			this.ngoVehicleMap = controllerUtil
					.initNgoVehicleMapForNgo(getNgoName(sessionNgoValue));
		}

		if (sessionBatchCount != null) {
			for (int i = 0; i < sessionBatchCount; i++) {// extract
				batchDogs.add(new BatchIdaDog());
			}
		}

		checkIfNgo();
		// checkIfCaller();

	}

	private void setUpIdaCasePerson() {

		this.idaCasePerson = new IdaCasePerson();
		this.idaCasePerson.setContactDetails(new ContactDetails());
		this.idaCasePerson.setAddress(new Address());
	}

	public String showBatchEntryPage(Long phoneCallId) {
		try {
			this.phoneCall = this.genericDao.find(PhoneCall.class, phoneCallId);
			this.batchCount = 1;
			this.idaCaseActivityValue = this.idaCaseActivityMap
					.get(this.phoneCall.getCallStatus().getViewValue());
			this.idaCasePersonTypeValue = this.idaCasePersonTypeMap
					.get("Caller");
			this.ngo = this.genericDao.findFirstWhereCondition(Ngo.class,
					"ngoName", "IDA");
			this.ngoValue = this.ngo.getId().toString();
			this.ngoVehicleValue = this.phoneCall.getNgoVehicle().getId()
					.toString();
			this.broughtFromLocalityValue = this.phoneCall.getComplainSource()
					.getLocality().getId().toString();
			this.dogBroughtFromArea = this.phoneCall.getComplainSource();

			this.idaCasePersonGenderValue = this.personGenderMap.get(Gender
					.valueOf(this.phoneCall.getComplainer().getGender()));
			this.idaCasePerson.setHumanData(this.phoneCall.getComplainer());
			this.idaCasePerson.setPersonType(IdaCasePersonType.CALLER);
			this.idaCasePersonLocalityValue = this.phoneCall.getComplainer()
					.getAddress().getLocality().getId().toString();

			String message = showBatchEntryPage();
			if (message.equals("error")) {
				throw new Exception("Problem in base show entry page !!!! ");
			}
		} catch (Exception e) {
			LOGGER.error(
					"Problem while generating batch entry page with phone call "
							+ e.getMessage(), e);
			return "error";
		}
		return "registerIdaMedicalCaseBatch";
	}

	public String showBatchEntryPage() {
		String personType = getIdaCasePersonTypeName();
		if (personType != null
				&& (personType.toString().equalsIgnoreCase(
						IdaCasePersonType.INDIVIDUAL.name()) || personType
						.toString().equalsIgnoreCase(
								IdaCasePersonType.CALLER.name()))
				&& !"IDA".equalsIgnoreCase(getNgoName())) {
			facesUtil
					.addErrorMessage("Please select IDA as the only Ngo for Individual and Caller case.");
			cleanSession();
			return null;
		}
		if (personType != null
				&& (personType.toString()
						.equalsIgnoreCase(IdaCasePersonType.INDIVIDUAL.name()))
				&& (3l != this.idaCaseActivityValue)) {
			facesUtil
					.addErrorMessage("Only Self activity allowed for Individual case.");
			cleanSession();
			return null;
		}

		LOGGER.info("Preparing data for batch entry.");
		this.ngoDriverMap = controllerUtil
				.initNgoDriverMapForNgo(getNgoName(ngoValue));
		this.ngoVehicleMap = controllerUtil
				.initNgoVehicleMapForNgo(getNgoName(ngoValue));

		this.ngoCatcherMap = controllerUtil
				.initNgoCatcherMapForNgo(getNgoName(ngoValue));

		facesUtil.putInSession("ngoValue", ngoValue);
		facesUtil.putInSession("batchCount", batchCount);
		facesUtil.putInSession("personType", getIdaCasePersonTypeName());
		facesUtil.putInSession("idaCaseActivityValue",
				this.idaCaseActivityValue);

		batchDogs.clear();

		for (int i = 0; i < batchCount; i++) {
			batchDogs.add(new BatchIdaDog());
		}

		try {
			checkIfNgo();
			checkIfIndividual();
			checkIfCaller();
		} catch (Exception e) {
			LOGGER.error(
					"Problem while checking if ngo case " + e.getMessage(), e);
			return "error";
		}

		return "registerIdaMedicalCaseBatch";
	}

	private void checkIfNgo() throws NumberFormatException, Exception {
		Object personType = facesUtil.getFromSession("personType");

		if (personType != null
				&& personType.toString().equalsIgnoreCase(
						IdaCasePersonType.NGO.name()) && this.ngoValue != null) {

			this.ngo = this.genericDao.find(Ngo.class, Long
					.valueOf(this.ngoValue));
			this.idaCasePerson = this.ngo.getChairPerson();
			this.idaCasePersonGenderValue = this.personGenderMap
					.get(this.idaCasePerson.getGender().getViewValue());
			this.idaCasePersonLocalityValue = this.idaCasePerson.getAddress()
					.getLocality().getId().toString();
			this.ngoChairPersonLocalityViewValue = this.idaCasePerson
					.getAddress().getLocality().getLocalityName();

		} else if (personType != null
				&& personType.toString().equalsIgnoreCase(
						IdaCasePersonType.NGO.name())
				&& facesUtil.getFromSession("ngoValue") != null) {

			this.ngo = this.genericDao.find(Ngo.class, Long.valueOf(facesUtil
					.getFromSession("ngoValue").toString()));
			this.idaCasePerson = this.ngo.getChairPerson();
			this.idaCasePersonGenderValue = this.personGenderMap
					.get(this.idaCasePerson.getGender().getViewValue());
			this.idaCasePersonLocalityValue = this.idaCasePerson.getAddress()
					.getLocality().getId().toString();
			this.ngoChairPersonLocalityViewValue = this.idaCasePerson
					.getAddress().getLocality().getLocalityName();
		} else if (personType != null
				&& !personType.toString().equalsIgnoreCase(
						IdaCasePersonType.NGO.name())) {
			if (this.idaCasePerson != null)
				return;
			setUpIdaCasePerson();
			this.idaCasePersonGenderValue = null;
		}

	}

	private void checkIfIndividual() throws NumberFormatException, Exception {
		// facesUtil.putInSession("personType", getIdaCasePersonTypeName());
		if (getIdaCasePersonTypeName().equalsIgnoreCase(
				IdaCasePersonType.INDIVIDUAL.name())) {
			this.isNoneCase = true;
			this.ngoDriverValue = "-1";
			this.ngoCatcherValue = "-1";
			this.ngoVehicleValue = "-1";

			facesUtil.putInSession("isNoneCase", true);

			return;
		}
		facesUtil.putInSession("isNoneCase", false);
	}

	private void checkIfCaller() throws NumberFormatException, Exception {
		// facesUtil.putInSession("personType", getIdaCasePersonTypeName());
		if (getIdaCasePersonTypeName().equalsIgnoreCase(
				IdaCasePersonType.CALLER.name())) {

			facesUtil.putInSession("isCallerCase", true);

			return;
		}
		facesUtil.putInSession("isCallerCase", false);
	}

	public String saveBatchEntry() {

		LOGGER.info("Saving ida case in batch !!!!!!!");
		try {

			saveIdaCase();
			prepareIngestedBatchCaseCsv();
		} catch (Exception e) {
			LOGGER.error("Error occured while saving batch entries !!!! "
					+ e.getMessage(), e);

			return "error";
		}

		LOGGER.info("Removing ngo value and batch count from session");
		cleanSession();

		return "idaMedicalCaseBatchView?faces-redirect=true&amp;includeViewParams=true";
	}

	@Transactional
	public void saveIdaCase() throws Exception {
		IdaCase idaCaseBatch;
		IdaDog idaDog;
		NgoDriver ngoDriver = null;
		NgoCatcher ngoCatcher = null;
		NgoVehicle ngoVehicle = null;
		if (this.phoneCall != null && this.phoneCall.getId() != null) {
			this.phoneCall = this.genericDao.find(PhoneCall.class,
					this.phoneCall.getId());
			this.phoneCall.setAttendedDate(new Date());
			this.phoneCall = this.genericDao.mergeNT(this.phoneCall);
			this.dogBroughtFromArea = this.phoneCall.getComplainSource();
			this.idaCasePerson.setId(this.phoneCall.getComplainer().getId());
			this.idaCasePerson.setHumanData(this.phoneCall.getComplainer());
			// this.idaCasePerson.getAddress().setId(this.phoneCall.getComplainer().getAddress())
		}
		if (!getIsMobileCase()) {
			List<String> tokenNumberList = new ArrayList<String>();
			for (BatchIdaDog batchIdaDog : batchDogs) {
				tokenNumberList.add(batchIdaDog.getTokenNo());
			}
			if (!tokenNumberList.isEmpty()) {
				String tokenNumberError = controllerUtil
						.tokenNumberExixts(tokenNumberList);
				if (!"".equalsIgnoreCase(tokenNumberError)) {
					facesUtil.addErrorMessage(tokenNumberError);
					return;
				}
			}
		}
		LOGGER.info("Batch size fond as " + batchDogs.size());

		Breed breed = this.genericDao.findFirstWhereCondition(Breed.class,
				"name", "Street Dog");

		IdaCaseDoctor idaCaseDoctor = genericDao.getReference(
				IdaCaseDoctor.class, Long.valueOf(this.doctorValue));
		Locality broughtFromLocality = this.genericDao.getReference(
				Locality.class, Long.valueOf(broughtFromLocalityValue));
		// this.dogBroughtFromArea.setSimpleLocality(new SimpleLocality(
		// broughtFromLocalityValue));
		this.dogBroughtFromArea.setLocality(broughtFromLocality);
		Locality idaCasePersonLocality = this.genericDao.getReference(
				Locality.class, Long.valueOf(idaCasePersonLocalityValue));
		this.idaCasePerson.getAddress().setLocality(idaCasePersonLocality);

		if (!isNoneCase) {
			ngoDriver = this.genericDao.getReference(NgoDriver.class, Long
					.valueOf(ngoDriverValue));
			ngoCatcher = this.genericDao.getReference(NgoCatcher.class, Long
					.valueOf(ngoCatcherValue));
			ngoVehicle = this.genericDao.getReference(NgoVehicle.class, Long
					.valueOf(ngoVehicleValue));
		}

		if (!IdaCasePersonType.NGO.name().equalsIgnoreCase(
				facesUtil.getFromSession("personType").toString())) {
			this.idaCasePerson.setPersonType(IdaCasePersonType
					.getIdaCasePersonType(facesUtil
							.getFromSession("personType").toString()));

		}
		if (this.idaCasePersonGenderValue != null
				|| !"".equals(this.idaCasePersonGenderValue)) {
			this.idaCasePerson.setGender(Gender
					.getGender(getGenderNameIdaCasePerson()));
		}

		this.idaCasePerson = this.genericDao.mergeNT(this.idaCasePerson);
		this.dogBroughtFromArea = this.genericDao
				.mergeNT(this.dogBroughtFromArea);

		for (BatchIdaDog batchIdaDog : batchDogs) {
			idaCaseBatch = new IdaCase();

			Gender dogGender = Gender
					.getGender(getGenderNameIdaCaseDog(batchIdaDog
							.getIdaCaseDogGenderValue()));
			batchIdaDog.setGender(dogGender);
			idaDog = batchIdaDog.getIdaDog();

			if (breed == null) {
				throw new Exception("Breed found as null !!!! for Street Dog");
			}
			idaDog.setBreed(breed);

			if (batchIdaDog.getAge() != null && batchIdaDog.getAge() > 0) {
				idaDog.setDogDateOfBirthFromAge();
			}

			idaCaseAdmissionReasonList = this.genericDao
					.findListWhereInCondition(IdaCaseAdmissionReason.class,
							"id", (Serializable[]) batchIdaDog
									.getAdmissionReasonValues());

			for (IdaCaseAdmissionReason idaCaseAdmissionReason : idaCaseAdmissionReasonList) {
				idaCaseBatch.addIdaCaseAdmissionReason(idaCaseAdmissionReason);
			}

			IdaCaseType idaCaseType = IdaCaseType
					.getIdaCaseType(getIdaCaseTypeName(batchIdaDog
							.getIdaCaseType()));

			idaCaseBatch.setDoctor(idaCaseDoctor);
			idaCaseBatch.setIdaCaseType(idaCaseType);
			idaCaseBatch.setAnimal(idaDog);
			idaCaseBatch.setBroughtFromArea(dogBroughtFromArea);
			idaCaseBatch.setAdmittedOn(idaCase.getAdmittedOn());
			idaCaseBatch.setNgoDriver(ngoDriver);
			idaCaseBatch.setNgoVehicle(ngoVehicle);
			idaCaseBatch.setNgoCatcher(ngoCatcher);
			idaCaseBatch.setIsOpdCase(idaCase.getIsOpdCase());
			if (!getIsMobileCase()) {
				idaCaseBatch.setTokenNumber(batchIdaDog.getTokenNo());
				idaCaseBatch.setKennelNumber(batchIdaDog.getKennelNo());
			}
			// idaCaseBatch.setAdmittedOn(new Date());
			idaCaseBatch.setSterlizationDate(batchIdaDog.getSterlizationDate());
			idaCaseBatch.setTreatmentPrescribed(batchIdaDog
					.getTreatmentPrescribed());

			idaCaseBatch.setNgo(getNgoName((String) facesUtil
					.getFromSession("ngoValue")));

			idaCaseBatch.setPersonReportingCase(idaCasePerson);

			ingestedBatchCase.add(idaCaseBatch);
			if (this.phoneCall != null && this.phoneCall.getId() != null) {
				idaCaseBatch.setPhoneCall(phoneCall);

				String idaCaseActivity = phoneCall.getCallStatus()
						.getViewValue();
				if (IdaCaseActivity.isValidIdaCaseActivity(idaCaseActivity))
					idaCaseBatch.setIdaCaseActivity(IdaCaseActivity
							.getIdaCaseActivity(idaCaseActivity));

			} else if (this.idaCaseActivityValue != null) {
				idaCaseBatch.setIdaCaseActivity(IdaCaseActivity
						.getIdaCaseActivity(getIdaCaseActivityName()));
			}
			// idaCaseBatch = this.genericDao.merge(idaCaseBatch);

		}

		ingestedBatchCase = this.genericDao.mergeNT(getIngestedBatchCase());

		LOGGER.info("Done with saving batch entry !!!!!!");
	}

	private void cleanSession() {
		facesUtil.removeFromSession("ngoValue");
		facesUtil.removeFromSession("batchCount");
		facesUtil.removeFromSession("isNoneCase");
		facesUtil.removeFromSession("personType");
		facesUtil.removeFromSession("idaCaseActivityValue");
	}

	public boolean getIsMobileCase() {
		return getIdaCaseActivityName().equalsIgnoreCase("mobile");
	}

	public String getGenderNameIdaCasePerson() {
		return this.controllerUtil.getSelectKeyFromValueFor(personGenderMap,
				idaCasePersonGenderValue);
	}

	public String getIdaCasePersonTypeName() {
		return this.controllerUtil.getSelectKeyFromValueFor(
				idaCasePersonTypeMap, idaCasePersonTypeValue);
	}

	public String getNgoName() {
		return this.controllerUtil.getSelectKeyFromValueFor(ngoMap, ngoValue);
	}

	public String getIdaCaseActivityName() {
		return this.controllerUtil.getSelectKeyFromValueFor(idaCaseActivityMap,
				idaCaseActivityValue);
	}

	private void prepareIngestedBatchCaseCsv() {
		LOGGER.info("Preparing ingested batch case csv list !!!!!");
		StringBuilder medicalCaseCsv = new StringBuilder();
		for (IdaCase medicalCase : ingestedBatchCase) {
			medicalCaseCsv.append(medicalCase.getId()).append(",");
		}
		this.batchIdaCaseCsvList = medicalCaseCsv.substring(0, medicalCaseCsv
				.length() - 1);

	}

	public void searchForIdaBatchCase() throws Exception {
		String[] ids = batchIdaCaseCsvList.split(",");
		LOGGER.info("Preparing long array of ids from string array");
		Long[] idl = controllerUtil.makeLongArrayFromStringArray(ids);
		LOGGER.info("Fetching ida cases for ids " + batchIdaCaseCsvList);
		// try {
		ingestedBatchCase = genericDao.findListWhereInCondition(IdaCase.class,
				"id", (Serializable[]) idl);

		this.idaCaseActivityValue = this.idaCaseActivityMap
				.get(this.ingestedBatchCase.get(0).getIdaCaseActivity()
						.getViewValue());
		// } catch (Exception e) {
		// LOGGER.error("Problem fetching ida cases for list "
		// + e.getMessage(), e);
		// ingestedBatchCase = Collections.emptyList();
		// facesUtil.addErrorMessage("Problem while searching"
		// + " ida cases for ids " + batchIdaCaseCsvList);
		// }
		LOGGER.info("Fetched ida cases for ids " + batchIdaCaseCsvList);
	}

	public Ngo getNgoName(String sessionNgoValue) {
		try {
			return this.genericDao.find(Ngo.class, Long
					.valueOf(sessionNgoValue));
		} catch (Exception ex) {
			LOGGER.error("Problem getting ngo for value " + ngoValue + " "
					+ ex.getMessage(), ex);
		}
		return null;
	}

	public void deleteRow(int index) {

		batchDogs.remove(index);
		Object sessionCountObject = facesUtil.getFromSession("batchCount");
		Integer sessionBatchCount = null;
		if (sessionCountObject != null) {
			sessionBatchCount = ((Integer) sessionCountObject) - 1;
			facesUtil.putInSession("batchCount", sessionBatchCount);

		}

		this.isRequired = false;
		// FacesContext.getCurrentInstance().renderResponse();

	}

	public void valueChangeMethod(ValueChangeEvent vce) {
		System.out.println("In valueChangeMethod: A value was changed!"
				+ vce.getNewValue());
		if (vce.getNewValue().equals("true")) {
			this.isRequired = true;
		} else {
			this.isRequired = false;
		}
	}

	public void increaseRow() {// ActionEvent actionEvent){

		idaBatchDogHtmlDataTable.processUpdates(FacesContext
				.getCurrentInstance());
		batchDogs.add(new BatchIdaDog());
		Object sessionCountObject = facesUtil.getFromSession("batchCount");
		Integer sessionBatchCount = null;
		if (sessionCountObject != null) {
			sessionBatchCount = ((Integer) sessionCountObject) + 1;
			facesUtil.putInSession("batchCount", sessionBatchCount);

		}
		// FacesContext.getCurrentInstance().getCurrentPhaseId()
		this.isRequired = false;
		FacesContext.getCurrentInstance().setCurrentPhaseId(
				PhaseId.UPDATE_MODEL_VALUES);
		// FacesContext.getCurrentInstance().renderResponse();
	}

	public Boolean getIsRequired() {
		return this.isRequired;
	}

	// public void increaseRow(ValueChangeEvent actionEvent){
	// batchDogs.add(new BatchIdaDog());
	// Object sessionCountObject = facesUtil.getFromSession("batchCount");
	// Integer sessionBatchCount = null;
	// if (sessionCountObject != null) {
	// sessionBatchCount = ((Integer) sessionCountObject) + 1;
	// facesUtil.putInSession("batchCount", sessionBatchCount);
	//            
	// }
	// FacesContext.getCurrentInstance().setCurrentPhaseId(PhaseId.UPDATE_MODEL_VALUES);
	// // FacesContext.getCurrentInstance().renderResponse();
	// }
	public String getGenderNameIdaCaseDog(String gender) {
		return this.controllerUtil.getSelectKeyFromValueFor(genderMap, gender);
	}

	public String getIdaCaseTypeName(String idaCaseTypeValue) {
		return this.controllerUtil.getSelectKeyFromValueFor(idaCaseTypeMap,
				idaCaseTypeValue);
	}

	public HtmlDataTable getIdaBatchDogHtmlDataTable() {
		return idaBatchDogHtmlDataTable;
	}

	public void setIdaBatchDogHtmlDataTable(
			HtmlDataTable idaBatchDogHtmlDataTable) {
		this.idaBatchDogHtmlDataTable = idaBatchDogHtmlDataTable;
	}

	public List<BatchIdaDog> getBatchDogs() {
		return batchDogs;
	}

	public void setBatchDogs(List<BatchIdaDog> batchDogs) {
		this.batchDogs = batchDogs;
	}

	public void setIdaCasePerson(IdaCasePerson idaCasePerson) {
		this.idaCasePerson = idaCasePerson;
	}

	public IdaCasePerson getIdaCasePerson() {
		return idaCasePerson;
	}

	public Integer getBatchCount() {
		if (!facesUtil.isPostBack()) {
			this.idaCaseActivityValue = null;
			cleanSession();
		}
		return batchCount;
	}

	public void setBatchCount(Integer batchCount) {
		this.batchCount = batchCount;
	}

	public Map<String, String> getNgoMap() {
		return ngoMap;
	}

	public void setNgoMap(Map<String, String> ngoMap) {
		this.ngoMap = ngoMap;
	}

	public String getNgoValue() {
		return ngoValue;
	}

	public void setNgoValue(String ngoValue) {
		this.ngoValue = ngoValue;
	}

	public Map<String, String> getNgoDriverMap() {
		return ngoDriverMap;
	}

	public void setNgoDriverMap(Map<String, String> ngoDriverMap) {
		this.ngoDriverMap = ngoDriverMap;
	}

	public String getNgoDriverValue() {
		return ngoDriverValue;
	}

	public void setNgoDriverValue(String ngoDriverValue) {
		this.ngoDriverValue = ngoDriverValue;
	}

	public List<IdaCaseAdmissionReason> getIdaCaseAdmissionReasonList() {
		return idaCaseAdmissionReasonList;
	}

	public void setIdaCaseAdmissionReasonList(
			List<IdaCaseAdmissionReason> idaCaseAdmissionReasonList) {
		this.idaCaseAdmissionReasonList = idaCaseAdmissionReasonList;
	}

	public Map<String, String> getGenderMap() {
		return genderMap;
	}

	public void setGenderMap(Map<String, String> genderMap) {
		this.genderMap = genderMap;
	}

	public Map<String, String> getAdmissionReasonMap() {
		return admissionReasonMap;
	}

	public void setAdmissionReasonMap(Map<String, String> admissionReasonMap) {
		this.admissionReasonMap = admissionReasonMap;
	}

	public Map<String, String> getDoctorMap() {
		return doctorMap;
	}

	public void setDoctorMap(Map<String, String> doctorMap) {
		this.doctorMap = doctorMap;
	}

	public IdaCase getIdaCase() {
		return idaCase;
	}

	public void setIdaCase(IdaCase idaCase) {
		this.idaCase = idaCase;
	}

	public IdaCaseDoctor getIdaCaseDoctor() {
		return idaCaseDoctor;
	}

	public void setIdaCaseDoctor(IdaCaseDoctor idaCaseDoctor) {
		this.idaCaseDoctor = idaCaseDoctor;
	}

	public NgoDriver getNgoDriver() {
		return ngoDriver;
	}

	public void setNgoDriver(NgoDriver ngoDriver) {
		this.ngoDriver = ngoDriver;
	}

	public GenericDao getGenericDao() {
		return genericDao;
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public ControllerUtil getControllerUtil() {
		return controllerUtil;
	}

	public void setControllerUtil(ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
	}

	public FacesUtil getFacesUtil() {
		return facesUtil;
	}

	public void setFacesUtil(FacesUtil facesUtil) {
		this.facesUtil = facesUtil;
	}

	public void setDoctorValue(String doctorValue) {
		this.doctorValue = doctorValue;
	}

	public String getDoctorValue() {
		return doctorValue;
	}

	public void setIdaCaseTypeMap(Map<String, String> idaCaseTypeMap) {
		this.idaCaseTypeMap = idaCaseTypeMap;
	}

	public Map<String, String> getIdaCaseTypeMap() {
		return idaCaseTypeMap;
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

	public void setIngestedBatchCase(List<IdaCase> ingestedBatchCase) {
		this.ingestedBatchCase = ingestedBatchCase;
	}

	public List<IdaCase> getIngestedBatchCase() {
		return ingestedBatchCase;
	}

	public void setBatchIdaCaseCsvList(String batchIdaCaseCsvList) {
		this.batchIdaCaseCsvList = batchIdaCaseCsvList;
	}

	public String getBatchIdaCaseCsvList() {
		return batchIdaCaseCsvList;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsRequiredString() {
		return isRequiredString;
	}

	public void setIsRequiredString(String isRequiredString) {
		this.isRequiredString = isRequiredString;
	}

	public void setIdaCaseDogGenderValue(String idaCaseDogGenderValue) {
		this.idaCaseDogGenderValue = idaCaseDogGenderValue;
	}

	public String getIdaCaseDogGenderValue() {
		return idaCaseDogGenderValue;
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

	public void setIdaCasePersonGenderValue(String idaCasePersonGenderValue) {
		this.idaCasePersonGenderValue = idaCasePersonGenderValue;
	}

	public String getIdaCasePersonGenderValue() {
		return idaCasePersonGenderValue;
	}

	public String getIsReadonlyString() {
		return "readonly";
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

	public void setIsNoneCase(Boolean isNoneCase) {
		this.isNoneCase = isNoneCase;
	}

	public Boolean getIsNoneCase() {
		return isNoneCase;
	}

	public void setPersonGenderMap(Map<String, String> personGenderMap) {
		this.personGenderMap = personGenderMap;
	}

	public Map<String, String> getPersonGenderMap() {
		return personGenderMap;
	}

	public Map<String, String> getLocalityMap() {
		return localityMap;
	}

	public void setLocalityMap(Map<String, String> localityMap) {
		this.localityMap = localityMap;
	}

	public String getIdaCasePersonLocalityValue() {
		return idaCasePersonLocalityValue;
	}

	public void setIdaCasePersonLocalityValue(String idaCasePersonLocalityValue) {
		this.idaCasePersonLocalityValue = idaCasePersonLocalityValue;
	}

	public String getNgoChairPersonLocalityViewValue() {
		return ngoChairPersonLocalityViewValue;
	}

	public void setNgoChairPersonLocalityViewValue(
			String ngoChairPersonLocalityViewValue) {
		this.ngoChairPersonLocalityViewValue = ngoChairPersonLocalityViewValue;
	}

	public PhoneCall getPhoneCall() {
		return phoneCall;
	}

	public void setPhoneCall(PhoneCall phoneCall) {
		this.phoneCall = phoneCall;
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

	public Ngo getNgo() {
		return ngo;
	}

	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

}
