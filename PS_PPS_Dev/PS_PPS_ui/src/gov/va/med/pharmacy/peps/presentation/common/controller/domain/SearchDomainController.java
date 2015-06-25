/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller.domain;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.presentation.common.controller.AbstractSearchController;
import gov.va.med.pharmacy.peps.presentation.common.controller.ControllerConstants;


/**
 * The controller for data elements search 
 */
@Controller("searchDomainController")
public class SearchDomainController extends AbstractSearchController {

    //private static final Logger LOG = Logger.getLogger(SearchDomainController.class);

    /**
     * 
     * Gets the Manage PEPS > Data Elements page
     *
     *
     * @param searchCriteria search criteria 
     * @param isFirstRun is this the first time the search has run
     * @param model Spring Model
     * @return PEPS Data Elements view
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "searchDataElements.go", method = RequestMethod.GET)
    public String getManagedDomainSearch(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = "isFirstRun", required = false) String isFirstRun,
        Model model) throws ValidationException {

        pageTrail.clearTrail();
        pageTrail.addPage("domainSearch", "Search Domains", true);

        if (isFirstRun == null) {
            this.loadPrefsToDomainCrit(searchCriteria);
        } else {
            prepareSearchCriteria(searchCriteria);
            this.saveLastDomainSearch(searchCriteria);
            model.addAttribute("itemType", searchCriteria.getSearchTerms().get(0).getSearchField().getEntityType());
            model.addAttribute("items", getManagedItemService().search(searchCriteria));
            model.addAttribute("printTemplate", retrievePrintTemplate(searchCriteria, false));
        }
        
        return "managed.data.search";
    }
}
