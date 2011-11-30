package com.green.ida.controller.register.animal.medicalcase;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.pojos.IdaCase;
import com.green.ida.entity.animal.idacase.result.enums.IdaCaseResultType;
import com.green.ida.entity.animal.idacase.result.pojos.IdaCaseResult;
import com.green.ida.entity.animal.idacase.result.pojos.MobileIdaCaseResult;
import com.green.ida.entity.animal.idacase.result.pojos.MobileIdaCaseResult.MobileResult;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("view")
public class IdaMobileMedicalCaseClosureController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2171172039199518851L;
	private static transient Logger LOGGER = Logger
			.getLogger(IdaMobileMedicalCaseClosureController.class);
	private IdaCase idaCase;
	private MobileIdaCaseResult mobileIdaCaseResult;
	private Map<String, Long> mobileResultTypeMap;
	private Long mobileResultTypeValue;

	@Inject
	private GenericDao genericDao;
	@Inject
	private ControllerUtil controllerUtil;
	@Inject
	private FacesUtil facesUtil;

	@PostConstruct
	public void initIdaCaseComponents() {
		this.idaCase = new IdaCase();
		this.mobileIdaCaseResult = new MobileIdaCaseResult();
		this.idaCase.setIdaCaseResult(mobileIdaCaseResult);
		this.mobileResultTypeMap = MobileIdaCaseResult.MobileResult
				.getMobileResultMap();
	}

	public String closeIdaCase() {
		LOGGER.info("Closing ida case with id " + this.idaCase.getId());
		try {
			idaCase = genericDao.findFirstWhereCondition(IdaCase.class, "id",
					this.idaCase.getId());
			if (idaCase.getIsCaseClosed()) {
				LOGGER
						.error("Attempt to close a case which was already closed "
								+ idaCase.getId());
				facesUtil.addErrorMessage("Case with id " + idaCase.getId()
						+ " already closed !!!!");
				return null;
			}
			idaCase.setIsCaseClosed(Boolean.TRUE);
			// idaCaseResult.setReleaseDate(new Date());
			mobileIdaCaseResult.setIdaResultType(IdaCaseResultType.MOBILE);
			// idaCase.setIdaCaseResult(getIdaCaseResult());
			// setCaseAcccordingToChoiceMade();
			mobileIdaCaseResult.setMobileResult(MobileResult
					.getMobileResult(getIdaCaseMobileResultName()));
			idaCase.setIdaCaseResult(mobileIdaCaseResult);
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
		return "mobileIdaMedicalCaseCloseView"
				+ "?faces-redirect=true&amp;includeViewParams=true&amp;caseId="
				+ this.idaCase.getId() + "&amp;" + "closureId="
				+ this.idaCase.getIdaCaseResult().getId();
	}

	public void searchForIdaCase() throws Exception {
		LOGGER.info("Searching for ida case "
				+ this.idaCase.getIdaCaseResult().getId());
		mobileIdaCaseResult = (MobileIdaCaseResult) genericDao.find(
				IdaCaseResult.class, this.mobileIdaCaseResult.getId());
		this.idaCase.setIdaCaseResult(mobileIdaCaseResult);
		LOGGER
				.info("Searched for ida case "
						+ this.mobileIdaCaseResult.getId());
	}

	public String getIdaCaseMobileResultName() {
		return this.controllerUtil.getSelectKeyFromValueFor(
				mobileResultTypeMap, mobileResultTypeValue);
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

	public void setMobileResultTypeMap(Map<String, Long> mobileResultTypeMap) {
		this.mobileResultTypeMap = mobileResultTypeMap;
	}

	public Map<String, Long> getMobileResultTypeMap() {
		return mobileResultTypeMap;
	}

	public void setMobileResultTypeValue(Long mobileResultTypeValue) {
		this.mobileResultTypeValue = mobileResultTypeValue;
	}

	public Long getMobileResultTypeValue() {
		return mobileResultTypeValue;
	}

	public void setMobileIdaCaseResult(MobileIdaCaseResult mobileIdaCaseResult) {
		this.mobileIdaCaseResult = mobileIdaCaseResult;
	}

	public MobileIdaCaseResult getMobileIdaCaseResult() {
		return mobileIdaCaseResult;
	}

}
