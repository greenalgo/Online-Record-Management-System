package com.green.ida.controller.register.idacase.followup;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.pojos.FollowUpCase;
import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("request")
public class RegisterFollowupController {

    private static transient Logger LOGGER = Logger.getLogger(RegisterFollowupController.class);
    private Long idaCaseId;
    private FollowUpCase followUpCase;
    


    private Map<String, String> doctorMap;
    private String doctorValue;

    private Map<String, String> ngoDriverMap;
    private String ngoDriverValue;
    private Map<String, String> ngoVehicleMap;
    private String ngoVehicleValue;
    private Map<String, String> ngoCatcherMap;
    private String ngoCatcherValue;
   
    private Long toEditFollowupId;
    private Boolean byPassEditGetListener = Boolean.FALSE;
    private Integer page = 0;
    private List<FollowUpCase> followUpList;
    @Inject
    private GenericDao genericDao;
    @Inject
    private ControllerUtil controllerUtil;
    @Inject
    private FacesUtil facesUtil;

    @PostConstruct
    public void initFollowUpComponents() {
       
        this.doctorMap = this.controllerUtil.initDoctorMap();
       
        this.ngoDriverMap = this.controllerUtil.initNgoDriverMap();
        this.ngoVehicleMap = this.controllerUtil.initNgoVehicleMap();
        this.ngoCatcherMap = this.controllerUtil.initNgoCatcherMap();

       

    }

    public String saveFollowUp() {

        LOGGER.info("Saving ida medical case .....");
        try {
            ingestFollowUpCase();

            LOGGER.info("Saved ida case with id .... " + this.followUpCase.getId());

        } catch (Exception e) {
            LOGGER.error("Problem saving ida case " + e.getMessage(), e);
            return "error";
        }
        return "followUpCaseView?faces-redirect=true&amp;includeViewParams=true";
    }

    private void ingestFollowUpCase() throws Exception {
       
     

       IdaCaseDoctor idaCaseDoctor = this.genericDao.getReference(IdaCaseDoctor.class, Long.valueOf(doctorValue));
       
            NgoCatcher ngoCatcher = this.genericDao.getReference(
                    NgoCatcher.class, Long.valueOf(ngoCatcherValue));
            NgoDriver ngoDriver = this.genericDao.getReference(NgoDriver.class,
                    Long.valueOf(ngoDriverValue));
            NgoVehicle ngoVehicle = this.genericDao.getReference(
                    NgoVehicle.class, Long.valueOf(ngoVehicleValue));

            this.followUpCase.setNgoCatcher(ngoCatcher);
            this.followUpCase.setNgoDriver(ngoDriver);
            this.followUpCase.setNgoVehicle(ngoVehicle);
       
       
        this.followUpCase.setDoctor(idaCaseDoctor);
      
        this.followUpCase = this.genericDao.merge(this.followUpCase);
    }

    public void searchForFollowUp() {
        LOGGER.info("Searching entry for ida case with id "
                + this.followUpCase.getId());

        try {
            this.followUpCase = this.genericDao.findFirstWhereCondition(
                    FollowUpCase.class, "id", this.followUpCase.getId());
            
            this.doctorValue = this.followUpCase.getDoctor().getId().toString();
            // put none case here
            this.ngoCatcherValue =  this.followUpCase.getNgoCatcher().getId().toString();
            this.ngoDriverValue = this.followUpCase.getNgoDriver().getId().toString();
            this.ngoVehicleValue = this.followUpCase.getNgoVehicle().getId().toString();

           
            this.byPassEditGetListener = Boolean.TRUE;
           
        } catch (Exception e) {
            LOGGER.error("Problem while searching ida case for id "
                    + followUpCase.getId() + " " + e.getMessage(), e);
            facesUtil.addErrorMessage("Problem while searching"
                    + " ida case for id " + followUpCase.getId());
        }

        LOGGER.info("Finished searching ida case for id " + followUpCase.getId());

    }

  
    public void followUpForEdit() {
        if (getByPassEditGetListener()) {
            return;
        }
        // this.isEdit = true;
        Long id = this.followUpCase.getId();
        LOGGER.info("Fetching ida case details for id " + id);
        if (FacesContext.getCurrentInstance().isValidationFailed()) {
            LOGGER.info("Validation failed by passing search !!!!!!");
            return;
        }

        if (id == null || id <= 0) {
            LOGGER.error("Ida case with null id or negative value cannot be editted");
            facesUtil.addErrorMessage("Ida case cannot be fetched for null Id");
            return;
        }

        searchForFollowUp();
    }

