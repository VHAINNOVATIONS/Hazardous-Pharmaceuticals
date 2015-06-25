/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Date;


/**
 * Search criteria for a request
 */
public class SearchRequestCriteriaVo extends ValueObject {

    /** ONE_DAY */
    public static final long ONE_DAY = 86400000; // Number of milliseconds in one day
    private static final long serialVersionUID = 1L;
    private static final long SEVEN_DAYS = 604800000; // Number of milliseconds in seven days

    private boolean allRequests = false;
    private boolean requestDateUse = false;
    private SearchDomain searchDomain;
    private boolean localSearch = false;
    private LocalRequestFilter localRequest = LocalRequestFilter.ALL_ITEMS;

    // search fields used at local
    private Date searchRequestStartDate;
    private Date searchRequestEndDate;
    private boolean localRequests;
    private String siteNumber;

    // search fields used at national
    private boolean underReview = false;
    private boolean markedForPepsSecondReview = false;
    private boolean problemReport = false;
    private boolean pendingAddition = false;
    private boolean pendingModification = false;
    private boolean pendingSecondApprovalAddition = false;
    private boolean pendingSecondApprovalModification = false;
    private boolean notLastReviewer = false;

    /**
     * Set the {@link SearchDomain#SIMPLE}
     */
    public SearchRequestCriteriaVo() {
        this(SearchDomain.SIMPLE);
    }

    /**
     * Set the {@link SearchDomain}
     * 
     * @param searchDomain {@link SearchDomain}
     */
    public SearchRequestCriteriaVo(SearchDomain searchDomain) {
        setSearchDomain(searchDomain);

        setSearchRequestEndDate(new Date());
        setSearchRequestStartDate(new Date(getSearchRequestEndDate().getTime() - SEVEN_DAYS));
    }

    /**
     * Description
     * @return isUnderReview
     */
    public boolean isUnderReview() {
        return underReview;
    }

    /**
     * Description
     * @param underReview flag to mark a request as under review
     */
    public void setUnderReview(boolean underReview) {
        this.underReview = underReview;
    }

    /**
     * Description
     * @return isMarkedForPepsSecondReview
     */
    public boolean isMarkedForPepsSecondReview() {
        return markedForPepsSecondReview;
    }

    /**
     * Description
     * @param markedForPepsSecondReview flag to mark a request for PEPS second review
     */
    public void setMarkedForPepsSecondReview(boolean markedForPepsSecondReview) {
        this.markedForPepsSecondReview = markedForPepsSecondReview;
    }

    /**
     * Description
     * @return isProblemReport
     */
    public boolean isProblemReports() {
        return problemReport;
    }

    /**
     * Description
     * @param probReport flag to retrieve problem reports
     */
    public void setProblemReports(boolean probReport) {
        this.problemReport = probReport;
    }

    /**
     * Description
     * @return isAllRequests
     */
    public boolean isRequestDateUse() {
        return requestDateUse;
    }

    /**
     * Description
     * @param requestDateUse for requestDateUse
     */
    public void setRequestDateUse(boolean requestDateUse) {
        this.requestDateUse = requestDateUse;
    }

    /**
     * Description
     * @return isAllRequests
     */
    public boolean isAllRequests() {
        return allRequests;
    }

    /**
     * Description
     * @param all for all requests
     */
    public void setAllRequests(boolean all) {
        this.allRequests = all;
    }

    /**
     * Description
     * @return searchRequestStartDate
     */
    public Date getSearchRequestStartDate() {
        return searchRequestStartDate;
    }

    /**
     * Description
     * @param searchStartDate date to start the search
     */
    public void setSearchRequestStartDate(Date searchStartDate) {
        this.searchRequestStartDate = searchStartDate;
    }

    /**
     * Description
     * @return searchRequestEndDate
     */
    public Date getSearchRequestEndDate() {
        return searchRequestEndDate;
    }

