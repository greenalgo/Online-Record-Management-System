package com.green.ida.controller.register.ngo;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.base.entity.living.human.related.address.Locality;
import com.green.base.entity.living.related.enums.Gender;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.living.human.pojos.IdaCasePerson;
import com.green.ida.entity.living.human.pojos.enums.IdaCasePersonType;
import com.green.ida.entity.ngo.Ngo;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterNgoController {
	private static transient Logger LOGGER = Logger
			.getLogger(RegisterNgoController.class);

	private Ngo ngo;

	private Map<String, String> genderMap;
	private String genderValue;

	private Map<String, String> localityMap;
	private String localityValue;

	private Boolean byPassEditGetListener = Boolean.FALSE;

	@Inject
	private GenericDao genericDao;

	@Inject
	private ControllerUtil controllerUtil;

	@Inject
	private FacesUtil facesUtil;

	private List<Ngo> ngoList;

	public void searchAllNgo() {
		LOGGER.info("searching all ngo list");
		try {
			ngoList = genericDao.findAll(Ngo.class);
		} catch (Exception e) {
			LOGGER.error("Problem finding all ngo" + e.getMessage(), e);
		}
		LOGGER.info("done searching all ngos list");

	}

	public void searchForNgo() {
		LOGGER.info("Searching ngo with id " + this.ngo.getId());
		try {

			this.ngo = genericDao.findFirstWhereCondition(Ngo.class, "id",
					this.ngo.getId());
			this.genderValue = this.genderMap.get(Gender.valueOf(this.ngo
					.getChairPerson().getGender()));

			this.localityValue = this.ngo.getChairPerson().getAddress()
					.getLocality().getId().toString();

		} catch (Exception e) {
			LOGGER.error("Problem searching ngo with id " + this.ngo.getId()
					+ " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem searching ngo with id "
					+ this.ngo.getId());
		}
		LOGGER.info("Finished searching ngo with id " + this.ngo.getId());
	}

	public String saveNgo() {
		// System.out.println(idaCaseDoctor.getFirstName());
		try {
			LOGGER.info("Saving ngo " + ngo);
			if (isNgoPresentBySameName()) {
				return null;
			}
			ingestNgo();
			LOGGER.info("Ngo saved with id " + this.ngo.getId());
		} catch (Exception e) {
			LOGGER.error("Problem saving ngo " + ngo + " " + e.getMessage(), e);
			return "error";
		}
		return "ngoDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private boolean isNgoPresentBySameName() throws Exception {
		Ngo ngoPresent = null;
		try {
			ngoPresent = this.genericDao.findFirstWhereCondition(Ngo.class,
					"ngoName", this.ngo.getNgoName());
		} catch (Exception e) {

		}
		if (ngoPresent != null) {
			facesUtil.addErrorMessage("Ngo with " + this.ngo.getNgoName()
					+ " name already exists.");
			return true;
		}
		return false;
	}

	private boolean isNgoPresentBySameNameEditCase() throws Exception {
		List<Object> result = this.genericDao.executeNamedQuery(
				"Ngo.existsNgoEditCase", new String[] { "ngoName", "id" },
				new Object[] { this.ngo.getNgoName(), this.ngo.getId() });
		if (result != null && result.size() > 0) {
			facesUtil.addErrorMessage("Ngo with " + this.ngo.getNgoName()
					+ " name already exists.");
			return true;
		}
		return false;
	}

	public void ngoForEdit() {
		if (byPassEditGetListener) {
			return;
		}
		Long id = this.ngo.getId();
		LOGGER.info("Fetching ngo details for id " + id);
		// this.isEdit = true;
		if (id == null || id <= 0) {
			LOGGER.error("Ngo with null id or negative value cannot be editted");
			facesUtil.addErrorMessage("Ngo cannot be fetched for null Id");
			return;
		}

		searchForNgo();
		// //this.toEditDoctorId = null;
		// FacesContext context = FacesContext.getCurrentInstance();
		// ConfigurableNavigationHandler handler =
		// (ConfigurableNavigationHandler)
		// context.getApplication().getNavigationHandler();
		// handler.performNavigation("editDoctor");
		// return "registerDoctor";
	}

	public String updateNgo() {
		LOGGER.info("Updating ngo " + this.ngo + " for id " + this.ngo.getId());
		try {
			if (isNgoPresentBySameNameEditCase()) {
				return null;
			}
			ingestNgo();

		} catch (Exception e) {
			LOGGER.error("Problem updating ngo " + ngo + " " + e.getMessage(),
					e);
			return "error";
		}
		LOGGER.info("Updated ngo with id " + this.ngo.getId());
		// FacesContext.getCurrentInstance().responseComplete();
		byPassEditGetListener = true;
		// return "editedDoctorDetails";
		return "ngoDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private void ingestNgo() throws Exception {
		Locality locality = this.genericDao.getReference(Locality.class,
				Long.valueOf(localityValue));

		HumanBeing chairPerson = this.ngo.getChairPerson();
		chairPerson.setGender(Gender.getGender(getGenderName()));
		chairPerson.getAddress().setLocality(locality);

		this.ngo = this.genericDao.merge(this.ngo);
	}

	@PostConstruct
	public void initNgoVehicleComponents() {
		this.ngo = new Ngo();
		IdaCasePerson chairPerson = new IdaCasePerson();
		chairPerson.setPersonType(IdaCasePersonType.NGO);
		chairPerson.setAddress(new Address());
		chairPerson.setContactDetails(new ContactDetails());
		this.ngo.setChairPerson(chairPerson);

		this.genderMap = this.controllerUtil.initGenderMap();
		this.localityMap = controllerUtil.initLocalityMap();

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
				genderValue);
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

	public Ngo getNgo() {
		return ngo;
	}

	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

	public Map<String, String> getGenderMap() {
		return genderMap;
	}

	public void setGenderMap(Map<String, String> genderMap) {
		this.genderMap = genderMap;
	}

	public String getGenderValue() {
		return genderValue;
	}

	public void setGenderValue(String genderValue) {
		this.genderValue = genderValue;
	}

	public void setNgoList(List<Ngo> ngoList) {
		this.ngoList = ngoList;
	}

	public List<Ngo> getNgoList() {
		return ngoList;
	}

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}

	public String getLocalityValue() {
		return localityValue;
	}

	public void setLocalityValue(String localityValue) {
		this.localityValue = localityValue;
	}

	public Map<String, String> getLocalityMap() {
		return localityMap;
	}

	public void setLocalityMap(Map<String, String> localityMap) {
		this.localityMap = localityMap;
	}

}
