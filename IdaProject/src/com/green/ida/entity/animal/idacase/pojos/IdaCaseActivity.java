/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.green.ida.entity.animal.idacase.pojos;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author gaurav
 */
public enum IdaCaseActivity {
	AMBULANCE, MOBILE, SELF;
	private static final Map<String, Long> idaCaseActivityMap = new LinkedHashMap<String, Long>();
	static {

		idaCaseActivityMap.put("Select Ida Case Activity", 0l);
		idaCaseActivityMap.put("Mobile", 1l);
		idaCaseActivityMap.put("Ambulance", 2l);
		idaCaseActivityMap.put("Self", 3l);
	}

	public static IdaCaseActivity getIdaCaseActivity(String idaCaseActivity) {

		if (idaCaseActivity == null || "".equals(idaCaseActivity))
			throw new IllegalArgumentException(
					"IdaCaseActivity argument cannot be null or blank");

		if (idaCaseActivity.equalsIgnoreCase("ambulance"))
			return AMBULANCE;
		else if (idaCaseActivity.equalsIgnoreCase("mobile"))
			return MOBILE;
		else if (idaCaseActivity.equalsIgnoreCase("self"))
			return SELF;

		else
			throw new IllegalArgumentException(
					"IdaCaseActivity could not be determined for value "
							+ idaCaseActivity);

	}

	public String getViewValue() {
		return valueOf(this);
	}

	public boolean isMobile() {
		return valueOf(this).equalsIgnoreCase("mobile");
	}

	public static String valueOf(IdaCaseActivity idaCaseActivity) {
		if (idaCaseActivity == null)
			throw new IllegalArgumentException(
					"Ida case activity argument cannot be null");

		if (IdaCaseActivity.MOBILE.equals(idaCaseActivity)) {
			return "Mobile";
		} else if (IdaCaseActivity.AMBULANCE.equals(idaCaseActivity)) {
			return "Ambulance";
		} else if (IdaCaseActivity.SELF.equals(idaCaseActivity)) {
			return "Self";
		} else
			throw new IllegalArgumentException(
					"IdaCaseActivity could not be determined for value "
							+ idaCaseActivity);

	}

	public static Map<String, Long> getIdaCaseActivityMap() {
		return idaCaseActivityMap;

	}

	public static boolean isValidIdaCaseActivity(String idaCaseActivity) {

		try {
			getIdaCaseActivity(idaCaseActivity);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
