package com.green.ida.entity.animal.idacase.call.register.pojos;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author gaurav
 */
public enum CallStatus {

    DISCARDED, MOBILE, AMBULANCE, OPEN;
    private static final Map<String, Long> callStatusMap = new LinkedHashMap<String, Long>();
    private static final Map<String, String> callStatusSearchMap = new LinkedHashMap<String, String>();
    
    static {
        
        callStatusMap.put("Select Call Status", 0l);
        callStatusMap.put("Discarded", 1l);
        callStatusMap.put("Mobile", 2l);
        callStatusMap.put("Ambulance", 3l);
       


        
        callStatusSearchMap.put("Select Call Status", "0");
        callStatusSearchMap.put("Discarded", "DISCARDED");
        callStatusSearchMap.put("Mobile", "MOBILE");
        callStatusSearchMap.put("Ambulance", "AMBULANCE");
        



    }

    public static CallStatus getCallStatus(String callStatus) {

        if (callStatus == null || "".equals(callStatus)) {
            throw new IllegalArgumentException(
                    "CallStatus argument cannot be null or blank");
        }

        if (callStatus.equalsIgnoreCase("discarded")) {
            return DISCARDED;
        } else if (callStatus.equalsIgnoreCase("mobile")) {
            return MOBILE;
        } else if (callStatus.equalsIgnoreCase("ambulance")) {
            return AMBULANCE;
        } else if (callStatus.equalsIgnoreCase("open")) {
            return OPEN;
        } else {
            throw new IllegalArgumentException(
                    "CallStatus could not be determined for value "
                    + callStatus);
        }

    }

    public String getViewValue() {
        return valueOf(this);
    }

    public static String valueOf(CallStatus callStatus) {
        if (callStatus == null) {
            throw new IllegalArgumentException(
                    "Call status type argument cannot be null");
        }

        if (CallStatus.DISCARDED.equals(callStatus)) {
            return "Discarded";
        } else if (CallStatus.MOBILE.equals(callStatus)) {
            return "Mobile";
        } else if (CallStatus.AMBULANCE.equals(callStatus)) {
            return "Ambulance";
        } else if (CallStatus.OPEN.equals(callStatus)) {
            return "Open";
        } else {
            throw new IllegalArgumentException(
                    "Call status type could not be determined for value "
                    + callStatus);
        }

    }

    public static Map<String, Long> initCallStatusMap() {

        return callStatusMap;

    }
    
     public static Map<String, String> initCallStatusMapForSearch() {


        return callStatusSearchMap;

    }
}
