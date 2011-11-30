package com.green.ida.entity.animal.idacase.result.pojos;

import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import java.util.Date;
import javax.persistence.CascadeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = "BURIED_IN_CENTER")
public class IdaBuriedInCenterResult extends IdaCaseResult {

    //duplicate date of death and reason for death in animal also
    private String reasonForDeath;
    private String reasonForPutDown;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfDeath;
    @Temporal(TemporalType.TIMESTAMP)
    private Date burialDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private IdaCaseDoctor idaCaseDoctor;

    public String getReasonForDeath() {
        return reasonForDeath;
    }

    public void setReasonForDeath(String reasonForDeath) {
        this.reasonForDeath = reasonForDeath;
    }

    public String getReasonForPutDown() {
        return reasonForPutDown;
    }

    public void setReasonForPutDown(String reasonForPutDown) {
        this.reasonForPutDown = reasonForPutDown;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Date getBurialDate() {
        return burialDate;
    }

    public void setBurialDate(Date burialDate) {
        this.burialDate = burialDate;
    }

    public IdaCaseDoctor getIdaCaseDoctor() {
        return idaCaseDoctor;
    }

    public void setIdaCaseDoctor(IdaCaseDoctor idaCaseDoctor) {
        this.idaCaseDoctor = idaCaseDoctor;
    }
}
