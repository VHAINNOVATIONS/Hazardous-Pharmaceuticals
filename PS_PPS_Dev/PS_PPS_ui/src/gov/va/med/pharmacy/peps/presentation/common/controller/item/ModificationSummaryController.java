/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.PepsController;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * 
 * ModificationSummaryController's brief summary
 * 
 * Details of ModificationSummaryController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller
@RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
@RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}")
public class ModificationSummaryController extends PepsController {

    /** managedItemService */
    @Autowired
    private ManagedItemService managedItemService;

    /** manageItemController */
    @Autowired
    private ManageItemController manageItemController;

    /**
     * 
     * Redirect to apply to all view
     *
     * @param entityType of item
     * @param itemId of item
     * @param model Spring Model
     * @param modifcationSummary ModificationSummary
     * @return view
     * @throws ItemNotFoundException ItemNotFoundException
     */
    @RequestMapping(value = "/startApplyToAll.go", method = RequestMethod.POST)
    public String submitApplyToAll(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        Model model,
        @ModelAttribute ModificationSummary modifcationSummary)
        throws ItemNotFoundException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        Collection<ModDifferenceVo> differences = bindReasons(modifcationSummary, editManageItemBean);
        editManageItemBean.setDifferences(differences);
        flowInputScope.put(editManageItemBean);

