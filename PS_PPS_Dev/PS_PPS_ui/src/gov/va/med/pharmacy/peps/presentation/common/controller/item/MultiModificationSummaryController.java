/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey.FieldSubType;
import gov.va.med.pharmacy.peps.common.vo.ItemModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.PepsController;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * MultiModificationSummaryController's brief summary
 * 
 * Details of MultiModificationSummaryController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller
@RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}")
public class MultiModificationSummaryController extends PepsController {

    /** managedItemService */
    @Autowired
    ManagedItemService managedItemService;

    /**
     * applyToAllModificationSummary
     *
     * @param entityType EntityType
     * @param itemId String
     * @param tab String
     * @param model Model
     * @return String
     * @throws ItemNotFoundException exception
     */
    @RequestMapping(value = "/applyToAll.go", method = RequestMethod.GET)
    public String applyToAllModificationSummary(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab,
        Model model) throws ItemNotFoundException {

        pageTrail.addPage("applyToAll", "Review All Changes");

        // the current item
        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return "redirect:/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go";
        }

        // the current item differences
        Collection<ModDifferenceVo> differences = editManageItemBean.getDifferences();
        boolean groupListDataField = false;
        
        for (ModDifferenceVo difference : differences) {
            FieldSubType fieldSubType = difference.getDifference().getFieldKey().getFieldSubType();
            
            if (fieldSubType.equals(FieldSubType.GROUP_LIST_DATA_FIELD)) {
                groupListDataField = true;
            }
        }

        // get all the items
        Collection<ManagedItemVo> items = new ArrayList<ManagedItemVo>();

        MultipleSelectItemBean multipleSelectItemBean = (MultipleSelectItemBean) flowScope.get("multipleSelectItemBean");

        if (multipleSelectItemBean != null) {
            for (String currentItemId : multipleSelectItemBean.getItemIds()) {
                items.add(managedItemService.retrieve(currentItemId, entityType, getUser()));
            }
        }

        // the current item differences

        Collection<ItemModDifferenceVo> itemModDifferences =
            (Collection<ItemModDifferenceVo>) flashScope.get("itemModDifferences");

        if (itemModDifferences == null) {
            if (groupListDataField) {
                itemModDifferences = managedItemService.submitGroupModifications(differences, items, getUser()); 
            } else {
                itemModDifferences = managedItemService.submitAllModifications(differences, items, getUser()); 
            }
        }

        int fromIndex = 0;

        // propagate reasons from the original item to all other items
        for (ModDifferenceVo applyfromDifference : differences) {
            String applyFromReason = applyfromDifference.getModificationReason();

            if (StringUtils.isEmpty(applyFromReason)) {

                for (ItemModDifferenceVo applyToItemDifference : itemModDifferences) {
                    int toIndex = 0;

                    for (ModDifferenceVo applyToDifference : applyToItemDifference.getDifferences()) {
                        if (toIndex++ == fromIndex) {
                            applyToDifference.setModificationReason(applyFromReason);
                            break;
                        }
                    }
                }
            }

            fromIndex++;
        }

        model.addAttribute(ControllerConstants.ITEM_KEY, item);
        model.addAttribute(ControllerConstants.TAB_KEY, tab);
        model.addAttribute("itemModDifferences", itemModDifferences);

        return "multi.edit.mod.summary";
    }

    /**
     * cancelCommitMultipleModifications
     *
     * @param entityType EntityType
     * @param itemId String
     * @param tab String
     * @return String
     * @throws ValidationException exception
     * @throws OptimisticLockingException exception
     */
    @RequestMapping(value = "/cancelCommitMultiModifications.go", method = RequestMethod.POST)
    public String cancelCommitMultipleModifications(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = "tab", required = false) String tab) throws ValidationException, OptimisticLockingException {

        return REDIRECT + pageTrail.goToPreviousUrl();
    }

    /**
     * commitMultipleModifications
     *
     * @param entityType EntityType
     * @param itemId String
     * @param tab String
     * @return String
     * @throws ValidationException exception
     * @throws OptimisticLockingException exception
     */
    @RequestMapping(value = "/commitMultiModifications.go", method = RequestMethod.POST)
    public String commitMultipleModifications(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = "tab", required = false) String tab)
        throws ValidationException, OptimisticLockingException {

        Map<String, Object> params = request.getParameterMap();

        // get the original item
        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);

        // the current item differences
        Collection<ModDifferenceVo> differences = editManageItemBean.getDifferences();

        // get all the items
        Collection<ManagedItemVo> items = new ArrayList<ManagedItemVo>();

        for (String currentItemId : ((MultipleSelectItemBean) flowScope.get("multipleSelectItemBean")).getItemIds()) {
            items.add(managedItemService.retrieve(currentItemId, entityType, getUser()));
        }

        Collection<ItemModDifferenceVo> itemModDifferences =
            managedItemService.submitAllModifications(differences, items, getUser());

        for (ItemModDifferenceVo itemDiff : itemModDifferences) {
            int index = 0;

            for (ModDifferenceVo difference : itemDiff.getDifferences()) {
                boolean acceptChange =
                    Boolean.valueOf(((String[]) params.get(itemDiff.getItem().getId() + "-" + index + "-acceptChange"))[0]);
                String reason = ((String[]) params.get(itemDiff.getItem().getId() + "-" + index + "-reason"))[0];

                difference.setModificationReason(reason);
                difference.setAcceptChange(acceptChange);
                index++;
            }

        }

        // incase there is an exception, send the diffs to the next request
        flashScope.put("itemModDifferences", itemModDifferences);

        Collection<ProcessedItemVo> processedItems =
            managedItemService.commitAllModifications(itemModDifferences, getUser());

        for (ProcessedItemVo processedItem : processedItems) {
            flashScope.put(ControllerConstants.WARNINGS, processedItem.getWarnings().getLocalizedErrors(getLocale()));
            updateMultiEditIndex(processedItem.getItem().getId());
        }

        return "redirect:/" + entityType.toString().toLowerCase() + "/" + itemId + "/confirmModifications.go";
    }

}
