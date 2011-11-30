package com.green.ida.entity.nonliving.idavehicle;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.green.base.entity.nonliving.vehicle.Vehicle;
import com.green.ida.entity.ngo.Ngo;

@Entity
@NamedQueries( {
		@NamedQuery(name = "NgoVehicle.exists", query = "select v.id from NgoVehicle v where v.registrationNumber like :registrationNumber"),
		@NamedQuery(name = "NgoVehicle.existsEditCase", query = "select v.id from NgoVehicle v where v.registrationNumber like :registrationNumber and v.id != :id") })
public class NgoVehicle extends Vehicle {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Ngo ngo;

	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

	public Ngo getNgo() {
		return ngo;
	}

	public String getConcatenatedNgoAndRegistrationNo() {
		return this.ngo.getNgoName() + " " + this.registrationNumber;
	}

	public String getConcatenatedNgoRegistrationNoAndVehicleType() {
		return this.ngo.getNgoName() + " - "
				+ this.getVehicleType().getVehicleType() + " - "
				+ this.registrationNumber;
	}

}
