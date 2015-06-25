/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.presentation.common.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.FdbAddBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.FdbAddStateBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.FdbReportBean;
import gov.va.med.pharmacy.peps.service.common.session.FDBUpdateProcessService;


/**
 * FdbReportController 
 * 
 */
@Controller("fdbReportController")
public class FdbReportController extends PepsController {
    
    @Autowired
    private FDBUpdateProcessService fDBUpdateProcessService;
    
    @Autowired
    private Errors errors;

    /**
     * get for the fdbAddBean.
     *
     * @return FdbAddBean 
     */
    @ModelAttribute("fdbAddBean")
    public FdbAddBean get() {
        return new FdbAddBean();
    }
    
    
    /**
     * FdbUpdate
     *
     * @param fdbReportBean the modelAttribute
     * @param pModel the model
     * @return url
     */
    @RequestMapping(value = "/fdbUpdateReport.go", method = RequestMethod.GET)
    public String fdbUpdateReport(@ModelAttribute("fdbReportBean") FdbReportBean fdbReportBean, Model pModel) {
        
        if (flashScope.get("isRedirectFromListMod") == null) {
            pageTrail.clearTrail();
        }

        pageTrail.addPage("fdbUpdateReport", "Update Report", true);
        
        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get("fdbAddStateBean");

        if (addStateBean == null) {
            addStateBean = new FdbAddStateBean();
            flowScope.put("fdbAddStateBean", addStateBean);            
            addStateBean.setAutoUpdateList(fDBUpdateProcessService.retrieveEplAutoUpdateList());
        } 

        
        addStateBean.setFdbSearchMode(true);
        addStateBean.setDisplayTable(true);
        
        pModel.addAttribute("printTemplate", DefaultPrintTemplateFactory.fdbUpdatedReport(true));
        
        return "fdb-auto-update";
    }
    
    
    /**
     * FdbAddReport - FdbAutoAdd
     *
     * @param fdbReportBean the modelAttribute
     * @param pModel the model
     * @return url
     */
    @RequestMapping(value = "/fdbAddReport.go", method = RequestMethod.GET)
    public String fdbAddReport(@ModelAttribute("fdbReportBean") FdbReportBean fdbReportBean, Model pModel) {
        
        if (flashScope.get("isRedirectFromListMod") == null) {
            pageTrail.clearTrail();
        }

        pageTrail.addPage("fdbAddReport", "Add Report", true);
        
        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get("fdbAddStateBean");
        

        if (addStateBean == null) {
            addStateBean = new FdbAddStateBean();
            flowScope.put("fdbAddStateBean", addStateBean);
            addStateBean.setAutoAddList(fDBUpdateProcessService.retrieveEplAutoAddList());
        } 
        
        addStateBean.setFdbSearchMode(true);
        addStateBean.setDisplayTable(true);

        pModel.addAttribute("printTemplate", DefaultPrintTemplateFactory.fdbAddedReport(true));

        return "fdb-auto-add";
    }
    
    
    /**
     * Display fdb details
     *
     * @param entityType entity type
     * @param model the model
     * @param id id
     * @exception Exception Exception
     * @return URL
     */
    @RequestMapping(value = "/fdbUpdateDetails.go", method = RequestMethod.GET)
    public String fdbUpdateDetails(
            @RequestParam(value = ControllerConstants.ENTITY_TYPE) EntityType entityType,
            @RequestParam(value = "id", required = false) String id, Model model) throws Exception {
       
        return "redirect:" + entityType + "/" + id + "/edit.go";

    }
   
   
   /**
    * Display fdb details
    *
    * @param id the id
    * @param pModel the model
    * @return URL
    */
    @RequestMapping(value = "/fdbAddDetails.go", method = RequestMethod.GET)
    public String fdbAddDetails(@RequestParam(value = "id", required = false) String id, Model pModel) {
       
        return "redirect:" + EntityType.NDC + "/" + id + "/edit.go";
        
    }
    
    /**
     * fdbAutoAddDelete
     * @param fdbAddBean ndc numbers 
     * @param pModel model
     * @return URL
     */
    @RequestMapping(value = "/fdbAutoAddDelete.go", method = RequestMethod.POST)
    public String fdbAutoAddDelete(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean,  Model pModel) {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get("fdbAddStateBean");

        if (addStateBean == null) {
            return REDIRECT + "/fdbAddReport.go";
        }
        
        
        
        if (fdbAddBean.getItemIds() == null) {
            errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED_DELETED);
            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));
            
            return REDIRECT + "/fdbAddReport.go";
        }

        fDBUpdateProcessService.deleteItemsFromAutoAddList(fdbAddBean.getItemIds());
        addStateBean.setAutoAddList(fDBUpdateProcessService.retrieveEplAutoAddList());

        flashScope.put("isRedirectFromListMod", true);

        return REDIRECT + "/fdbAddReport.go";

    }

    
    
    /**
     * fdbAddDelete - removes selected item from pending list
     * @param fdbAddBean item id 
     * @param pModel model
     * @return URL
     */
    @RequestMapping(value = "/fdbAutoUpdateDelete.go", method = RequestMethod.POST)
    public String fdbAddDelete(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean,  Model pModel) {
    

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get("fdbAddStateBean");

        if (addStateBean == null) {
            return REDIRECT + "/fdbUpdateReport.go";
        }
        

        if (fdbAddBean.getItemIds() == null) {
            errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED_DELETED);
            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));

            return REDIRECT + "/fdbUpdateReport.go";
        }

        fDBUpdateProcessService.deleteItemsFromAutoUpdateList(fdbAddBean.getItemIds());
        addStateBean.setAutoUpdateList(fDBUpdateProcessService.retrieveEplAutoUpdateList());

        flashScope.put("isRedirectFromListMod", true);

        return REDIRECT + "/fdbUpdateReport.go";
    }
    
}
