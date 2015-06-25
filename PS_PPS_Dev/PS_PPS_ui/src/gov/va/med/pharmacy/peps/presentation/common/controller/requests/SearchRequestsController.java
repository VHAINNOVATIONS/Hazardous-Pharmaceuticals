/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.requests;


import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.SearchDomain;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SessionPreferenceType;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.PepsController;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;
import gov.va.med.pharmacy.peps.service.common.session.UserService;


/**
 * Manage Peps Controller for Manage Peps Tab
 */
@Controller
public class SearchRequestsController extends PepsController {

    private static final Logger LOG = Logger.getLogger(SearchRequestsController.class);

    private static final String URL_SEARCH_DOMAIN_REQ = "/searchDomainRequests.go";
    private static final String MODEL_ATTR_SEARCH_REQ_CRIT = "searchRequestCriteriaVo";

    @Autowired
    private RequestService requestService;
    
    @Autowired
    private ManageRequestsController manageRequestsController;

    @Autowired
    private UserService userService;

    /**
     * 
     * Creates the search criteria
     *
     * @return the search criteria
     */
    @ModelAttribute(MODEL_ATTR_SEARCH_REQ_CRIT)
    public SearchRequestCriteriaVo createSearchRequestCriteriaVo() {
        if (URL_SEARCH_DOMAIN_REQ.equals(request.getServletPath())) {
            return resetSearchRequestCriteria(SearchDomain.DOMAIN);
        } else {
            return resetSearchRequestCriteria(SearchDomain.SIMPLE);
        }
    }

    /**
     * Peps Data Domain Requests and Requests Mapping GET
     * 
     * @param searchRequestCriteriaVo SearchRequestCriteriaVo
     * @param isFirstRun boolean
     * @param model Sprint model
     * @return the user to the search screen
     * @throws ValueObjectValidationException an exception      
     */
    @RequestMapping(value = { URL_SEARCH_DOMAIN_REQ, "/searchRequests.go" }, method = RequestMethod.GET)
    public String searchRequests(
        @ModelAttribute(MODEL_ATTR_SEARCH_REQ_CRIT) SearchRequestCriteriaVo searchRequestCriteriaVo,
        @RequestParam(value = "isFirstRun", required = false) String isFirstRun,
        Model model) throws ValueObjectValidationException {

        pageTrail.clearTrail();

        if (URL_SEARCH_DOMAIN_REQ.equals(request.getServletPath())) {
            pageTrail.addPage("searchDomainRequests", "Search Domain Requests", true);
        } else {
            pageTrail.addPage("searchRequests", "Search Requests", true);
        }

        if (isFirstRun == null) {
            loadPrefsToRequestCrit(searchRequestCriteriaVo);
        } else {
            this.saveLastRequestSearch(searchRequestCriteriaVo);
            model.addAttribute("requests", requestService.search(searchRequestCriteriaVo, getUser()));
            model.addAttribute("printTemplate", getPrintTemplate(searchRequestCriteriaVo.getSearchDomain()));
        }

        return "review.search.spring";

    }
    
    
    /**
     * Starts the multi item edit flow
     *
     * @param multiEditBean the multi item edit bean
     * @return the redirect to the item edit for the first item
     */
    @RequestMapping(value = "editRequests.go", method = RequestMethod.POST)
    public String searchRequests(@ModelAttribute("multiEditBean") MultipleSelectItemBean multiEditBean) {

        return startMultiItemEditFlow(multiEditBean);
    }
    
    /**
     * Starts the multi-edit request flow
     * 
     * @param multipleSelectItemBean the bean with the selections
     * @return request 
     */
    public String startMultiItemEditFlow(MultipleSelectItemBean multipleSelectItemBean) {
        flowInputScope.put("multipleSelectItemBean", multipleSelectItemBean);
        multipleSelectItemBean.setCurrentIndex(0);
        
        return REDIRECT + "/" + multipleSelectItemBean.getItemEntityTypes()[0].toString().toLowerCase() + "/"
            + multipleSelectItemBean.getItemIds()[0] + "/request/" + multipleSelectItemBean.getRequestIds()[0]
            + manageRequestsController.getRequestEvent(multipleSelectItemBean.getItemEntityTypes()[0]);
    }

