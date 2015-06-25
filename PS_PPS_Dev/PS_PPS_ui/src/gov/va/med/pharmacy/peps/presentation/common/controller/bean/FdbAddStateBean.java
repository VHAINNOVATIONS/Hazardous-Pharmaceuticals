/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.bean;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionResultVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoAddVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAutoUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.FdbUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;


/**
 * FdbAddStateBean's brief summary
 * 
 * Details of FdbAddStateBean's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Component("fdbAddStateBean")
@Scope("prototype")
public class FdbAddStateBean {

    private static final String FDB_ADD = "/fdbAdd.go";
    private static final String FDB_SEARCH = "/fdbSearch.go";

    private boolean searchState = false;
    private List<FdbAddVo> pendingList = new ArrayList<FdbAddVo>();
    private List<FdbUpdateVo> updateList = new ArrayList<FdbUpdateVo>();
    private List<FdbAutoAddVo> autoAddList = new ArrayList<FdbAutoAddVo>();
    private List<FdbAutoUpdateVo> autoUpdateList = new ArrayList<FdbAutoUpdateVo>();
    private boolean fdbSearchMode;
    private boolean displayTable = false;
    private boolean hideFromPPS = false;
    private FDBSearchOptionResultVo details;
    private List<ProductVo> closeProductsMatches;
    private List<FdbAddVo> matchNDCList;
    private ProductVo product;
    private List<FDBSearchOptionResultVo> csvSearchResults = new ArrayList<FDBSearchOptionResultVo>();
    private List<String> selectedNdcs;

    /**
     * isSearchState
     * @return the searchState
     */
    public boolean isSearchState() {
        return searchState;
    }

    /**
     * setSearchState
     * @param searchState the searchState to set
     */
    public void setSearchState(boolean searchState) {
        this.searchState = searchState;
    }

    /**
     * getPendingList
     * @return the pendingList
     */
    public List<FdbAddVo> getPendingList() {
        return pendingList;
    }

    /**
     * setPendingList
     * @param pendingList the pendingList to set
     */
    public void setPendingList(List<FdbAddVo> pendingList) {
        this.pendingList = pendingList;
    }

    /**
     * isFdbSearchMode
     * @return the fdbSearchMode
     */
    public boolean isFdbSearchMode() {
        return fdbSearchMode;
    }

    /**
     * setFdbSearchMode
     * @param fdbSearchMode the fdbSearchMode to set
     */
    public void setFdbSearchMode(boolean fdbSearchMode) {
        this.fdbSearchMode = fdbSearchMode;
    }
    
    /**
     * isHideFromPPS
     * @return the hideFromPPS
     */
    public boolean isHideFromPPS() {
        return hideFromPPS;
    }

    /**
     * setHideFromPPS
     * @param hideFromPPS the hideFromPPS to set
     */
    public void setHideFromPPS(boolean hideFromPPS) {
        this.hideFromPPS = hideFromPPS;
    }

    /**
     * isDisplayTable
     * @return the displayTable
     */
    public boolean isDisplayTable() {
        return displayTable;
    }

    /**
     * setDisplayTable
     * @param displayTable the displayTable to set
     */
    public void setDisplayTable(boolean displayTable) {
        this.displayTable = displayTable;
    }

    /**
     * Returns the printTemplate based on the boolean value of the fdbSearchMode field
     * 
     * @return the printTemplate
     */
    public PrintTemplateVo getPrintTemplate() {

        return DefaultPrintTemplateFactory.fdbAddSearch(isFdbSearchMode());
    }

    /**
     * getDetails
     * @return the details
     */
    public FDBSearchOptionResultVo getDetails() {
        return details;
    }

    /**
     * setDetails
     * @param details the details to set
     */
    public void setDetails(FDBSearchOptionResultVo details) {
        this.details = details;
    }

    /**
     * getCloseProductsMatches
     * @return the closeProductsMatches
     */
    public List<ProductVo> getCloseProductsMatches() {
        return closeProductsMatches;
    }

    /**
     * setCloseProductsMatches
     * @param closeProductsMatches the closeProductsMatches to set
     */
    public void setCloseProductsMatches(List<ProductVo> closeProductsMatches) {
        this.closeProductsMatches = closeProductsMatches;
    }

    /**
     * getMatchNDCList
     * @return the matchNDCList
     */
    public List<FdbAddVo> getMatchNDCList() {
        return matchNDCList;
    }

    /**
     * setMatchNDCList
     * @param matchNDCList the matchNDCList to set
     */
    public void setMatchNDCList(List<FdbAddVo> matchNDCList) {
        this.matchNDCList = matchNDCList;
    }

    /**
     * getProduct
     * @return the product
     */
    public ProductVo getProduct() {
        return product;
    }

    /**
     * setProduct
     * @param product the product to set
     */
    public void setProduct(ProductVo product) {
        this.product = product;
    }

    /**
     * getCsvSearchResults
     * @return the csvSearchResults
     */
    public List<FDBSearchOptionResultVo> getCsvSearchResults() {
        return csvSearchResults;
    }

    /**
     * setCsvSearchResults
     * @param csvSearchResults the csvSearchResults to set
     */
    public void setCsvSearchResults(List<FDBSearchOptionResultVo> csvSearchResults) {
        this.csvSearchResults = csvSearchResults;
    }

    /**
     * 
     * Returns the URL dependent on whether or not the page is in fdb search mode.
     *
     * @return String
     */
    public String getFdbAddUrl() {
        if (isFdbSearchMode()) {
            return FDB_SEARCH;
        } else {
            return FDB_ADD;
        }
    }

    /**
     * getSelectedNdcs
     * @return the selectedNdcs
     */
    public List<String> getSelectedNdcs() {
        return selectedNdcs;
    }

    /**
     * setSelectedNdcs
     * @param selectedNdcs the selectedNdcs to set
     */
    public void setSelectedNdcs(List<String> selectedNdcs) {
        this.selectedNdcs = selectedNdcs;
    }

    /**
     * getUpdateList
     * @return the updateList
     */
    public List<FdbUpdateVo> getUpdateList() {
        return updateList;
    }

    /**
     * setUpdateList
     * @param updateList the updateList to set
     */
    public void setUpdateList(List<FdbUpdateVo> updateList) {
        this.updateList = updateList;
    }

    /**
     * getAutoAddList
     * @return the autoAddList
     */
    public List<FdbAutoAddVo> getAutoAddList() {
        return autoAddList;
    }

    /**
     * setAutoAddList
     * @param autoAddList the autoAddList to set
     */
    public void setAutoAddList(List<FdbAutoAddVo> autoAddList) {
        this.autoAddList = autoAddList;
    }

    /**
     * getAutoUpdateList
     * @return the autoUpdateList
     */
    public List<FdbAutoUpdateVo> getAutoUpdateList() {
        return autoUpdateList;
    }

    /**
     * setAutoUpdateList
     * @param autoUpdateList the autoUpdateList to set
     */
    public void setAutoUpdateList(List<FdbAutoUpdateVo> autoUpdateList) {
        this.autoUpdateList = autoUpdateList;
    }

    /**
     * getButtonKeys
     * @return the buttonKeys
     */
    public String[] getButtonKeys() {
        if (fdbSearchMode) {
            return new String[] { "Match Results" };
        } else {
            return new String[] { "button.match.results", "button.delete" };
        }
    }

    /**
     * getButtonActions
     * @return the buttonActions
     */
    public String[] getButtonActions() {
        if (fdbSearchMode) {
            return new String[] { "matchResults.go" };
        } else {
            return new String[] { "matchResults.go", "fdbAddDelete.go" };
        }
    }

    /**
     * getButtonMethods
     * @return the buttonMethods
     */
    public String[] getButtonMethods() {
        if (!fdbSearchMode) {
            return new String[] { "POST", "POST" };
        } else {
            return new String[] { "POST" };
        }
    }

    /**
     * getAddlButtonKeys
     * @return the buttonKeys
     */
    public String[] getAddlButtonKeys() {
        if (fdbSearchMode) {
            return new String[] { "button.hide.existing.pps" };
        } else {
            return new String[] {};
        }
    }

    /**
     * getAddlButtonActions
     * @return the buttonActions
     */
    public String[] getAddlButtonActions() {
        if (fdbSearchMode) {
            return new String[] { "hideExistingPPS.go" };
        } else {
            return new String[] {};
        }
    }

    /**
     * getAddlButtonMethods
     * @return the buttonMethods
     */
    public String[] getAddlButtonMethods() {
        if (fdbSearchMode) {
            return new String[] { "POST" };
        } else {
            return new String[] {};
        }
    }
}
