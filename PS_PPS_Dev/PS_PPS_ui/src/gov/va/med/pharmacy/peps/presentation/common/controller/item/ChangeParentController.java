/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.AddManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;


/**
 * ChangeParentController's brief summary
 * 
 * Details of ChangeParentController's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
@Controller
@RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
public class ChangeParentController extends AbstractManageItemController {

    /**
     * Description here
     *
     */
    public ChangeParentController() {
        super();
    }

    /**
     * 
     * Forwards to the post for searching for a new parent
     *
     * @param entityType EntityType
     * @return String redirect to searchParent view
     * @throws ValueObjectValidationException ValueObjectValidationException
     * @throws Exception
     */
    @RequestMapping(value = "/changeParent.go", method = RequestMethod.POST)
    public String changeParentItem(@RequestParam(value = "entityType") EntityType entityType)
        throws ValueObjectValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);

        ManagedItemVo item = editManageItemBean.getItem();

        if (item == null) {
            AddManagedItemBean addManageItemBean = flowScope.get(AddManagedItemBean.class);
            item = addManageItemBean.getItem();
        }

        bindParameters(item);

        flowInputScope.put(ControllerConstants.ITEM_KEY, item);
        flowInputScope.put(ControllerConstants.ADDING_NEW_PARENT, true);

        return "redirect:/searchParent.go?entityType=" + entityType.toString().toLowerCase();
    }

    /**
     * 
     * Cancels the replace parent search and returns to the edit item screen
     *
     * @param entityType EntityType
     * @return String 
     */
    @RequestMapping(value = "/searchParentCancel.go", method = RequestMethod.GET)
    public String cancelChangeParentItem(@RequestParam(value = "entityType") EntityType entityType) {

        return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchItems.go");
    }

    /**
     * 
     * Forwards the get to a post for associating the parent with the child
     *
     * @param entityType the EntityType of the item
     * @param itemId the id of the item
     * @return the url for the edit page
     * @throws ItemNotFoundException 
     */
    @RequestMapping(value = "/{entity_type}/{itemId}/selectParent.go", method = RequestMethod.GET)
    public String associateNewParent(@PathVariable(value = "entity_type") EntityType entityType, @PathVariable(
        value = "itemId") String itemId) throws ItemNotFoundException {

//        pageTrail.addPage("selectParent." + entityType.name(), "selectParent", true);

        ManagedItemVo item = (ManagedItemVo) flowScope.get(ControllerConstants.ITEM_KEY);

        if (item == null) {
            Errors errors = new Errors();

            if (entityType.isOrderableItem()) {
                errors.addError(new ValidationError(ErrorKey.ORDERABLE_ITEM_PARENT_EMPTY_CHANGE_PARENT));
            } else {
                errors.addError(new ValidationError(ErrorKey.PRODUCT_PARENT_EMPTY_CHANGE_PARENT));
            }

            flashScope.put(ERRORS, errors.getLocalizedErrors());

            return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchItems.go");
        }

        item = getManagedItemService().selectParent(item, itemId, entityType);

        return REDIRECT + pageTrail.goToPreviousFlowUrl("/searchItems.go");
    }

}
