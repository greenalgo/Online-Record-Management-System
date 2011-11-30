package com.green.ida.entity.animal.idacase.pojos;

public enum IdaCaseType {
	STERLIZATION, STERLIZATION_AND_TREATMENT, TREATMENT;

	public static IdaCaseType getIdaCaseType(String idaCaseType) {

		if (idaCaseType == null || "".equals(idaCaseType))
			throw new IllegalArgumentException(
					"IdaCaseType argument cannot be null or blank");

		if (idaCaseType.equalsIgnoreCase("sterlization")
				|| idaCaseType.equalsIgnoreCase("s"))
			return STERLIZATION;
		else if (idaCaseType.equalsIgnoreCase("sterlization and treatment")
				|| idaCaseType.equalsIgnoreCase("st"))
			return STERLIZATION_AND_TREATMENT;
		else if (idaCaseType.equalsIgnoreCase("treatment")
				|| idaCaseType.equalsIgnoreCase("t"))
			return TREATMENT;
		else
			throw new IllegalArgumentException(
					"IdaCaseType could not be determined for value "
							+ idaCaseType);

	}

	public String getViewValue() {
		return valueOf(this);
	}

	public static String valueOf(IdaCaseType idaCaseType) {
		if (idaCaseType == null)
			throw new IllegalArgumentException(
					"Ida case type argument cannot be null");

		if (IdaCaseType.STERLIZATION.equals(idaCaseType)) {
			return "Sterlization";
		} else if (IdaCaseType.STERLIZATION_AND_TREATMENT.equals(idaCaseType)) {
			return "Sterlization and Treatment";
		} else if (IdaCaseType.TREATMENT.equals(idaCaseType)) {
			return "Treatment";
		} else
			throw new IllegalArgumentException(
					"Ida case type could not be determined for value "
							+ idaCaseType);

	}

}
