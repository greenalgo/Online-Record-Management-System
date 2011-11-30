/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green.ida.entity.animal.idacase.adoption;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.green.base.entity.living.human.HumanBeing;
import com.green.ida.entity.center.IdaCenter;
import com.green.ida.entity.living.animal.pojos.IdaAdoptedAnimal;

/**
 * 
 * @author gaurav
 */
@Entity
public class IdaAdoption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1071746306578434632L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date adoptionDate;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaAdoptedAnimal idaAdoptedAnimal;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private HumanBeing idaAnimalAdopter;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaCenter idaCenter;
	private String supervisedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAdoptionDate() {
		return adoptionDate;
	}

	public void setAdoptionDate(Date adoptionDate) {
		this.adoptionDate = adoptionDate;
	}

	public IdaAdoptedAnimal getIdaAdoptedAnimal() {
		return idaAdoptedAnimal;
	}

	public void setIdaAdoptedAnimal(IdaAdoptedAnimal idaAdoptedAnimal) {
		this.idaAdoptedAnimal = idaAdoptedAnimal;
	}

	public HumanBeing getIdaAnimalAdopter() {
		return idaAnimalAdopter;
	}

	public void setIdaAnimalAdopter(HumanBeing idaAnimalAdopter) {
		this.idaAnimalAdopter = idaAnimalAdopter;
	}

	public IdaCenter getIdaCenter() {
		return idaCenter;
	}

	public void setIdaCenter(IdaCenter idaCenter) {
		this.idaCenter = idaCenter;
	}

	public String getSupervisedBy() {
		return supervisedBy;
	}

	public void setSupervisedBy(String supervisedBy) {
		this.supervisedBy = supervisedBy;
	}
}
