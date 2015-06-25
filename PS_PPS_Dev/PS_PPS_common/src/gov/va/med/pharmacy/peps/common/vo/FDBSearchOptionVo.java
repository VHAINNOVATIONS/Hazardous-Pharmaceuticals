/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;

import org.springframework.stereotype.Component;


/**
 * The FDBSearchOptionVo is used to contain the criteria and search results for an NDC
 */
@Component ("fdbSearchOptionVo")
public class FDBSearchOptionVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    
    private FDBSearchOptionType fdbSearchOptionType;
    private String fdbSearchString;
    private Collection<FDBSearchOptionResultVo> searchOptionResultsVo = null;
    private String errorMessage;

    /**
     * Empty constructor.
     */
    public FDBSearchOptionVo() {
        super();
    }
    
    /**
     * Create a new exception with the arguments in the constructor.
     * 
     * @param fdbSearchOptionType  The type of search to perform
     * @param fdbSearchString  The value to search for in FDB. 
     */
    public FDBSearchOptionVo(FDBSearchOptionType fdbSearchOptionType, String fdbSearchString) {
        this.fdbSearchOptionType = fdbSearchOptionType;
        this.fdbSearchString = fdbSearchString;
    }

    /**
     * Returns the Search Option Result set
     * 
     * @return The Result set stored in the object
     */
    public Collection<FDBSearchOptionResultVo> getSearchOptionResults() {
        return searchOptionResultsVo;
    }
    
    /**
     * Returns the Error Message
     * 
     * @return The Error Message stored in the object
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * Returns the Search Results Set Name Value
     * 
     * @param searchOptionResults  The Search Result Set Value to set in this object
     */
    public void setSearchOptionResults(Collection<FDBSearchOptionResultVo> searchOptionResults) {
        this.searchOptionResultsVo = searchOptionResults;
    }
    
    /**
     * Returns the Error Message Values
     *  
     * @param errorMessage The errorMessage returned from the Search Results
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * getFdbSearchOptionType.
     * @return fdbSearchOptionType property
     */
    public FDBSearchOptionType getFdbSearchOptionType() {
        return fdbSearchOptionType;
    }

    /**
     * setFdbSearchOptionType.
     * @param fdbSearchOptionType fdbSearchOptionType property
     */
    public void setFdbSearchOptionType(FDBSearchOptionType fdbSearchOptionType) {
        this.fdbSearchOptionType = fdbSearchOptionType;
    }

    /**
     * getFdbSearchString.
     * @return fdbSearchString property
     */
    public String getFdbSearchString() {
        return fdbSearchString;
    }

    /**
     * setFdbSearchString.
     * @param fdbSearchString fdbSearchString property
     */
    public void setFdbSearchString(String fdbSearchString) {
        this.fdbSearchString = fdbSearchString;
    }    
}
