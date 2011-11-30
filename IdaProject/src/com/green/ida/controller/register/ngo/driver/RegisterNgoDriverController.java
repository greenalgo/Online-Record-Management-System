package com.green.ida.controller.register.ngo.driver;

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
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.ngo.Ngo;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterNgoDriverController {

	private static transient Logger LOGGER = Logger
			.getLogger(RegisterNgoDriverController.class);
	private NgoDriver ngoDriver;
	private Map<String, String> localityMap;
	private String localityValue;
	private Map<String, String> genderMap;
	private String ngoDriverGenderValue;
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

	private List<NgoDriver> ngoDriverList;

	public void searchAllNgoDriver() {
		LOGGER.info("searching all drivers list");
		try {
			ngoDriverList = genericDao.findAll(NgoDriver.class);
		} catch (Exception e) {
			LOGGER.error("Problem finding all ngo driver" + e.getMessage(), e);
		}
		LOGGER.info("done searching all drivers list");

	}

	public void searchForNgoDriver() {
		LOGGER.info("Searching ngo driver with id " + this.ngoDriver.getId());
		try {

			this.ngoDriver = genericDao.findFirstWhereCondition(
					NgoDriver.class, "id", this.ngoDriver.getId());
			this.localityValue = this.ngoDriver.getAddress().getLocality()
					.getId().toString();

			this.ngoDriverGenderValue = this.genderMap.get(Gender
					.valueOf(this.ngoDriver.getGender()));

			this.ngoValue = this.ngoMap.get(this.ngoDriver.getNgo()
					.getNgoName());
		} catch (Exception e) {
			LOGGER.error("Problem searching ngo driver with id "
					+ this.ngoDriver.getId() + " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem searching ngo driver with id "
					+ this.ngoDriver.getId());
		}
		LOGGER.info("Finished searching ngo driver with id "
				+ this.ngoDriver.getId());
	}

	public String saveNgoDriver() {
		// System.out.println(idaCaseDoctor.getFirstName());
		try {
			LOGGER.info("Saving ngoDriver " + ngoDriver);
			String presentMsg = controllerUtil.isHumanBeingPresentBySameName(
					this.ngoDriver.getName().getFirstName(), this.ngoDriver
							.getName().getMiddleName(), this.ngoDriver
							.getName().getLastName());
			if (!"".equals(presentMsg)) {
				facesUtil.addErrorMessage(presentMsg);
				return null;
			}
			ingestNgoDriver();
			LOGGER.info("Ngo driver saved with id " + this.ngoDriver.getId());
		} catch (Exception e) {
			LOGGER.error("Problem saving ngo driver " + ngoDriver + " "
					+ e.getMessage(), e);
			return "error";
		}
		return "ngoDriverDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	public void ngoDriverForEdit() {
		if (byPassEditGetListener) {
			return;
		}
		Long id = this.ngoDriver.getId();
		LOGGER.info("Fetching ngo driver details for id " + id);
		// this.isEdit = true;
		if (id == null || id <= 0) {
			LOGGER
					.error("NgoDriver with null id or negative value cannot be editted");
			facesUtil
					.addErrorMessage("Ngo Driver cannot be fetched for null Id");
			return;
		}

		searchForNgoDriver();
		// //this.toEditDoctorId = null;
		// FacesContext context = FacesContext.getCurrentInstance();
		// ConfigurableNavigationHandler handler =
		// (ConfigurableNavigationHandler)
		// context.getApplication().getNavigationHandler();
		// handler.performNavigation("editDoctor");
		// return "registerDoctor";
	}

	public String updateNgoDriver() {
		LOGGER.info("Updating ngo driver " + this.ngoDriver + " for id "
				+ this.ngoDriver.getId());
		try {
			String presentMsg = controllerUtil
					.isHumanBeingPresentBySameNameEditCase(this.ngoDriver
							.getId(), this.ngoDriver.getName().getFirstName(),
							this.ngoDriver.getName().getMiddleName(),
							this.ngoDriver.getName().getLastName());
			if (!"".equals(presentMsg)) {
				facesUtil.addErrorMessage(presentMsg);
				return null;
			}
			ingestNgoDriver();

		} catch (Exception e) {
			LOGGER.error("Problem updating ngo driver " + ngoDriver + " "
					+ e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Updated ngo driver with id " + this.ngoDriver.getId());
		// FacesContext.getCurrentInstance().responseComplete();
		byPassEditGetListener = true;
		// return "editedDoctorDetails";
		return "ngoDriverDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private void ingestNgoDriver() throws Exception {
		Locality locality = this.genericDao.getReference(Locality.class, Long
				.valueOf(localityValue));
		Ngo ngo = this.genericDao.find(Ngo.class, Long.valueOf(ngoValue));
		this.ngoDriver.getAddress().setLocality(locality);
		this.ngoDriver.setGender(Gender.getGender(getGenderName()));
		this.ngoDriver.setNgo(ngo);
		this.ngoDriver = this.genericDao.merge(this.ngoDriver);
	}

	@PostConstruct
	public void initNgoDriverComponents() {
		this.ngoDriver = new NgoDriver();
		this.ngoDriver.setContactDetails(new ContactDetails());
		this.ngoDriver.setAddress(new Address(new SimpleLocality()));
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
				ngoDriverGenderValue);
	}

	public String getNgoName() {
		return this.controllerUtil.getSelectKeyFromValueFor(ngoMap, ngoValue);
	}

	public NgoDriver getNgoDriver() {
		return ngoDriver;
	}

	public void setNgoDriver(NgoDriver ngoDriver) {
		this.ngoDriver = ngoDriver;
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

	public String getNgoDriverGenderValue() {
		return ngoDriverGenderValue;
	}

	public void setNgoDriverGenderValue(String ngoDriverGenderValue) {
		this.ngoDriverGenderValue = ngoDriverGenderValue;
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

	public void setNgoDriverList(List<NgoDriver> ngoDriverList) {
		this.ngoDriverList = ngoDriverList;
	}

	public List<NgoDriver> getNgoDriverList() {
		return ngoDriverList;
	}

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}
}
