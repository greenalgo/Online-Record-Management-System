package com.green.ida.entity.animal.idacase.result.pojos;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Entity
public class MobileIdaCaseResult extends IdaCaseResult {

	@Enumerated(EnumType.STRING)
	private MobileResult mobileResult;

	@Size(max = 214000000)
	private String remarks;

	public void setMobileResult(MobileResult mobileResult) {
		this.mobileResult = mobileResult;
	}

	public MobileResult getMobileResult() {
		return mobileResult;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public static enum MobileResult {
		CURED, DIED, DISCARDED;

		private static final Map<String, Long> mobileResultMap = new LinkedHashMap<String, Long>();
		static {

			mobileResultMap.put("Select Mobie Ida Case Result", 0l);
			mobileResultMap.put("Cured", 1l);
			mobileResultMap.put("Died", 2l);
			mobileResultMap.put("Discarded", 3l);
		}

		public static Map<String, Long> getMobileResultMap() {
			return mobileResultMap;
		}

		public String getViewValue() {
			return valueOf(this);
		}

		public static MobileResult getMobileResult(String mobileResult) {

			if (mobileResult == null || "".equals(mobileResult))
				throw new IllegalArgumentException(
						"MobileResult argument cannot be null or blank");

			if (mobileResult.equalsIgnoreCase("cured"))
				return CURED;
			else if (mobileResult.equalsIgnoreCase("died"))
				return DIED;
			else if (mobileResult.equalsIgnoreCase("discarded"))
				return DISCARDED;

			else
				throw new IllegalArgumentException(
						"MobileResult could not be determined for value "
								+ mobileResult);

		}

		public static String valueOf(MobileResult mobileResult) {
			if (mobileResult == null)
				throw new IllegalArgumentException(
						"Mobile result argument cannot be null");

			if (MobileResult.CURED.equals(mobileResult)) {
				return "Cured";
			} else if (MobileResult.DIED.equals(mobileResult)) {
				return "Died";
			} else if (MobileResult.DISCARDED.equals(mobileResult)) {
				return "Discarded";
			} else
				throw new IllegalArgumentException(
						"MobileResult could not be determined for value "
								+ mobileResult);

		}
	}

}
