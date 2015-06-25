/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.requests;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.BusinessRuleException;
import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.RequestCompletedException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestRejectionReason;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.item.AbstractManageItemController;
import gov.va.med.pharmacy.peps.presentation.common.controller.item.ManageItemController;
import gov.va.med.pharmacy.peps.presentation.common.controller.item.ModificationSummary;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.RequestService;
import gov.va.med.pharmacy.peps.service.common.utility.requestmachine.RequestStateMachine;


/**
 * Controller for managing all request approvals/rejections/etc.
 */
@Controller("manageRequestsController")
@RequestMapping(value = "{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}/request/{"
                        + ControllerConstants.REQUEST_ID + "}")
public class ManageRequestsController extends AbstractManageItemController {

    @Autowired
    private ManagedItemService managedItemService;

    @Autowired
    private ManageItemController manageItemController;

    @Autowired
    private RequestService requestService;

    /**
     * 
     * Retrieves all types of requests for viewing and approving
     *
     * @param entityType The entityType of the item
     * @param itemId The id of the item associated with the request 
     * @param requestId The id of the request to retrieve
     * @param tabKey The tab to display
     * @param model The spring model
     * @return The request view
     * @throws ItemNotFoundException and exception
     * @throws RequestCompletedException if the request was completed by another user
     * @throws ValueObjectValidationException 
     */
    @RequestMapping(value = { "/manageRequest.go", "/manageDomainRequest.go" }, method = RequestMethod.GET)
    public String retrieveRequests(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.REQUEST_ID) String requestId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tabKey, Model model)
        throws ItemNotFoundException, RequestCompletedException, ValueObjectValidationException {

        updateMultiEditIndex(itemId);

        pageTrail.addPage("manageRequest", "Edit " + entityType.getName() + " Request", true);

        EditManagedItemBean editManagedItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());
        RequestVo mainRequest = null;

        ManagedItemVo item = null;

        if (editManagedItemBean == null
            || (editManagedItemBean != null && !editManagedItemBean.getMainRequest().getId().equals(requestId))) {

            editManagedItemBean = new EditManagedItemBean();

            mainRequest = requestService.retrieve(requestId);

            editManagedItemBean.setMainRequest(mainRequest);
            item = retrieve(itemId, entityType, getUser(), mainRequest);
            editManagedItemBean.setItem(item);
            editManagedItemBean.setOriginalItem((ManagedItemVo) editManagedItemBean.getItem().copy());
            flowScope.put(EditManagedItemBean.class.getName(), editManagedItemBean);

        } else {
            item = editManagedItemBean.getItem();
            mainRequest = editManagedItemBean.getMainRequest();
        }

        // Setup the correct tab
        String newTabKey = selectTab(entityType, mainRequest, tabKey);

        model.addAttribute(ControllerConstants.ITEM_KEY, editManagedItemBean.getItem());
        model.addAttribute("oldItem", editManagedItemBean.getOriginalItem());
        model.addAttribute("itemType", entityType);
        model.addAttribute(ControllerConstants.MAIN_REQUEST, mainRequest);
        model.addAttribute("mainRequestSize", mainRequest.getRequestDetails().size());
        model.addAttribute(ControllerConstants.TAB_KEY, newTabKey);

        // tab specific methods
        handleProductNdcs(entityType, model, item);
        handleLanguageChoices(entityType, model, newTabKey);
        handleWarningLabels(entityType, model, newTabKey, item);

        return entityType.getViewName() + ".edit.spring";
    }

    /**
     * Post from request screen screen. Saves any user inputed values if they are changing tabs
     *
     * @param entityType The entityType of the item
     * @param itemId The id of the item associated with the request 
     * @param requestId The id of the request to retrieve
     * @param tab The tab to display
     * @return Redirect to request view 
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = { "/manageRequest.go", "/manageDomainRequest.go" }, method = RequestMethod.POST)
    public String retrieveRequestsPostTabChange(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.REQUEST_ID) String requestId,
        @RequestParam(value = ControllerConstants.POST_TAB_KEY, required = false) String tab)
        throws ValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // If the item isn't null, save any changes the user has made
        if (item != null) {
            bindAndUpdateSpecialHandling(item);
        }

        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/request/" + requestId
            + getRequestEvent(entityType) + "?" + ControllerConstants.TAB_KEY + "=" + tab;
    }

    /**
     * 
     * Directs the user to the request screen if the item they want to edit has a request associated with it
     *
     * @param entityType of item
     * @param itemId of item
     * @param requestId of item
     * @param tabKey to display
     * @return edit request screen
     */
    @RequestMapping(value = { "/editRequest.go" }, method = RequestMethod.GET)
    public String redirectFromEditOrAdd(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.REQUEST_ID) String requestId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tabKey) {

        // Put any errors or warnings onto flashScope if necessary
        List<String> errors = (List<String>) request.getAttribute(ERRORS);

        if (errors != null && !errors.isEmpty()) {
            flashScope.put(ERRORS, errors);
        }

        List<String> warnings = (List<String>) request.getAttribute(ControllerConstants.WARNINGS);

        if (warnings != null && !warnings.isEmpty()) {
            flashScope.put(ControllerConstants.WARNINGS, warnings);
        }

        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/request/" + requestId
            + getRequestEvent(entityType) + "?" + ControllerConstants.TAB_KEY + "="
            + (StringUtils.isNotEmpty(tabKey) ? tabKey : ControllerConstants.DEFAULT_TAB.get(entityType));

    }

    /**
     * 
     * Processes the request approvals/mods and redirects to the summary page 
     * Invoked when the 'Accept Actions' button is hit from the page as part of the mod Request review flow
     * Invoked when the 'Approve' button is hit from the page as part of the addition Request review flow.
     *
     * @param entityType The entityType of the item
     * @param itemId The item associated with the request
     * @param requestId the request to post
     * @param tabKey The tab to display
     * @param model The spring model
     * @return The redirect string
     * @throws ValidationException an exception
     */
    @RequestMapping(value = "/approveRequest.go", method = RequestMethod.POST)
    public String postRequests(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.REQUEST_ID) String requestId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tabKey, Model model)
        throws ValidationException {

        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());
        RequestVo mainRequest = editManageItemBean.getMainRequest();
        ManagedItemVo item = editManageItemBean.getItem();
        ManagedItemVo oldItem = editManageItemBean.getOriginalItem();

        bindRequestStatusAndComments(mainRequest);

        bindAndUpdateSpecialHandling(item);

        Collection<ModDifferenceVo> modDifferences = oldItem.compareDifferences(item);
        editManageItemBean.setDifferences(modDifferences);
        ProcessedRequestVo processedRequest;

        if (mainRequest.isAddition() && request.getServletPath().contains("/approveRequest.go")) {
            processedRequest = managedItemService.approveRequest(item, mainRequest, modDifferences, getUser());
        } else {
            processedRequest = managedItemService.submitRequest(item, mainRequest, modDifferences, getUser());
        }

        item = processedRequest.getItem();
        mainRequest = processedRequest.getRequest();

        flashScope.put(ControllerConstants.WARNINGS, processedRequest.getWarnings().getLocalizedErrors(request.getLocale()));

        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + ControllerConstants.SLASH_REQUEST_SLASH
               + requestId + "/requestSummary.go";
    }

    /**
     * Displays the request summary view
     *
     * @param entityType the EntityType of the item to commit
     * @param itemId the id of the item to associated with the request
     * @param requestId the id of the request to retrieve 
     * @param model the Model
     * @return the tile definition for the modification summary
     * @throws RequestCompletedException RequestCompletedException
     * @throws ItemNotFoundException ItemNotFoundException
     * @throws BusinessRuleException BusinessRuleException
     */
    @RequestMapping(value = "/requestSummary.go", method = RequestMethod.GET)
    public String requestSummary(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = "requestId") String requestId,
        Model model) throws ItemNotFoundException, RequestCompletedException, BusinessRuleException {

        pageTrail.addPage("requestItemSummary", "Request Summary");

        // the current item
        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());
        RequestVo mainRequest = null;

        // Id the bean is null, the user has come here in error
        if (editManageItemBean == null) {
            return REDIRECT + entityType.toString().toLowerCase() + "/" + itemId + ControllerConstants.SLASH_REQUEST_SLASH
                   + requestId + ControllerConstants.getRequestReturnEvent(entityType);
        } else {
            mainRequest = editManageItemBean.getMainRequest();
        }

        model.addAttribute(ControllerConstants.ITEM_KEY, editManageItemBean.getItem());
        model.addAttribute(ControllerConstants.MAIN_REQUEST, mainRequest);
        model.addAttribute("mainRequestSize", mainRequest.getRequestDetails().size());
        model.addAttribute(ControllerConstants.WARNINGS, flashScope.get(ControllerConstants.WARNINGS));
        model.addAttribute("modDifferences", editManageItemBean.getDifferences());
        model.addAttribute("modificatonSummary", new ModificationSummary());
        model.addAttribute("requestStateStatusMessage",
                           getRequestStateStatusMessage(mainRequest, editManageItemBean.getDifferences()));

        return "request.summary";
    }

    /**
     * 
     * Commit the request modifications
     *
     * @param modifcationSummary modifcationSummary
     * @return The user to the request search page
     * @throws ValidationException an exception
     */
    @RequestMapping(value = "/requestSummary.go", method = RequestMethod.POST)
    public String commitRequest(@ModelAttribute ModificationSummary modifcationSummary) throws ValidationException {

        // the current item
        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());
        RequestVo mainRequest = editManageItemBean.getMainRequest();
        ManagedItemVo oldItem = editManageItemBean.getOriginalItem();

        if (mainRequest.isAddition()) {
            mainRequest.setNewNote(request.getParameter(FieldKey.NEW_NOTE.toAttributeName()));
        }

        String psrName = request.getParameter(FieldKey.PSR_NAME.toAttributeName());

        if (!StringUtils.isEmpty(psrName)) {
            mainRequest.setPsrName(psrName);
            mainRequest = managedItemService.markRequestForPsr(editManageItemBean.getItem(), mainRequest, getUser());

        }

        bindRequestStatusAndComments(mainRequest);

        UserVo saveUser = mainRequest.getLastReviewer();
        mainRequest.setLastReviewer(getUser());
        ProcessedRequestVo processedRequest;

        Collection<ModDifferenceVo> differences =
            editManageItemBean.getDifferences() == null ? new ArrayList<ModDifferenceVo>() : editManageItemBean
                .getDifferences();

        int index = 0;

        for (ModDifferenceVo difference : differences) {
            difference.setModificationReason(modifcationSummary.getModifications().get(index).getReason());
            difference.setAcceptChange(modifcationSummary.getModifications().get(index).getAcceptChange());
            index++;
        }

        // Need to catch validation exceptions and reset the user to the old user for checking on the
        // request mod screen for info messages.
        try {
            processedRequest = managedItemService.commitRequest(oldItem, mainRequest, differences, getUser(), false);
        } catch (ValidationException e) {
            mainRequest.setLastReviewer(saveUser);
            throw e;
        }

        editManageItemBean.setItem(processedRequest.getItem());
        mainRequest = processedRequest.getRequest();
        flashScope.put(ControllerConstants.WARNINGS, processedRequest.getWarnings().getErrors());

        int multiBeanIndex = getNextIndexOfMultiEdit();

        if (multiBeanIndex == -1) {
            return getRedirectAfterCompletionUrl(mainRequest.getEntityType());
        } else {
            MultipleSelectItemBean multipleSelectItemBean = (MultipleSelectItemBean) flowScope.get("multipleSelectItemBean");

            return REDIRECT + "/" + multipleSelectItemBean.getItemEntityTypes()[multiBeanIndex].toLowerCase() + "/"
                + multipleSelectItemBean.getItemIds()[multiBeanIndex] + "/request/"
                + multipleSelectItemBean.getRequestIds()[multiBeanIndex]
                + getRequestEvent(multipleSelectItemBean.getItemEntityTypes()[multiBeanIndex]);
        }
    }

    /**
     * 
     * Description
     *
     * @param entityType of the item
     * @param itemId of the item associated with the request
     * @param requestId used to retrieve request
     * @return Redirects to the request summary
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = { "/markRequestUnderReview.go" }, method = RequestMethod.POST)
    public String markUnderReview(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.REQUEST_ID) String requestId) throws ValidationException {

        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());
        ManagedItemVo item = editManageItemBean.getItem();
        bindAndUpdateSpecialHandling(item);
        editManageItemBean.setDifferences(editManageItemBean.getOriginalItem().compareDifferences(item));
        editManageItemBean.setMainRequest(managedItemService.markRequestUnderReview(item,
            editManageItemBean.getMainRequest(),
                                                                                    getUser()));

        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + ControllerConstants.SLASH_REQUEST_SLASH
               + requestId + "/requestSummary.go";
    }

    /**
     * 
     * Description
     *
     * @param entityType of the item
     * @param itemId of the item associated with the request
     * @param requestId used to retrieve request
     * @return Redirects to the rejection summary view
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = { "/rejectRequest.go" }, method = RequestMethod.POST)
    public String rejectRequest(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @PathVariable(value = ControllerConstants.REQUEST_ID) String requestId) throws ValidationException {

        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());
        ManagedItemVo item = editManageItemBean.getItem();

        bindRequestStatusAndComments(editManageItemBean.getMainRequest());

        bindAndUpdateSpecialHandling(item);

        editManageItemBean.setDifferences(editManageItemBean.getOriginalItem().compareDifferences(item));
        editManageItemBean.setMainRequest(managedItemService.rejectRequest(item, editManageItemBean.getMainRequest(),
                                                                           editManageItemBean.getDifferences(), getUser()));

        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + ControllerConstants.SLASH_REQUEST_SLASH
               + requestId + "/rejectRequestSummary.go?";
    }

    /**
     * 
     * Post for rejecting a problem report
     *
     * @param entityType of the item 
     * @return Redirects to the request search page
     * @throws ValueObjectValidationException an exception
     */
    @RequestMapping(value = { "/rejectProblemReport.go" }, method = RequestMethod.POST)
    public String rejectProblemReport(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType)
        throws ValueObjectValidationException {

        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());

        editManageItemBean.setMainRequest(managedItemService.rejectProblemReport(editManageItemBean.getItem(),
            editManageItemBean.getMainRequest(), getUser()));

        return getRedirectAfterCompletionUrl(entityType);
    }

    /**
     * 
     * Invoked when the 'Reject' button is hit from the page as part of the "Add" Request review flow.
     *
     * @param entityType The entityType
     * @param model Spring model
     * @return The confirm reject view
     */
    @RequestMapping(value = { "/rejectRequestSummary.go" }, method = RequestMethod.GET)
    public String getRejectRequestSummary(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        Model model) {
        pageTrail.addPage("rejectRequest", "Confirm Reject");

        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());

        if (editManageItemBean == null) {

            return getRedirectAfterCompletionUrl(entityType);
        }

        model.addAttribute(ControllerConstants.MAIN_REQUEST, editManageItemBean.getMainRequest());

        return "confirm.reject";
    }

    /**
     * 
     * Commits the reject to the database
     *
     * @return The user to the request search page
     * @throws ValidationException exception
     */
    @RequestMapping(value = { "/rejectRequestSummary.go" }, method = RequestMethod.POST)
    public String postRejectRequestSummary() throws ValidationException {

        pageTrail.addPage("rejectRequest", "Confirm Reject");

        EditManagedItemBean editManageItemBean = (EditManagedItemBean) flowScope.get(EditManagedItemBean.class.getName());
        RequestVo mainRequest = editManageItemBean.getMainRequest();
        ManagedItemVo item = editManageItemBean.getItem();
        ManagedItemVo oldItem = editManageItemBean.getOriginalItem();

        try {
            mainRequest.setRequestRejectionReason(RequestRejectionReason.valueOf(request
                .getParameter(FieldKey.REQUEST_REJECTION_REASON.toAttributeName())));
        } catch (IllegalArgumentException e) {
            mainRequest.setRequestRejectionReason(RequestRejectionReason.INAPPROPRIATE_REQUEST);
        }

        mainRequest.setRejectionReasonText(request.getParameter(FieldKey.REJECTION_REASON_TEXT.toAttributeName()));
        
        if (StringUtils.isEmpty(mainRequest.getRejectionReasonText())) {
            Errors errors = new Errors();
            errors.addError(FieldKey.REJECTION_REASON_TEXT, ErrorKey.COMMON_EMPTY, FieldKey.REJECTION_REASON_TEXT);
            throw new ValueObjectValidationException(errors);
        } else if (StringUtils.length(mainRequest.getRejectionReasonText()) < 1
            || StringUtils.length(mainRequest.getRejectionReasonText()) > PPSConstants.I120) {
            Errors errors = new Errors();
            errors.addError(FieldKey.REJECTION_REASON_TEXT, ErrorKey.COMMON_MAX_MIN_LENGTH,
                FieldKey.REJECTION_REASON_TEXT, 1, PPSConstants.I120);
            throw new ValueObjectValidationException(errors);
        }

        mainRequest.setNewNote(request.getParameter(FieldKey.NEW_NOTE.toAttributeName()));

        bindAndUpdateSpecialHandling(item);

        Collection<ModDifferenceVo> modDifferences = oldItem.compareDifferences(item);

        mainRequest = managedItemService.rejectRequest(item, mainRequest, modDifferences, getUser());

        managedItemService.commitRequest(oldItem, mainRequest, modDifferences, getUser(), false);

        return getRedirectAfterCompletionUrl(item.getEntityType());
    }

    /**
     * 
     * Cancels the request flow and returns to the search page
     *
     * @param entityType the entityType of the item
     * @return The redirect to the previous page 
     */
    @RequestMapping(value = "requestCancel.go", method = RequestMethod.POST)
    public String cancelAdd(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType) {

        return getRedirectAfterCompletionUrl(entityType);
    }

    /**
     * Returns REDIRECT string for cancel or completion of flow
     *
     * @param entityType of item
     * @return Page to redirect to
     */
    private String getRedirectAfterCompletionUrl(EntityType entityType) {
        if (entityType.isDomainType()) {
            return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchDomainRequests.go");
        } else {
            return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchRequests.go");
        }
    }

    /**
     * 
     * Returns to the previous page in the flow
     *
     * @return The previous page
     * 
     */
    @RequestMapping(value = "returnToRequest.go", method = RequestMethod.POST)
    public String returnToAdd() {

        return REDIRECT + pageTrail.goToPreviousUrl();
    }

    /**
     * 
     * Prepare to forward to the add managed domain screen from the edit screen
     *
     * @param entityType entityType
     * @param formElementName formElementName
     * @param model model
     * @return add managed domain view
     * @throws ValidationException ValidationException
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
    @RequestMapping(value = "manageRequest/prepareAddManagedDomain.go", method = RequestMethod.POST)
    public String prepareForAddManagedDomain(
        @RequestParam(value = ControllerConstants.DOMAIN_ENTITY_TYPE) EntityType entityType, @RequestParam(
            value = ControllerConstants.FORM_ELEMENT_NAME, required = true) String formElementName, Model model)
        throws ValidationException {

        return manageItemController.prepForAddManagedDomain(entityType, formElementName);
    }


    /**
     * Selects the proper tab to display when viewing the request
     *
     * @param entityType The entity Type
     * @param mainRequest The request
     * @param tab The tab to display 
     * @return The proper tab to display
     */
    public String selectTab(EntityType entityType, RequestVo mainRequest, String tab) {
        String modTab = tab;

        // Request and Problem Reports come from the same table, but our PrintTemplateVo can only link to one tab.
        // So here we reset the tabElementId request parameter if this happens to be a Problem Report.
        if (mainRequest.getRequestState().isPendingFirstReview() && mainRequest.getRequestType().isProblemReport()
            && modTab == null) {
            modTab = ControllerConstants.PROBLEM_REPORT_TAB;
        } else if (modTab == null) {
            modTab = ControllerConstants.REQUESTS_TAB;
        }

        if (ControllerConstants.REQUESTS_TAB.equals(modTab) && mainRequest.isAddition()
            && (mainRequest.getRequestDetails() == null || mainRequest.getRequestDetails().isEmpty())) {
            modTab = ControllerConstants.DEFAULT_TAB.get(entityType);
        } else if (ControllerConstants.REQUESTS_TAB.equals(modTab) && mainRequest.isProblemReport()) {
            modTab = ControllerConstants.REPORTS_TAB;
        }

        return modTab;
    }

    /**
     * Binds user approvals and comments
     *
     * @param mainRequest The request to bind to
     */
    public void bindRequestStatusAndComments(RequestVo mainRequest) {
        int i = 0;
        Object[] differences = mainRequest.getRequestDetails().toArray();

        String[] modRequestItemStatuses = request.getParameterValues(FieldKey.MOD_REQUEST_ITEM_STATUS.toAttributeName());

        if (modRequestItemStatuses != null) {
            for (String modRequestItemStatus : modRequestItemStatuses) {
                ((ModDifferenceVo) differences[i]).setModRequestItemStatus(RequestItemStatus.valueOf(modRequestItemStatus
                    .toUpperCase(Locale.US)));
                i++;
            }

            i = 0;
        }

        String[] comments = request.getParameterValues(FieldKey.COMMENTS.toAttributeName());

        if (comments != null) {
            for (String comment : comments) {
                ((ModDifferenceVo) differences[i]).setComments(comment);
                i++;
            }
        }
    }

    /**
     * Retrieve the item using the provided {@link #itemId} and {@link EntityType}.
     * 
     * @param itemId The item id
     * @param entityType The entityType of the item
     * @param user The logged in user
     * @param mainRequest The request
     * @return The item
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    public ManagedItemVo retrieve(String itemId, EntityType entityType, UserVo user, RequestVo mainRequest)
        throws ItemNotFoundException {

        ManagedItemVo item = managedItemService.retrieve(itemId, entityType, getUser());

        // Per CR1959 "Show Updated Values During Modification Review", apply 'approved' changes to the item so the
        // next PNM reviewer 'sees' the item's data fields set as they would look if the request completed.
        if (mainRequest != null && environmentUtility.isNational()) {
            managedItemService.applyChanges(item, mainRequest, user);
        }

        return item;
    }

    /**
     * 
     * Returns the event string given an entityType
     *
     * @param entityType of item
     * @return /manageRequest.go or /manageDomainRequest.go
     */
    public String getRequestEvent(EntityType entityType) {
        if (entityType.isDomainType()) {
            return "/manageDomainRequest.go";
        } else {
            return "/manageRequest.go";
        }

    }

    /**
     * 
     * If this message is populated in the jsp mod summary screen so the user knows that the current request will stay in the
     * pending 2nd review state even if they click the Accept changes button
     *
     * @param mainRequest The request
     * @param modDifferences The differences made to them while approving the request
     * @return String message
     * @throws RequestCompletedException 
     * @throws ItemNotFoundException 
     * @throws BusinessRuleException 
     */
    public String getRequestStateStatusMessage(RequestVo mainRequest, Collection<ModDifferenceVo> modDifferences)
        throws ItemNotFoundException, RequestCompletedException, BusinessRuleException {
        String message = "";

        if (mainRequest != null) {

            // if state is pending second review
            if (RequestState.PENDING_SECOND_REVIEW.equals(mainRequest.getRequestState())) {

                // If this same user approved the request last time
                if (mainRequest.getLastReviewer().getUsername().equals(getUser().getUsername()) 
                    && EntityType.isSecondApprovalType(mainRequest.getEntityType())) {
                    message = messageSource.getMessage("stay.message.sameuser", null, request.getLocale());

                } else {
                    if (RequestType.ADDITION.equals(mainRequest.getRequestType())) {
                        message = getRequestStateStatusMessageHelper(mainRequest, modDifferences, "stay.message.add.changed");
                    }

                    if (RequestType.MODIFICATION.equals(mainRequest.getRequestType())) {
                        message =
                            getRequestStateStatusMessageHelper(mainRequest, modDifferences, "stay.message.modify.changed");
                    }
                }
            }
        }

        return message;
    }

    /**
     * A helper method which builds the message to display
     *
     * @param mainRequest The request
     * @param modDifferences Any differences which have been made item (do not pass request.getRequestDetails())
     * @param messageKey resource bundle key
     * @return message to display to the user
     * @throws RequestCompletedException RequestCompletedException
     * @throws ItemNotFoundException ItemNotFoundException
     * @throws BusinessRuleException BusinessRuleException
     */
    private String getRequestStateStatusMessageHelper(RequestVo mainRequest, Collection<ModDifferenceVo> modDifferences,
        String messageKey) throws ItemNotFoundException, RequestCompletedException, BusinessRuleException {

        StringBuffer message = new StringBuffer();

        if (modDifferences != null) {
            if (mainRequest.hasConflicts()) {
                generateMessageHelper("stay.message.modify.conflict", message);
            }

            // Iterates through the modDifferences to see if any are Second Review data fields
            for (ModDifferenceVo dif : modDifferences) {
                if (dif.getDifference().getFieldKey().isRequiresSecondApproval()) {
                    generateMessageHelper(messageKey, message);
                    break;
                }

            }
        }

        if (RequestStateMachine.requestItemStatusChanged(mainRequest, requestService.retrieve(mainRequest.getId()),
            getUser())) {
            generateMessageHelper(messageKey, message);
        }

        return message.toString();
    }

    /**
     * Builds the localized message to display to the user
     *
     * @param messageKey the resource bundle key
     * @param message the message to display
     */
    private void generateMessageHelper(String messageKey, StringBuffer message) {
        if (message.length() > 0) {
            message.append("<br/>");
        }

        message.append(messageSource.getMessage(messageKey, null, request.getLocale()));
    }
}
