package com.green.ida.entity.animal.idacase.result.pojos;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.green.ida.entity.living.animal.pojos.IdaResidentDog;

@Entity
@DiscriminatorValue(value = "CURED")
public class IdaCuredResult extends IdaCaseResult {

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaResidentDog idaResidentDog;

	public IdaResidentDog getIdaResidentDog() {
		return idaResidentDog;
	}

	public void setIdaResidentDog(IdaResidentDog idaResidentDog) {
		this.idaResidentDog = idaResidentDog;
	}

}
