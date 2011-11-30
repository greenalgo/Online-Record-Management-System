/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.green.ida.entity.animal.idacase.pojos;

import com.green.base.entity.medical.personals.Doctor;
import com.green.ida.entity.living.human.pojos.NgoCatcher;
import com.green.ida.entity.living.human.pojos.NgoDriver;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author gaurav
 */
@Entity
public class FollowUpCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch= FetchType.LAZY)
    private FollowUpCase followUpCaseLink;
    @ManyToOne
    private IdaCase idaCase;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoDriver ngoDriver;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoVehicle ngoVehicle;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private NgoCatcher ngoCatcher;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Doctor doctor;// attending doctor
    @Temporal(TemporalType.DATE)
    private Date treatmentDate;
    @Size(max = 214000000)
    private String treatment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FollowUpCase getFollowUpCaseLink() {
        return followUpCaseLink;
    }

    public void setFollowUpCaseLink(FollowUpCase followUpCaseLink) {
        this.followUpCaseLink = followUpCaseLink;
    }

    public IdaCase getIdaCase() {
        return idaCase;
    }

    public void setIdaCase(IdaCase idaCase) {
        this.idaCase = idaCase;
    }

    public NgoDriver getNgoDriver() {
        return ngoDriver;
    }

    public void setNgoDriver(NgoDriver ngoDriver) {
        this.ngoDriver = ngoDriver;
    }

    public NgoVehicle getNgoVehicle() {
        return ngoVehicle;
    }

    public void setNgoVehicle(NgoVehicle ngoVehicle) {
        this.ngoVehicle = ngoVehicle;
    }

    public NgoCatcher getNgoCatcher() {
        return ngoCatcher;
    }

    public void setNgoCatcher(NgoCatcher ngoCatcher) {
        this.ngoCatcher = ngoCatcher;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    
}
