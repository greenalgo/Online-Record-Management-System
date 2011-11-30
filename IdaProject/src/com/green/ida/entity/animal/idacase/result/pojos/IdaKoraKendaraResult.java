package com.green.ida.entity.animal.idacase.result.pojos;

import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import java.util.Date;
import javax.persistence.CascadeType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = "KORA_KENDARA")
public class IdaKoraKendaraResult extends IdaCaseResult {

    private String reasonForDeath;
    private String reasonForPutDown;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfDeath;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pickUpDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private IdaCaseDoctor idaCaseDoctor;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoDriver ngoDriver;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoVehicle ngoVehicle;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoCatcher ngoCatcher;

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

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public IdaCaseDoctor getIdaCaseDoctor() {
        return idaCaseDoctor;
    }

    public void setIdaCaseDoctor(IdaCaseDoctor idaCaseDoctor) {
        this.idaCaseDoctor = idaCaseDoctor;
    }

    public NgoDriver getNgoDriver() {
        return ngoDriver;
    }

    public void setNgoDriver(NgoDriver ngoDriver) {
        this.ngoDriver = ngoDriver;
    }

    public NgoVehicle getNgoVehicle() {
        return ngoVehicle;
    }

    public void setNgoVehicle(NgoVehicle ngoVehicle) {
        this.ngoVehicle = ngoVehicle;
    }

    public NgoCatcher getNgoCatcher() {
        return ngoCatcher;
    }

    public void setNgoCatcher(NgoCatcher ngoCatcher) {
        this.ngoCatcher = ngoCatcher;
    }
}