    /**
     * Reset the {@link SearchRequestCriteriaVo} when the user fetches the page from a GET
     *
     * 
     * @param searchDomain {@link SearchDomain} for which to configure the {@link SearchRequestCriteriaVo}
     * @return the searchCriteria
     */
    private SearchRequestCriteriaVo resetSearchRequestCriteria(SearchDomain searchDomain) {

        SearchRequestCriteriaVo criteria = new SearchRequestCriteriaVo(searchDomain);

        criteria.setAllRequests(true); // default 'All' filter to true at both site
        criteria.setRequestDateUse(false); // default using date range search at local

        if (getUser().isOnlySecondReviewer()) {
            criteria.setAllRequests(false);
            criteria.setMarkedForPepsSecondReview(true);
        }

        return criteria;
    }

    /**
     * 
     * Gets the print template depending on the type of search domain
     *
     * @param searchDomain the search domain
     * @return print template
     */
    private PrintTemplateVo getPrintTemplate(SearchDomain searchDomain) {

        if (searchDomain == SearchDomain.SIMPLE) {
            return DefaultPrintTemplateFactory.defaultRequestSearch();
        } else {
            return DefaultPrintTemplateFactory.defaultDomainRequestSearch();
        }
    }

    /**
     * get the requestService
     * 
     * @return the requestService
     */
    public RequestService getRequestService() {
        return requestService;
    }

    /**
     * sets requestService field
     * 
     * @param requestService the requestService to set
     */
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * get the manageRequestsController
     * 
     * @return the manageRequestsController
     */
    public ManageRequestsController getManageRequestsController() {
        return manageRequestsController;
    }

    /**
     * sets manageRequestsController field
     * 
     * @param manageRequestsController the manageRequestsController to set
     */
    public void setManageRequestsController(ManageRequestsController manageRequestsController) {
        this.manageRequestsController = manageRequestsController;
    }

    /**
     * get the userService for the SearchRequestController.
     * 
     * 
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * sets userService field for the SearchRequestController.
     * 
     * @param userService the userService to set
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * clearRequestSearchPrefs
     *
     * @param criteria SearchRequestCriteriaVo
     */
    protected void clearRequestSearchPrefs(SearchRequestCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        // really this should never execute
        if (criteria.getSearchDomain() == null) {
            LOG.error("This if should never evaluate to true.");
            prefs.remove(SessionPreferenceType.LAST_REQUEST_SEARCH_DOMAIN);
        }

        if (SearchDomain.DOMAIN.equals(criteria.getSearchDomain())) {
            clearDomainRequestPrefs(criteria);
        } else {
            clearRequestPrefs(criteria);
        }
    }

