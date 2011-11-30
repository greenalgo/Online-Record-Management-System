package com.green.ida.controller.search.ida.call;

import com.green.ida.entity.animal.idacase.call.register.pojos.CallStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IdaCallSearchQueryBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2088561871357810293L;
	private String csvColumnNames;
	private List<Serializable> columnValues = new ArrayList<Serializable>();
	private String likeColumnNameCsv;
	private List<String> likeColumnValues = new ArrayList<String>();
	private Boolean isLike = false;

	private IdaCallSearchForm idaCallSearchForm;

	public IdaCallSearchQueryBuilder(
			IdaCallSearchForm idaCallSearchForm) {
		this.idaCallSearchForm = idaCallSearchForm;
	}

	public void buildQueryParts() {
		StringBuilder queryBuilder = new StringBuilder();
		StringBuilder queryBuilderForLike = new StringBuilder();

		
		if (this.idaCallSearchForm.getMobile() != null
				&& !"".equals(this.idaCallSearchForm.getMobile())) {
                    setIsLike(true);
			queryBuilder.append("complainer.contactDetails.mobile").append(",");
                        likeColumnValues.add(this.idaCallSearchForm
					.getMobile()
					+ "%");
			
		}
		if (this.idaCallSearchForm.getCallDate() != null) {
			queryBuilder.append("complainDate").append(",");
			Date midNiteDate = makeDateOfMidnite(this.idaCallSearchForm
					.getCallDate());
			columnValues.add(midNiteDate);
		}
		if (this.idaCallSearchForm.getComplainerName() != null
				&& !"".equals(this.idaCallSearchForm
						.getComplainerName())) {
			setIsLike(true);
			queryBuilderForLike.append("complainer.name.firstName")
					.append(",");
			likeColumnValues.add(this.idaCallSearchForm
					.getComplainerName()
					+ "%");
		}

		if (this.idaCallSearchForm.getCallStatus() != null &&
				!"0".equals(this.idaCallSearchForm.getCallStatus()) && !"".equals(this.idaCallSearchForm.getCallStatus()) ) {
			//setIsLike(true);
			queryBuilder.append(
					"callStatus").append(",");
			columnValues.add(CallStatus.getCallStatus(this.idaCallSearchForm.getCallStatus())
					);
		}

		if (queryBuilder.length() != 0)
			setCsvColumnNames(queryBuilder.substring(0,
					queryBuilder.length() - 1));
		else
			setCsvColumnNames("");

		if (queryBuilderForLike.length() != 0) {
			likeColumnNameCsv = queryBuilderForLike.substring(0,
					queryBuilderForLike.length() - 1);
		} else {
			likeColumnNameCsv = "";
		}
	}

	@SuppressWarnings("deprecation")
	public Date makeDateOfMidnite(Date admissionDateCopy) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(admissionDateCopy.getYear() + 1900, admissionDateCopy
				.getMonth(), admissionDateCopy.getDate(), 0, 0, 0);
		return calendar.getTime();
	}

	public String getMultiselectColumnNames() {
		return "id,complainer.contactDetails.mobile,complainDate,complainer.name,callStatus,attendedDate";
	}

	public void setCsvColumnNames(String csvColumnNames) {
		this.csvColumnNames = csvColumnNames;
	}

	public String getCsvColumnNames() {
		return csvColumnNames;
	}

	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}

	public Boolean getIsLike() {
		return isLike;
	}

	public String getLikeColumnNameCsv() {
		return likeColumnNameCsv;
	}

	public void setLikeColumnNameCsv(String likeColumnNameCsv) {
		this.likeColumnNameCsv = likeColumnNameCsv;
	}

	public List<Serializable> getColumnValues() {
		return columnValues;
	}

	public void setColumnValues(List<Serializable> columnValues) {
		this.columnValues = columnValues;
	}

	public List<String> getLikeColumnValues() {
		return likeColumnValues;
	}

	public void setLikeColumnValues(List<String> likeColumnValues) {
		this.likeColumnValues = likeColumnValues;
	}

	public Serializable[] getColumnValuesArray() {
		return this.columnValues == null ? new Serializable[0]
				: this.columnValues.toArray(new Serializable[0]);
	}

	public String[] getLikeColumnValuesArray() {
		return this.likeColumnValues == null ? new String[0]
				: this.likeColumnValues.toArray(new String[0]);
	}

}