    public String updateFollowUp() {
        LOGGER.info("Updating ida case for " + this.followUpCase + " for id "
                + this.followUpCase.getId());
        try {
          
            ingestFollowUpCase();
        } catch (Exception e) {
            LOGGER.error("Problem updating ida case " + this.followUpCase + " "
                    + e.getMessage(), e);
            return "error";
        }
        LOGGER.info("Updated ida case for id " + this.followUpCase.getId());
        return "followUpCaseView?faces-redirect=true&amp;includeViewParams=true";
    }

   



    public String getDoctorName() {
        return this.controllerUtil.getSelectKeyFromValueFor(doctorMap,
                doctorValue);
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

  

    public void setDoctorValue(String doctorValue) {
        this.doctorValue = doctorValue;
    }

    public String getDoctorValue() {
        return doctorValue;
    }

    public void setDoctorMap(Map<String, String> doctorMap) {
        this.doctorMap = doctorMap;
    }

    public Map<String, String> getDoctorMap() {
        return doctorMap;
    }


   
    public void setToEditIdaCaseId(Long toEditIdaCaseId) {
        this.toEditFollowupId = toEditIdaCaseId;
    }

    public Long getToEditIdaCaseId() {
        return toEditFollowupId;
    }

   

    public void setFacesUtil(FacesUtil facesUtil) {
        this.facesUtil = facesUtil;
    }

    public FacesUtil getFacesUtil() {
        return facesUtil;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public void setByPassEditGetListener(Boolean byPassEditGetListener) {
        this.byPassEditGetListener = byPassEditGetListener;
    }

    public Boolean getByPassEditGetListener() {
        return byPassEditGetListener;
    }

    public void setNgoDriverMap(Map<String, String> ngoDriverMap) {
        this.ngoDriverMap = ngoDriverMap;
    }

    public Map<String, String> getNgoDriverMap() {
        return ngoDriverMap;
    }

    public void setNgoDriverValue(String ngoDriverValue) {
        this.ngoDriverValue = ngoDriverValue;
    }

    public String getNgoDriverValue() {
        return ngoDriverValue;
    }

    public void setNgoVehicleMap(Map<String, String> ngoVehicleMap) {
        this.ngoVehicleMap = ngoVehicleMap;
    }

    public Map<String, String> getNgoVehicleMap() {
        return ngoVehicleMap;
    }

    public void setNgoVehicleValue(String ngoVehicleValue) {
        this.ngoVehicleValue = ngoVehicleValue;
    }

    public String getNgoVehicleValue() {
        return ngoVehicleValue;
    }

    public void setNgoCatcherMap(Map<String, String> ngoCatcherMap) {
        this.ngoCatcherMap = ngoCatcherMap;
    }

    public Map<String, String> getNgoCatcherMap() {
        return ngoCatcherMap;
    }

    public void setNgoCatcherValue(String ngoCatcherValue) {
        this.ngoCatcherValue = ngoCatcherValue;
    }

    public String getNgoCatcherValue() {
        return ngoCatcherValue;
    }

  

    public FollowUpCase getFollowUpCase() {
        return followUpCase;
    }

    public void setFollowUpCase(FollowUpCase followUpCase) {
        this.followUpCase = followUpCase;
    }

    public List<FollowUpCase> getFollowUpList() {
        return followUpList;
    }

    public void setFollowUpList(List<FollowUpCase> followUpList) {
        this.followUpList = followUpList;
    }

    public Long getIdaCaseId() {
        return idaCaseId;
    }

    public void setIdaCaseId(Long idaCaseId) {
        this.idaCaseId = idaCaseId;
    }

   

   
}
