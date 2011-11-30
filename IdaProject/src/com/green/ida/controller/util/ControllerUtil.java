package com.green.ida.controller.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.human.related.address.Locality;
import com.green.base.entity.nonliving.vehicle.VehicleType;
import com.green.ida.entity.animal.idacase.admission.reason.pojos.IdaCaseAdmissionReason;
import com.green.ida.entity.center.IdaCenter;
import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.ngo.Ngo;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;

@Scope
@Component
public class ControllerUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5414943357055641371L;

	private static transient Logger LOGGER = Logger
			.getLogger(ControllerUtil.class);

	@Inject
	private GenericDao genericDao;

	public Map<String, String> initLocalityMap() {
		LOGGER.info("Getting locality map .......");
		Map<String, String> localityMap = new LinkedHashMap<String, String>();
		try {
			localityMap.put("Select Locality", "0");
			List<Locality> localityList = genericDao.findAll(Locality.class);
			if (localityList == null || localityList.isEmpty()) {
				LOGGER.info("Locality map found as null or empty ....");
				return localityMap;
			}
			for (Locality locality : localityList) {
				localityMap.put(locality.getLocality() + " "
						+ locality.getDistinguished(), locality.getId()
						.toString());
			}
			LOGGER.info("Finished filling locality map ....");
			return localityMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting locality ......" + e.getMessage(), e);
		}
		return null;
	}

	public Map<String, String> initNgoMap() {
		LOGGER.info("Getting ngo map .......");
		Map<String, String> ngoMap = new LinkedHashMap<String, String>();
		try {
			ngoMap.put("Select Ngo", "0");
			List<Ngo> ngoList = genericDao.findAll(Ngo.class);
			if (ngoList == null || ngoList.isEmpty()) {
				LOGGER.info("Locality map found as null or empty ....");
				return ngoMap;
			}
			for (Ngo ngo : ngoList) {
				ngoMap.put(ngo.getNgoName(), ngo.getId().toString());
			}
			LOGGER.info("Finished filling ngo map ....");
			return ngoMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ngo ......" + e.getMessage(), e);
		}
		return null;
	}

	public Map<String, String> initIdaCenterMap() {
		LOGGER.info("Getting ida center map .......");
		Map<String, String> idaCenterMap = new LinkedHashMap<String, String>();
		try {
			idaCenterMap.put("Select Ida Center", "0");
			List<IdaCenter> idaCenterList = genericDao.findAll(IdaCenter.class);
			if (idaCenterList == null || idaCenterList.isEmpty()) {
				LOGGER.info("Ida Center map found as null or empty ....");
				return idaCenterMap;
			}
			for (IdaCenter idaCenter : idaCenterList) {
				idaCenterMap.put(idaCenter.getCenterName(), idaCenter.getId()
						.toString());
			}
			LOGGER.info("Finished filling ida center map ....");
			return idaCenterMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ida center ......" + e.getMessage(),
					e);
		}
		return null;
	}

	public Map<String, String> initVehicleTypeMap() {
		LOGGER.info("Getting vehicle type map .......");
		Map<String, String> vehicleTypeMap = new LinkedHashMap<String, String>();
		try {
			vehicleTypeMap.put("Select Vehicle Type", "0");
			List<VehicleType> vehicleTypeList = genericDao
					.findAll(VehicleType.class);
			if (vehicleTypeList == null || vehicleTypeList.isEmpty()) {
				LOGGER.info("Locality map found as null or empty ....");
				return vehicleTypeMap;
			}
			for (VehicleType vehicleType : vehicleTypeList) {
				vehicleTypeMap.put(vehicleType.getVehicleType(), vehicleType
						.getId().toString());
			}
			vehicleTypeMap.put("None", "-1");
			LOGGER.info("Finished filling vehicle type map ....");
			return vehicleTypeMap;
		} catch (Exception e) {
			LOGGER.error(
					"Problem getting vehicle type ......" + e.getMessage(), e);
		}
		return null;
	}

	public Map<String, String> initAdmissionReasonMap() {
		LOGGER.info("Getting admission reason map .......");
		Map<String, String> localityMap = new LinkedHashMap<String, String>();
		try {
			// localityMap.put("Select Admission Reason","0");
			List<IdaCaseAdmissionReason> idaCaseAdmissionReasonList = genericDao
					.findAll(IdaCaseAdmissionReason.class);
			if (idaCaseAdmissionReasonList == null
					|| idaCaseAdmissionReasonList.isEmpty()) {
				LOGGER.info("Admission Reason map found as null or empty ....");
				return localityMap;
			}
			for (IdaCaseAdmissionReason idaCaseAdmissionReason : idaCaseAdmissionReasonList) {
				localityMap.put(idaCaseAdmissionReason.getReasonForAdmission(),
						idaCaseAdmissionReason.getId().toString());
			}
			LOGGER.info("Finished filling admission reason map ....");
			return localityMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting admission reason ......"
					+ e.getMessage(), e);
		}
		return null;
	}

	public Map<String, String> initNgoDriverMapForNgo(Ngo ngo) {
		LOGGER.info("Getting ngo driver map ....... for ngo " + ngo);
		Map<String, String> ngoDriverMap = new LinkedHashMap<String, String>();
		try {
			ngoDriverMap.put("Select Ngo Driver", "0");
			List<NgoDriver> ngoDriverList = genericDao.findAllWhereCondition(
					NgoDriver.class, "ngo", ngo);
			if (ngoDriverList == null || ngoDriverList.isEmpty()) {
				LOGGER.info("Ngo Driver map found as null or empty ....");
				return ngoDriverMap;
			}
			for (NgoDriver ngoDriver : ngoDriverList) {
				ngoDriverMap.put(ngo.getNgoName() + " - "
						+ ngoDriver.getName().getFullName(), ngoDriver.getId()
						.toString());
			}
			ngoDriverMap.put("None", "-1");
			LOGGER.info("Finished filling ngo driver map ....");
			return ngoDriverMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ngo driver ......" + e.getMessage(),
					e);
		}
		return null;
	}

	public Map<String, String> initNgoDriverMap() {
		LOGGER.info("Getting ngo driver map ....... for all ");
		Map<String, String> ngoDriverMap = new LinkedHashMap<String, String>();
		try {
			ngoDriverMap.put("Select Ngo Driver", "0");
			List<NgoDriver> ngoDriverList = genericDao.findAll(NgoDriver.class);
			if (ngoDriverList == null || ngoDriverList.isEmpty()) {
				LOGGER.info("Ngo Driver map found as null or empty ....");
				return ngoDriverMap;
			}
			for (NgoDriver ngoDriver : ngoDriverList) {
				ngoDriverMap.put(ngoDriver.getNgo().getNgoName() + " - "
						+ ngoDriver.getName().getFullName(), ngoDriver.getId()
						.toString());
			}
			ngoDriverMap.put("None", "-1");
			LOGGER.info("Finished filling ngo driver map ....");
			return ngoDriverMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ngo driver ......" + e.getMessage(),
					e);
		}
		return null;
	}

	public Map<String, String> initNgoCatcherMap() {
		LOGGER.info("Getting ngo catcher map ....... for all ");
		Map<String, String> ngoCatcherMap = new LinkedHashMap<String, String>();
		try {
			ngoCatcherMap.put("Select Ngo Catcher", "0");
			List<NgoCatcher> ngoCatcherList = genericDao
					.findAll(NgoCatcher.class);
			if (ngoCatcherList == null || ngoCatcherList.isEmpty()) {
				LOGGER.info("Ngo catcher map found as null or empty ....");
				return ngoCatcherMap;
			}
			for (NgoCatcher ngoDriver : ngoCatcherList) {
				ngoCatcherMap.put(ngoDriver.getNgo().getNgoName() + " - "
						+ ngoDriver.getName().getFullName(), ngoDriver.getId()
						.toString());
			}
			ngoCatcherMap.put("None", "-1");
			LOGGER.info("Finished filling ngo catcher map ....");
			return ngoCatcherMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ngo catcher ......" + e.getMessage(),
					e);
		}
		return null;
	}

	public Map<String, String> initNgoCatcherMapForNgo(Ngo ngo) {
		LOGGER.info("Getting ngo catcher map ....... for ngo " + ngo);
		Map<String, String> ngoCatcherMap = new LinkedHashMap<String, String>();
		try {
			ngoCatcherMap.put("Select Ngo Catcher", "0");
			List<NgoCatcher> ngoCatcherList = genericDao.findAllWhereCondition(
					NgoCatcher.class, "ngo", ngo);
			;
			if (ngoCatcherList == null || ngoCatcherList.isEmpty()) {
				LOGGER.info("Ngo catcher map found as null or empty ....");
				return ngoCatcherMap;
			}
			for (NgoCatcher ngoDriver : ngoCatcherList) {
				ngoCatcherMap.put(ngoDriver.getNgo().getNgoName() + " - "
						+ ngoDriver.getName().getFullName(), ngoDriver.getId()
						.toString());
			}
			ngoCatcherMap.put("None", "-1");
			LOGGER.info("Finished filling ngo catcher map ....");
			return ngoCatcherMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ngo catcher ......" + e.getMessage(),
					e);
		}
		return null;
	}

	public Map<String, String> initNgoVehicleMapForNgo(Ngo ngo) {
		LOGGER.info("Getting ngo vehicle map ....... for ngo " + ngo);
		Map<String, String> ngoVehicleMap = new LinkedHashMap<String, String>();
		try {
			ngoVehicleMap.put("Select Ngo Vehicle", "0");
			List<NgoVehicle> ngoVehicleList = genericDao.findAllWhereCondition(
					NgoVehicle.class, "ngo", ngo);
			if (ngoVehicleList == null || ngoVehicleList.isEmpty()) {
				LOGGER.info("Ngo Driver map found as null or empty ....");
				return ngoVehicleMap;
			}
			for (NgoVehicle ngoVehicle : ngoVehicleList) {
				ngoVehicleMap.put(ngo.getNgoName() + " - "
						+ ngoVehicle.getVehicleType().getVehicleType() + " - "
						+ ngoVehicle.getRegistrationNumber(), ngoVehicle
						.getId().toString());
			}
			ngoVehicleMap.put("None", "-1");
			LOGGER.info("Finished filling ngo driver map ....");
			return ngoVehicleMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ngo driver ......" + e.getMessage(),
					e);
		}
		return null;
	}

	public Map<String, String> initNgoVehicleMap() {
		LOGGER.info("Getting ngo vehicle map ....... for all ");
		Map<String, String> ngoVehicleMap = new LinkedHashMap<String, String>();
		try {
			ngoVehicleMap.put("Select Ngo Vehicle", "0");
			List<NgoVehicle> ngoVehicleList = genericDao
					.findAll(NgoVehicle.class);
			if (ngoVehicleList == null || ngoVehicleList.isEmpty()) {
				LOGGER.info("Ngo Driver map found as null or empty ....");
				return ngoVehicleMap;
			}
			for (NgoVehicle ngoVehicle : ngoVehicleList) {
				ngoVehicleMap.put(ngoVehicle.getNgo().getNgoName() + " - "
						+ ngoVehicle.getVehicleType().getVehicleType() + " - "
						+ ngoVehicle.getRegistrationNumber(), ngoVehicle
						.getId().toString());
			}
			ngoVehicleMap.put("None", "-1");
			LOGGER.info("Finished filling ngo driver map ....");
			return ngoVehicleMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting ngo driver ......" + e.getMessage(),
					e);
		}
		return null;
	}

	public Map<String, String> initGenderMap() {
		LOGGER.info("Getting gender map .......");

		Map<String, String> genderMap = new LinkedHashMap<String, String>();

		genderMap.put("Select Gender", "0");
		genderMap.put("Male", "1");
		genderMap.put("Female", "2");

		LOGGER.info("Finished filling gender map ....");
		return genderMap;

	}

	public Map<String, String> initShortenGenderMap() {
		LOGGER.info("Getting shorten gender map .......");

		Map<String, String> genderMap = new LinkedHashMap<String, String>();

		genderMap.put("Gender", "0");
		genderMap.put("M", "1");
		genderMap.put("F", "2");

		LOGGER.info("Finished shorten filling gender map ....");
		return genderMap;

	}

	public Map<String, String> initIdaAdoptedAnimalTypeMap() {
		LOGGER.info("Getting ida adopted animal type result map .......");

		Map<String, String> idaAdoptedAnimalResultMap = new LinkedHashMap<String, String>();

		idaAdoptedAnimalResultMap.put("Select Ida Adopted Animal", "0");
		idaAdoptedAnimalResultMap.put("Dog", "Dog");
		idaAdoptedAnimalResultMap.put("Cat", "Cat");

		LOGGER.info("Finished filling ida adopted animal type result map ....");
		return idaAdoptedAnimalResultMap;

	}

	public Map<String, String> initIdaCaseTypeMap() {
		LOGGER.info("Getting ida case type map .......");

		Map<String, String> idaCaseTypeMap = new LinkedHashMap<String, String>();

		idaCaseTypeMap.put("Select Ida Case Type", "0");
		idaCaseTypeMap.put("Sterlization", "1");
		idaCaseTypeMap.put("Sterlization and Treatment", "2");
		idaCaseTypeMap.put("Treatment", "3");

		LOGGER.info("Finished filling ida case type map ....");
		return idaCaseTypeMap;

	}

	public Map<String, String> initShortenIdaCaseTypeMap() {
		LOGGER.info("Getting shorten ida case type map .......");

		Map<String, String> idaCaseTypeMap = new LinkedHashMap<String, String>();

		idaCaseTypeMap.put("Case", "0");
		idaCaseTypeMap.put("S", "1");
		idaCaseTypeMap.put("ST", "2");
		idaCaseTypeMap.put("T", "3");

		LOGGER.info("Finished filling shorten ida case type map ....");
		return idaCaseTypeMap;

	}

	public Map<String, String> initIdaCaseResultTypeMap() {
		LOGGER.info("Getting ida case result map .......");

		Map<String, String> idaCaseResultMap = new LinkedHashMap<String, String>();

		idaCaseResultMap.put("Select Ida Case Result", "0");
		idaCaseResultMap.put("Resident", "1");
		idaCaseResultMap.put("Adopted", "2");
		idaCaseResultMap.put("Released", "3");
		idaCaseResultMap.put("Kora Kendra", "4");
		idaCaseResultMap.put("Buried In Center", "5");

		LOGGER.info("Finished filling ida case result map ....");
		return idaCaseResultMap;

	}

	public Map<String, String> initIdaCasePersonTypeMap() {
		LOGGER.info("Getting ida case person type map .......");

		Map<String, String> idaCasePersonTypeMap = new LinkedHashMap<String, String>();

		idaCasePersonTypeMap.put("Select Person Type", "0");
		idaCasePersonTypeMap.put("Individual", "1");
		idaCasePersonTypeMap.put("Caller", "2");
		idaCasePersonTypeMap.put("Ngo", "3");

		LOGGER.info("Finished filling ida case person type map ....");
		return idaCasePersonTypeMap;

	}

	public Map<String, String> initDoctorMap() {
		LOGGER.info("Getting doctor map .......");
		Map<String, String> doctorMap = new LinkedHashMap<String, String>();
		try {
			doctorMap.put("Select Doctor", "0");
			List<IdaCaseDoctor> doctorList = genericDao
					.findAll(IdaCaseDoctor.class);
			if (doctorList == null || doctorList.isEmpty()) {
				LOGGER.info("Doctor map found as null or empty ....");
				return doctorMap;
			}
			StringBuilder doctorName = new StringBuilder();
			for (IdaCaseDoctor doctor : doctorList) {
				doctorName.delete(0, doctorName.length());
				doctorName.append(doctor.getName().getFullName());
				doctorMap.put(doctorName.toString(), doctor.getId().toString());
			}
			LOGGER.info("Finished filling doctor map ....");
			return doctorMap;
		} catch (Exception e) {
			LOGGER.error("Problem getting doctor ......" + e.getMessage(), e);
		}
		return null;
	}

	// public void buildName(StringBuilder nameBuilder, HumanBeing humanBeing) {
	// nameBuilder.append(humanBeing.getFirstName()).append(" ");
	// if (humanBeing.getMiddleName() != null
	// && !humanBeing.getMiddleName().equals("")) {
	// nameBuilder.append(humanBeing.getMiddleName()).append(" ");
	// }
	// nameBuilder.append(humanBeing.getLastName());
	// }
	//
	// public String buildName(HumanBeing humanBeing) {
	// StringBuilder nameBuilder = new StringBuilder();
	// buildName(nameBuilder, humanBeing);
	// return nameBuilder.toString();
	// }

	// public String getSelectKeyFromValueFor(Map<String, String> map, String
	// value) {
	// if (map != null) {
	// Set<Entry<String, String>> entrySetOflocalityMap = map.entrySet();
	// for (Entry<String, String> entry : entrySetOflocalityMap) {
	// if (entry.getValue().equals(value)) {
	// return entry.getKey();
	// }
	// }
	// }
	// return "";
	// }

	public <T> String getSelectKeyFromValueFor(Map<String, T> map, T value) {
		// return getSelectKeyFromValueFor(map,value.toString());
		if (map != null) {
			Set<Entry<String, T>> entrySetOflocalityMap = map.entrySet();
			for (Entry<String, T> entry : entrySetOflocalityMap) {
				if (entry.getValue().equals(value)) {
					return entry.getKey();
				}
			}
		}
		return "";
	}

	public Long[] makeLongArrayFromStringArray(String[] ids) {
		Long[] idl = new Long[ids.length];
		for (int i = 0; i < ids.length; ++i) {
			idl[i] = Long.valueOf(ids[i]);
		}
		return idl;
	}

	public Date getDogDateOfBirthFromAge(Integer age) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, 0);
		calendar.add(Calendar.YEAR, -age);
		return calendar.getTime();
	}

	@SuppressWarnings("deprecation")
	public Integer getDogAgeFromDateOfBirth(Date dateOfBirth) {
		return new Date().getYear() - dateOfBirth.getYear();
	}

	public String tokenNumberExixts(Collection<String> tokenNumberList) {
		List<Object> existingTokenNumberList = this.genericDao
				.executeNamedQuery("IdaCase.findTokenAlreadyInUse",
						new String[] { "flag", "tokenNumberList" },
						new Object[] { Boolean.FALSE, tokenNumberList });
		return buildTokenNumberErrorMessage(existingTokenNumberList);
	}

	public String tokenNumberExixts(String tokenNumber, Long id) {
		List<Object> existingTokenNumberList = this.genericDao
				.executeNamedQuery("IdaCase.findTokenAlreadyInUseEditCase",
						new String[] { "flag", "tokenNumber", "id" },
						new Object[] { Boolean.FALSE, tokenNumber, id });
		return buildTokenNumberErrorMessage(existingTokenNumberList);
	}

	private String buildTokenNumberErrorMessage(
			List<Object> existingTokenNumberList) {
		if (!existingTokenNumberList.isEmpty()) {
			StringBuilder msgBuilder = new StringBuilder("Following ");
			for (Object object : existingTokenNumberList) {
				msgBuilder.append(object).append(" ");
			}
			msgBuilder.append("token number(s) is(are) already in use.");
			return msgBuilder.toString();

		}
		return "";
	}

	public String isHumanBeingPresentBySameName(String... name) {
		String msg = parseNameForAlreadyExistsCase(name);
		List<Object> list = this.genericDao.executeNamedQuery(
				"HumanBeing.exists", new String[] { "firstName", "middleName",
						"lastName" }, name);
		if (list != null && list.size() > 0)
			return msg;
		else
			return "";

	}

	public String isHumanBeingPresentBySameNameEditCase(Long id, String... name) {
		String msg = parseNameForAlreadyExistsCase(name);
		List<Object> params = new ArrayList<Object>();
		for (String n : name) {
			params.add(n);
		}
		params.add(id);
		List<Object> list = this.genericDao.executeNamedQuery(
				"HumanBeing.existsEditCase", new String[] { "firstName",
						"middleName", "lastName", "id" }, params.toArray());
		if (list != null && list.size() > 0)
			return msg;
		else
			return "";

	}

	private String parseNameForAlreadyExistsCase(String... name) {
		String msg = String.format("Person %s %s %s already exists.", name[0],
				name[1], name[2]);
		for (int i = 0; i < name.length; i++) {

			name[i] = "%" + name[i] + "%";

		}
		return msg;
	}

}
