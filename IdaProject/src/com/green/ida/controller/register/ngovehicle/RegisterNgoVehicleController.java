package com.green.ida.controller.register.ngovehicle;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.nonliving.vehicle.VehicleType;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.ngo.Ngo;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterNgoVehicleController {
	private static transient Logger LOGGER = Logger
			.getLogger(RegisterNgoVehicleController.class);

	private NgoVehicle ngoVehicle;

	private Map<String, String> ngoMap;
	private String ngoValue;

	private Map<String, String> ngoVehicleTypeMap;
	private String ngoVehicleTypeValue;

	// private Boolean isEdit;

	private Boolean byPassEditGetListener = Boolean.FALSE;

	@Inject
	private GenericDao genericDao;

	@Inject
	private ControllerUtil controllerUtil;

	@Inject
	private FacesUtil facesUtil;

	private List<NgoVehicle> ngoVehicleList;

	public void searchAllNgoVehicle() {
		LOGGER.info("searching all ngo vehicle list");
		try {
			ngoVehicleList = genericDao.findAll(NgoVehicle.class);
		} catch (Exception e) {
			LOGGER.error("Problem finding all ngo vehicle" + e.getMessage(), e);
		}
		LOGGER.info("done searching all vehicle list");

	}

	public void searchForNgoVehicle() {
		LOGGER.info("Searching ngo vehicle with id " + this.ngoVehicle.getId());
		try {

			this.ngoVehicle = genericDao.findFirstWhereCondition(
					NgoVehicle.class, "id", this.ngoVehicle.getId());
			this.ngoVehicleTypeValue = this.ngoVehicle.getVehicleType().getId()
					.toString();

			this.ngoValue = this.ngoMap.get(this.ngoVehicle.getNgo()
					.getNgoName());
		} catch (Exception e) {
			LOGGER.error("Problem searching ngo vehicle with id "
					+ this.ngoVehicle.getId() + " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem searching ngo vehicle with id "
					+ this.ngoVehicle.getId());
		}
		LOGGER.info("Finished searching ngo vehicle with id "
				+ this.ngoVehicle.getId());
	}

	public String saveNgoVehicle() {
		// System.out.println(idaCaseDoctor.getFirstName());
		try {
			LOGGER.info("Saving ngoVehicle " + ngoVehicle);
			if (isNgoVehicleBySameName()) {
				return null;
			}
			ingestNgoVehicle();
			LOGGER.info("Ngo vehicle saved with id " + this.ngoVehicle.getId());
		} catch (Exception e) {
			LOGGER.error("Problem saving ngo driver " + ngoVehicle + " "
					+ e.getMessage(), e);
			return "error";
		}
		return "ngoVehicleDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	public void ngoVehicleForEdit() {
		if (byPassEditGetListener) {
			return;
		}
		Long id = this.ngoVehicle.getId();
		LOGGER.info("Fetching ngo driver details for id " + id);
		// this.isEdit = true;
		if (id == null || id <= 0) {
			LOGGER
					.error("NgoVehicle with null id or negative value cannot be editted");
			facesUtil
					.addErrorMessage("Ngo Vehicle cannot be fetched for null Id");
			return;
		}

		searchForNgoVehicle();
		// //this.toEditDoctorId = null;
		// FacesContext context = FacesContext.getCurrentInstance();
		// ConfigurableNavigationHandler handler =
		// (ConfigurableNavigationHandler)
		// context.getApplication().getNavigationHandler();
		// handler.performNavigation("editDoctor");
		// return "registerDoctor";
	}

	public String updateNgoVehicle() {
		LOGGER.info("Updating ngo vehicle " + this.ngoVehicle + " for id "
				+ this.ngoVehicle.getId());
		try {
			if (isNgoVehicleBySameNameEditCase()) {
				return null;
			}
			ingestNgoVehicle();

		} catch (Exception e) {
			LOGGER.error("Problem updating ngo vehicle " + ngoVehicle + " "
					+ e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Updated ngo vehicle with id " + this.ngoVehicle.getId());
		// FacesContext.getCurrentInstance().responseComplete();
		byPassEditGetListener = true;
		// return "editedDoctorDetails";
		return "ngoVehicleDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private void ingestNgoVehicle() throws Exception {
		VehicleType vehicleType = this.genericDao.getReference(
				VehicleType.class, Long.valueOf(ngoVehicleTypeValue));
		Ngo ngo = this.genericDao.find(Ngo.class, Long.valueOf(ngoValue));
		this.ngoVehicle.setVehicleType(vehicleType);
		this.ngoVehicle.setNgo(ngo);
		this.ngoVehicle = this.genericDao.merge(this.ngoVehicle);
	}

	@PostConstruct
	public void initNgoVehicleComponents() {
		this.ngoVehicle = new NgoVehicle();

		this.ngoVehicleTypeMap = controllerUtil.initVehicleTypeMap();
		this.ngoMap = controllerUtil.initNgoMap();
	}

	private boolean isNgoVehicleBySameName() throws Exception {
		List<Object> listVehicle = this.genericDao.executeNamedQuery(
				"NgoVehicle.exists", new String[] { "registrationNumber" },
				new Object[] { "%" + this.ngoVehicle.getRegistrationNumber()
						+ "%" });
		return constructAlreadyExistsErrorMessage(listVehicle);
	}

	private boolean constructAlreadyExistsErrorMessage(List<Object> listVehicle) {
		if (listVehicle != null && listVehicle.size() > 0) {
			facesUtil.addErrorMessage("Ngo Vehicle by "
					+ this.ngoVehicle.getRegistrationNumber()
					+ " number already exists.");
			return true;
		}
		return false;
	}

	private boolean isNgoVehicleBySameNameEditCase() throws Exception {
		List<Object> listVehicle = this.genericDao.executeNamedQuery(
				"NgoVehicle.existsEditCase", new String[] {
						"registrationNumber", "id" }, new Object[] {
						"%" + this.ngoVehicle.getRegistrationNumber() + "%",
						this.ngoVehicle.getId() });
		return constructAlreadyExistsErrorMessage(listVehicle);
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

	public String getNgoName() {
		return this.controllerUtil.getSelectKeyFromValueFor(ngoMap, ngoValue);
	}

	public String getVehicleTypeName() {
		return this.controllerUtil.getSelectKeyFromValueFor(ngoVehicleTypeMap,
				ngoVehicleTypeValue);
	}

	public NgoVehicle getNgoVehicle() {
		return ngoVehicle;
	}

	public void setNgoVehicle(NgoVehicle ngoVehicle) {
		this.ngoVehicle = ngoVehicle;
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

	public Map<String, String> getNgoVehicleTypeMap() {
		return ngoVehicleTypeMap;
	}

	public void setNgoVehicleTypeMap(Map<String, String> ngoVehicleTypeMap) {
		this.ngoVehicleTypeMap = ngoVehicleTypeMap;
	}

	public String getNgoVehicleTypeValue() {
		return ngoVehicleTypeValue;
	}

	public void setNgoVehicleTypeValue(String ngoVehicleTypeValue) {
		this.ngoVehicleTypeValue = ngoVehicleTypeValue;
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

	public void setNgoVehicleList(List<NgoVehicle> ngoVehicleList) {
		this.ngoVehicleList = ngoVehicleList;
	}

	public List<NgoVehicle> getNgoVehicleList() {
		return ngoVehicleList;
	}

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}

}