    /**
     * Description
     * @param searchEndDate date to end the search
     */
    public void setSearchRequestEndDate(Date searchEndDate) {
        this.searchRequestEndDate = searchEndDate;
    }

    /**
     * Description
     * 
     * @return pendingAddition property
     */
    public boolean isPendingAddition() {
        return pendingAddition;
    }

    /**
     * Description
     * 
     * @param pendingAddition pendingAddition property
     */
    public void setPendingAddition(boolean pendingAddition) {
        this.pendingAddition = pendingAddition;
    }

    /**
     * Description
     * 
     * @return pendingModification property
     */
    public boolean isPendingModification() {
        return pendingModification;
    }

    /**
     * Description
     * 
     * @param pendingModification pendingModification property
     */
    public void setPendingModification(boolean pendingModification) {
        this.pendingModification = pendingModification;
    }

    /**
     * Description
     * 
     * @return pendingSecondApprovalAddition property
     */
    public boolean isPendingSecondApprovalAddition() {
        return pendingSecondApprovalAddition;
    }

    /**
     * Description
     * 
     * @param pendingSecondApprovalAddition pendingSecondApprovalAddition property
     */
    public void setPendingSecondApprovalAddition(boolean pendingSecondApprovalAddition) {
        this.pendingSecondApprovalAddition = pendingSecondApprovalAddition;
    }

    /**
     * Description
     * 
     * @return pendingSecondApprovalModification property
     */
    public boolean isPendingSecondApprovalModification() {
        return pendingSecondApprovalModification;
    }

    /**
     * Description
     * 
     * @param pendingSecondApprovalModification pendingSecondApprovalModification property
     */
    public void setPendingSecondApprovalModification(boolean pendingSecondApprovalModification) {
        this.pendingSecondApprovalModification = pendingSecondApprovalModification;
    }

    /**
     * isNotLastReviewer
     * @return the notLastReviewer
     */
    public boolean isNotLastReviewer() {

        return notLastReviewer;
    }

    /**
     * setNotLastReviewer
     * @param notLastReviewer the notLastReviewer to set
     */
    public void setNotLastReviewer(boolean notLastReviewer) {

        this.notLastReviewer = notLastReviewer;
    }

    /**
     * Description
     * 
     * @return searchDomain property
     */
    public SearchDomain getSearchDomain() {
        return searchDomain;
    }

    /**
     * Description
     * 
     * @param searchDomain searchDomain property
     */
    public void setSearchDomain(SearchDomain searchDomain) {
        this.searchDomain = searchDomain;
    }

    /**
     * Description
     * 
     * @return localSearch property
     */
    public boolean isLocalSearch() {
        return localSearch;
    }

    /**
     * Description
     * 
     * @param localSearch localSearch property
     */
    public void setLocalSearch(boolean localSearch) {
        this.localSearch = localSearch;
    }

    /**
     * Description
     * 
     * @return localRequests property
     */
    public boolean isLocalRequests() {
        return localRequests;
    }

    /**
     * Description
     * 
     * @param localRequests localRequests property
     */
    public void setLocalRequests(boolean localRequests) {
        this.localRequests = localRequests;
    }

    /**
     * Description
     * 
     * @return siteNumber property
     */
    public String getSiteNumber() {
        return siteNumber;
    }

    /**
     * Description
     * 
     * @param siteNumber siteNumber property
     */
    public void setSiteNumber(String siteNumber) {
        this.siteNumber = trimToEmpty(siteNumber);
    }

    /**
     * Description
     * 
     * @return localRequest property
     */
    public LocalRequestFilter getLocalRequest() {
        return localRequest;
    }

    /**
     * Description
     * 
     * @param localRequest localRequest property
     */
    public void setLocalRequest(LocalRequestFilter localRequest) {
        this.localRequest = localRequest;
    }

    /**
     * Description
     * 
     * Is this a domain search?
     * 
     * @return domain search property
     */
    public boolean isDomainSearch() {
        return this.searchDomain.isDomainSearch();
    }

}
