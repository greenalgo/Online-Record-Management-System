package com.green.base.entity.living.related.enums;

public enum Gender {

	MALE, FEMALE;

	public static Gender getGender(String gender) {
		if (gender == null || "".equals(gender))
			throw new IllegalArgumentException(
					"Gender argument cannot be null or blank");

		if (gender.equalsIgnoreCase(MALE.toString())
				|| gender.equalsIgnoreCase("m"))
			return MALE;
		else if (gender.equalsIgnoreCase(FEMALE.toString())
				|| gender.equalsIgnoreCase("f"))
			return FEMALE;
		else
			throw new IllegalArgumentException(
					"Gender could not be determined for value " + gender);

	}

	public String getViewValue() {
		return valueOf(this);
	}
	
	public void setViewValue(String val) {
		
	}

	public static String valueOf(Gender gender) {
		if (gender == null)
			throw new IllegalArgumentException("Gender argument cannot be null");

		if (Gender.FEMALE.equals(gender)) {
			return "Female";
		} else if (Gender.MALE.equals(gender)) {
			return "Male";
		} else
			throw new IllegalArgumentException(
					"Gender could not be determined for value " + gender);

	}

}
