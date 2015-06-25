/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NationalSetting;
import gov.va.med.pharmacy.peps.common.vo.NationalSettingVo;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.EditManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;
import gov.va.med.pharmacy.peps.service.common.session.ReportsService;


/**
 * Handles all partial items searches for display
 */
@Controller
public class SearchPartialItemsController extends PepsController {
    private static final Logger LOG = Logger.getLogger(SearchPartialItemsController.class);

    @SuppressWarnings("unused")
    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ManagedItemService managedItemService;

    @Autowired
    private ReportsService reportsService;
    
    @Autowired
    private ManageApplicationController manageApplicationController;
    
    @Autowired
    private SystemSettingsController systemSettingsController;

    /**
     * Home Tab GET w/ Partial Items Model Attributes
     * @param model 
     * @return the home page view
     */
    @RequestMapping(value = "/home.go", method = RequestMethod.GET)
    public String getPartialItemsForHome(Model model) {

        pageTrail.clearTrail();
        pageTrail.addPage("home", "Home", true);
        Map<String, Object> partialItemsMap = retrieveNewestPartialItems();

        model.addAttribute("partialItemsCount", partialItemsMap.get("partialItemsCount"));
        model.addAttribute("partialItems", partialItemsMap.get("partialItems"));
        model.addAttribute("printTemplate", ControllerConstants.HOME_PARTIAL_SAVE_PRINT_TEMPLATE);
        model.addAttribute("messagingStatus", getNationalSetting(NationalSetting.MESSAGE_STATUS));
        model.addAttribute("inQueue", getNationalSetting(NationalSetting.NUM_MSG_QUEUE));
        model.addAttribute(ControllerConstants.BUILD_INFO, manageApplicationController.getBuildInfo());
        
        SystemSettingsController.Preferences prefs = systemSettingsController.createPreferences();
//        LOG.debug("homepage vals: ");
//        LOG.debug("    title: .... " + prefs.getTitle());
//        LOG.debug("    body: ..... " + prefs.getBody());
//        LOG.debug("    link title: " + prefs.getLinkTitle());
//        LOG.debug("    link: ..... " + prefs.getLink());
        
        model.addAttribute("announcement", prefs);

        return "home-page";
    }

    /**
     * Manage Partial Item (Saved Work in Progress) Mapping GET
     * 
     * @param model Model
     * @return The user to the partial items tab
     */
    @RequestMapping(value = "/managePartialItem.go", method = RequestMethod.GET)
    public String getPartialItemsForTab(Model model) {

        pageTrail.clearTrail();
        pageTrail.addPage("partialItemsSearch", "Saved Work in Progress", true);
        List<PartialSaveVo> partialItems = retrievePartialItems();

        model.addAttribute("partialItemsCount", partialItems.size());
        model.addAttribute("partialItems", partialItems);
        model.addAttribute("printTemplate", ControllerConstants.PARTIAL_SAVE_PRINT_TEMPLATE);

        return "partial.items";
    }

    /**
     * delete
     *
     * @param request HttpServletRequest
     * @param multiPartialItemBean MultipleSelectItemBean
     * @return String
     * @throws ItemNotFoundException exception
     */
    @RequestMapping(value = "/managePartialItem.go", method = RequestMethod.POST)
    public String delete(
        HttpServletRequest request,
        @ModelAttribute MultipleSelectItemBean multiPartialItemBean)
        throws ItemNotFoundException {

        int index = 0;

        for (String partialId : multiPartialItemBean.getItemIds()) {
            managedItemService.deletePartial(partialId, multiPartialItemBean.getItemEntityTypes()[index++]);
        }

        return REDIRECT + pageTrail.getCurrentUrl();
    }

    /**
     * editPartialItem
     *
     * @param entityType EntityType
     * @param partialSaveId String
     * @return String
     * @throws ItemNotFoundException exception
     * @throws ValidationException exception
     */
    @RequestMapping("{" + ControllerConstants.ENTITY_TYPE + "}/{partialSaveId}/editPartialItem.go")
    public String editPartialItem(
        @PathVariable(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
        @PathVariable(value = "partialSaveId") String partialSaveId)
        throws ItemNotFoundException, ValidationException {

        PartialSaveVo partialSave = managedItemService.retrievePartialSave(partialSaveId, entityType);
        ManagedItemVo item = managedItemService.retrievePartialItem(partialSaveId, entityType);

        if (partialSave == null || item == null) {
            return REDIRECT + "/managePartialItem.go";
        } else {

            EditManagedItemBean editManagedItemBean = flowInputScope.get(EditManagedItemBean.class);

            editManagedItemBean.setPartialSave(partialSave);

            String itemId = item.getId();

            ManagedItemVo oldItem = managedItemService.retrieve(itemId, entityType);

            editManagedItemBean.setOriginalItem(oldItem);

            editManagedItemBean.setItem(item);

            return REDIRECT + "/" + entityType.toString().toLowerCase() + "/" + itemId + "/edit.go";
        }

    }

    /**
     * Retrieves a list of all partial save items that the current user is allowed to see
     * 
     * @return An array list of all partial saves
     */
    private List<PartialSaveVo> retrievePartialItems() {

        List<PartialSaveVo> partialItems = new ArrayList<PartialSaveVo>();

        // If its not a valid manager, return an empty list
        if (getUser() != null && (getUser().isEitherManager() || getUser().isAdministrativeLevel())) {
            partialItems = managedItemService.retrievePartialSaves();
        }

        return partialItems;
    }

    /**
     * 
     * Retrieve all partial saves, then limit the result to the first five.
     *
     * @return A map containing the value for partialItemsCount and partialItems, using those values as keys
     */
    private Map<String, Object> retrieveNewestPartialItems() {
        List<PartialSaveVo> partialItems = retrievePartialItems();

        int partialItemsCount = partialItems.size();

        if (partialItems.size() > PPSConstants.I5) {
            partialItems = partialItems.subList(0, PPSConstants.I5);
        }

        HashMap<String, Object> partialItemsMap = new HashMap<String, Object>();
        partialItemsMap.put("partialItemsCount", partialItemsCount);
        partialItemsMap.put("partialItems", partialItems);

        return partialItemsMap;
    }

    /**
     * Retrieve Data from National Setting
     * @param psetting National Setting 
     * @return String 
     */
    public String getNationalSetting(NationalSetting psetting) {

        List<NationalSettingVo> settingList = null;

        settingList = reportsService.getGenerateDates();
        String settingValue = null;

        for (NationalSettingVo vo : settingList) {
            if (psetting.toString().equals(vo.getKeyName())) {
                if (psetting.equals(NationalSetting.MESSAGE_STATUS)) {
                    settingValue = vo.getBooleanValue().toString();
                    break;
                }

                if (psetting.equals(NationalSetting.NUM_MSG_QUEUE)) {
                    settingValue = vo.getIntegerValue().toString();
                    break;
                }

            }
        }

        return settingValue;
    }

}
