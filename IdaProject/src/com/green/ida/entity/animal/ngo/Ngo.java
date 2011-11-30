package com.green.ida.entity.animal.ngo;

public enum Ngo {

	IDA, SOS, ASHA, BMC, PAWS, RM;

	public static Ngo getNgo(String ngo) {

		if (ngo == null || "".equals(ngo))
			throw new IllegalArgumentException(
					"Ngo argument cannot be null or blank");

		if (ngo.equalsIgnoreCase("ida"))
			return IDA;
		else if (ngo.equalsIgnoreCase("sos"))
			return SOS;
		else if (ngo.equalsIgnoreCase("asha"))
			return ASHA;
		else if (ngo.equalsIgnoreCase("bmc"))
			return BMC;
		else if (ngo.equalsIgnoreCase("paws"))
			return PAWS;
		else if (ngo.equalsIgnoreCase("rm"))
			return RM;
		else
			throw new IllegalArgumentException(
					"Ngo could not be determined for value " + ngo);

	}

	public static String valueOf(Ngo ngo) {
		if (ngo == null)
			throw new IllegalArgumentException("Ngo argument cannot be null");

		if (Ngo.IDA.equals(ngo)) {
			return "Ida";
		} else if (Ngo.SOS.equals(ngo)) {
			return "Sos";
		} else if (Ngo.ASHA.equals(ngo)) {
			return "Asha";
		} else if (Ngo.BMC.equals(ngo)) {
			return "Bmc";
		} else if (Ngo.PAWS.equals(ngo)) {
			return "Paws";
		} else if (Ngo.RM.equals(ngo)) {
			return "Rm";
		} else
			throw new IllegalArgumentException(
					"Ngo could not be determined for value " + ngo);

	}
}
