/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.presentation.common.controller.AbstractSearchController;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;


/**
 * 
 * MoveChildrenController's brief summary
 * 
 * Details of MoveChildrenController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller
@RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
public class MoveChildrenController extends AbstractSearchController {

    /**
     * searchNewParent
     *
     * @param entityType EntityType
     * @param searchCriteria SearchCriteriaVo
     * @param isFirstRun boolean
     * @param model Model
     * @return String
     * @throws ValidationException exception
     */
    @RequestMapping(value = "searchNewParent.go", method = RequestMethod.GET)
    public String searchNewParent(
        @RequestParam EntityType entityType,
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = "isFirstRun", required = false, defaultValue = "true") boolean isFirstRun,
        Model model)
        throws ValidationException {

        pageTrail.addPage("move" + entityType.getChild().toLowerCase() + "s",
            "Move " + StringUtils.capitalize(entityType.getChild().toLowerCase()) + "s", true);

        if (!isFirstRun) {

            prepareSearchCriteria(searchCriteria);
            model.addAttribute("items", getManagedItemService().search(searchCriteria));
            switch (entityType.getChild()) {

                case PRODUCT:
                    model.addAttribute("printTemplate", DefaultPrintTemplateFactory.moveProductSelectOrderableItemSearch());
                    break;

                case NDC:
                    model.addAttribute("printTemplate", DefaultPrintTemplateFactory.moveNDCSelectProductSearch());
                    break;

                default:
            }
        }

        if (EntityType.NDC.equals(entityType.getChild())) {
            MultipleSelectItemBean multiEditBean = (MultipleSelectItemBean) flowScope.get("multiEditBean");
            EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
            ManagedItemVo item = editManageItemBean.getItem();

            ProductVo product = (ProductVo) item;
            List<NdcVo> ndcs = product.getNdcs();

            ArrayList packageTypes = new ArrayList();

            for (String childId : multiEditBean.getItemIds()) {
                for (NdcVo productNdcs : ndcs) {
                    if (childId.equals(productNdcs.getId())) {
                        packageTypes.add(productNdcs.getPackageType());
                    }
                }
            }

            // Removes duplicate items in list
            HashSet hs = new HashSet();
            hs.addAll(packageTypes);
            packageTypes.clear();
            packageTypes.addAll(hs);

            model.addAttribute(ControllerConstants.ITEM_KEY, item);
            model.addAttribute("packageTypes", packageTypes);
        }

        model.addAttribute("cancelUrl", "/cancelSearchNewParent.go");
        model.addAttribute("addTitle", ": Moving " + StringUtils.capitalize(entityType.getChild().toLowerCase()) + "s");

        return entityType.getViewName() + ".search";
    }

    /**
     * moveChildren
     *
     * @param entityType EntityType
     * @param itemId String
     * @return String
     * @throws OptimisticLockingException exception
     * @throws ValidationException exception
     */
    @RequestMapping(value = "{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID
            + "}/moveChildren.go", method = RequestMethod.GET)
    public String moveChildren(
        @PathVariable EntityType entityType,
        @PathVariable String itemId)
        throws OptimisticLockingException, ValidationException {

        MultipleSelectItemBean multiEditBean = (MultipleSelectItemBean) flowScope.get("multiEditBean");
        EditManagedItemBean editBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo toItem = getManagedItemService().retrieve(itemId, editBean.getItem().getEntityType());

        if (multiEditBean == null) {
            throw new ValidationException(ValidationException.NO_NDCS_SELECTED);
        }

        for (String childId : multiEditBean.getItemIds()) {
            ProcessedItemVo processedItem = getManagedItemService().updateParentChildRelationships(
                getManagedItemService().retrieve(childId, multiEditBean.getItemEntityTypes()[0]), toItem, getUser());

            flashScope.put(ControllerConstants.WARNINGS, processedItem.getWarnings().getLocalizedErrors(getLocale()));
        }

        editBean.setItem(getManagedItemService().retrieve(editBean.getItem().getId(), editBean.getItem().getEntityType()));

        return REDIRECT + pageTrail.goToPreviousFlowUrl();
    }

    /**
     * cancelSearchNewParent
     *
     * @return String
     */
    @RequestMapping(value = "cancelSearchNewParent.go", method = RequestMethod.GET)
    public String cancelSearchNewParent() {
        return REDIRECT + pageTrail.goToPreviousFlowUrl();
    }
}
