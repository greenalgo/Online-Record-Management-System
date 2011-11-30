package com.green.ida.entity.animal.idacase.result.pojos;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PUTDOWN")
public class IdaPutdownResult extends IdaCaseResult {

	private Date koraKendraReleaseDate;
	private Date buriedInCenterDate;

	public Date getKoraKendraReleaseDate() {
		return koraKendraReleaseDate;
	}

	public void setKoraKendraReleaseDate(Date koraKendraReleaseDate) {
		this.koraKendraReleaseDate = koraKendraReleaseDate;
	}

	public void setBuriedInCenterDate(Date buriedInCenterDate) {
		this.buriedInCenterDate = buriedInCenterDate;
	}

	public Date getBuriedInCenterDate() {
		return buriedInCenterDate;
	}

}
