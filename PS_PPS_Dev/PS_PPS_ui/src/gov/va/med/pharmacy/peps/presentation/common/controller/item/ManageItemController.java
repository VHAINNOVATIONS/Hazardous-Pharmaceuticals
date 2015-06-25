/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.presentation.common.utility.DomainUtility;
import gov.va.med.pharmacy.peps.presentation.common.utility.JspTagUtility;


/**
 * ManageItemController's brief summary
 * 
 * Details of ManageItemController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller
@RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}")
public class ManageItemController extends AbstractManageItemController {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManageItemController.class);
    
    @Autowired
    private DomainUtility domainUtility;

    @Autowired
    private AddItemController addItemController;
    
    @Autowired
    private Errors errors;

    /**
     * edit
     *
     * @param entityType EntityType
     * @param itemId String
     * @param tab to display
     * @param model Model
     * @return String
     * @throws ItemNotFoundException exception 
     */
    @RequestMapping(value = ControllerConstants.PAGE_EDIT, method = RequestMethod.GET)
    public String edit(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab,
        Model model) throws ItemNotFoundException {


        updateMultiEditIndex(itemId);

        String tabKey;

        if (StringUtils.isEmpty(tab)) {
            tabKey = getTabKey(entityType);
        } else {
            tabKey = tab;
        }

        pageTrail.addPage("editItem", "Edit " + entityType.getName(), true);

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();
        ManagedItemVo originalItem = editManageItemBean.getOriginalItem();

        
        // is this the first time here or the item changed since the last time were were here
        if (item == null || !itemId.equals(item.getId()) || item.getEntityType() != entityType) {

            item = getManagedItemService().retrieve(itemId, entityType, getUser());

            String redirectToRequest = redirectToRequest(entityType, itemId, tabKey);

            if (!redirectToRequest.isEmpty()) {
                return redirectToRequest;
            }

            originalItem = item.copy();

            editManageItemBean = flowScope.getNew(EditManagedItemBean.class);

            editManageItemBean.setItem(item);
            editManageItemBean.setOriginalItem(originalItem);
        }

       
        // if this was a partial save, set it reset it
        item.setPartialSave(false);

        // setup the model
        model.addAttribute(ControllerConstants.ITEM_KEY, item);
        model.addAttribute(ControllerConstants.TAB_KEY, tabKey);

        // tab specific methods
        handleProductNdcs(entityType, model, item);

        handleLanguageChoices(entityType, model, tabKey);

        handleWarningLabels(entityType, model, tabKey, item);


        // forward to the tiles view
        return entityType.getViewName() + ".edit.spring";
    }

    /**
     * Post from edit screen. Saves any user inputed values if they are changing tabs
     *
     * @param entityType EntityType
     * @param itemId String
     * @param tab to display
     * @return String
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = ControllerConstants.PAGE_EDIT, method = RequestMethod.POST)
    public String editPostTabChange(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.POST_TAB_KEY, required = false) String tab)
        throws ValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // If the item isn't null, save any changes the user has made
        if (item != null) {
            bindAndUpdateSpecialHandling(item);
        }

        return REDIRECT + "/" + entityType.toLowerCase(Locale.US) + "/" + itemId + ControllerConstants.PAGE_EDIT + "?"
            + ControllerConstants.TAB_KEY + "=" + tab;
    }

    /**
     * Sets the show spanish warning labels flag
     * 
     * @param itemId String
     * @param tab String
     * @return String
     * @throws ValueObjectValidationException ValueObjectValidationException
     * 
     */
    @RequestMapping(value = "/changeShowSpanishWarningLabels.go", method = RequestMethod.POST)
    public String submitModifications(
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab)
        throws ValueObjectValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return ControllerConstants.REDIRECT_SLASH + "product/" + itemId + ControllerConstants.PAGE_EDIT;
        }

        bindAndUpdateSpecialHandling(item);

        boolean showSpanishWarningLabels = request.getParameterMap().keySet().contains("SHOW_SPANISH_WARNING_LABELS");

        if (showSpanishWarningLabels) {
            flowScope.put(SHOW_SPANISH_WARNING_LABELS, Boolean.TRUE);
        } else {
            flowScope.put(SHOW_SPANISH_WARNING_LABELS, Boolean.FALSE);
        }

        // redirect to the edit page
        return ControllerConstants.REDIRECT_SLASH + "product/" + itemId + ControllerConstants.PAGE_EDIT + "?"
            + ControllerConstants.TAB_KEY + "=" + tab;
    }

    /**
     * startMultiItemEditFlow
     *
     * @param multipleSelectItemBean MultipleSelectItemBean
     * @return String
     * @throws ValueObjectValidationException 
     * @throws ItemNotFoundException 
     */
    public String startMultiItemEditFlow(MultipleSelectItemBean multipleSelectItemBean) 
        throws ValueObjectValidationException, ItemNotFoundException {
        flowInputScope.put("multipleSelectItemBean", multipleSelectItemBean);
        multipleSelectItemBean.setCurrentIndex(0);
        
        //check the selected items to see if any are pending - if so, return an error message
        for (int x = 0; x < multipleSelectItemBean.getItemIds().length; x++) {
            ManagedItemVo item = getManagedItemService().retrieve(multipleSelectItemBean.getItemIds()[x], 
                multipleSelectItemBean.getItemEntityTypes()[x], getUser());
            RequestItemStatus itemStatus = item.getRequestItemStatus();
                
            if (itemStatus.equals(RequestItemStatus.PENDING)) {
                errors.addError(new ValidationError(ErrorKey.PENDING_MULTIEDIT, item.getValue()));
                break;
            }        
        }
        
        if (errors.hasErrors()) {
            throw new ValueObjectValidationException(errors);
        }
        
        return ControllerConstants.REDIRECT_SLASH + multipleSelectItemBean.getItemEntityTypes()[0].toLowerCase() + "/"
            + multipleSelectItemBean.getItemIds()[0] + ControllerConstants.PAGE_EDIT;
    }

    /**
     * submitModifications
     *
     * @param entityType EntityType
     * @param itemId String
     * @param tab String
     * @return String
     * @throws ValidationException ValidationException
     * 
     */
    @RequestMapping(value = "/submitModifications.go", method = RequestMethod.POST)
    public String submitModifications(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab)
        throws ValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();
        ManagedItemVo originalItem = editManageItemBean.getOriginalItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return ControllerConstants.REDIRECT_SLASH + entityType.toLowerCase(Locale.US) + "/" + itemId
                + ControllerConstants.PAGE_EDIT;
        }

        // build the modified item
        bindAndUpdateSpecialHandling(item);

        item.validate(getUser(), environmentUtility.getEnvironment());

        // get the summary of changes between the original item and the modified one
        ModificationSummaryVo modificationSummary =
            getManagedItemService().submitModifications(originalItem, item, getUser());
        editManageItemBean.setDifferences(modificationSummary.getModDifferences());

        // Make the special handling update



        flashScope.put(ControllerConstants.WARNINGS, modificationSummary.getWarnings().getLocalizedErrors(getLocale()));

        // redirect to the modification summary page
        return ControllerConstants.REDIRECT_SLASH + entityType.toString().toLowerCase() + "/" + itemId
            + "/modificationSummary.go?"
            + ControllerConstants.TAB_KEY + "=" + tab;
    }

    /**
     * Cancel edit and return to the last page in the previous flow or the search page
     *
     * @param entityType the EntityType of the item to commit
     * @param itemId the id of the item to commit
     * @return the last page in the previous flow or the search page
     */
    @RequestMapping(value = "/editCancel.go", method = RequestMethod.POST)
    public String cancelEdit(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId) {

        flowScope.getNew(EditManagedItemBean.class);

        return REDIRECT + pageTrail.goToPreviousUrl("/searchItems.go");
    }

    /**
     * startPartialSave
     *
     * @param entityType EntityType
     * @param itemId String
     * @param tab String
     * @return String 
     * @throws ValidationException exception
     */
    @RequestMapping("/startPartialSave.go")
    public String startPartialSave(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab) throws ValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return REDIRECT + entityType.toLowerCase(Locale.US) + "/" + itemId + ControllerConstants.PAGE_EDIT;
        }

        bindAndUpdateSpecialHandling(item);

        flowInputScope.put(editManageItemBean);

        // redirect to the modification summary page
        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/editPartialSave.go?"
            + ControllerConstants.TAB_KEY + "=" + tab;

    }

    /**
     * 
     * Prepare to forward to the add managed domain screen from the edit screen
     *
     * @param entityType The entityType being added.
     * @param formElementName formElementName
     * @param model model
     * @return add managed domain view
     * @throws ValidationException ValidationException
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
    @RequestMapping(value = "edit/prepareAddManagedDomain.go", method = RequestMethod.POST)
    public String prepareForAddManagedDomain(
        @RequestParam(value = ControllerConstants.DOMAIN_ENTITY_TYPE) EntityType entityType,
        @RequestParam(value = ControllerConstants.FORM_ELEMENT_NAME, required = true) String formElementName,
        Model model) throws ValidationException {

        return prepForAddManagedDomain(entityType, formElementName);
    }

    /**
     * 
     * Helper for prepareForManagedDomain()
     *
     * @param entityType of domain
     * @param formElementName of domain
     * @return add managed domain view
     * @throws ValidationException ValidationException
     */
    public String prepForAddManagedDomain(EntityType entityType, String formElementName)
        throws ValidationException {
        EditManagedItemBean editBean = flowScope.get(EditManagedItemBean.class);
        
        Map<String, Object> keyMap = JspTagUtility.getParentAndChildFieldKeys(formElementName);
        FieldKey fieldKey = ((FieldKey) keyMap.get(JspTagUtility.PARENT_FIELD_KEY));
        
        if (fieldKey == null) {
            fieldKey = ((FieldKey) keyMap.get(JspTagUtility.FIELD_KEY));
        }
        
        String key = fieldKey.fromDotsToCamelCase();
        
        flowScope.put(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX, (Integer) keyMap.get(JspTagUtility.INDEX));

        bindAndUpdateSpecialHandling(editBean.getItem());

        flowInputScope.put(ControllerConstants.ITEM_KEY, editBean.getItem());
        flowInputScope.put(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX, 
            flowScope.get(key + "_" + ControllerConstants.PLUS_BUTTON_INDEX));

        return addItemController.getAddManagedDomainRedirectUrl(entityType, formElementName);
    }

    /**
     * startMoveChildren
     *
     * @param entityType EntityType
     * @param itemId String
     * @param multiEditBean MultipleSelectItemBean
     * @return String
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "/startMoveChildren.go", method = RequestMethod.POST)
    public String startMoveChildren(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @ModelAttribute("multiEditBean") MultipleSelectItemBean multiEditBean) throws ValidationException {

        flowInputScope.put("multiEditBean", multiEditBean);
        EditManagedItemBean editBean = flowScope.get(EditManagedItemBean.class);
        bindAndUpdateSpecialHandling(editBean.getItem());

        flowInputScope.put(editBean);

        return REDIRECT + "/searchNewParent.go?entityType="
            + multiEditBean.getItemEntityTypes()[0].getParent().toLowerCase();

    }

    /**
     * startCopyChildren
     *
     * @param entityType EntityType
     * @param itemId String
     * @param multiEditBean MultipleSelectItemBean
     * @return String
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "/startCopyChildren.go", method = RequestMethod.POST)
    public String startCopyChildren(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @ModelAttribute("multiEditBean") MultipleSelectItemBean multiEditBean) throws ValidationException {
        
        //add the selected NDCs to the url
        flowInputScope.put(MULTI_SEL_ITEM_BEAN, multiEditBean);
        
//        String ndcString = "";
//        int ndcCount = multiEditBean.getItemIds().length;

//        for (int a=0; a < ndcCount; a++) {
//            ndcString = ndcString + "&ndc" + a + "=" + multiEditBean.getItemIds()[a].toString();
//        }
      
        //add the current product to flowScope
        EditManagedItemBean editBean = flowScope.get(EditManagedItemBean.class);
        bindAndUpdateSpecialHandling(editBean.getItem());

        return REDIRECT + "/product/" + itemId + "/openTemplate/add.go?entityType=" + editBean.getItem().getEntityType();
        
//            + multiEditBean.getItemEntityTypes()[0].getParent().toLowerCase() + ndcString + "&ndcCount=" + ndcCount;

    }


    /**
     * 
     * Rescinds the rejection on an item
     *
     * @param entityType of item
     * @param itemId of item
     * @return the user to the previous URL, which is probably the search
     * @throws ValidationException 
     */
    @RequestMapping(value = "/rescindRejection.go", method = RequestMethod.POST)
    public String rescindRejection(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId) throws ValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return REDIRECT + entityType.toLowerCase(Locale.US) + "/" + itemId + ControllerConstants.PAGE_EDIT;
        }

        getManagedItemService().commitRescindRejection(item, getUser());

        return REDIRECT + pageTrail.goToPreviousFlowUrl();
    }

    /**
     * Returns the current tab key
     *
     * @param entityType the EntityType
     * @return the String tab key
     */
    protected String getTabKey(EntityType entityType) {
        String tabKey = request.getParameter(ControllerConstants.TAB_KEY);

        if (StringUtils.isEmpty(tabKey)) {
            tabKey = ControllerConstants.DEFAULT_TAB.get(entityType);

            if (tabKey == null && entityType.isDomainType()) {
                tabKey = ControllerConstants.ALPHA_TAB;
            }
        }

        return tabKey;
    }

    /**
     * get the domainUtility
     * 
     * @return the domainUtility
     */
    @Override
    public DomainUtility getDomainUtility() {
        return domainUtility;
    }

    /**
     * sets domainUtility field
     * 
     * @param domainUtility the domainUtility to set
     */
    public void setDomainUtility(DomainUtility domainUtility) {
        this.domainUtility = domainUtility;
    }

    /**
     * get the addItemController
     * 
     * @return the addItemController
     */
    public AddItemController getAddItemController() {
        return addItemController;
    }

    /**
     * sets addItemController field
     * 
     * @param addItemController the addItemController to set
     */
    public void setAddItemController(AddItemController addItemController) {
        this.addItemController = addItemController;
    }
}
