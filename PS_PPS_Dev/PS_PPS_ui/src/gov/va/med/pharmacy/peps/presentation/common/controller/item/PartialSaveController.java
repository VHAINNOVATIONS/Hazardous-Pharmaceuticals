/**
 * Source file created in 2012 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;


/** 
 * PartialSaveController
 */
@Controller
@RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}/{" + ControllerConstants.ITEM_ID + "}")
public class PartialSaveController extends AbstractManageItemController {

    /**
     * 
     * Auto-save
     *
     * @param entityType the EntityType
     * @param itemId the item id
     * @param tab String
     * @param model the model
     * @return the tile view
     * @throws ItemNotFoundException a item not found exception
     * @throws ValueObjectValidationException 
     */    
    @RequestMapping(value = "/autoPartialSaveLogout.go", method = RequestMethod.POST)
    public String autoPartialSaveLogout(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @RequestParam(value = ControllerConstants.TAB_KEY, required = false) String tab,
        Model model) throws ItemNotFoundException, ValueObjectValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();
        
        if (item != null && itemId.equals(item.getId()) && getUser().isEitherManager()) {
            
            // build the modified item
            bindParameters(item);

            PartialSaveVo partialSave = editManageItemBean.getPartialSave();

            if (partialSave == null) {
                partialSave = new PartialSaveVo();
            }

            //get all the partial saves for this user.
            List<PartialSaveVo> savedVos = getManagedItemService().retrievePartialSaves(getUser());
            List<PartialSaveVo> deletedVos = new ArrayList<PartialSaveVo>();

            // cycle thru users saves and remove previous auto-saves
            for (PartialSaveVo p : savedVos) {
                if (p == null) {
                    continue;
                }

                // remove user's other auto-saved partial saves.
                if (PPSConstants.AUTOSAVE_COMMENT.equals(p.getComment())) {
                    deletedVos.add(p);
                    getManagedItemService().deletePartial(p.getId(), p.getEntityType());
                }
            }

            item = getManagedItemService().savePartial(item, PPSConstants.AUTOSAVE_COMMENT, getUser());

        }

        return REDIRECT + "/logout.go";
    }

    /**
     * 
     * Edit a partial save
     *
     * @param entityType the EntityType
     * @param itemId the item id
     * @param model the model
     * @return the tile view
     * @throws ItemNotFoundException a item not found exception
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
    @RequestMapping(value = "/editPartialSave.go", method = RequestMethod.GET)
    public String editPartialSave(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        Model model) throws ItemNotFoundException {

        pageTrail.addPage("savePartialItem", "Comment", true);

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        PartialSaveVo partialSave = editManageItemBean.getPartialSave();

        if (partialSave == null) {
            partialSave = new PartialSaveVo();
        }

        model.addAttribute("item", item);
        model.addAttribute("partialSave", partialSave);

        return "partial.comment";
    }

    /**
     * Cancel a partial save edit
     *
     * @param entityType the EntityType
     * @param itemId the id of the managed item
     * @param partialSave the partial save model attribute
     * @return redirect to edit partial save
     * @throws ValidationException a validation exception
     */
    @RequestMapping(value = "/editPartialCancel.go", method = RequestMethod.POST)
    public String editPartialCancel(@PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId, @ModelAttribute PartialSaveVo partialSave)
        throws ValidationException {
        return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go";
    }

    /**
     * Save a partial save
     *
     * @param entityType the EntityType
     * @param itemId the item id
     * @param partialSave the partial save model attribute
     * @return redirect to the search
     * @throws ValidationException a validation exception
     */
    @RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
    @RequestMapping(value = "/savePartial.go", method = RequestMethod.POST)
    public String savePartial(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = ControllerConstants.ITEM_ID) String itemId,
        @ModelAttribute PartialSaveVo partialSave)
        throws ValidationException {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return "redirect:/" + entityType.toLowerCase(Locale.US) + "/" + itemId + "/edit.go";
        }

        String comment = partialSave.getComment();

        if (comment == null || comment.trim().length() == 0) {
            Errors errors = new Errors();

            errors.addError(FieldKey.PARTIAL_SAVE, ErrorKey.EMPTY_COMMENT);

            throw new ValueObjectValidationException(errors);
        }

        item = getManagedItemService().savePartial(item, comment, getUser());

        pageTrail.goToPreviousFlowUrl();

        return REDIRECT + "/searchItems.go";
    }
}
