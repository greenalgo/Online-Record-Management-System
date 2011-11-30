package com.green.ida.entity.animal.idacase.result.pojos;

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
@DiscriminatorValue(value = "RELEASED")
public class IdaReleasedResult extends IdaCaseResult {

    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoVehicle ngoVehicle;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoDriver ngoDriver;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoCatcher ngoCatcher;
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;
    
    public NgoVehicle getNgoVehicle() {
        return ngoVehicle;
    }

    public void setNgoVehicle(NgoVehicle ngoVehicle) {
        this.ngoVehicle = ngoVehicle;
    }

    public NgoDriver getNgoDriver() {
        return ngoDriver;
    }

    public void setNgoDriver(NgoDriver ngoDriver) {
        this.ngoDriver = ngoDriver;
    }

    public NgoCatcher getNgoCatcher() {
        return ngoCatcher;
    }

    public void setNgoCatcher(NgoCatcher ngoCatcher) {
        this.ngoCatcher = ngoCatcher;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
