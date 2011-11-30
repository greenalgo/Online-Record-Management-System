/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.green.ida.controller.register.animal.medicalcase;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.related.enums.Gender;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.adoption.IdaAdoption;
import com.green.ida.entity.animal.idacase.resident.IdaResident;
import com.green.ida.entity.center.IdaCenter;
import com.green.ida.entity.living.animal.pojos.IdaAdoptedAnimal;
import com.green.ida.entity.living.animal.pojos.IdaResidentAnimal;
import java.util.Map;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 *
 * @author gaurav
 */
@Scope("request")
@Component
public class IdaAdoptionResidentCaseHelper {
    
    private static final Logger LOGGER = Logger.getLogger(IdaAdoptionResidentCaseHelper.class);
    
    @Inject
    private GenericDao genericDao;
    
    @Inject
    private ControllerUtil controllerUtil;

    public GenericDao getGenericDao() {
        return genericDao;
    }

    public void setGenericDao(GenericDao genericDao) {
        this.genericDao = genericDao;
    }
    
    public void ingestAdoptionCase(IdaMedicalCaseClosureForm form){
        LOGGER.info("Ingesting ida adoption case");
        try {
            this.genericDao.merge(form.getIdaAdoption());
        } catch (Exception ex) {
            LOGGER.error("Problem in ingesting ida adoption case " + ex.getMessage(), ex);
        }
        LOGGER.info("Done ingesting ida adoption case");
    }
    
     public IdaAdoption getFilledIdaAdoptionInstance(IdaMedicalCaseClosureForm form){
         IdaAdoption idaAdoption = null;
         IdaAdoptedAnimal idaAdoptedAnimal = null;
         HumanBeing idaAdopter = null;
         LOGGER.info("Filling ida adoption instance with relevant values");
       try{
           IdaCenter idaCenter = this.genericDao.getReference(IdaCenter.class, Long.valueOf(form.getCenterValue()));
           idaAdoption = form.getIdaAdoption();
           idaAdoption.setIdaCenter(idaCenter);
           
           idaAdoptedAnimal = idaAdoption.getIdaAdoptedAnimal();
           idaAdoptedAnimal.setGender(Gender.getGender(getGenderName(form.getDogGenderValue(), form.getGenderMap())));
           idaAdoptedAnimal.setAnimalType(form.getAnimalTypeValue());
           
           idaAdopter = idaAdoption.getIdaAnimalAdopter();
           idaAdopter.setGender(Gender.getGender(getGenderName(form.getAdopterGenderValue(), form.getGenderMap())));
           idaAdopter.setIdentityTag("ADOPTER");
           
           idaAdoptedAnimal.setIdaAnimalAdopter(idaAdopter);
           
           LOGGER.info("Done filling ida adoption instance with relevant values");
           
           return idaAdoption;
       }catch(Exception e){
           LOGGER.error("Problem filling ida adoption instance " + e.getMessage(), e);
       }
       
       return null;
   }
     
     public IdaResident getFilledIdaResidentInstance(IdaMedicalCaseClosureForm form){
         IdaResident idaResident;
         IdaResidentAnimal idaResidentAnimal;
         LOGGER.info("Filling ida resident instance with relevant values");
       try{
           IdaCenter idaCenter = this.genericDao.getReference(IdaCenter.class, Long.valueOf(form.getCenterValue()));
           
           idaResident = form.getIdaResident();
           idaResident.setIdaCenter(idaCenter);
           
           idaResidentAnimal = idaResident.getIdaResidentAnimal();
           idaResidentAnimal.setGender(Gender.getGender(getGenderName(form.getDogGenderValue(), form.getGenderMap())));
          
           
           LOGGER.info("Done filling ida resident instance with relevant values");
           
           return idaResident;
       }catch(Exception e){
           LOGGER.error("Problem filling ida adoption instance " + e.getMessage(), e);
       }
       
       return null;
   }
     
     public String getGenderName(String value,Map<String,String> map) {
		return this.controllerUtil.getSelectKeyFromValueFor(map,
				value);
	}
     
    

    public ControllerUtil getControllerUtil() {
        return controllerUtil;
    }

    public void setControllerUtil(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }
    
    
    

}
