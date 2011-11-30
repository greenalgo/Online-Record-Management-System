package com.green.ida.entity.animal.idacase.result.pojos;

import com.green.ida.entity.animal.idacase.resident.IdaResident;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue(value = "RESIDENT")
public class IdaResidentResult extends IdaCaseResult {

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private IdaResident idaResident;

    public IdaResident getIdaResident() {
        return idaResident;
    }

    public void setIdaResident(IdaResident idaResident) {
        this.idaResident = idaResident;
    }
}
