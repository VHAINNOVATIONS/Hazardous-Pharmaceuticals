/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ItemStatus;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.validator.AbstractValidator;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.item.AbstractManageItemController;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;


/**
 * 
 * ManageDomainController
 * 
 * Handles the edits/updates on domains and fields on the A-Z tab
 *
 */
@Controller
@RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}")
public class ManageDomainController extends AbstractManageItemController {

    @Autowired
    private ManagedItemService managedItemService;

    /**
     * 
     * Gets the edit field view
     *
     * @param entityType of the item
     * @param itemId of the item
     * @param fieldKey of the field to update
     * @param model sprint model
     * @return the view
     * @throws ItemNotFoundException an exception
     */
    @RequestMapping(value = "/editField.go", method = RequestMethod.GET)
    public String getManagedDomainItemEdit(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.FIELD_KEY) String fieldKey, Model model) throws ItemNotFoundException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();
        ManagedItemVo originalItem = editManageItemBean.getOriginalItem();

        // is this the first time here or the item changed since the last time were were here
        if (item == null || !itemId.equals(item.getId()) || item.getEntityType() != entityType) {

            item = managedItemService.retrieve(itemId, entityType, getUser());
            originalItem = item.copy();

            editManageItemBean = flowScope.getNew(EditManagedItemBean.class);

            editManageItemBean.setItem(item);
            editManageItemBean.setOriginalItem(originalItem);
        }

        model.addAttribute(ControllerConstants.FIELD_KEY, FieldKey.getKey(fieldKey));
        model.addAttribute(ControllerConstants.DOMAIN_KEY, item);
        model.addAttribute(ControllerConstants.ITEM_KEY, item);

        return "edit.field";
    }

    /**
     * 
     * Saves the change on the A-Z tab on the item
     *
     * @param entityType of the item
     * @param itemId of the item
     * @param fieldKey of the field to update
     * @return the view
     * @throws ValueObjectValidationException an exception
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR, Role.PSS_PPSN_SECOND_APPROVER })
    @RequestMapping(value = "saveDomainField.go", method = RequestMethod.POST)
    public String saveDomainField(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.FIELD_KEY) String fieldKey) throws ValueObjectValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item != null && itemId.equals(item.getId()) && item.getEntityType() == entityType) {

            // build the modified item
            bindParameters(item);

            // Validate the changes
            item.validate(getUser(), environmentUtility.getEnvironment());
        }

        // redirect based on whether the request is null or not.
        if (editManageItemBean.getMainRequest() == null) {
            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go?"
                   + ControllerConstants.TAB_KEY + "=" + ControllerConstants.ALPHA_TAB;
        } else {
            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId
                   + ControllerConstants.SLASH_REQUEST_SLASH + editManageItemBean.getMainRequest().getId()
                   + ControllerConstants.getRequestReturnEvent(entityType) + "?" + ControllerConstants.TAB_KEY + "="
                   + ControllerConstants.ALPHA_TAB;
        }

    }

    /**
     * 
     * Handler to activate/inactivate a domain using the button
     *
     * @param entityType of item
     * @param itemId string id
     * @param activate boolean
     * @return the view
     * @throws ValueObjectValidationException an exception
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
    @RequestMapping(value = "activateField.go", method = RequestMethod.POST)
    public String activateField(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.ACTIVATE) boolean activate) throws ValueObjectValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item != null && itemId.equals(item.getId()) && item.getEntityType() == entityType) {

            if (activate) {
                item.setItemStatus(ItemStatus.ACTIVE);
            } else {
                item.setItemStatus(ItemStatus.INACTIVE);
            }
        }


        if (editManageItemBean.getMainRequest() == null) {
            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go?"
                   + ControllerConstants.TAB_KEY + "=" + ControllerConstants.ALPHA_TAB;
        } else {
            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId
                   + ControllerConstants.SLASH_REQUEST_SLASH + editManageItemBean.getMainRequest().getId()
                   + ControllerConstants.getRequestReturnEvent(entityType) + "?" + ControllerConstants.TAB_KEY + "="
                   + ControllerConstants.ALPHA_TAB;
        }
    }

    /**
     * 
     * Handler to delete a domain
     *
     * @param entityType of item
     * @param itemId string id
     * @return the view
     * @throws ValidationException 
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
    @RequestMapping(value = "deleteDomain.go", method = RequestMethod.POST)
    public String deleteDomain(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId) throws ValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are deleting the correct item id
        if (item != null && itemId.equals(item.getId()) && item.getEntityType() == entityType) {
            managedItemService.deleteItem(item);
        }

        return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchDataElements.go");

    }

    /**
     * Validates the field value set by the user on the A-Z tab, if that field has its own validator.
     *
     * @param fieldKey of the field
     * @throws ValueObjectValidationException an exception
     */
    public void validateFieldValue(String fieldKey) throws ValueObjectValidationException {
        FieldKey fK = FieldKey.getKey(fieldKey);

        String value = request.getParameter(fK.fromDotsToCamelCase());

        if (fK.hasValidator()) {
            AbstractValidator validator = fK.newValidatorInstance();

            validator.validate(value, getUser(), environmentUtility.getEnvironment());
        }
    }
}
