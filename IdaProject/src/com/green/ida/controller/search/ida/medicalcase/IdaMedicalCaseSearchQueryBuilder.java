package com.green.ida.controller.search.ida.medicalcase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IdaMedicalCaseSearchQueryBuilder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2088161871357810293L;
	private String csvColumnNames;
	private List<Serializable> columnValues = new ArrayList<Serializable>();
	private String likeColumnNameCsv;
	private List<String> likeColumnValues = new ArrayList<String>();
	private Boolean isLike = false;

	private IdaMedicalCaseSearchForm idaMedicalCaseSearchForm;

	public IdaMedicalCaseSearchQueryBuilder(
			IdaMedicalCaseSearchForm idaMedicalCaseSearchForm) {
		this.idaMedicalCaseSearchForm = idaMedicalCaseSearchForm;
	}

	public void buildQueryParts() {
		StringBuilder queryBuilder = new StringBuilder();
		StringBuilder queryBuilderForLike = new StringBuilder();

		if (this.idaMedicalCaseSearchForm.getId() != null) {
			queryBuilder.append("id").append(",");
			columnValues.add(this.idaMedicalCaseSearchForm.getId());
		}
		if (this.idaMedicalCaseSearchForm.getTokenNo() != null
				&& !"".equals(this.idaMedicalCaseSearchForm.getTokenNo())) {
			queryBuilder.append("tokenNumber").append(",");
			columnValues.add(this.idaMedicalCaseSearchForm.getTokenNo());
		}
		if (this.idaMedicalCaseSearchForm.getAdmissionDate() != null) {
			queryBuilder.append("admittedOn").append(",");
			Date midNiteDate = makeDateOfMidnite(this.idaMedicalCaseSearchForm
					.getAdmissionDate());
			columnValues.add(midNiteDate);
		}
		if (this.idaMedicalCaseSearchForm.getPersonFirstName() != null
				&& !"".equals(this.idaMedicalCaseSearchForm
						.getPersonFirstName())) {
			setIsLike(true);
			queryBuilderForLike.append("personReportingCase.name.firstName")
					.append(",");
			likeColumnValues.add(this.idaMedicalCaseSearchForm
					.getPersonFirstName()
					+ "%");
		}

		if (this.idaMedicalCaseSearchForm.getLocality() != null
				&& !"0".equals(this.idaMedicalCaseSearchForm.getLocality()) && !"".equals(this.idaMedicalCaseSearchForm.getLocality()) ) {
			//setIsLike(true);
			queryBuilder.append(
					"broughtFromArea.locality.id").append(",");
			columnValues.add(this.idaMedicalCaseSearchForm.getLocality()
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
		return "id,tokenNumber,admittedOn,isCaseClosed,idaCaseActivity";
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
