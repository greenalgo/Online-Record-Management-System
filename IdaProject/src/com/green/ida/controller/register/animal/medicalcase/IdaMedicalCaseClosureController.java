package com.green.ida.controller.register.animal.medicalcase;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.adoption.IdaAdoption;
import com.green.ida.entity.animal.idacase.pojos.IdaCase;
import com.green.ida.entity.animal.idacase.resident.IdaResident;
import com.green.ida.entity.animal.idacase.result.enums.IdaCaseResultType;
import com.green.ida.entity.animal.idacase.result.pojos.IdaAdoptedResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaBuriedInCenterResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaCaseResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaKoraKendaraResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaReleasedResult;
import com.green.ida.entity.animal.idacase.result.pojos.IdaResidentResult;
import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("view")
public class IdaMedicalCaseClosureController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2171172039193518851L;
	private static transient Logger LOGGER = Logger
			.getLogger(IdaMedicalCaseClosureController.class);
	private IdaCase idaCase;
	private IdaCaseResult idaCaseResult;
	private Map<String, String> idaCaseResultTypeMap;
	private String idaCaseResultTypeValue;
	private String currentSelection;
	@Inject
	private GenericDao genericDao;
	@Inject
	private ControllerUtil controllerUtil;
	@Inject
	private FacesUtil facesUtil;
	private IdaMedicalCaseClosureForm form = new IdaMedicalCaseClosureForm();
	@Inject
	private IdaAdoptionResidentCaseHelper idaAdoptionResidentCaseHelper;

	private String navigation;

	@PostConstruct
	public void initIdaCaseComponents() {
		this.idaCase = new IdaCase();
		setIdaCaseResult(new IdaCaseResult());
		this.idaCaseResultTypeMap = this.controllerUtil
				.initIdaCaseResultTypeMap();
		form.setControllerUtil(controllerUtil);
		form.init();

	}

	public String closeIdaCase() {
		LOGGER.info("Closing ida case with id " + this.idaCase.getId());
		try {
			idaCase = genericDao.findFirstWhereCondition(IdaCase.class, "id",
					this.idaCase.getId());
                        if(idaCase.getIsCaseClosed()){
                            LOGGER.error("Attempt to close a case which was already closed " + idaCase.getId());
                            facesUtil.addErrorMessage("Case with id " + idaCase.getId() + " already closed !!!!");
                            return null;
                        }
			idaCase.setIsCaseClosed(Boolean.TRUE);
			// idaCaseResult.setReleaseDate(new Date());
			getIdaCaseResult().setIdaResultType(
					IdaCaseResultType
							.getIdaCaseResultType(getIdaCaseResultType()));
			// idaCase.setIdaCaseResult(getIdaCaseResult());
			setCaseAcccordingToChoiceMade();
			idaCase = genericDao.merge(idaCase);

			// Flash flash = FacesContext.getCurrentInstance()
			// .getExternalContext().getFlash();
			// flash.put("closureId", this.idaCase.getIdaCaseResult().getId());
			// flash.put("caseId", this.idaCase.getId());
		} catch (Exception e) {
			LOGGER.error("Problem in closing ida case with id "
					+ this.idaCase.getId() + " " + e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Closed ida case with id " + this.idaCase.getId());
		return navigation
				+ "?faces-redirect=true&amp;includeViewParams=true&amp;caseId="
				+ this.idaCase.getId() + "&amp;" + "closureId="
				+ this.idaCase.getIdaCaseResult().getId();
	}

	public void setCaseAcccordingToChoiceMade() throws Exception {
		// try{
		if (this.currentSelection.equalsIgnoreCase("adopted")) {
			IdaAdoption idaAdoption = this.idaAdoptionResidentCaseHelper
					.getFilledIdaAdoptionInstance(this.form);
			IdaAdoptedResult idaAdoptedResult = this.form.getIdaAdoptedResult();
			idaAdoptedResult.setIdaAdoption(idaAdoption);
			idaAdoptedResult.setSuperValues(getIdaCaseResult());
			idaCase.setIdaCaseResult(idaAdoptedResult);
			navigation = "idaMedicalCaseAdoptionView";
		} else if (this.currentSelection.equalsIgnoreCase("resident")) {
			IdaResident idaResident = this.idaAdoptionResidentCaseHelper
					.getFilledIdaResidentInstance(this.form);
			IdaResidentResult idaResidentResult = this.form
					.getIdaResidentResult();
			idaResidentResult.setIdaResident(idaResident);
			idaResidentResult.setSuperValues(getIdaCaseResult());
			idaCase.setIdaCaseResult(idaResidentResult);
			navigation = "idaMedicalCaseResidentView";
		} else if (this.currentSelection.equalsIgnoreCase("released")) {
			IdaReleasedResult idaReleasedResult = this.form
					.getIdaReleasedResult();
			idaReleasedResult.setSuperValues(getIdaCaseResult());

			NgoDriver ngoDriver = this.genericDao.getReference(NgoDriver.class,
					Long.valueOf(this.form.getDriverValue()));
			NgoVehicle ngoVehicle = this.genericDao.getReference(
					NgoVehicle.class, Long.valueOf(this.form.getVanValue()));
			NgoCatcher ngoCatcher = this.genericDao
					.getReference(NgoCatcher.class, Long.valueOf(this.form
							.getCatcherValue()));

			idaReleasedResult.setNgoCatcher(ngoCatcher);
			idaReleasedResult.setNgoDriver(ngoDriver);
			idaReleasedResult.setNgoVehicle(ngoVehicle);

			idaCase.setIdaCaseResult(idaReleasedResult);
			navigation = "idaMedicalCaseReleaseView";
		} else if (this.currentSelection.equalsIgnoreCase("kora kendra")) {
			IdaKoraKendaraResult idaKoraKendaraResult = this.form
					.getIdaKoraKendaraResult();
			idaKoraKendaraResult.setSuperValues(getIdaCaseResult());

			NgoDriver ngoDriver = this.genericDao.getReference(NgoDriver.class,
					Long.valueOf(this.form.getDriverValue()));
			NgoVehicle ngoVehicle = this.genericDao.getReference(
					NgoVehicle.class, Long.valueOf(this.form.getVanValue()));
			NgoCatcher ngoCatcher = this.genericDao
					.getReference(NgoCatcher.class, Long.valueOf(this.form
							.getCatcherValue()));
			IdaCaseDoctor idaCaseDoctor = this.genericDao.getReference(
					IdaCaseDoctor.class, Long.valueOf(this.form
							.getDoctorValue()));

			idaKoraKendaraResult.setNgoCatcher(ngoCatcher);
			idaKoraKendaraResult.setNgoDriver(ngoDriver);
			idaKoraKendaraResult.setNgoVehicle(ngoVehicle);
			idaKoraKendaraResult.setIdaCaseDoctor(idaCaseDoctor);

			idaCase.setIdaCaseResult(idaKoraKendaraResult);
			navigation = "idaMedicalCaseKoraKendaraView";
		} else if (this.currentSelection.equalsIgnoreCase("buried in center")) {
			IdaBuriedInCenterResult idaBuriedInCenterResult = this.form
					.getIdaBuriedInCenterResult();
			idaBuriedInCenterResult.setSuperValues(getIdaCaseResult());

			IdaCaseDoctor idaCaseDoctor = this.genericDao.getReference(
					IdaCaseDoctor.class, Long.valueOf(this.form
							.getDoctorValue()));
			idaBuriedInCenterResult.setIdaCaseDoctor(idaCaseDoctor);

			idaCase.setIdaCaseResult(idaBuriedInCenterResult);
			navigation = "idaMedicalCaseBuriedInCenterView";

		}

		// }catch(Exception e){

		// }
	}

	public String gotIdaCase(Long id) {
		System.out.println("Got id " + id);
		return null;
	}

	public void searchForIdaCase() throws Exception {
		LOGGER.info("Searching for ida case "
				+ this.idaCase.getIdaCaseResult().getId());
		idaCaseResult = genericDao.find(IdaCaseResult.class, this.idaCaseResult
				.getId());
		this.form.setIdaResultAccordingToCaseType(idaCaseResult);
		LOGGER.info("Searched for ida case " + this.idaCaseResult.getId());
	}

	public String getIdaCaseResultType() {
		return this.controllerUtil.getSelectKeyFromValueFor(
				idaCaseResultTypeMap, idaCaseResultTypeValue);
	}

	public String getIdaCaseResultType(String value) {
		return this.controllerUtil.getSelectKeyFromValueFor(
				idaCaseResultTypeMap, value);
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

	public void setIdaCase(IdaCase idaCase) {
		this.idaCase = idaCase;
	}

	public IdaCase getIdaCase() {
		return idaCase;
	}

	public void setFacesUtil(FacesUtil facesUtil) {
		this.facesUtil = facesUtil;
	}

	public FacesUtil getFacesUtil() {
		return facesUtil;
	}

	public void setIdaCaseResultTypeMap(Map<String, String> idaCaseResultTypeMap) {
		this.idaCaseResultTypeMap = idaCaseResultTypeMap;
	}

	public Map<String, String> getIdaCaseResultTypeMap() {
		return idaCaseResultTypeMap;
	}

	public void setIdaCaseResultTypeValue(String idaCaseResultTypeValue) {
		this.idaCaseResultTypeValue = idaCaseResultTypeValue;
	}

	public String getIdaCaseResultTypeValue() {
		return idaCaseResultTypeValue;
	}

	/**
	 * @return the idaCaseResult
	 */
	public IdaCaseResult getIdaCaseResult() {
		return idaCaseResult;
	}

	/**
	 * @param idaCaseResult
	 *            the idaCaseResult to set
	 */
	public void setIdaCaseResult(IdaCaseResult idaCaseResult) {
		this.idaCaseResult = idaCaseResult;
	}

	public void processCaseTypeChange(ValueChangeEvent evt) {
		System.out.println("Event source: "
				+ ((UIInput) evt.getSource()).getId());
		System.out.println("Old value: " + evt.getOldValue());
		System.out.println("New Value: " + evt.getNewValue());

		this.currentSelection = getIdaCaseResultType((String) evt.getNewValue());

		System.out.println(currentSelection);

		facesUtil.removeAllMessages();

	}

	public Boolean isCaseClosureVisisbleFor(String resultType) {

		final boolean selection = resultType
				.equalsIgnoreCase(this.currentSelection);
		LOGGER.info("is visible value true for " + resultType + "    "
				+ selection);
		return selection;
	}

	public IdaMedicalCaseClosureForm getForm() {
		return this.form;
	}
}
