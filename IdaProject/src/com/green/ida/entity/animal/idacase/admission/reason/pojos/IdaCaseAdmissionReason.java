package com.green.ida.entity.animal.idacase.admission.reason.pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IdaCaseAdmissionReason {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String reasonForAdmission;
	
	public IdaCaseAdmissionReason() {
		
	}
	
	
	
	public IdaCaseAdmissionReason(String reasonForAdmission) {
		super();
		this.reasonForAdmission = reasonForAdmission;
	}



	//@ManyToMany
	//private Set<IdaCase> idaCaseSet = new HashSet<IdaCase>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReasonForAdmission() {
		return reasonForAdmission;
	}

	public void setReasonForAdmission(String reasonForAdmission) {
		this.reasonForAdmission = reasonForAdmission;
	}

	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((reasonForAdmission == null) ? 0 : reasonForAdmission
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdaCaseAdmissionReason other = (IdaCaseAdmissionReason) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reasonForAdmission == null) {
			if (other.reasonForAdmission != null)
				return false;
		} else if (!reasonForAdmission.equals(other.reasonForAdmission))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IdaCaseAdmissionReason [id=" + id 
				+ ", reasonForAdmission=" + reasonForAdmission + "]";
	}



//	public void setIdaCaseSet(Set<IdaCase> idaCaseSet) {
//		this.idaCaseSet = idaCaseSet;
//	}
//
//
//
//	public Set<IdaCase> getIdaCaseSet() {
//		return idaCaseSet;
//	}
	
	
	
}
