/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green.ida.entity.animal.idacase.resident;

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

import com.green.ida.entity.center.IdaCenter;
import com.green.ida.entity.living.animal.pojos.IdaResidentAnimal;

/**
 * 
 * @author gaurav
 */
@Entity
public class IdaResident implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2130869367556247953L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date actionDate;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaResidentAnimal idaResidentAnimal;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaCenter idaCenter;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public IdaResidentAnimal getIdaResidentAnimal() {
		return idaResidentAnimal;
	}

	public void setIdaResidentAnimal(IdaResidentAnimal idaResidentAnimal) {
		this.idaResidentAnimal = idaResidentAnimal;
	}

	public IdaCenter getIdaCenter() {
		return idaCenter;
	}

	public void setIdaCenter(IdaCenter idaCenter) {
		this.idaCenter = idaCenter;
	}

}
