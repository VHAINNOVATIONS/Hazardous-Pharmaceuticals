/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;




/**
 * Type of Search to PERFORM
 */


public enum FDBSearchOptionType {
    
    /**
     * ALL 
     */
    ALL,
    
    /**
     * NDC_SEARCH
     */
    NDC_SEARCH,
    
    /**
     * LABEL_SEARCH
     */
    LABEL_SEARCH,
    
    /**
     * GENERIC_SEARCH.
     */
    GENERIC_SEARCH,
    
    /**
     * GCNSEQNO_SEARCH.
     */
    GCNSEQNO_SEARCH,
    
    /**
     * LABEL_GENERIC_SEARCH.
     */
    DRUG_CLASS,
    
    /**
     * LABEL_GENERIC_SEARCH.
     */
    LABEL_GENERIC_SEARCH;
    
    
  
    
    /**
     * isNdcSearch.
     * @return boolean true if this FDBSearchOptionType is NDC_SEARCH
     */
    public boolean isNdcSearch() {
        return NDC_SEARCH.equals(this);
    }
    
    /**
     * isLabelSearch.
     * @return boolean true if this FDBSearchOptionType is LABEL_SEARCH
     */
    public boolean isLabelSearch() {
        return LABEL_SEARCH.equals(this);
    }
    
    /**
     * isGenericSearch.
     * @return boolean true if this FDBSearchOptionType is GENERIC_SEARCH
     */
    public boolean isGenericSearch() {
        return GENERIC_SEARCH.equals(this);
    }
    
    /**
     * isGcnSeqNoSearch.
     * @return boolean true if this FDBSearchOptionType is GCNSEQNO_SEARCH
     */
    public boolean isGcnSeqNoSearch() {
        return GCNSEQNO_SEARCH.equals(this);
    }
    
    /**
     * isLabelGenericSearch.
     * @return boolean true if this FDBSearchOptionType is LABEL_GENERIC_SEARCH
     */
    public boolean isLabelGenericSearch() {
        return LABEL_GENERIC_SEARCH.equals(this);
    }
    
    /**
     * isAllSearch.
     * @return boolean true if this FDBSearchOptionType is ALL
     */
    public boolean isAllSearch() {
        return ALL.equals(this);
    }

    /**
     * isDrugClassSearch
     *
     * @return boolean
     */
    public boolean isDrugClassSearch() {
        return DRUG_CLASS.equals(this);
    }
    
    
}
