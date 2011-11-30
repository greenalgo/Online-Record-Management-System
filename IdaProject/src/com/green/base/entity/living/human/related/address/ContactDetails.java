package com.green.base.entity.living.human.related.address;

import javax.persistence.Embeddable;

@Embeddable
public class ContactDetails {

	private String landLine;
	private String mobile;
	private String emailId;
	
	public ContactDetails() {
		
	}
	
	
	
	public ContactDetails(String landLine, String mobile, String emailId) {
		super();
		this.landLine = landLine;
		this.mobile = mobile;
		this.emailId = emailId;
	}



	public String getLandLine() {
		return landLine;
	}
	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public String toString() {
		return "ContactDetails [emailId=" + emailId + ", landLine=" + landLine
				+ ", mobile=" + mobile + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result
				+ ((landLine == null) ? 0 : landLine.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
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
		ContactDetails other = (ContactDetails) obj;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		if (landLine == null) {
			if (other.landLine != null)
				return false;
		} else if (!landLine.equals(other.landLine))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		return true;
	}
	
	
	
}
