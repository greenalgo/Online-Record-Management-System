package com.green.ida.entity.living.human.pojos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.green.base.entity.living.human.HumanBeing;
import com.green.ida.entity.ngo.Ngo;

@Entity
public class NgoDriver extends HumanBeing {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Ngo ngo;

	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

	public Ngo getNgo() {
		return ngo;
	}

	public String getConcatenatedNgoAndDriverFullName() {
		return ngo.getNgoName() + " - " + name.getFullName();
	}

}
