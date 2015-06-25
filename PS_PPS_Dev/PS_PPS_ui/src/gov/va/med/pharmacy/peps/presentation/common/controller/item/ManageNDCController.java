/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.item;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;


/**
 * ManageNDCController
 *
 */
@Controller
@RequestMapping("product/{" + ControllerConstants.ITEM_ID + "}")
public class ManageNDCController extends AbstractManageItemController {

    /** SHOW_INACTIVE_NDCS */
    public static final String SHOW_INACTIVE_NDCS = "showInactiveNDCs";
    
    /** REDIRECT_PRODUCT */
    public static final String REDIRECT_PRODUCT = "redirect:/product/";

    /**
     * submitModifications
     * @param itemId itemId
     * @param tab tab
     * @return view view
     * @throws Exception Exception
     */
    @RequestMapping(value = "/changeShowActive.go", method = RequestMethod.POST)
    public String submitModifications(@PathVariable(value = ControllerConstants.ITEM_ID) String itemId, @RequestParam(
        value = ControllerConstants.TAB_KEY, required = false) String tab) throws Exception {

        EditManagedItemBean editManageItemBean = flowScope.get(EditManagedItemBean.class);
        ManagedItemVo item = editManageItemBean.getItem();

        // validate we are editing the correct item id
        if (item == null || !itemId.equals(item.getId())) {
            return REDIRECT_PRODUCT + itemId + "/edit.go";
        }

        // build the modified item
        bindParameters(item);

        boolean showInactiveNdcs = request.getParameterMap().keySet().contains("showInactiveNdcs");

        if (showInactiveNdcs) {
            flowScope.put(SHOW_INACTIVE_NDCS, Boolean.TRUE);
        } else {
            flowScope.put(SHOW_INACTIVE_NDCS, Boolean.FALSE);
        }

        // redirect to the edit page
        if (editManageItemBean.getMainRequest() == null) {
            return REDIRECT_PRODUCT + itemId + "/edit.go?" + ControllerConstants.TAB_KEY + "=" + tab;
        } else {
            return REDIRECT_PRODUCT + itemId + "/request/" + editManageItemBean.getMainRequest().getId()
                + "/manageRequest.go?" + ControllerConstants.TAB_KEY + "=" + tab;
        }
    }

}
