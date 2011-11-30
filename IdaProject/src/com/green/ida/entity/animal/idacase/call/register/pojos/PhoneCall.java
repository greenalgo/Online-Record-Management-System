package com.green.ida.entity.animal.idacase.call.register.pojos;

import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.related.address.Address;
import com.green.ida.entity.nonliving.idavehicle.NgoVehicle;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class PhoneCall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date complainDate;
    
    @Temporal(TemporalType.DATE)
    private Date attendedDate;
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private HumanBeing complainer;
    
    @OneToOne(cascade = { CascadeType.ALL })
    private Address complainSource;
    
    @ManyToOne
    private NgoVehicle ngoVehicle;
  
    @Size(max = 214000000)
    private String complain;
    
    @Enumerated(value = EnumType.STRING)
    private CallStatus callStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getComplainDate() {
        return complainDate;
    }

    public void setComplainDate(Date complainDate) {
        this.complainDate = complainDate;
    }

    public Address getComplainSource() {
        return complainSource;
    }

    public void setComplainSource(Address complainSource) {
        this.complainSource = complainSource;
    }

    public NgoVehicle getNgoVehicle() {
        return ngoVehicle;
    }

    public void setNgoVehicle(NgoVehicle ngoVehicle) {
        this.ngoVehicle = ngoVehicle;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public Date getAttendedDate() {
        return attendedDate;
    }

    public void setAttendedDate(Date attendedDate) {
        this.attendedDate = attendedDate;
    }

    public HumanBeing getComplainer() {
        return complainer;
    }

    public void setComplainer(HumanBeing complainer) {
        this.complainer = complainer;
    }

  
    

}
