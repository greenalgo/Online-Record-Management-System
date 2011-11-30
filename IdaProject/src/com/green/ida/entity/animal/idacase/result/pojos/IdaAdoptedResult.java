package com.green.ida.entity.animal.idacase.result.pojos;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.green.ida.entity.animal.idacase.adoption.IdaAdoption;

@Entity
@DiscriminatorValue(value = "ADOPTED")
public class IdaAdoptedResult extends IdaCaseResult {

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaAdoption idaAdoption;

	public IdaAdoption getIdaAdoption() {
		return idaAdoption;
	}

	public void setIdaAdoption(IdaAdoption idaAdoption) {
		this.idaAdoption = idaAdoption;
	}

}
