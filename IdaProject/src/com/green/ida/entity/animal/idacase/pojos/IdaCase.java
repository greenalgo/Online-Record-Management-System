package com.green.ida.entity.animal.idacase.pojos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.green.base.entity.animal.medicalcase.AnimalCase;
import com.green.base.entity.living.animal.Animal;
import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.related.address.Address;
import com.green.base.entity.medical.personals.Doctor;
import com.green.ida.entity.animal.idacase.admission.reason.pojos.IdaCaseAdmissionReason;
import com.green.ida.entity.animal.idacase.call.register.pojos.PhoneCall;
import com.green.ida.entity.animal.idacase.result.pojos.IdaCaseResult;
import com.green.ida.entity.living.animal.pojos.IdaDog;
import com.green.ida.entity.living.human.pojos.IdaCaseDoctor;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.ngo.Ngo;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;

@Entity
@DiscriminatorValue(value = "IDA_CASE")
@NamedQueries( {
		@NamedQuery(name = "IdaCase.findIdaCaseResultClosureDate", query = "select i.idaCaseResult.closureDate from IdaCase i where i.id = :id"),
		@NamedQuery(name = "IdaCase.findTokenAlreadyInUse", query = "select i.tokenNumber from IdaCase i where i.isCaseClosed = :flag and i.tokenNumber in (:tokenNumberList)"),
		@NamedQuery(name = "IdaCase.findTokenAlreadyInUseEditCase", query = "select i.tokenNumber from IdaCase i where i.isCaseClosed = :flag and i.tokenNumber = :tokenNumber and i.id != :id") })
public class IdaCase extends AnimalCase {

	private String tokenNumber;
	private String kennelNumber;
	private Boolean isCaseClosed = Boolean.FALSE;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Address broughtFromArea;
	@ManyToOne()
	private NgoDriver ngoDriver;
	@ManyToOne()
	private NgoVehicle ngoVehicle;
	@ManyToOne()
	private NgoCatcher ngoCatcher;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private IdaCaseResult idaCaseResult;
	@Enumerated(value = EnumType.STRING)
	private IdaCaseType idaCaseType;// Sterilization or treatment
	@Enumerated(value = EnumType.STRING)
	private IdaCaseActivity idaCaseActivity;
	@ManyToOne()
	private Ngo ngo;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private PhoneCall phoneCall;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = { @JoinColumn(name = "idaCase_fid") }, inverseJoinColumns = { @JoinColumn(name = "idaCaseAdmissionReason_fid") })
	private Set<IdaCaseAdmissionReason> idaCaseAdmissionReasons = new HashSet<IdaCaseAdmissionReason>();
	@Temporal(TemporalType.DATE)
	private Date sterlizationDate;

	public IdaCase() {
	}

	public IdaCase(String tokenNumber, Doctor doctor,
			HumanBeing personReportingCase, Date admittedOn,
			Date tentatativeDateOfRelease, Boolean isOpdCase,
			String reasonForAdmissionDetails, String treatmentPrescribed,
			Animal animal, Address broughtFromArea, IdaCaseType idaCaseType) {
		super(doctor, personReportingCase, admittedOn,
				tentatativeDateOfRelease, isOpdCase, reasonForAdmissionDetails,
				treatmentPrescribed, animal);
		this.tokenNumber = tokenNumber;
		this.broughtFromArea = broughtFromArea;
		this.reasonForAdmissionDetails = reasonForAdmissionDetails;
	}

	public void setBroughtFromArea(Address broughtFromArea) {
		this.broughtFromArea = broughtFromArea;
	}

	public Address getBroughtFromArea() {
		return broughtFromArea;
	}

	public void setIdaCaseType(IdaCaseType idaCaseType) {
		this.idaCaseType = idaCaseType;
	}

	public IdaCaseType getIdaCaseType() {
		return idaCaseType;
	}

	public void setIdaCaseAdmissionReasons(
			Set<IdaCaseAdmissionReason> idaCaseAdmissionReasons) {
		this.idaCaseAdmissionReasons = idaCaseAdmissionReasons;
	}

	public Set<IdaCaseAdmissionReason> getIdaCaseAdmissionReason() {
		return idaCaseAdmissionReasons;
	}

	public void addIdaCaseAdmissionReason(
			IdaCaseAdmissionReason idaCaseAdmissionReason) {
		this.idaCaseAdmissionReasons.add(idaCaseAdmissionReason);
	}

	public IdaDog getIdaDog() {
		return (IdaDog) this.animal;
	}

	public IdaCaseDoctor getIdaDoctor() {
		return (IdaCaseDoctor) this.doctor;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setNgoDriver(NgoDriver ngoDriver) {
		this.ngoDriver = ngoDriver;
	}

	public NgoDriver getNgoDriver() {
		return ngoDriver;
	}

	public void setNgoVehicle(NgoVehicle ngoVehicle) {
		this.ngoVehicle = ngoVehicle;
	}

	public NgoVehicle getNgoVehicle() {
		return ngoVehicle;
	}

	public String getKennelNumber() {
		return kennelNumber;
	}

	public void setKennelNumber(String kennelNumber) {
		this.kennelNumber = kennelNumber;
	}

	public String getIdaCaseAdmissionReasonsCsvList() {
		StringBuilder csv = new StringBuilder();
		for (IdaCaseAdmissionReason reason : idaCaseAdmissionReasons) {
			csv.append(reason.getReasonForAdmission()).append(",");
		}

		return csv.substring(0, csv.length() - 1);
	}

	public String[] getIdaCaseAdmissionReasonIdList() {
		String[] ids = new String[idaCaseAdmissionReasons.size()];
		int i = 0;
		for (IdaCaseAdmissionReason reason : idaCaseAdmissionReasons) {
			ids[i] = reason.getId().toString();
			i++;
		}

		return ids;
	}

	public Boolean getIsCaseClosed() {
		return this.isCaseClosed;
	}

	public void setIsCaseClosed(Boolean isCaseClosed) {
		this.isCaseClosed = isCaseClosed;
	}

	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

	public Ngo getNgo() {
		return ngo;
	}

	public void setIdaCaseResult(IdaCaseResult idaCaseResult) {
		this.idaCaseResult = idaCaseResult;
	}

	public IdaCaseResult getIdaCaseResult() {
		return idaCaseResult;
	}

	public void setSterlizationDate(Date sterlizationDate) {
		this.sterlizationDate = sterlizationDate;
	}

	public Date getSterlizationDate() {
		return sterlizationDate;
	}

	public void setNgoCatcher(NgoCatcher ngoCatcher) {
		this.ngoCatcher = ngoCatcher;
	}

	public NgoCatcher getNgoCatcher() {
		return ngoCatcher;
	}

	public PhoneCall getPhoneCall() {
		return phoneCall;
	}

	public void setPhoneCall(PhoneCall phoneCall) {
		this.phoneCall = phoneCall;
	}

	public IdaCaseActivity getIdaCaseActivity() {
		return idaCaseActivity;
	}

	public void setIdaCaseActivity(IdaCaseActivity idaCaseActivity) {
		this.idaCaseActivity = idaCaseActivity;
	}

	public boolean getIsMobileActivity() {
		return this.getIdaCaseActivity().isMobile();
	}

}
