package com.green.ida.entity.living.human.pojos.enums;

public enum IdaCasePersonType {

	INDIVIDUAL, CALLER, NGO;

	public static IdaCasePersonType getIdaCasePersonType(
			String idaCasePersonType) {
		if (idaCasePersonType == null || "".equals(idaCasePersonType))
			throw new IllegalArgumentException(
					"Person Type argument cannot be null or blank");

		if (idaCasePersonType.equalsIgnoreCase(INDIVIDUAL.toString()))
			return INDIVIDUAL;
		else if (idaCasePersonType.equalsIgnoreCase(NGO.toString()))
			return NGO;
		else if (idaCasePersonType.equalsIgnoreCase(CALLER.toString()))
			return CALLER;
		else
			throw new IllegalArgumentException(
					"Person Type could not be determined for value "
							+ idaCasePersonType);

	}

	public String getViewValue() {
		return valueOf(this);
	}

	public static String valueOf(IdaCasePersonType idaCasePersonType) {
		if (idaCasePersonType == null)
			throw new IllegalArgumentException(
					"Person Type argument cannot be null");

		if (IdaCasePersonType.INDIVIDUAL.equals(idaCasePersonType)) {
			return "Individual";
		} else if (IdaCasePersonType.NGO.equals(idaCasePersonType)) {
			return "Ngo";
		} else if (IdaCasePersonType.CALLER.equals(idaCasePersonType)) {
			return "Caller";
		} else
			throw new IllegalArgumentException(
					"Person type could not be determined for value "
							+ idaCasePersonType);

	}

}
