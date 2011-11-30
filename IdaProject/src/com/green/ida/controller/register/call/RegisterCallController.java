package com.green.ida.controller.register.call;

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
import com.green.ida.entity.animal.idacase.call.register.pojos.CallStatus;
import com.green.ida.entity.animal.idacase.call.register.pojos.PhoneCall;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterCallController {

    private static transient Logger LOGGER = Logger.getLogger(RegisterCallController.class);
    private PhoneCall call;
    private Map<String, String> localityMap;
    private String localityValue;
    private String complainerLocalityValue;
    private Map<String, Long> callStatusMap;
    private Long callStatusValue;
    private Map<String, String> ngoVehicleMap;
    private String ngoVehicleValue;
    private Map<String, String> genderMap;
    private String complainerGenderValue;
    // private Boolean isEdit;
    private Boolean byPassEditGetListener = Boolean.FALSE;
    @Inject
    private GenericDao genericDao;
    @Inject
    private ControllerUtil controllerUtil;
    @Inject
    private FacesUtil facesUtil;
    
    private Integer page = 0;

    //private List<Call> callList;
    public void searchCall() {
        LOGGER.info("Searching call with id " + this.call.getId());
        try {

            this.call = genericDao.findFirstWhereCondition(
                    PhoneCall.class, "id", this.call.getId());
//			this.localityValue = this.idaCaseDoctor.getAddress()
//					.getLocality().getLocalityName();
            this.localityValue = this.call.getComplainSource().getLocality().getId().toString();
            this.complainerLocalityValue = this.call.getComplainer().getAddress().getLocality().getId().toString();
            this.ngoVehicleValue = this.call.getNgoVehicle().getId().toString();
            this.callStatusValue = this.callStatusMap.get(this.call.getCallStatus().getViewValue());
            this.complainerGenderValue = this.genderMap.get(Gender
					.valueOf(this.call.getComplainer().getGender()));


        } catch (Exception e) {
            LOGGER.error("Problem searching call with id "
                    + this.call.getId() + " " + e.getMessage(), e);
            facesUtil.addErrorMessage("Problem searching call with id "
                    + this.call.getId());
        }
        LOGGER.info("Finished searching call with id "
                + this.call.getId());
    }

    public String saveCall() {
        // System.out.println(idaCaseDoctor.getFirstName());
        try {
            LOGGER.info("Saving call " + call);
            ingestCall();
            LOGGER.info("Call saved with id " + this.call.getId());
        } catch (Exception e) {
            LOGGER.error("Problem saving call " + call + " "
                    + e.getMessage(), e);
            return "error";
        }
        return "callDetailsView?faces-redirect=true&amp;includeViewParams=true";
    }

    public void callForEdit() {
        if (byPassEditGetListener) {
            return;
        }
        Long id = this.call.getId();
        LOGGER.info("Fetching call details for id " + id);
        // this.isEdit = true;
        if (id == null || id <= 0) {
            LOGGER.error("Call with null id or negative value cannot be editted");
            facesUtil.addErrorMessage("Call cannot be fetched for null Id");
            return;
        }

        searchCall();
        // //this.toEditDoctorId = null;
        // FacesContext context = FacesContext.getCurrentInstance();
        // ConfigurableNavigationHandler handler =
        // (ConfigurableNavigationHandler)
        // context.getApplication().getNavigationHandler();
        // handler.performNavigation("editDoctor");
        // return "registerDoctor";
    }

    public String updateCall() {
        LOGGER.info("Updating call " + this.call + " for id "
                + this.call.getId());
        try {
            ingestCall();

        } catch (Exception e) {
            LOGGER.error("Problem updating call " + call + " "
                    + e.getMessage(), e);
            return "error";
        }
        LOGGER.info("Updated ida case call with id "
                + this.call.getId());
        // FacesContext.getCurrentInstance().responseComplete();
        byPassEditGetListener = true;
        // return "editedDoctorDetails";
        return "callDetailsView?faces-redirect=true&amp;includeViewParams=true";
    }

    private void ingestCall() throws Exception {
        Locality locality = this.genericDao.getReference(Locality.class, Long.valueOf(localityValue));
        this.call.getComplainSource().setLocality(locality);
        
        locality = this.genericDao.getReference(Locality.class, Long.valueOf(complainerLocalityValue));
        this.call.getComplainer().getAddress().setLocality(locality);
        
        NgoVehicle ngoVehicle = this.genericDao.getReference(NgoVehicle.class, Long.valueOf(ngoVehicleValue));
        this.call.setNgoVehicle(ngoVehicle);
        
       this.call.getComplainer().setGender(Gender.getGender(getComplainerGender()));

        CallStatus callStatus = CallStatus.getCallStatus(getCallStatusName());
        this.call.setCallStatus(callStatus);

        //        setSimpleLocality(
        //		new SimpleLocality(localityValue));

        this.call = this.genericDao.merge(this.call);
    }

    @PostConstruct
    public void initCallComponents() {
        this.call = new PhoneCall();

        //this.idaCaseDoctor.setAddress(new Address(new SimpleLocality()));
        HumanBeing complainer = new HumanBeing();
        complainer.setAddress(new Address());
        complainer.setContactDetails(new ContactDetails());
        this.call.setComplainer(complainer);
        
        this.call.setComplainSource(new Address());
       
        //this.setLocalityMap(new LinkedHashMap<String, String>());
        this.localityMap = controllerUtil.initLocalityMap();
        this.callStatusMap = CallStatus.initCallStatusMap();
        this.ngoVehicleMap = controllerUtil.initNgoVehicleMap();
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
    
    public String getComplainerLocalityName() {
        return this.controllerUtil.getSelectKeyFromValueFor(localityMap,
                complainerLocalityValue);
    }
    
     public String getComplainerGender() {
        return this.controllerUtil.getSelectKeyFromValueFor(genderMap,
                complainerGenderValue);
    }

    public String getCallStatusName() {
        return this.controllerUtil.getSelectKeyFromValueFor(callStatusMap,
                callStatusValue);
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

    public PhoneCall getCall() {
        return call;
    }

    public void setCall(PhoneCall call) {
        this.call = call;
    }

    public Map<String, Long> getCallStatusMap() {
        return callStatusMap;
    }

    public void setCallStatusMap(Map<String, Long> callStatusMap) {
        this.callStatusMap = callStatusMap;
    }

    public Long getCallStatusValue() {
        return callStatusValue;
    }

    public void setCallStatusValue(Long callStatusValue) {
        this.callStatusValue = callStatusValue;
    }

    public Map<String, String> getNgoVehicleMap() {
        return ngoVehicleMap;
    }

    public void setNgoVehicleMap(Map<String, String> ngoVehicleMap) {
        this.ngoVehicleMap = ngoVehicleMap;
    }

    public String getNgoVehicleValue() {
        return ngoVehicleValue;
    }

    public void setNgoVehicleValue(String ngoVehicleValue) {
        this.ngoVehicleValue = ngoVehicleValue;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Map<String, String> getGenderMap() {
        return genderMap;
    }

    public void setGenderMap(Map<String, String> genderMap) {
        this.genderMap = genderMap;
    }

    public String getComplainerGenderValue() {
        return complainerGenderValue;
    }

    public void setComplainerGenderValue(String complainerGenderValue) {
        this.complainerGenderValue = complainerGenderValue;
    }

    public String getComplainerLocalityValue() {
        return complainerLocalityValue;
    }

    public void setComplainerLocalityValue(String complainerLocalityValue) {
        this.complainerLocalityValue = complainerLocalityValue;
    }
    
    
}
