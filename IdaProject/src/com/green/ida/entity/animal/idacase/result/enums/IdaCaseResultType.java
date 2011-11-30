package com.green.ida.entity.animal.idacase.result.enums;

public enum IdaCaseResultType {

	RESIDENT, ADOPTED, ABANDONED, RELEASED, KORA_KENDRA, BURIED_IN_CENTRE, MOBILE;

	public static IdaCaseResultType getIdaCaseResultType(
			String idaCaseResultType) {

		if (idaCaseResultType == null || "".equals(idaCaseResultType))
			throw new IllegalArgumentException(
					"IdaCaseResultType argument cannot be null or blank");

		if (idaCaseResultType.equalsIgnoreCase("resident"))
			return RESIDENT;
		else if (idaCaseResultType.equalsIgnoreCase("adopted"))
			return ADOPTED;
		else if (idaCaseResultType.equalsIgnoreCase("abandoned"))
			return ABANDONED;
		else if (idaCaseResultType.equalsIgnoreCase("released"))
			return RELEASED;
		else if (idaCaseResultType.equalsIgnoreCase("kora kendra"))
			return KORA_KENDRA;
		else if (idaCaseResultType.equalsIgnoreCase("buried in center"))
			return BURIED_IN_CENTRE;
		else
			throw new IllegalArgumentException(
					"IdaCaseResultType could not be determined for value "
							+ idaCaseResultType);
	}

	public static String valueOf(IdaCaseResultType idaCaseResultType) {
		if (idaCaseResultType == null)
			throw new IllegalArgumentException(
					"IdaCaseResultType cannot be null");

		if (IdaCaseResultType.RESIDENT.equals(idaCaseResultType))
			return "Resident";
		else if (IdaCaseResultType.ADOPTED.equals(idaCaseResultType))
			return "Adopted";
		else if (IdaCaseResultType.ABANDONED.equals(idaCaseResultType))
			return "Abandoned";
		else if (IdaCaseResultType.RELEASED.equals(idaCaseResultType))
			return "Released";
		else if (IdaCaseResultType.KORA_KENDRA.equals(idaCaseResultType))
			return "Kora Kendra";
		else if (IdaCaseResultType.BURIED_IN_CENTRE.equals(idaCaseResultType))
			return "Buried In Center";
		else
			throw new IllegalArgumentException(
					"IdaCaseResultType could not be determined for value "
							+ idaCaseResultType);

	}
}
