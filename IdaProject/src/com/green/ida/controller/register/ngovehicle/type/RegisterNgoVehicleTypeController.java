package com.green.ida.controller.register.ngovehicle.type;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.nonliving.vehicle.VehicleType;
import com.green.ida.controller.util.ControllerUtil;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterNgoVehicleTypeController {
	private static transient Logger LOGGER = Logger
			.getLogger(RegisterNgoVehicleTypeController.class);

	private VehicleType vehicleType;

	private Boolean byPassEditGetListener = Boolean.FALSE;

	@Inject
	private GenericDao genericDao;

	@Inject
	private ControllerUtil controllerUtil;

	@Inject
	private FacesUtil facesUtil;

	private List<VehicleType> vehicleTypeList;

	public void searchAllVehicleType() {
		LOGGER.info("searching all ngo vehicle type list");
		try {
			vehicleTypeList = genericDao.findAll(VehicleType.class);
		} catch (Exception e) {
			LOGGER.error("Problem finding all ngo vehicle type "
					+ e.getMessage(), e);
		}
		LOGGER.info("done searching all vehicle type list");

	}

	public void searchForNgoVehicleType() {
		LOGGER.info("Searching ngo vehicle type with id "
				+ this.vehicleType.getId());
		try {

			this.vehicleType = genericDao.findFirstWhereCondition(
					VehicleType.class, "id", this.vehicleType.getId());

		} catch (Exception e) {
			LOGGER.error("Problem searching ngo vehicle with id "
					+ this.vehicleType.getId() + " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem searching ngo vehicle with id "
					+ this.vehicleType.getId());
		}
		LOGGER.info("Finished searching ngo vehicle with id "
				+ this.vehicleType.getId());
	}

	public String saveNgoVehicleType() {
		// System.out.println(idaCaseDoctor.getFirstName());
		try {
			LOGGER.info("Saving ngoVehicle type " + vehicleType);
			ingestNgoVehicleType();
			LOGGER.info("Ngo vehicle type saved with id "
					+ this.vehicleType.getId());
		} catch (Exception e) {
			LOGGER.error("Problem saving ngo vehicle type " + vehicleType + " "
					+ e.getMessage(), e);
			return "error";
		}
		return "ngoVehicleTypeDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	public void ngoVehicleTypeForEdit() {
		if (byPassEditGetListener) {
			return;
		}
		Long id = this.vehicleType.getId();
		LOGGER.info("Fetching ngo vehicle type details for id " + id);
		// this.isEdit = true;
		if (id == null || id <= 0) {
			LOGGER
					.error("NgoVehicle type with null id or negative value cannot be editted");
			facesUtil
					.addErrorMessage("Ngo Vehicle type cannot be fetched for null Id");
			return;
		}

		searchForNgoVehicleType();
		// //this.toEditDoctorId = null;
		// FacesContext context = FacesContext.getCurrentInstance();
		// ConfigurableNavigationHandler handler =
		// (ConfigurableNavigationHandler)
		// context.getApplication().getNavigationHandler();
		// handler.performNavigation("editDoctor");
		// return "registerDoctor";
	}

	public String updateNgoVehicleType() {
		LOGGER.info("Updating ngo vehicle " + this.vehicleType + " for id "
				+ this.vehicleType.getId());
		try {
			ingestNgoVehicleType();

		} catch (Exception e) {
			LOGGER.error("Problem updating ngo vehicle " + vehicleType + " "
					+ e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Updated ngo vehicle with id " + this.vehicleType.getId());
		// FacesContext.getCurrentInstance().responseComplete();
		byPassEditGetListener = true;
		// return "editedDoctorDetails";
		return "ngoVehicleTypeDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private void ingestNgoVehicleType() throws Exception {

		this.vehicleType = this.genericDao.merge(this.vehicleType);
	}

	@PostConstruct
	public void initNgoVehicleComponents() {
		this.vehicleType = new VehicleType();

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

	public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}

	public List<VehicleType> getVehicleTypeList() {
		return vehicleTypeList;
	}

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}

}
