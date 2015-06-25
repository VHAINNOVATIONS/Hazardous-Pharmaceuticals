/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.FdbAddStateBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.FdbUpdateBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.session.FDBUpdateProcessService;


/**
 * FdbUpdateController 
 * 
 */
@Controller("fdbUpdateController")
@RoleNeeded(roles = { Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
public class FdbUpdateController extends PepsController { 
   
   
    @Autowired
    private FDBUpdateProcessService fDBUpdateProcessService;
    
    @Autowired
    private Errors errors;

    /**
     * Constructor for the FdbUpdateController
     */
    public FdbUpdateController() {
    }

    /**
     * get for the FdbUpdateBean
     * @return FdbUpdateBean
     */
    @ModelAttribute("fdbUpdateBean")
    public FdbUpdateBean get() {
        return new FdbUpdateBean();
    }
    
    
    
    /**
     * FdbUpdate
     *
     * @param fdbUpdateBean the modelAttribute
     * @param pModel the model
     * @return url
     */
    @RequestMapping(value = "/fdbUpdate.go", method = RequestMethod.GET)
    public String fdbUpdate(@ModelAttribute("fdbUpdateBean") FdbUpdateBean fdbUpdateBean, Model pModel) {
        

        if (flashScope.get("isRedirectFromListMod") == null) {
            pageTrail.clearTrail();
        }

        pageTrail.addPage("fdbUpdate", "FDB Update", true);
        
        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get("fdbAddStateBean");

        if (addStateBean == null) {
            addStateBean = new FdbAddStateBean();
            flowScope.put("fdbAddStateBean", addStateBean);
            addStateBean.setUpdateList(fDBUpdateProcessService.retrieveEPLUpdateList());
        }
        
        pModel.addAttribute("printTemplate", DefaultPrintTemplateFactory.fdbUpdate());
        
        return "fdb-update";
    }
    
    
    /**
     * fdbAddDelete - removes selected item from pending list
     * @param fdbUpdateBean fdbUpdateBean
     * @param pModel model
     * @return URL
     */
    @RequestMapping(value = "/fdbUpdateDelete.go", method = RequestMethod.POST)
    public String fdbAddDelete(@ModelAttribute("fdbUpdateBean") FdbUpdateBean fdbUpdateBean, Model pModel) {
        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get("fdbAddStateBean");

        if (addStateBean == null) {
            return REDIRECT + "/fdbUpdate.go";
        }

        if (fdbUpdateBean.getItemIds() == null) {
            errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED_DELETED);
            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));
             
            return REDIRECT + "/fdbUpdate.go";
        }
        
        fDBUpdateProcessService.deleteItemsFromUpdateList(fdbUpdateBean.getItemIds());
        addStateBean.setUpdateList(fDBUpdateProcessService.retrieveEPLUpdateList());
        
        flashScope.put("isRedirectFromListMod", true);

        return REDIRECT + StringUtils.remove(pageTrail.getCurrentUrl(), "resetSearch=true");
    }
    
    /**
     * Product/Ndc edit
     *
     * @param entityType - product/NDC
     * @param model the model
     * @param id - id
     * @return URL
     * @throws Exception Exception
     */
    @RequestMapping(value = "/fdbUpdateEdit.go", method = RequestMethod.GET)
    public String fdbUpdateDetails(
            @RequestParam(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
            @RequestParam(value = "id", required = false) String id, Model model) throws Exception {
         
        return "redirect:" + entityType + "/" + id + "/edit.go";
    }
 

 
    

}
