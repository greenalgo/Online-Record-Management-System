package com.green.ida.controller.register.ngo.catcher;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.base.entity.living.human.related.address.Locality;
import com.green.base.entity.living.human.related.address.SimpleLocality;
import com.green.base.entity.living.related.enums.Gender;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.ngo.Ngo;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterNgoCatcherController {

	private static transient Logger LOGGER = Logger
			.getLogger(RegisterNgoCatcherController.class);
	private NgoCatcher ngoCatcher;
	private Map<String, String> localityMap;
	private String localityValue;
	private Map<String, String> genderMap;
	private String ngoCatcherGenderValue;
	private Map<String, String> ngoMap;
	private String ngoValue;
	// private Boolean isEdit;
	private Boolean byPassEditGetListener = Boolean.FALSE;
	@Inject
	private GenericDao genericDao;
	@Inject
	private ControllerUtil controllerUtil;
	@Inject
	private FacesUtil facesUtil;

	private List<NgoCatcher> ngoCatcherList;

	public void searchAllNgoCatcher() {
		LOGGER.info("searching all catchers list");
		try {
			ngoCatcherList = genericDao.findAll(NgoCatcher.class);
		} catch (Exception e) {
			LOGGER.error("Problem finding all ngo catcher" + e.getMessage(), e);
		}
		LOGGER.info("done searching all catcher list");
	}

	public void searchForNgoCatcher() {
		LOGGER.info("Searching ngo catcher with id " + this.ngoCatcher.getId());
		try {

			this.ngoCatcher = genericDao.findFirstWhereCondition(
					NgoCatcher.class, "id", this.ngoCatcher.getId());
			this.localityValue = this.ngoCatcher.getAddress().getLocality()
					.getId().toString();

			this.ngoCatcherGenderValue = this.genderMap.get(Gender
					.valueOf(this.ngoCatcher.getGender()));

			this.ngoValue = this.ngoMap.get(this.ngoCatcher.getNgo()
					.getNgoName());
		} catch (Exception e) {
			LOGGER.error("Problem searching ngo catcher with id "
					+ this.ngoCatcher.getId() + " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem searching ngo catcher with id "
					+ this.ngoCatcher.getId());
		}
		LOGGER.info("Finished searching ngo catcher with id "
				+ this.ngoCatcher.getId());
	}

	public String saveNgoCatcher() {
		// System.out.println(idaCaseDoctor.getFirstName());
		try {
			LOGGER.info("Saving ngoCatcher " + ngoCatcher);
			String presentMsg = controllerUtil.isHumanBeingPresentBySameName(
					this.ngoCatcher.getName().getFirstName(), this.ngoCatcher
							.getName().getMiddleName(), this.ngoCatcher
							.getName().getLastName());
			if (!"".equals(presentMsg)) {
				facesUtil.addErrorMessage(presentMsg);
				return null;
			}
			ingestNgoCatcher();
			LOGGER.info("Ngo catcher saved with id " + this.ngoCatcher.getId());
		} catch (Exception e) {
			LOGGER.error("Problem saving ngo catcher " + ngoCatcher + " "
					+ e.getMessage(), e);
			return "error";
		}
		return "ngoCatcherDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	public void ngoCatcherForEdit() {
		if (byPassEditGetListener) {
			return;
		}
		Long id = this.ngoCatcher.getId();
		LOGGER.info("Fetching ngo catcher details for id " + id);
		// this.isEdit = true;
		if (id == null || id <= 0) {
			LOGGER
					.error("NgoCatcher with null id or negative value cannot be editted");
			facesUtil
					.addErrorMessage("Ngo catcher cannot be fetched for null Id");
			return;
		}

		searchForNgoCatcher();
		// //this.toEditDoctorId = null;
		// FacesContext context = FacesContext.getCurrentInstance();
		// ConfigurableNavigationHandler handler =
		// (ConfigurableNavigationHandler)
		// context.getApplication().getNavigationHandler();
		// handler.performNavigation("editDoctor");
		// return "registerDoctor";
	}

	public String updateNgoCatcher() {
		LOGGER.info("Updating ngo catcher " + this.ngoCatcher + " for id "
				+ this.ngoCatcher.getId());
		try {
			String presentMsg = controllerUtil
					.isHumanBeingPresentBySameNameEditCase(this.ngoCatcher
							.getId(), this.ngoCatcher.getName().getFirstName(),
							this.ngoCatcher.getName().getMiddleName(),
							this.ngoCatcher.getName().getLastName());
			if (!"".equals(presentMsg)) {
				facesUtil.addErrorMessage(presentMsg);
				return null;
			}
			ingestNgoCatcher();

		} catch (Exception e) {
			LOGGER.error("Problem updating ngo catcher " + ngoCatcher + " "
					+ e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Updated ngo catcher with id " + this.ngoCatcher.getId());
		// FacesContext.getCurrentInstance().responseComplete();
		byPassEditGetListener = true;
		// return "editedDoctorDetails";
		return "ngoCatcherDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private void ingestNgoCatcher() throws Exception {
		Locality locality = this.genericDao.getReference(Locality.class, Long
				.valueOf(localityValue));
		Ngo ngo = this.genericDao.find(Ngo.class, Long.valueOf(ngoValue));
		this.ngoCatcher.getAddress().setLocality(locality);
		// new SimpleLocality(localityValue));
		this.ngoCatcher.setGender(Gender.getGender(getGenderName()));
		this.ngoCatcher.setNgo(ngo);
		this.ngoCatcher = this.genericDao.merge(this.ngoCatcher);
	}

	@PostConstruct
	public void initNgoCatcherComponents() {
		this.ngoCatcher = new NgoCatcher();
		this.ngoCatcher.setContactDetails(new ContactDetails());
		this.ngoCatcher.setAddress(new Address(new SimpleLocality()));
		// this.setLocalityMap(new LinkedHashMap<String, String>());
		this.localityMap = controllerUtil.initLocalityMap();
		this.genderMap = controllerUtil.initGenderMap();
		this.ngoMap = controllerUtil.initNgoMap();
	}

	// private void initLocalityMap() {
	// LOGGER.info("Getting locality map .......");
	// try{
	// this.getLocalityMap().put("Select Locality","0");
	// List<Locality> localityList = genericDao.findAll(Locality.class);
	// for (Locality locality : localityList) {
	// this.getLocalityMap().put(locality.getLocality() + " " +
	// locality.getDistinguished(),locality.getId().toString());
	// }
	// }catch (Exception e) {
	// LOGGER.error("Problem getting locality ......" + e.getMessage(),e);
	// }
	// }
	public String getLocalityName() {
		return this.controllerUtil.getSelectKeyFromValueFor(localityMap,
				localityValue);
	}

	public String getGenderName() {
		return this.controllerUtil.getSelectKeyFromValueFor(genderMap,
				ngoCatcherGenderValue);
	}

	public String getNgoName() {
		return this.controllerUtil.getSelectKeyFromValueFor(ngoMap, ngoValue);
	}

	public Map<String, String> getLocalityMap() {
		return localityMap;
	}

	public void setLocalityMap(Map<String, String> localityMap) {
		this.localityMap = localityMap;
	}

	public String getLocalityValue() {
		return localityValue;
	}

	public void setLocalityValue(String localityValue) {
		this.localityValue = localityValue;
	}

	public Map<String, String> getGenderMap() {
		return genderMap;
	}

	public void setGenderMap(Map<String, String> genderMap) {
		this.genderMap = genderMap;
	}

	public String getNgoCatcherGenderValue() {
		return ngoCatcherGenderValue;
	}

	public void setNgoCatcherGenderValue(String ngoCatcherGenderValue) {
		this.ngoCatcherGenderValue = ngoCatcherGenderValue;
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

	public NgoCatcher getNgoCatcher() {
		return ngoCatcher;
	}

	public void setNgoCatcher(NgoCatcher ngoCatcher) {
		this.ngoCatcher = ngoCatcher;
	}

	public void setNgoCatcherList(List<NgoCatcher> ngoCatcherList) {
		this.ngoCatcherList = ngoCatcherList;
	}

	public List<NgoCatcher> getNgoCatcherList() {
		return ngoCatcherList;
	}

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}
}
