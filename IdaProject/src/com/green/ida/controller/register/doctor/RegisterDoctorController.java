package com.green.ida.controller.register.doctor;

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
import com.green.base.entity.living.related.enums.Gender;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterDoctorController {
	private static transient Logger LOGGER = Logger
			.getLogger(RegisterDoctorController.class);

	private IdaCaseDoctor idaCaseDoctor;
	private Map<String, String> localityMap;
	private String localityValue;

	private Map<String, String> genderMap;
	private String doctorGenderValue;

	// private Boolean isEdit;

	private Boolean byPassEditGetListener = Boolean.FALSE;

	@Inject
	private GenericDao genericDao;

	@Inject
	private ControllerUtil controllerUtil;

	@Inject
	private FacesUtil facesUtil;

	private List<IdaCaseDoctor> doctorList;

	public void searchAllDoctor() {
		LOGGER.info("searching all doctor list");
		try {
			doctorList = genericDao.findAll(IdaCaseDoctor.class);
		} catch (Exception e) {
			LOGGER.error("Problem finding all doctor" + e.getMessage(), e);
		}
		LOGGER.info("done searching all doctor list");

	}

	public void setIdaCaseDoctor(IdaCaseDoctor idaCaseDoctor) {
		this.idaCaseDoctor = idaCaseDoctor;
	}

	public IdaCaseDoctor getIdaCaseDoctor() {
		return idaCaseDoctor;
	}

	public void searchForIdaDoctor() {
		LOGGER.info("Searching doctor with id " + this.idaCaseDoctor.getId());
		try {

			this.idaCaseDoctor = genericDao.findFirstWhereCondition(
					IdaCaseDoctor.class, "id", this.idaCaseDoctor.getId());
			// this.localityValue = this.idaCaseDoctor.getAddress()
			// .getLocality().getLocalityName();
			this.localityValue = this.idaCaseDoctor.getAddress().getLocality()
					.getId().toString();

			this.doctorGenderValue = this.genderMap.get(Gender
					.valueOf(this.idaCaseDoctor.getGender()));
		} catch (Exception e) {
			LOGGER.error("Problem searching doctor with id "
					+ this.idaCaseDoctor.getId() + " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem searching doctor with id "
					+ this.idaCaseDoctor.getId());
		}
		LOGGER.info("Finished searching doctor with id "
				+ this.idaCaseDoctor.getId());
	}

	public String saveDoctor() {
		// System.out.println(idaCaseDoctor.getFirstName());
		try {
			LOGGER.info("Saving doctor " + idaCaseDoctor);
			String presentMsg = controllerUtil.isHumanBeingPresentBySameName(
					this.idaCaseDoctor.getName().getFirstName(),
					this.idaCaseDoctor.getName().getMiddleName(),
					this.idaCaseDoctor.getName().getLastName());
			if (!"".equals(presentMsg)) {
				facesUtil.addErrorMessage(presentMsg);
				return null;
			}
			ingestIdaCaseDoctor();
			LOGGER.info("Doctor saved with id " + this.idaCaseDoctor.getId());
		} catch (Exception e) {
			LOGGER.error("Problem saving doctor " + idaCaseDoctor + " "
					+ e.getMessage(), e);
			return "error";
		}
		return "doctorDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	public void doctorForEdit() {
		if (byPassEditGetListener) {
			return;
		}
		Long id = this.idaCaseDoctor.getId();
		LOGGER.info("Fetching doctor details for id " + id);
		// this.isEdit = true;
		if (id == null || id <= 0) {
			LOGGER
					.error("Doctor with null id or negative value cannot be editted");
			facesUtil.addErrorMessage("Doctor cannot be fetched for null Id");
			return;
		}

		searchForIdaDoctor();
		// //this.toEditDoctorId = null;
		// FacesContext context = FacesContext.getCurrentInstance();
		// ConfigurableNavigationHandler handler =
		// (ConfigurableNavigationHandler)
		// context.getApplication().getNavigationHandler();
		// handler.performNavigation("editDoctor");
		// return "registerDoctor";
	}

	public String updateDoctor() {
		LOGGER.info("Updating doctor " + this.idaCaseDoctor + " for id "
				+ this.idaCaseDoctor.getId());
		try {
			String presentMsg = controllerUtil
					.isHumanBeingPresentBySameNameEditCase(this.idaCaseDoctor
							.getId(), this.idaCaseDoctor.getName()
							.getFirstName(), this.idaCaseDoctor.getName()
							.getMiddleName(), this.idaCaseDoctor.getName()
							.getLastName());
			if (!"".equals(presentMsg)) {
				facesUtil.addErrorMessage(presentMsg);
				return null;
			}
			ingestIdaCaseDoctor();

		} catch (Exception e) {
			LOGGER.error("Problem updating doctor " + idaCaseDoctor + " "
					+ e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Updated ida case doctor with id "
				+ this.idaCaseDoctor.getId());
		// FacesContext.getCurrentInstance().responseComplete();
		byPassEditGetListener = true;
		// return "editedDoctorDetails";
		return "doctorDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private void ingestIdaCaseDoctor() throws Exception {
		Locality locality = this.genericDao.getReference(Locality.class, Long
				.valueOf(localityValue));
		this.idaCaseDoctor.getAddress().setLocality(locality);

		// setSimpleLocality(
		// new SimpleLocality(localityValue));
		this.idaCaseDoctor.setGender(Gender.getGender(getGenderName()));
		this.idaCaseDoctor = this.genericDao.merge(this.idaCaseDoctor);
	}

	@PostConstruct
	public void initDoctorComponents() {
		this.idaCaseDoctor = new IdaCaseDoctor();
		this.idaCaseDoctor.setContactDetails(new ContactDetails());
		// this.idaCaseDoctor.setAddress(new Address(new SimpleLocality()));
		this.idaCaseDoctor.setAddress(new Address());
		// this.setLocalityMap(new LinkedHashMap<String, String>());
		this.localityMap = controllerUtil.initLocalityMap();
		this.genderMap = controllerUtil.initGenderMap();
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
				doctorGenderValue);
	}

	public Map<String, String> getLocalityMap() {
		return localityMap;
	}

	//
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

	public String getDoctorGenderValue() {
		return doctorGenderValue;
	}

	public void setDoctorGenderValue(String doctorGenderValue) {
		this.doctorGenderValue = doctorGenderValue;
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

	public void setDoctorList(List<IdaCaseDoctor> doctorList) {
		this.doctorList = doctorList;
	}

	public List<IdaCaseDoctor> getDoctorList() {
		return doctorList;
	}

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}

}
