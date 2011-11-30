package com.green.ida.controller.search.ida.call;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;

import com.green.base.entity.generic.dao.GenericDao;
import com.green.base.entity.living.human.HumanBeing;
import com.green.base.entity.living.human.Name;
import com.green.base.entity.living.human.related.address.ContactDetails;
import com.green.ida.controller.util.ControllerUtil;
import com.green.ida.entity.animal.idacase.call.register.pojos.CallStatus;
import com.green.ida.entity.animal.idacase.call.register.pojos.PhoneCall;
import com.green.jsf.faces.util.FacesUtil;
import java.util.Comparator;
import javax.annotation.PostConstruct;

@Named
@Scope("session")
public class IdaCallSearchController implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -123957203763861798L;
    private static transient Logger LOGGER = Logger.getLogger(IdaCallSearchController.class);
    private IdaCallSearchForm idaCallSearchForm = new IdaCallSearchForm();
    private LazyDataModel<PhoneCall> lazyModel;
    @Inject
    private GenericDao genericDao;
    @Inject
    private ControllerUtil controllerUtil;
    @Inject
    private FacesUtil facesUtil;
    private Integer page = 0;
    private String back;
    private Integer count;
    private IdaCallSearchQueryBuilder qBuilder;
    private Map<String, String> statusMap;
    
    public void setIdaCallSearchForm(
            IdaCallSearchForm idaMedicalCaseSearchForm) {
        this.idaCallSearchForm = idaMedicalCaseSearchForm;
    }
    
    public IdaCallSearchForm getIdaCallSearchForm() {
        return idaCallSearchForm;
    }
    
    @PostConstruct
    public void initComponents() {
        
        this.statusMap = CallStatus.initCallStatusMapForSearch();
        
    }

    // @PostConstruct
    public void initSearchLazyList() {
        qBuilder = new IdaCallSearchQueryBuilder(
                idaCallSearchForm);
        qBuilder.buildQueryParts();
        // if (count == null) {
        if (qBuilder.getIsLike()) {
            count = genericDao.countDistinctOnRootWhereAllMatchAndMultiLike(
                    Long.class, PhoneCall.class, qBuilder.getLikeColumnNameCsv(),
                    qBuilder.getLikeColumnValuesArray(),
                    qBuilder.getCsvColumnNames(),
                    qBuilder.getColumnValuesArray()).intValue();
        } else {
            count = genericDao.countDistinctOnRootWhereAllMatch(Long.class,
                    PhoneCall.class, qBuilder.getCsvColumnNames(),
                    qBuilder.getColumnValuesArray()).intValue();
        }
        // }
        lazyModel = new LazyDataModel<PhoneCall>(count) {

            /**
             * 
             */
            private static final long serialVersionUID = 7144873906078340400L;
            
            @Override
            public List<PhoneCall> fetchLazyData(int first, int pageSize) {
                try {
                    List<Object[]> searchResult;
                    if (back != null && back.equals("back")) {
                        first = pageSize * (page - 1);
                        back = null;
                    }
                    page = (first + pageSize) / pageSize;
                    if (qBuilder.getIsLike()) {
                        searchResult = genericDao.multiSelectWithLimitWhereAllMatchAndMultiLike(
                                Object[].class, PhoneCall.class, first,
                                pageSize, qBuilder.getMultiselectColumnNames(),
                                qBuilder.getLikeColumnNameCsv(),
                                qBuilder.getLikeColumnValuesArray(),
                                qBuilder.getCsvColumnNames(), qBuilder.getColumnValuesArray());
                    } else {
                        searchResult = genericDao.multiSelectWithLimitWhereAllMatch(
                                Object[].class, PhoneCall.class, first,
                                pageSize, qBuilder.getMultiselectColumnNames(),
                                qBuilder.getCsvColumnNames(), qBuilder.getColumnValues().toArray(
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
    
    private List<PhoneCall> processSearchResultObjectArray(
            List<Object[]> searchresult) {
        List<PhoneCall> result = new ArrayList<PhoneCall>();
        for (Object[] res : searchresult) {
            PhoneCall phoneCall = new PhoneCall();
            HumanBeing complainer = new HumanBeing();
             complainer.setContactDetails(new ContactDetails());
            phoneCall.setComplainer(complainer);
           
            phoneCall.setId((Long) res[0]);
            phoneCall.getComplainer().getContactDetails().setMobile(res[1].toString());
            phoneCall.setComplainDate((Date) res[2]);
            phoneCall.getComplainer().setName((Name)res[3]);
            
            phoneCall.setCallStatus(CallStatus.getCallStatus(res[4].toString()));
            phoneCall.setAttendedDate((Date)res[5]);
            
            result.add(phoneCall);
        }
        Collections.sort(result, new Comparator<PhoneCall>() {
            
            @Override
            public int compare(PhoneCall o1, PhoneCall o2) {
                return o2.getComplainDate().compareTo(o1.getComplainDate());
            }
        });
        return result;
        
    }
    
    public String searchIdaCallCases() {
        initSearchLazyList();
        idaCallSearchForm.reset();
        return "search";
    }
    
    
    public void setLazyModel(LazyDataModel<PhoneCall> lazyModel) {
        this.lazyModel = lazyModel;
    }
    
    public LazyDataModel<PhoneCall> getLazyModel() {
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
    
    public Map<String, String> getStatusMap() {
        if (!facesUtil.isPostBack()) {
            statusMap = CallStatus.initCallStatusMapForSearch();
        }
        return statusMap;
    }
    
    public void setStatusMap(Map<String, String> statusMap) {
        this.statusMap = statusMap;
    }
}
