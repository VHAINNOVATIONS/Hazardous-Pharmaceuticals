/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.med.pharmacy.peps.common.vo.LanguageChoice;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;


/**
 * 
 * WarningLabelController
 *
 */
@Controller
@RequestMapping("product/{" + ControllerConstants.ITEM_ID + "}")
public class WarningLabelController extends AbstractManageItemController {

    /**
     * The warning label language choice key
     */
    public static final String WARNING_LABEL_LANGUAGE_CHOICE = "languageChoice";

    /**
     * Change Warning Label Handler
     *
     * @param itemId the item id
     * @return manage product page
     * @throws Exception Exception
     */
    @RequestMapping(value = "/changeWarningLabel.go", method = RequestMethod.POST)
    public String changeWarningLabel(@PathVariable(value = ControllerConstants.ITEM_ID) String itemId) throws Exception {
        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return "redirect:/product/" + itemId + "/edit.go";
        }

        String spanishText = request.getParameter("warningLableLanguageChoice");

        if (spanishText != null && spanishText.equals(LanguageChoice.SPANISH.toString())) {
            flowScope.put(WARNING_LABEL_LANGUAGE_CHOICE, LanguageChoice.SPANISH);
        } else {
            flowScope.put(WARNING_LABEL_LANGUAGE_CHOICE, LanguageChoice.ENGLISH);
        }

        // redirect to the edit page
        if (editManageItemBean.getMainRequest() == null) {
            return "redirect:/product/" + itemId + "/edit.go?" + ControllerConstants.TAB_KEY + "="
                + ControllerConstants.OPTIONS_TAB;
        } else {
            return "redirect:/product/" + itemId + "/request/" + editManageItemBean.getMainRequest().getId()
                + "/manageRequest.go?" + ControllerConstants.TAB_KEY + "=" + ControllerConstants.OPTIONS_TAB;
        }

    }

}
