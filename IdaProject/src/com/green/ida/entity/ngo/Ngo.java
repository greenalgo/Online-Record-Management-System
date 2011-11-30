package com.green.ida.entity.ngo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.green.ida.entity.living.human.pojos.IdaCasePerson;

@Entity
@NamedQueries( { @NamedQuery(name = "Ngo.existsNgoEditCase", query = "select n.id from Ngo n where n.ngoName = :ngoName and n.id != :id") })
public class Ngo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -901992398260088625L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ngoName;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaCasePerson chairPerson;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNgoName() {
		return ngoName;
	}

	public void setNgoName(String ngoName) {
		this.ngoName = ngoName;
	}

	public IdaCasePerson getChairPerson() {
		return chairPerson;
	}

	public void setChairPerson(IdaCasePerson chairPerson) {
		this.chairPerson = chairPerson;
	}
}