        return REDIRECT + "/" + entityType.toLowerCase(Locale.US) + "/" + itemId + "/applyToAll.go";
    }

    /**
     * The modification summary view
     *
     * @param entityType the EntityType of the item to commit
     * @param itemId the id of the item to commit
     * @param tab the tab of the edit pages
     * @param model the Model
     * @throws ValidationException exception
     * @return the tile definition for the modification summary
     */
    @RequestMapping(value = "/modificationSummary.go", method = RequestMethod.GET)
    public String modificationSummary(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId, 
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab,
        Model model) throws ValidationException {
        boolean showPsrName = false;
        pageTrail.addPage("editItemSummary", "Modification Summary");

        // the current item
        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go";
        }

        // the current item differences
        Collection<ModDifferenceVo> differences = editManageItemBean.getDifferences();

        // go back to edit
        if (differences == null) {
            return REDIRECT + "/" + entityType.toLowerCase(Locale.US) + "/" + itemId + "/modificationSummary.go"
                   + ControllerConstants.TAB_KEY + "=" + tab;
        } else { //check if the psrName field should be displayed
            if (differences != null) {
                for (ModDifferenceVo vo : differences) {

                    //for domains, item status is checked as requiring 2nd approval but the check above returns false
                    if (vo.getDifference().getFieldKey().isRequiresSecondApproval()
                        || vo.getDifference().getFieldKey() == FieldKey.ITEM_STATUS) {
                        showPsrName = true;
                    }
                }
            }
        }

        model.addAttribute(ControllerConstants.ITEM_KEY, item);
        model.addAttribute("modDifferences", differences);
        model.addAttribute(ControllerConstants.TAB_KEY, tab);
        model.addAttribute("modificatonSummary", new ModificationSummary());
        model.addAttribute("showPsrName", showPsrName);

        return "NATIONAL.modification.summary";
    }

    /**
     * 
     * Cancels the modification summary, redirects to the last URL in the page trail.
     *
     * @param entityType the EntityType of the item to commit
     * @param itemId the id of the item to commit
     * @param tab the tab of the edit pages
     * @return the last URL in the page trail
     */
    @RequestMapping(value = "/modificationSummaryCancel.go", method = RequestMethod.POST)
    public String cancelModificationSummary(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId, @RequestParam(value = ControllerConstants.TAB_KEY,
                                                                                        required = false) String tab) {
        return REDIRECT + pageTrail.goToPreviousUrl();
    }

    /**
     * Commit the modifications
     *
     * @param entityType the EntityType of the item to commit
     * @param itemId the id of the item to commit
     * @param tab the tab of the edit pages
     * @param modifcationSummary the summary bean posted
     * @return the URL of the confirm modifications view
     * @throws ValidationException ValidationException 
     */
    @RequestMapping(value = "/commitModifications.go", method = RequestMethod.POST)
    public String commitModifications(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = "tab", required = false) String tab, @ModelAttribute ModificationSummary modifcationSummary)
        throws ValidationException {

        @SuppressWarnings({ "unused", "unchecked" })
        Map<String, Object> params = request.getParameterMap();

        // get the original item
        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo originalItem = editManageItemBean.getOriginalItem();

        Collection<ModDifferenceVo> differences = bindReasons(modifcationSummary, editManageItemBean);

        try {
            ProcessedItemVo processedItem = managedItemService.commitModifications(differences, originalItem, getUser());
            flashScope.put(ControllerConstants.WARNINGS, processedItem.getWarnings().getLocalizedErrors(getLocale()));
        } catch (OptimisticLockingException e) {

            editManageItemBean.setItem(managedItemService.retrieve(itemId, entityType, getUser()));
            String redirectToRequest = redirectToRequest(entityType, itemId, tab);

            List<String> errors = new ArrayList<String>();
            errors.add(e.getLocalizedMessage(getLocale()));
            flashScope.put(ERRORS, errors);

            if (!redirectToRequest.isEmpty()) {
                return redirectToRequest;
            }

            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go";
        }
        
        // if user enters a PSR Name, update the requestVo with the name
        String psrName = request.getParameter(FieldKey.PSR_NAME.toAttributeName());
        
        if (!StringUtils.isEmpty(psrName)) {
            
            //System.out.println("psrName = " + psrName);
            savePsrName(originalItem, psrName);

        }

//        } catch (OptimisticLockingException e) {
//            flowInputScope.put("mergeVo",
//                managedItemService.computeMergeInformation(editManageItemBean.getItem(), differences, getUser()));
//            flowInputScope.put("editManagedItemBean", editManageItemBean);
//
//            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/mergeModifications.go";
//        }

        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/confirmModifications.go";
    }
    
    /**
     * Save the psrName to the respective RequestVo
     *
     * @param item the item being created
     * @param psrName the psrName to be saved
     * @throws ValidationException ValidationException
     */
    private void savePsrName(ManagedItemVo item, String psrName) throws ValidationException { //NOPMD

        EditManagedItemBean editBean = new EditManagedItemBean();
        RequestVo req = checkForRequest(item.getId(), item.getEntityType());
        item = managedItemService.retrieve(item.getId(), item.getEntityType(), getUser());
        editBean.setItem(item);
        editBean.setOriginalItem(item);
        req = requestService.retrieve(req.getId());
        req.setPsrName(psrName);
        req.setMarkedForPepsSecondReview(true);
        Collection<ModDifferenceVo> differences = new ArrayList<ModDifferenceVo>();
        managedItemService.commitRequest(item, req, differences, getUser(), false);

    }

    /**
     * 
     * Binds the modSummary reasons
     *
     * @param modificationSummary modificationSummary
     * @param editManageItemBean editManageItemBean
     * @return collection of differences with reasons
     */
    private Collection<ModDifferenceVo> bindReasons(ModificationSummary modificationSummary,
        EditManagedItemBean editManageItemBean) {
        Collection<ModDifferenceVo> differences = editManageItemBean.getDifferences();

        int index = 0;

        for (ModDifferenceVo difference : differences) {
            difference.setModificationReason(modificationSummary.getModifications().get(index).getReason());
            difference.setAcceptChange(modificationSummary.getModifications().get(index).getAcceptChange());
            index++;
        }

        return differences;
    }

    /**
     * The confirm modifications view.
     *
     * @param itemId the id of the item to confirm
     * @param tab the current tab
     * @param model the Model
     * @return the tile definition for confirm modifications
     */
    @RequestMapping(value = "/confirmModifications.go", method = RequestMethod.GET)
    public String confirmModifications(@PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab, Model model) {
        List<String> warnings = (List<String>) request.getAttribute(ControllerConstants.WARNINGS);

        model.addAttribute(ControllerConstants.WARNINGS);
        model.addAttribute("hasWarnings", !CollectionUtils.isEmpty(warnings));

        if (flowScope.containsKey(ControllerConstants.IS_REDIRECTED_FROM_MERGE_SUMMARY)) {
            pageTrail.goToPreviousFlowUrl("/searchItems.go");
        }

        MultipleSelectItemBean multiBean = (MultipleSelectItemBean) flowScope.get("multipleSelectItemBean");
        pageTrail.goToPreviousFlowUrl("/searchItems.go");

        flowInputScope.put("multipleSelectItemBean", multiBean);

        pageTrail.addPage("editItemConfirm", "Confirm Modifications", true);

        return "confirm.modifications";
    }

    /**
     * Returns back to the last flow or the search page.
     *
     * @return the last flow or the search page.
     */
    @RequestMapping(value = "/acknowledgeModifications.go", method = RequestMethod.POST)
    public String acknowledgeModifications() {

        int index = getNextIndexOfMultiEdit();

        if (index == -1) {
            return "redirect:" + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        }

        MultipleSelectItemBean multipleSelectItemBean = (MultipleSelectItemBean) flowScope.get("multipleSelectItemBean");

        return REDIRECT + "/" + multipleSelectItemBean.getItemEntityTypes()[index].toLowerCase() + "/"
            + multipleSelectItemBean.getItemIds()[index] + "/edit.go";

    }

    
    /**
     * getter
     * 
     * @return the managedItemService
     */
    public ManagedItemService getManagedItemService() {
        return managedItemService;
    }

    
    /**
     * setter
     * 
     * @param managedItemService the managedItemService to set
     */
    public void setManagedItemService(ManagedItemService managedItemService) {
        this.managedItemService = managedItemService;
    }

    
    /**
     * getter
     * 
     * @return the manageItemController
     */
    public ManageItemController getManageItemController() {
        return manageItemController;
    }

    
    /**
     * setter
     * 
     * @param manageItemController the manageItemController to set
     */
    public void setManageItemController(ManageItemController manageItemController) {
        this.manageItemController = manageItemController;
    }

}
