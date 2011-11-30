package com.green.ida.controller.register.animal.medicalcase;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.pojos.IdaCase;
import com.green.ida.entity.animal.idacase.result.pojos.IdaCaseResult;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class IdaMedicalCaseClosureViewController {

	private static transient Logger LOGGER = Logger
			.getLogger(IdaMedicalCaseClosureViewController.class);
	private IdaCase idaCase;
	@Inject
	private GenericDao genericDao;
	@Inject
	private ControllerUtil controllerUtil;
	@Inject
	private FacesUtil facesUtil;
	private IdaMedicalCaseClosureForm form = new IdaMedicalCaseClosureForm();
	// @Inject
	// private IdaAdoptionResidentCaseHelper idaAdoptionResidentCaseHelper;

	private IdaCaseResult idaCaseResult;

	@PostConstruct
	public void initIdaCaseComponents() {
		this.idaCase = new IdaCase();
		this.idaCase.setIdaCaseResult(new IdaCaseResult());
		// this.idaCaseResultTypeMap =
		// this.controllerUtil.initIdaCaseResultTypeMap();
		form.setControllerUtil(controllerUtil);
		// form.init();

	}

	public void searchForIdaCase() throws Exception {
		// Flash flash = FacesContext.getCurrentInstance().getExternalContext()
		// .getFlash();
		// Long id = (Long) flash.get("closureId");
		// this.idaCase.setId((Long) flash.get("caseId"));
		LOGGER.info("Searching for ida case result and ida case "
				+ this.idaCase.getIdaCaseResult().getId() + " "
				+ this.idaCase.getId());
		idaCaseResult = genericDao.find(IdaCaseResult.class, this.idaCase
				.getIdaCaseResult().getId());
		this.form.setIdaResultAccordingToCaseType(idaCaseResult);
		LOGGER.info("Searched for ida case result and ida case "
				+ this.idaCase.getIdaCaseResult().getId() + " "
				+ this.idaCase.getId());
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

	public IdaMedicalCaseClosureForm getForm() {
		return this.form;
	}

	public IdaCaseResult getIdaCaseResult() {
		return idaCaseResult;
	}

	public void setIdaCaseResult(IdaCaseResult idaCaseResult) {
		this.idaCaseResult = idaCaseResult;
	}
}