    /**
     * clearDomainRequestPrefs
     *
     * @param criteria SearchRequestCriteriaVo
     */
    protected void clearDomainRequestPrefs(SearchRequestCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_ALL_REQ);
        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_CHANGE_REQ);
        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_MARK_PSR);
        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_NOT_LAST_REV);
        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_2ND);
        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_2ND_MOD);
        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_ADD);
        prefs.remove(SessionPreferenceType.LAST_DOMAIN_REQUEST_UNDER_REVIEW);
    }

    /**
     * clearRequestPrefs
     *
     * @param criteria SearchRequestCriteriaVo
     */
    protected void clearRequestPrefs(SearchRequestCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        prefs.remove(SessionPreferenceType.LAST_REQUEST_ALL_REQ);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_CHANGE_REQ);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_MARK_PSR);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_NOT_LAST_REV);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_PEND_2ND);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_PEND_2ND_MOD);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_PEND_ADD);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_PEND_MOD);
        prefs.remove(SessionPreferenceType.LAST_REQUEST_UNDER_REVIEW);
    }

    /**
     * domainRequest2Prefs
     *
     * @param criteria SearchRequestCriteriaVo
     * @param prefs Map<SessionPreferenceType, String>
     */
    protected void domainRequest2Prefs(SearchRequestCriteriaVo criteria, Map<SessionPreferenceType, String> prefs) {
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_ALL_REQ, Boolean.toString(criteria.isAllRequests()));
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_CHANGE_REQ, Boolean.toString(criteria.isProblemReports()));
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_MARK_PSR,
            Boolean.toString(criteria.isMarkedForPepsSecondReview()));
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_NOT_LAST_REV, Boolean.toString(criteria.isNotLastReviewer()));
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_2ND,
            Boolean.toString(criteria.isPendingSecondApprovalAddition()));
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_2ND_MOD,
            Boolean.toString(criteria.isPendingSecondApprovalModification()));
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_ADD, Boolean.toString(criteria.isPendingAddition()));
        prefs.put(SessionPreferenceType.LAST_DOMAIN_REQUEST_UNDER_REVIEW, Boolean.toString(criteria.isUnderReview()));
    }

    /**
     * request2Prefs
     *
     * @param criteria SearchRequestCriteriaVo
     * @param prefs Map<SessionPreferenceType, String>
     */
    protected void request2Prefs(SearchRequestCriteriaVo criteria, Map<SessionPreferenceType, String> prefs) {
        prefs.put(SessionPreferenceType.LAST_REQUEST_ALL_REQ, Boolean.toString(criteria.isAllRequests()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_CHANGE_REQ, Boolean.toString(criteria.isProblemReports()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_MARK_PSR, Boolean.toString(criteria.isMarkedForPepsSecondReview()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_NOT_LAST_REV, Boolean.toString(criteria.isNotLastReviewer()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_PEND_2ND, Boolean.toString(criteria.isPendingSecondApprovalAddition()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_PEND_2ND_MOD,
            Boolean.toString(criteria.isPendingSecondApprovalModification()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_PEND_ADD, Boolean.toString(criteria.isPendingAddition()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_PEND_MOD, Boolean.toString(criteria.isPendingModification()));
        prefs.put(SessionPreferenceType.LAST_REQUEST_UNDER_REVIEW, Boolean.toString(criteria.isUnderReview()));
    }

    /**
     * prefs2DomainRequest
     *
     * @param criteria SearchRequestCriteriaVo
     * @param prefs Map<SessionPreferenceType, String>
     */
    protected void prefs2DomainRequest(SearchRequestCriteriaVo criteria, Map<SessionPreferenceType, String> prefs) {
        String value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_CHANGE_REQ);
        boolean on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setProblemReports(on);

        value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_MARK_PSR);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setMarkedForPepsSecondReview(on);

        value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_NOT_LAST_REV);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setNotLastReviewer(on);

        value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_2ND);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setPendingSecondApprovalAddition(on);

        value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_2ND_MOD);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setPendingSecondApprovalModification(on);

        value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_PEND_ADD);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setPendingAddition(on);

        value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_UNDER_REVIEW);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setUnderReview(on);

        value = prefs.get(SessionPreferenceType.LAST_DOMAIN_REQUEST_ALL_REQ);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setAllRequests(on);
    }

    /**
     * defaultDomainRequestCriteria
     *
     * @param criteria SearchRequestCriteriaVo
     */
    protected void defaultDomainRequestCriteria(SearchRequestCriteriaVo criteria) {
        boolean rv = false;

        rv = criteria.isProblemReports() || criteria.isMarkedForPepsSecondReview() || criteria.isNotLastReviewer();
        rv = rv || criteria.isPendingSecondApprovalAddition() || criteria.isPendingSecondApprovalModification();
        rv = rv || criteria.isPendingAddition() || criteria.isUnderReview();

        if (rv) {
            return;
        }

        criteria.setAllRequests(true);
    }

    /**
     * defaultRequestCriteria
     *
     * @param criteria SearchRequestCriteriaVo
     */
    protected void defaultRequestCriteria(SearchRequestCriteriaVo criteria) {
        boolean rv = false;

        rv = criteria.isProblemReports() || criteria.isMarkedForPepsSecondReview() || criteria.isNotLastReviewer();
        rv = rv || criteria.isPendingSecondApprovalAddition() || criteria.isPendingSecondApprovalModification();
        rv = rv || criteria.isPendingAddition() || criteria.isPendingModification() || criteria.isUnderReview();

        if (rv) {
            return;
        }

        criteria.setAllRequests(true);
    }

    /**
     * prefs2request
     *
     * @param criteria SearchRequestCriteriaVo
     * @param prefs Map<SessionPreferenceType, String>
     */
    protected void prefs2Request(SearchRequestCriteriaVo criteria, Map<SessionPreferenceType, String> prefs) {
        String value = prefs.get(SessionPreferenceType.LAST_REQUEST_CHANGE_REQ);
        boolean on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setProblemReports(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_MARK_PSR);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setMarkedForPepsSecondReview(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_NOT_LAST_REV);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setNotLastReviewer(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_PEND_2ND);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setPendingSecondApprovalAddition(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_PEND_2ND_MOD);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setPendingSecondApprovalModification(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_PEND_ADD);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setPendingAddition(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_PEND_MOD);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setPendingModification(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_UNDER_REVIEW);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setUnderReview(on);

        value = prefs.get(SessionPreferenceType.LAST_REQUEST_ALL_REQ);
        on = StringUtils.isEmpty(value) ? false : Boolean.valueOf(value);
        criteria.setAllRequests(on);
    }

    /**
     * loadPrefsToRequestCrit
     *
     * @param criteria SearchRequestCriteriaVo
     */
    protected void loadPrefsToRequestCrit(SearchRequestCriteriaVo criteria) {

        if (!this.canProceedWithPrefsOperations(criteria)) {
            return;
        }

        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        if (SearchDomain.DOMAIN.equals(criteria.getSearchDomain())) {
            this.prefs2DomainRequest(criteria, prefs);
            this.defaultDomainRequestCriteria(criteria);
        } else {
            this.prefs2Request(criteria, prefs);
            this.defaultRequestCriteria(criteria);
        }
    }

    /**
     * saveRequestCritToPrefs
     *
     * @param criteria SearchRequestCriteriaVo
     */
    protected void saveRequestCritToPrefs(SearchRequestCriteriaVo criteria) {
        Map<SessionPreferenceType, String> prefs = getUser().getSessionPreferences();

        if (SearchDomain.DOMAIN.equals(criteria.getSearchDomain())) {
            this.domainRequest2Prefs(criteria, prefs);
        } else {
            this.request2Prefs(criteria, prefs);
        }
    }

    /**
     * canProceedWithPrefsOperations
     *
     * @param criteria SearchRequestCriteriaVo
     * @return boolean
     */
    protected boolean canProceedWithPrefsOperations(SearchRequestCriteriaVo criteria) {
        if (criteria == null) {
            LOG.warn("Unable to save last search request criteria to settings. SearchRequestCriteria object is null.");

            return false;
        }

        if (getUser() == null) {
            LOG.warn("Unable to save last search request criteria to settings. User object is null.");

            return false;
        }

        if (criteria.getSearchDomain() == null) {
            LOG.warn("Unable to save last search request criteria to settings. Search domain value is null.");

            return false;
        }

        return true;
    }

    /**
     * saveLastRequestSearch save the values from the criteria to search preferences.
     *
     * @param criteria search request criteria to save parts of
     */
    protected void saveLastRequestSearch(SearchRequestCriteriaVo criteria) {

        if (!this.canProceedWithPrefsOperations(criteria)) {
            return;
        }

        clearRequestSearchPrefs(criteria);
        saveRequestCritToPrefs(criteria);

        try {
            userService.updatePreferences(getUser());
        } catch (ValueObjectValidationException e) {
            LOG.error("Unable to save user search domain preferences.", e);
        }
    }

}
