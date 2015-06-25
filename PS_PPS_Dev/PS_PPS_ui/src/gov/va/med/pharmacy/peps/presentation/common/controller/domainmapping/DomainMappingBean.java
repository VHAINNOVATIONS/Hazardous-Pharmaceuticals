/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.domainmapping;


import java.util.Date;
import java.util.Map;

import gov.va.med.pharmacy.peps.common.vo.FdbDomainType;


/**
 * Class DomainMappingBean comments.
 */
public class DomainMappingBean {

    private FdbDomainType domainChoice;
    private Map<String, Long> associationMap;
    private String filterString;
    private Date filterDate;
    private boolean searchAfterSave = false;

    /**
     * Method getDomainChoice comments.
     * 
     * @return one of 5 domain choices.
     */
    public FdbDomainType getDomainChoice() {
        return domainChoice;
    }

    /**
     * Method setDomainChoice comments.
     * @param domainChoice is one of 5 domain choices.
     */
    public void setDomainChoice(FdbDomainType domainChoice) {
        this.domainChoice = domainChoice;
    }

    /**
     * Method getAssociationMap comments.
     * @return association map
     */
    public Map<String, Long> getAssociationMap() {
        return associationMap;
    }

    /**
     * Method setAssociationMap comments.
     * @param associationMap 
     */
    public void setAssociationMap(Map<String, Long> associationMap) {
        this.associationMap = associationMap;
    }

    /**
     * Method getFilterString comments.
     * @return filter date.
     */
    public String getFilterString() {
        return filterString;
    }

    /**
     * Method setFilterString comments.
     * @param filterString 
     */
    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    /**
     * Method getFilterDate comments.
     * @return filterDate date.
     */
    public Date getFilterDate() {
        return filterDate;
    }

    /**
     * Method setFilterDate comments.
     * @param filterDate 
     */
    public void setFilterDate(Date filterDate) {
        this.filterDate = filterDate;
    }

    /**
     * @return the searchAfterSave
     */
    public boolean isSearchAfterSave() {
        return searchAfterSave;
    }

    /**
     * @param searchAfterSave the searchAfterSave to set
     */
    public void setSearchAfterSave(boolean searchAfterSave) {
        this.searchAfterSave = searchAfterSave;
    }

}
