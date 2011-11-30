package com.green.ida.controller.register.locality;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.human.related.address.Locality;
import com.green.base.entity.living.human.related.address.State;
import com.green.ida.controller.util.ControllerUtil;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterLocalityController {
	private static transient Logger LOGGER = Logger
			.getLogger(RegisterLocalityController.class);

	private Locality locality;

	private Boolean byPassEditGetListener = Boolean.FALSE;

	@Inject
	private GenericDao genericDao;

	@Inject
	private ControllerUtil controllerUtil;

	@Inject
	private FacesUtil facesUtil;

	private List<Locality> localityList;

	public void searchAllLocality() {
		LOGGER.info("searching all locality list");
		try {
			localityList = genericDao.findAll(Locality.class);
		} catch (Exception e) {
			LOGGER.error("Problem finding all locality " + e.getMessage(), e);
		}
		LOGGER.info("done searching all locality list");

	}

	public void searchForLocality() {
		LOGGER.info("Searching locality with id " + this.locality.getId());
		try {

			this.locality = genericDao.findFirstWhereCondition(Locality.class,
					"id", this.locality.getId());

		} catch (Exception e) {
			LOGGER.error("Problem searching locality with id "
					+ this.locality.getId() + " " + e.getMessage(), e);
			facesUtil.addErrorMessage("Problem searching locality with id "
					+ this.locality.getId());
		}
		LOGGER.info("Finished searching locality with id "
				+ this.locality.getId());
	}

	public String saveLocality() {
		// System.out.println(idaCaseDoctor.getFirstName());
		try {
			LOGGER.info("Saving locality " + locality);
			if (isLocalityPresentBySameName()) {
				return null;
			}
			ingestLocality();
			LOGGER.info("Locality saved with id " + this.locality.getId());
		} catch (Exception e) {
			LOGGER.error("Problem saving locality " + locality + " "
					+ e.getMessage(), e);
			return "error";
		}
		return "localityDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	public void localityForEdit() {
		if (byPassEditGetListener) {
			return;
		}
		Long id = this.locality.getId();
		LOGGER.info("Fetching locality details for id " + id);
		// this.isEdit = true;
		if (id == null || id <= 0) {
			LOGGER
					.error("Locality with null id or negative value cannot be editted");
			facesUtil.addErrorMessage("Locality cannot be fetched for null Id");
			return;
		}

		searchForLocality();
		// //this.toEditDoctorId = null;
		// FacesContext context = FacesContext.getCurrentInstance();
		// ConfigurableNavigationHandler handler =
		// (ConfigurableNavigationHandler)
		// context.getApplication().getNavigationHandler();
		// handler.performNavigation("editDoctor");
		// return "registerDoctor";
	}

	public String updateLocality() {
		LOGGER.info("Updating locality " + this.locality + " for id "
				+ this.locality.getId());
		try {
			if (isLocalityPresentBySameNameEditCase()) {
				return null;
			}
			ingestLocality();

		} catch (Exception e) {
			LOGGER.error("Problem updating locality " + locality + " "
					+ e.getMessage(), e);
			return "error";
		}
		LOGGER.info("Updated locality with id " + this.locality.getId());
		// FacesContext.getCurrentInstance().responseComplete();
		byPassEditGetListener = true;
		// return "editedDoctorDetails";
		return "localityDetailsView?faces-redirect=true&amp;includeViewParams=true";
	}

	private boolean isLocalityPresentBySameName() {
		List<Object> existingLocalities = this.genericDao.executeNamedQuery(
				"Locality.exists",
				new String[] { "locality", "distinguished" }, new Object[] {
						"%" + this.locality.getLocality() + "%",
						"%" + this.locality.getDistinguished() + "%" });
		return constructAlreadyExistsErrorMessage(existingLocalities);
	}

	private boolean isLocalityPresentBySameNameEditCase() {
		List<Object> existingLocalities = this.genericDao.executeNamedQuery(
				"Locality.existsEditCase", new String[] { "locality",
						"distinguished", "id" }, new Object[] {
						"%" + this.locality.getLocality() + "%",
						"%" + this.locality.getDistinguished() + "%",
						this.locality.getId() });
		return constructAlreadyExistsErrorMessage(existingLocalities);
	}

	private boolean constructAlreadyExistsErrorMessage(
			List<Object> existingLocalities) {
		if (existingLocalities != null && existingLocalities.size() > 0) {
			facesUtil
					.addErrorMessage("Locality with "
							+ this.locality.getLocality() + " "
							+ this.locality.getDistinguished()
							+ " name alredy exists.");
			return true;
		}
		return false;
	}

	private void ingestLocality() throws Exception {
		State state = this.genericDao.find(State.class, 1L);
		this.locality.setState(state);

		this.locality = this.genericDao.merge(this.locality);
	}

	@PostConstruct
	public void initNgoVehicleComponents() {
		this.locality = new Locality();

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

	public void setByPassEditGetListener(Boolean byPassEditGetListener) {
		this.byPassEditGetListener = byPassEditGetListener;
	}

	public Boolean getByPassEditGetListener() {
		return byPassEditGetListener;
	}

	public List<Locality> getLocalityList() {
		return localityList;
	}

	public void setLocalityList(List<Locality> localityList) {
		this.localityList = localityList;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

}
