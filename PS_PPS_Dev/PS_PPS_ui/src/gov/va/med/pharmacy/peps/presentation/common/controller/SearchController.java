/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.SearchCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.MultipleSelectItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.item.ManageItemController;
import gov.va.med.pharmacy.peps.presentation.common.utility.UrlUtility;


/**
 * The controller for simple and data elements search
 */
@Controller("searchController")
public class SearchController extends AbstractSearchController {

//    private static final Logger LOG = Logger.getLogger(SearchController.class);

    private static final String PARAM_FIRST_RUN = "isFirstRun";
    private static final String PARAM_ENITY_TYPE = "entityType";
    private static final String URL_SEARCH_ITEMS = "searchItems.go";
    private static final String NDC_MATCHES = "ndcMatches";

    /**
     * manageItemController
     */
    @Autowired
    private ManageItemController manageItemController;

    /**
     * Default constructor
     *
     */
    public SearchController() {
        super();
    }

    /**
     * 
     * Gets the page for viewing and fetches the search results
     *
     * @param searchCriteria SearchCriteriaVO
     * @param isFirstRun String
     * @param hasEntityChanged boolean
     * @param model the model
     * 
     * @return String the view name
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = URL_SEARCH_ITEMS, method = RequestMethod.GET)
    public String searchItems(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = PARAM_FIRST_RUN, required = false) String isFirstRun,
        @RequestParam(value = "hasEntityChanged", required = false) boolean hasEntityChanged,
        Model model) throws ValidationException {

        pageTrail.clearTrail();
        pageTrail.addPage("itemSearch", "Simple Search", true);

        boolean hasSearched = StringUtils.isEmpty(isFirstRun) ? true : Boolean.parseBoolean(isFirstRun);
        String searchItemsQueryURL = "/searchItems.go?";

        if (hasSearched) {
            loadPrefsToSearchCrit(searchCriteria);
        } else if (!hasSearched && !hasEntityChanged) {
            prepareSearchCriteria(searchCriteria);
            
          //Determine if user is doing an export
            boolean export = request.getParameter(TableTagParameters.PARAMETER_EXPORTING) != null;

            if (export) {                
                model.addAttribute("items", getManagedItemService().searchFullList(searchCriteria));
            } else {
              
                model.addAttribute("items", getManagedItemService().search(searchCriteria));
            }
            
            model.addAttribute("printTemplate", retrievePrintTemplate(searchCriteria, false));
            saveLastSimpleSearch(searchCriteria);
        } else if (!hasSearched && hasEntityChanged) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.putAll(request.getParameterMap());
            parameters.remove(FieldKey.DOSAGE_FORM.toAttributeName());
            parameters.remove(FieldKey.PRODUCT_STRENGTH.toAttributeName());
            parameters.remove(PARAM_FIRST_RUN);

            model.addAttribute("visited", true);

            saveLastSimpleSearch(searchCriteria);
            StringBuffer url = new StringBuffer(searchItemsQueryURL);
            UrlUtility.appendQueryParameters(url, parameters);

            return REDIRECT + url.toString();
        }

        return "simple.search";
    }

    /**
     * Starts the multi item edit flow
     *
     * @param multiEditBean the multi item edit bean
     * @return the redirect to the item edit for the first item
     * @throws ItemNotFoundException 
     * @throws ValueObjectValidationException 
     */
    @RequestMapping(value = URL_SEARCH_ITEMS, method = RequestMethod.POST)
    public String searchItems(@ModelAttribute("multiEditBean") MultipleSelectItemBean multiEditBean) 
        throws ValueObjectValidationException, ItemNotFoundException {

        return manageItemController.startMultiItemEditFlow(multiEditBean);
    }

    /**
     * 
     * Gets the page for viewing and fetches the search results for changing the managed item parent
     *
     * @param searchCriteria The fully populated SearchCriteriaVo.
     * @param entityType EntityType
     * @param isFirstRun String
     * @param model Model
     * 
     * @return String the view name
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "/searchParent.go", method = RequestMethod.GET)
    public String searchParentItems(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = PARAM_ENITY_TYPE) EntityType entityType,
        @RequestParam(value = PARAM_FIRST_RUN, required = false, defaultValue = "true") boolean isFirstRun,
        Model model) throws ValidationException {

        pageTrail.addPage("selectParent." + entityType.name(), "selectParent", true);

        ManagedItemVo product = (ManagedItemVo) flowScope.get(ControllerConstants.ITEM_KEY);

        if (!isFirstRun) {
            prepareSearchCriteria(searchCriteria);
            model.addAttribute("items", getManagedItemService().search(searchCriteria));
            model.addAttribute("printTemplate", retrievePrintTemplate(searchCriteria, true));
        }

        model.addAttribute("cancelUrl", "/searchParentCancel.go");
        model.addAttribute("product", product);

        return entityType.getViewName() + ".selectParent.search";
    }

    /**
     * 
     * Gets the page for viewing and fetches the search results for changing the managed item parent
     *
     * @param searchCriteria SearchCriteriaVo
     * @param entityType EntityType
     * @param isFirstRun String
     * @param model Model
     * 
     * @return String the view name
     * @throws ValidationException ValidationException
     */
    @RequestMapping(value = "/associateProduct.go", method = RequestMethod.GET)
    public String asociateProduct(
        @ModelAttribute(ControllerConstants.SEARCH_CRITERIA_KEY) SearchCriteriaVo searchCriteria,
        @RequestParam(value = PARAM_ENITY_TYPE) EntityType entityType,
        @RequestParam(value = PARAM_FIRST_RUN, required = false, defaultValue = "true") boolean isFirstRun,
        Model model) throws ValidationException {

        pageTrail.addPage("associateProduct", "Associate Product", true);

        List<FdbAddVo> matchNDCList = (List<FdbAddVo>) flowScope.get(NDC_MATCHES);
        Collection<String> ndcList = new ArrayList();

        for (FdbAddVo ndc : matchNDCList) {
            ndcList.add(ndc.getLabelName() + " / " + ndc.getPackageType());
        }
        
        HashSet hs = new HashSet();
        hs.addAll(ndcList);
        ndcList.clear();
        ndcList.addAll(hs);

        if (!isFirstRun) {
            prepareSearchCriteria(searchCriteria);
            model.addAttribute("items", getManagedItemService().search(searchCriteria));
            model.addAttribute("printTemplate", DefaultPrintTemplateFactory.matchResultProductSearch());
        }

        model.addAttribute("cancelUrl", "/matchResults.go");

        if (matchNDCList != null) {
            model.addAttribute("ndcList", ndcList);
        }

        return entityType.getViewName() + ".selectParent.search";
    }

}
