package com.green.ida.controller.search.ida.call;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;

@Named
public class IdaCallSearchForm implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8444237750949216076L;
    
    private String mobile;
    private Date callDate;
    private String complainerName;
    private String callStatus;

   

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public String getComplainerName() {
        return complainerName;
    }

    public void setComplainerName(String complainerName) {
        this.complainerName = complainerName;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public void reset() {
        this.callStatus = null;
        this.complainerName = null;
        this.callDate = null;
        this.mobile = null;

    }
}
