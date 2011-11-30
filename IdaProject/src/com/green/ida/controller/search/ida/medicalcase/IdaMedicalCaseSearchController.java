package com.green.ida.controller.search.ida.medicalcase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.pojos.IdaCase;
import com.green.ida.entity.animal.idacase.pojos.IdaCaseActivity;
import com.green.ida.entity.animal.idacase.result.pojos.IdaCaseResult;
import com.green.jsf.faces.util.FacesUtil;

@Named
@Scope("session")
public class IdaMedicalCaseSearchController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -123957213763861798L;

	private static transient Logger LOGGER = Logger
			.getLogger(IdaMedicalCaseSearchController.class);

	private IdaMedicalCaseSearchForm idaMedicalCaseSearchForm = new IdaMedicalCaseSearchForm();
	private LazyDataModel<IdaCase> lazyModel;

	@Inject
	private GenericDao genericDao;

	@Inject
	private ControllerUtil controllerUtil;

	@Inject
	private FacesUtil facesUtil;

	private Integer page = 0;

	private String back;

	private Integer count;

	private IdaMedicalCaseSearchQueryBuilder qBuilder;
	private Map<String, String> localityMap;

	public void setIdaMedicalCaseSearchForm(
			IdaMedicalCaseSearchForm idaMedicalCaseSearchForm) {
		this.idaMedicalCaseSearchForm = idaMedicalCaseSearchForm;
	}

	public IdaMedicalCaseSearchForm getIdaMedicalCaseSearchForm() {
		return idaMedicalCaseSearchForm;
	}

	@PostConstruct
	public void initComponents() {

		this.localityMap = controllerUtil.initLocalityMap();

	}

	// @PostConstruct
	public void initSearchLazyList() {
		qBuilder = new IdaMedicalCaseSearchQueryBuilder(
				idaMedicalCaseSearchForm);
		qBuilder.buildQueryParts();
		// if (count == null) {
		if (qBuilder.getIsLike()) {
			count = genericDao.countDistinctOnRootWhereAllMatchAndMultiLike(
					Long.class, IdaCase.class, qBuilder.getLikeColumnNameCsv(),
					qBuilder.getLikeColumnValuesArray(),
					qBuilder.getCsvColumnNames(),
					qBuilder.getColumnValuesArray()).intValue();
		} else {
			count = genericDao.countDistinctOnRootWhereAllMatch(Long.class,
					IdaCase.class, qBuilder.getCsvColumnNames(),
					qBuilder.getColumnValuesArray()).intValue();
		}
		// }
		lazyModel = new LazyDataModel<IdaCase>(count) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7144873906078340400L;

			@Override
			public List<IdaCase> fetchLazyData(int first, int pageSize) {
				try {
					List<Object[]> searchResult;
					if (back != null && back.equals("back")) {
						first = pageSize * (page - 1);
						back = null;
					}
					page = (first + pageSize) / pageSize;
					if (qBuilder.getIsLike()) {
						searchResult = genericDao
								.multiSelectWithLimitWhereAllMatchAndMultiLike(
										Object[].class, IdaCase.class, first,
										pageSize, qBuilder
												.getMultiselectColumnNames(),
										qBuilder.getLikeColumnNameCsv(),
										qBuilder.getLikeColumnValuesArray(),
										qBuilder.getCsvColumnNames(), qBuilder
												.getColumnValuesArray());
					} else {
						searchResult = genericDao
								.multiSelectWithLimitWhereAllMatch(
										Object[].class, IdaCase.class, first,
										pageSize, qBuilder
												.getMultiselectColumnNames(),
										qBuilder.getCsvColumnNames(), qBuilder
												.getColumnValues().toArray(
														new Serializable[0]));
					}
					return processSearchResultObjectArray(searchResult);
				} catch (Exception e) {
					LOGGER.error("Problem in pagination " + e.getMessage(), e);
				}
				return Collections.emptyList();
			}
		};
	}

	private List<IdaCase> processSearchResultObjectArray(
			List<Object[]> searchresult) {
		List<IdaCase> result = new ArrayList<IdaCase>();
		for (Object[] res : searchresult) {
			IdaCase idaCase = new IdaCase();
			idaCase.setIdaCaseResult(new IdaCaseResult());
			idaCase.setId((Long) res[0]);
			idaCase.setTokenNumber(res[1] == null ? "" : res[1].toString());

			idaCase.setAdmittedOn((Date) res[2]);
			idaCase.setIsCaseClosed((Boolean) res[3]);
			idaCase.setIdaCaseActivity((IdaCaseActivity) res[4]);
			if (idaCase.getIsCaseClosed()) {
				List<Object> idaCaseReleaseDate = genericDao
						.executeNamedQuery(
								"IdaCase.findIdaCaseResultClosureDate",
								new String[] { "id" }, new Object[] { idaCase
										.getId() });
				idaCase.getIdaCaseResult().setClosureDate(
						(Date) idaCaseReleaseDate.get(0));

			}

			result.add(idaCase);
		}
		Collections.sort(result, new Comparator<IdaCase>() {

			@Override
			public int compare(IdaCase o1, IdaCase o2) {
				return o2.getAdmittedOn().compareTo(o1.getAdmittedOn());
			}

		});
		return result;

	}

	public String searchIdaMedicalCases() {
		initSearchLazyList();
		idaMedicalCaseSearchForm.reset();
		return "search";
	}

	public void setLazyModel(LazyDataModel<IdaCase> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public LazyDataModel<IdaCase> getLazyModel() {
		return lazyModel;
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	public GenericDao getGenericDao() {
		return genericDao;
	}

	public void setControllerUtil(ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
	}

	public ControllerUtil getControllerUtil() {
		return controllerUtil;
	}

	public void setFacesUtil(FacesUtil facesUtil) {
		this.facesUtil = facesUtil;
	}

	public FacesUtil getFacesUtil() {
		return facesUtil;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPage() {
		return page;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getBack() {
		return back;
	}

	public Map<String, String> getLocalityMap() {
		if (!facesUtil.isPostBack()) {
			localityMap = controllerUtil.initLocalityMap();
		}
		return localityMap;
	}

	public void setLocalityMap(Map<String, String> localityMap) {
		this.localityMap = localityMap;
	}
}
