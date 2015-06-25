/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionResultVo;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionType;
import gov.va.med.pharmacy.peps.common.vo.FDBSearchOptionVo;
import gov.va.med.pharmacy.peps.common.vo.FdbAddVo;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.Role;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.FdbSearchValidator;
import gov.va.med.pharmacy.peps.external.common.drugdatavendor.session.DrugReferenceService;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.AddManagedItemBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.FdbAddBean;
import gov.va.med.pharmacy.peps.presentation.common.controller.bean.FdbAddStateBean;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RoleNeeded;
import gov.va.med.pharmacy.peps.service.common.session.FDBUpdateProcessService;


/**
 * FdbAddController.
 */
@Controller("fdbAddController")
@RoleNeeded(roles = { Role.PSS_PPSN_SECOND_APPROVER, Role.PSS_PPSN_VIEWER, Role.PSS_PPSN_MANAGER, Role.PSS_PPSN_SUPERVISOR })
public class FdbAddController extends PepsController {

    private static final String RESULTS_FROM_PRODUCT_SEARCH = "resultsFromProductSearch";
    private static final Logger LOG = Logger.getLogger(FdbAddController.class);
    private static final String NEW_ITEM = "_newItem";
    private static final String FDB_ADD_DETAIL_RESULTS = "fdbAddDetailResults";
    private static final String FDB_ADD_KEY = "fdbAddKey";
    private static final String FDB_ADD_VO = "fdbAddVo";
    private static final String FDB_ADD_STATE_BEAN = "fdbAddStateBean";
    private static final String FDB_UPDATE_DATE = "fdbUpdateDate";

    @Autowired
    private FDBUpdateProcessService fDBUpdateProcessService;

    @Autowired
    private DrugReferenceService drugReferenceService;

    @Autowired
    private Errors errors;

    /**
     * Constructor for FdbAddController.
     */
    public FdbAddController() {

    }

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
     * createFdbSearchOptionVO.
     * @return FDBSearchOptionVo
     */
    @ModelAttribute("fdbSearchOption")
    public FDBSearchOptionVo createFdbSearchOptionVO() {
        return new FDBSearchOptionVo();
    }

    /**
     * create FdbSearchOption Type Map.
     * @return SortedMap
     */
    @ModelAttribute("fdbSearchOptionType")
    public SortedMap<FDBSearchOptionType, String> createFdbSearchOptionTypeMap() {
        SortedMap<FDBSearchOptionType, String> fdbSearchOptionType = new TreeMap<FDBSearchOptionType, String>();

        for (FDBSearchOptionType type : FDBSearchOptionType.values()) {
            if (!type.equals(FDBSearchOptionType.LABEL_GENERIC_SEARCH)) {
                fdbSearchOptionType.put(type, getMessageSource().
                        getMessage(org.springframework.util.StringUtils.unqualify(type.getClass().getName())
                                + "." + type.name(), null, request.getLocale()));
            }
        }

        return fdbSearchOptionType;
    }

    /**
     * FdbAdd controller method.
     *
     * @param fdbAddBean fdbAddBean
     * @param pModel the model
     * @param pMode mode 
     * @return url
     */
    @RequestMapping(value = "/fdbAdd.go", method = RequestMethod.GET)
    public String fdbAdd(
        @ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean,
        @RequestParam(value = "mode", required = false) String pMode, 
        Model pModel) {

        pageTrail.clearTrail();
        pageTrail.addPage("fdbAdd", "FDB Add", true);
        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            addStateBean = new FdbAddStateBean();
            flowScope.put(FDB_ADD_STATE_BEAN, addStateBean);
        }

        if (addStateBean.isSearchState()) {
            addStateBean.setFdbSearchMode(true);
        } else {
            addStateBean.setPendingList(fDBUpdateProcessService.retrieveEPLAddList());
            addStateBean.setFdbSearchMode(false);
        }

        addStateBean.setDisplayTable(true);

        return "fdb-add";

    }

    /**
     * 
     * fdbAddPrint
     *
     * @param pType  - fdbAdd or fdbDetails
     * @param pNdc String
     * @param pModel model
     * @return url
     */
    @RequestMapping(value = "/fdbAddPrint.go", method = RequestMethod.GET)
    public String fdbAddPrint(
        @RequestParam(value = "type", required = false) String pType,
        @RequestParam(value = "ndc", required = false) String pNdc, 
        Model pModel) {

        if ("DETAILS".equals(pType)) {
            FDBSearchOptionResultVo details = (FDBSearchOptionResultVo) session.getAttribute(FDB_ADD_DETAIL_RESULTS);

            pModel.addAttribute("resultDetails", details);

            return "fdb-add-print-details";
        } else if ("POPUP".equals(pType)) {
            session.removeAttribute(FDB_ADD_DETAIL_RESULTS);
            FDBSearchOptionVo fDBSearchOptionVos = fDBUpdateProcessService.retrieveNdc(pNdc, getUser());
            List<FDBSearchOptionResultVo> details2 =
                (List<FDBSearchOptionResultVo>) fDBSearchOptionVos.getSearchOptionResults();

            if (details2.size() == 0) {
                errors.addError(ErrorKey.COULD_NOT_FIND_NDC, pNdc);
                pModel.addAttribute(ERRORS, errors.getLocalizedErrors(getLocale()));

                return "fdb-add-print-details";
            }

            session.setAttribute(FDB_ADD_DETAIL_RESULTS, details2.get(0));

            pModel.addAttribute("resultDetails", details2.get(0));

            return "fdb-add-print-details";
        } else {

            FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

            if (addStateBean == null) {
                pModel.addAttribute("pendingList", new ArrayList<FdbAddVo>());
                pModel.addAttribute("fdbSearchMode", Boolean.TRUE);
            } else {
                addStateBean.setFdbSearchMode(true);
                pModel.addAttribute("pendingList", addStateBean.getPendingList());
                pModel.addAttribute("fdbSearchMode", addStateBean.isFdbSearchMode());
            }

            return "fdb-add-print";
        }
    }

    /**
     * Fdb Search.
     *
     * @param pFdbSearchOption model Attribute
     * @param resetSearch Optional request parameter to re-run the search if the button has been clicked
     * @param pModel the model
     * @return url
     * @throws Exception exception
     */
    @RequestMapping(value = "/fdbSearch.go", method = RequestMethod.GET)
    public String fdbSearch(
        @ModelAttribute("fdbSearchOption") FDBSearchOptionVo pFdbSearchOption, 
        @RequestParam(value = "resetSearch", defaultValue = "false", required = false) boolean resetSearch, 
        Model pModel) throws Exception {
        
        List<FdbAddVo> fdbSearchList = new ArrayList<FdbAddVo>();
        FDBSearchOptionVo fdbSearchOption = null;
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyyMMdd", Locale.US);

        if (resetSearch) {
            pageTrail.clearTrail();
        }

        pageTrail.addPage("fdbSearch", "FDB Search", true);

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            addStateBean = new FdbAddStateBean();
            flowScope.put(FDB_ADD_STATE_BEAN, addStateBean);
        }

        flowScope.put(FDB_UPDATE_DATE, this.getFdbUpdateDate());
        
        if (addStateBean.getPendingList().isEmpty() && !addStateBean.isHideFromPPS()) {
            if (pFdbSearchOption.getFdbSearchOptionType() != null) {

                //validate search criteria
                Errors validationErrors = validateSearchCriteria(pFdbSearchOption);

                if (validationErrors.hasErrors()) {
                    addStateBean.setFdbSearchMode(true);

                    pModel.addAttribute(ERRORS, validationErrors.getLocalizedErrors(getLocale()));

                    return "fdb-add";
                }

                //Do search
                fdbSearchOption = drugReferenceService.performFDBSearchOption(pFdbSearchOption,
                        pFdbSearchOption.getFdbSearchOptionType(), getUser());

                pModel.addAttribute("fdbSearchOption", fdbSearchOption);

                // If there are errors, handle them.
                Errors searchErrors = handleSearchErrorMessages(fdbSearchOption);

                if (searchErrors.hasErrors()) {
                    addStateBean.setFdbSearchMode(true);

                    pModel.addAttribute(ERRORS, searchErrors.getLocalizedErrors(getLocale()));

                    return "fdb-add";
                }

                // get results
                List<FDBSearchOptionResultVo> searchResults =
                    (List<FDBSearchOptionResultVo>) fdbSearchOption.getSearchOptionResults();

                addStateBean.setCsvSearchResults(searchResults);

                for (FDBSearchOptionResultVo searchResult : searchResults) {

                    FdbAddVo fdbAddVo = new FdbAddVo();
                    fdbAddVo.setNdc(searchResult.getNDC());
                    fdbAddVo.setPackageSize(searchResult.getPackageSize());
                    fdbAddVo.setPackageType(searchResult.getPackageDescription());
                    fdbAddVo.setManufacturer(searchResult.getManufacturerDistributorName());
                    fdbAddVo.setLabelName(searchResult.getLabelName());
                    fdbAddVo.setAddDesc(searchResult.getAdditionalDescriptor());
                    fdbAddVo.setFdbProductName(searchResult.getGenericName());

                    if (StringUtils.isBlank(searchResult.getObsoleteDate())) {
                        fdbAddVo.setObsoleteDate(null);
                    } else {
                        Date convertedDate = formatter.parse(searchResult.getObsoleteDate());
                        fdbAddVo.setObsoleteDate(convertedDate);
                    }

                    fdbAddVo.setGcnSeqno(Long.valueOf(searchResult.getGCNSeqNo()));
                    fdbSearchList.add(fdbAddVo);
                }

                List<FdbAddVo> pendingList = fDBUpdateProcessService.sortPendingListByGCNSeqNo(fdbSearchList);
                addStateBean.setPendingList(pendingList);

                addStateBean.setDisplayTable(true);
            }
        }

        addStateBean.setHideFromPPS(false);
        addStateBean.setFdbSearchMode(true);

        return "fdb-add";
    }

    /**
     * Display fdb details
     *
     * @param pNdc the ndc
     * @param pModel the model
     * @param isNdcDetails is this ndc details?
     * @param popup is this a popup?
     * @return URL
     */
    @RequestMapping(value = "/fdbDetails.go", method = RequestMethod.GET)
    public String fdbDetails(@RequestParam(value = "ndc", required = false) String pNdc, @RequestParam(value = "ndcDetails",
            required = false) boolean isNdcDetails, @RequestParam(value = "popup", required = false) Boolean popup,
            Model pModel) {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            addStateBean = new FdbAddStateBean();

            if (!isNdcDetails) {
                return REDIRECT + addStateBean.getFdbAddUrl();
            }
        }

        FDBSearchOptionVo fDBSearchOptionVos = fDBUpdateProcessService.retrieveNdc(pNdc, getUser());
        List<FDBSearchOptionResultVo> details = (List<FDBSearchOptionResultVo>) fDBSearchOptionVos.getSearchOptionResults();

        if (details.size() == 0) {
            errors.addError(ErrorKey.COULD_NOT_FIND_NDC, pNdc);
            pModel.addAttribute(ERRORS, errors.getLocalizedErrors(getLocale()));

        } else {
            addStateBean.setDetails(details.get(0));
            pModel.addAttribute("resultDetails", addStateBean.getDetails());
        }

        if (isNdcDetails) {
            pModel.addAttribute("popup", popup);

            return "ndc-add-details";
        } else {
            return "fdb-add-details";
        }
    }

    /**
     * fdb Add Product Details
     * @param id ID
     * @param pModel pModel
     * @return URL
     */
    @RequestMapping(value = "/fdbAddProductDetails.go", method = RequestMethod.GET)
    public String fdbAddProductDetails(@RequestParam(value = "id", required = false) String id, Model pModel) {

        ManagedItemVo managedItemVo = fDBUpdateProcessService.retrieveManagedItem(id, EntityType.PRODUCT);
        pModel.addAttribute("resultDetails", managedItemVo);

        return "redirect:" + EntityType.PRODUCT + "/" + id + "/edit.go";

    }

    /**
     * hideExistingPPS - hides/removes entries from list that already exist in the db.
     *
     * @param fdbAddBean bean
     * @param pModel model
     * @return URL
     */
    @RequestMapping(value = "/hideExistingPPS.go", method = { RequestMethod.GET, RequestMethod.POST })
    public String hideExistingPPS(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean, Model pModel) {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbSearch.go";
        }

        boolean selected = true;

        List<String> ndcList = new ArrayList<String>();

        if (fdbAddBean.getItemIds() == null) {
            selected = false;

        } else {
            ndcList = addArrayToList(fdbAddBean.getItemIds());
        }

        List<FdbAddVo> pendingList = fDBUpdateProcessService.getRemovedIfExistList(ndcList, addStateBean.getPendingList(),
            selected);

        addStateBean.setPendingList(pendingList);
        addStateBean.setDisplayTable(true);
        addStateBean.setFdbSearchMode(true);
        addStateBean.setHideFromPPS(true);
        
        return REDIRECT + removeSearchResetParam(pageTrail.getCurrentUrl());
    }

    /**
    * fdbAddDelete - removes selected item from pending list
    * @param fdbAddBean fdbAddBean
    * @param pModel model
    * @return URL
    */
    @RequestMapping(value = "/fdbAddDelete.go", method = { RequestMethod.GET, RequestMethod.POST })
    public String fdbAddDelete(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean, Model pModel) {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbAdd.go";
        }

        addStateBean.setFdbSearchMode(false);

        if (fdbAddBean.getItemIds() == null) {
            errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED_DELETED);
            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));
        } else {
            fDBUpdateProcessService.deleteItemsFromAddList(fdbAddBean.getItemIds());
            List<FdbAddVo> updatedPendingList = fDBUpdateProcessService.retrieveEPLAddList();
            addStateBean.setPendingList(updatedPendingList);
        }

        return REDIRECT + removeSearchResetParam(pageTrail.getCurrentUrl());
        
    }

    /**
     * matchResultProductSearch
     * @param entityType entityType
     * @param itemId itemId 
     * @return URL
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{entity_type}/{itemId}/matchResultProductSearch.go", method = RequestMethod.GET)
    public String matchResultProductSearch(@PathVariable(value = "entity_type") EntityType entityType,
        @PathVariable(value = "itemId") String itemId) {
        
        ProductVo product = (ProductVo) fDBUpdateProcessService.retrieveManagedItem(itemId, entityType);
        List<ProductVo> closeProductMatches = (List<ProductVo>) flowScope.get("closeProductMatches");
        
        if (product != null) {
            boolean containsProduct = false;
            
            for (ProductVo existingMatch : closeProductMatches) {
                if (existingMatch.getId().equals(product.getId())) {
                    containsProduct = true;                    
                }
            }
            
            if (closeProductMatches.isEmpty() || !containsProduct) {
                closeProductMatches.add(product);
                flowInputScope.put(RESULTS_FROM_PRODUCT_SEARCH, closeProductMatches);
            }
        }

        return REDIRECT + "/matchResults.go";

    }

    /**
    * matchResutls - matches selected NDCs to products
    * @param fdbAddBean  fdb add bean
    * @param pModel the model
    * @return URL 
     * @throws ValidationException 
    */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/matchResults.go", method = { RequestMethod.POST, RequestMethod.GET })
    public String matchResult(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean, Model pModel) throws ValidationException {

        pageTrail.addPage("matchResults", "Match Results");

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbAdd.go";
        }

        pModel.addAttribute("printTemplate", addStateBean.getPrintTemplate());

        boolean isGetOrRedirect = "GET".equals(request.getMethod());

        // check to see if AddWizard was submitted, then remove NDCs from epl_fdb_add table.

        if (addWizardReturnStatus()) { //used for Use Existing and Blank Template buttons            

            // go to last URL - could be FDB Search
            return REDIRECT + removeSearchResetParam(pageTrail.getPreviousUrl());
        }

        if (fdbAddBean.getItemIds() == null && !isGetOrRedirect) {
            errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED);
            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));

            return REDIRECT + addStateBean.getFdbAddUrl(); // go to last URL - could be FDB Search
        }

        List<FdbAddVo> matchNDCList;
        List<ProductVo> closeMatchProducts;

        if (isGetOrRedirect) {
            matchNDCList = addStateBean.getMatchNDCList();
            closeMatchProducts = addStateBean.getCloseProductsMatches();
        } else {
            List<String> sequenceNos = getSequenceNumbers(fdbAddBean);
            boolean validationSuccess = validateSequenceNos(fdbAddBean.getGcnSeqno(), sequenceNos);

            if (!validationSuccess) {
                errors.addError(ErrorKey.SELECTED_NDCS_MUST_HAVE_SAME_GCN);
                flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));

                return REDIRECT + addStateBean.getFdbAddUrl(); // go to last URL - could be FDB Search
            }

            List<FdbAddVo> pendingList = addStateBean.getPendingList();
            List<String> ndcList = addArrayToList(fdbAddBean.getItemIds());
            matchNDCList = fDBUpdateProcessService.getFdbAddVoItemsList(ndcList, pendingList);
            addStateBean.setMatchNDCList(matchNDCList);
            closeMatchProducts = fDBUpdateProcessService.getClosestMatchProducts(sequenceNos);

        }

        if (flowScope.containsKey(RESULTS_FROM_PRODUCT_SEARCH)) {
            addStateBean.setCloseProductsMatches((List<ProductVo>) flowScope.get(RESULTS_FROM_PRODUCT_SEARCH));
        } else {
            addStateBean.setCloseProductsMatches(closeMatchProducts);
        }

        

        pModel.addAttribute("nationalMgrOrSuperRole", isNationalManagerOrSupervisorRole());

        return "matching-results";

    }

    /**
     * A helper method to remove the resetSearch request param
     * 
     * @param url to remove the parameter from
     * @return String URL with resetSearch param removed
     */
    private String removeSearchResetParam(String url) {
        return StringUtils.remove(url, "resetSearch=true");
    }

    /**
    * addWizardReturnStatus 
    * @return IGNORE = no addWizard, CANCELLED - user cancelled, SUBMITTED user submitted.
    * @throws ValidationException 
    * 
    */
    private boolean addWizardReturnStatus() throws ValidationException {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);
        ProductVo productVo = addStateBean.getProduct();

        if (productVo == null) {
            return false;
        }

        // if add wizard was canceled
        if (productVo.getId() == null || productVo.getId().endsWith(NEW_ITEM)) {
            addStateBean.setProduct(null);

            return false;

            // if addWizard was submitted
        } else if (productVo != null && productVo.getId() != null) {

            // Chris Walker 2/23/2012 - Added this piece to add the NDCS to the product just created through the template
            if (productVo.getNdcs().size() > 0) {
                String[] ndcs1 = new String[productVo.getNdcs().size()];

                for (int i = 0; i < productVo.getNdcs().size(); i++) {
                    ndcs1[i] = productVo.getNdcs().get(i).getNdc();
                }

                ProcessedItemVo processedItem =
                    fDBUpdateProcessService.addProductToNdcs(getUser(), ndcs1, productVo.getId());

                flashScope.put(ControllerConstants.WARNINGS, processedItem.getWarnings().getLocalizedErrors(getLocale()));

                deleteNdcsFromMatchAndPendingList(addStateBean);
            }

            if (!addStateBean.isFdbSearchMode()) {
                deleteNdcsFromFdbAddList(productVo);

                if (addStateBean.getMatchNDCList().size() == 0) {
                    addStateBean.setPendingList(fDBUpdateProcessService.retrieveEPLAddList());
                }
            }

            addStateBean.setProduct(null);

            return addStateBean.getMatchNDCList().size() == 0;
        }

        return false;

    }

    /**
     * 
     * Add selected NDCs to  selected Products
     *
     * @param fdbAddBean model Attribute
     * @param pModel model
     * @return URL
     * @throws ValidationException 
     */
    @RequestMapping(value = "/addNdcToProducts.go", method = RequestMethod.POST)
    public String addNdcToProducts(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean, Model pModel)
        throws ValidationException {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbAdd.go";
        }

        if (fdbAddBean.getFdbItem() == null || "0".equals(fdbAddBean.getProductId())) {

            if (fdbAddBean.getFdbItem() == null) {
                errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED);
            }

            if ("0".equals(fdbAddBean.getProductId())) {
                errors.addError(ErrorKey.REQUIRED_ITEMS_NOT_SELECTED);
            }

            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));

            return REDIRECT + "/matchResults.go";
        }

        // retrieve the selected items.
        List<String> ndcList = getSelectedItems(fdbAddBean.getNdcId(), fdbAddBean.getFdbItem());
        addStateBean.setSelectedNdcs(ndcList);

        String[] selectedNdcs = ndcList.toArray(new String[ndcList.size()]);

        ProcessedItemVo processedItem =
            fDBUpdateProcessService.addProductToNdcs(this.getUser(), selectedNdcs, fdbAddBean.getProductId());

        flashScope.put(ControllerConstants.WARNINGS, processedItem.getWarnings().getLocalizedErrors(getLocale()));

        if (addStateBean.isFdbSearchMode()) {
            addStateBean.setSearchState(true);
        } else {
            addStateBean.setSearchState(false);
            fDBUpdateProcessService.deleteItemsFromAddList(selectedNdcs);
        }

        deleteNdcsFromMatchAndPendingList(addStateBean);

        if (addStateBean.getMatchNDCList().size() > 0) {
            return REDIRECT + "/matchResults.go";
        } else {
            
            //return REDIRECT + addStateBean.getFdbAddUrl();
            return REDIRECT + pageTrail.getPreviousUrl();
            
        }

    }

    /**
     * Deletes the ndcs which have been already added to a product from the match list
     *
     * @param addStateBean the state bean for the controller
     * 
     */
    private void deleteNdcsFromMatchAndPendingList(FdbAddStateBean addStateBean) {
        List<FdbAddVo> tempMatchNDCList = new ArrayList<FdbAddVo>(addStateBean.getMatchNDCList());
        List<FdbAddVo> tempPendingNDCList = new ArrayList<FdbAddVo>(addStateBean.getPendingList());

        for (FdbAddVo ndc : tempMatchNDCList) {
            for (String selectedNdc : addStateBean.getSelectedNdcs()) {
                if (ndc.getId().equals(selectedNdc)) {
                    addStateBean.getMatchNDCList().remove(ndc);
                    break;
                }
            }
        }

        for (FdbAddVo ndc : tempPendingNDCList) {
            for (String selectedNdc : addStateBean.getSelectedNdcs()) {
                if (ndc.getId().equals(selectedNdc)) {
                    addStateBean.getPendingList().remove(ndc);
                    break;
                }
            }
        }

    }

    /**
     * create new product from template
     *
     * @param fdbAddBean  - model attribute
     * @param pModel - model
     * @return url
     */
    @RequestMapping(value = "/blankTemplate.go", method = RequestMethod.POST)
    public String blankTemplate(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean, Model pModel) {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbAdd.go";
        }

        if (fdbAddBean.getFdbItem() == null) {
            errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED);
            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));

            return REDIRECT + "/fdbAdd.go";
        }

        List<String> selectedNdcs = getSelectedItems(fdbAddBean.getNdcId(), fdbAddBean.getFdbItem());
        ProductVo productVo = fDBUpdateProcessService.createBlankTemplate(this.getUser(), selectedNdcs);

        addStateBean.setSelectedNdcs(selectedNdcs);
        addStateBean.setProduct(productVo);

        addNewProductBeanFlowInputScope(productVo);

        // tell the wizard you're adding from FDB
        flowInputScope.put(FDB_ADD_KEY, true);
        flowInputScope.put(FDB_ADD_VO, addStateBean.getMatchNDCList().get(0));

        return REDIRECT + "product/openBlankTemplate/add.go";
    }

    /**
     * Creates product from existing 
     *
     * @param fdbAddBean the model Attribute
     * @param pModel the model
     * @return URL
     */
    @RequestMapping(value = "/existingTemplate.go", method = RequestMethod.POST)
    public String createFromExisting(@ModelAttribute("fdbAddBean") FdbAddBean fdbAddBean, Model pModel) {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbAdd.go";
        }

        if (fdbAddBean.getFdbItem() == null || "0".equals(fdbAddBean.getProductId())) {

            if ("0".equals(fdbAddBean.getProductId())) {
                errors.addError(ErrorKey.REQUIRED_ITEMS_NOT_SELECTED);
            }

            if (fdbAddBean.getFdbItem() == null) {
                errors.addError(ErrorKey.NO_ITEMS_WERE_SELECTED);
            }

            flashScope.put(ERRORS, errors.getLocalizedErrors(getLocale()));

            return REDIRECT + "/matchResults.go";
        }

        List<String> ndcList = getSelectedItems(fdbAddBean.getNdcId(), fdbAddBean.getFdbItem());
        addStateBean.setSelectedNdcs(ndcList);

        String[] selectedNdcs = ndcList.toArray(new String[ndcList.size()]);

        ProductVo productVo =
            fDBUpdateProcessService.createFromExisting(this.getUser(), selectedNdcs,
                fdbAddBean.getProductId());
        productVo.setGcnSequenceNumber(fdbAddBean.getGcnSeqno()[0]); // only one product will be selected.
        productVo.setId(fdbAddBean.getProductId() + NEW_ITEM);
        addStateBean.setProduct(productVo);
        addNewProductBeanFlowInputScope(productVo);

        // tell the wizard you're adding from FDB
        flowInputScope.put(FDB_ADD_KEY, true);
        flowInputScope.put(FDB_ADD_VO, addStateBean.getMatchNDCList().get(0));

        addStateBean.setSearchState(addStateBean.isFdbSearchMode());

        return REDIRECT + "product/" + fdbAddBean.getProductId() + "/openTemplate/add.go";
    }

    /**
     * 
     * Redirects the matching page to the associate product search
     *
     * @return redirect to associate product page
     */
    @RequestMapping(value = "/searchForExistingProduct.go", method = RequestMethod.POST)
    public String searchForExistingProduct() {
        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbAdd.go";
        }

        flowInputScope.put("ndcMatches", addStateBean.getMatchNDCList());
        flowInputScope.put("closeProductMatches", addStateBean.getCloseProductsMatches());

        return REDIRECT + "/associateProduct.go?entityType=product&isFirstRun=true";
    }

    /**
     * Cancels Matching
     * @param pModel the model
     * @return URL
     */
    @RequestMapping(value = "/cancelFdbMatch.go", method = RequestMethod.POST)
    public String cancelMatching(Model pModel) {

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            return REDIRECT + "/fdbAdd.go";
        }

        return REDIRECT + pageTrail.goToPreviousUrl(addStateBean.getFdbAddUrl());

    }

    /**
     * export fdb search results to CSV file.
     * @param pModel the model
     * @param pResponse pResponse
     * @param file type of file to generate
     * @return URL
     * @throws IOException  IOException
     */
    @RequestMapping(value = "/exportToCsv.go", method = RequestMethod.GET)
    public String exportToCsv(@RequestParam(value = "file", required = false) String file,
        HttpServletResponse pResponse, Model pModel) throws IOException {
        StringBuilder sb = null;

        FdbAddStateBean addStateBean = (FdbAddStateBean) flowScope.get(FDB_ADD_STATE_BEAN);

        if (addStateBean == null) {
            addStateBean = new FdbAddStateBean();
        }

        if ("search".equals(file)) {
            sb = fDBUpdateProcessService.createFdbCSVFile(addStateBean.getCsvSearchResults());
        } else if ("add".equals(file)) {
            sb = fDBUpdateProcessService.createFdbAddCsvFile(addStateBean.getPendingList());
        } else if ("update".equals(file)) {
            sb = fDBUpdateProcessService.createFdbUpdateCsvFile(addStateBean.getUpdateList());
        } else if ("autoAdd".equals(file)) {
            sb = fDBUpdateProcessService.createFdbAutoAddCsvFile(addStateBean.getAutoAddList());
        } else if ("autoUpdate".equals(file)) {
            sb = fDBUpdateProcessService.createFdbAutoUpdateCsvFile(addStateBean.getAutoUpdateList());
        }

        if (sb.length() > 0) {
            downloadCsv(pResponse, sb);
        }

        return null;
    }

    /**
     * downloadCSV File
     *
     * @param pResponse pResponse
     * @param pSb stringBuilder
     * @throws IOException IOException
     */
    private void downloadCsv(HttpServletResponse pResponse, StringBuilder pSb)
        throws IOException {

        ServletOutputStream out = null;
        byte[] csvBytes = pSb.toString().getBytes();
        int length = csvBytes.length;
        byte[] outputByte = new byte[length];
        outputByte = csvBytes;
        String fileName = "tmpFile";

        try {
            pResponse.setContentType("application/octet-stream");
            pResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".csv");

            out = pResponse.getOutputStream();
            out.write(outputByte, 0, length);
            out.flush();
            out.close();

        } catch (Exception x) {
            out.flush();
            out.close();
            LOG.error("==>downloadCsv() " + x.getMessage());
        }
    }

    /**
     * validateSearchEntry search entry to be validated
     * @param pFdbSearchOption  pFdbSearchOption
     * @return errors
     */
    private Errors validateSearchCriteria(FDBSearchOptionVo pFdbSearchOption) {
        Errors validateErrors = new Errors();

        FdbSearchValidator validator = new FdbSearchValidator();
        validator.validate(pFdbSearchOption, validateErrors);

        return validateErrors;
    }

    /**
     * Creates a new AddManagedItemBean, sets the product, and puts that bean into flowInputScope.
     *
     * @param pProductVo the product vo
     */
    private void addNewProductBeanFlowInputScope(ProductVo pProductVo) {

        AddManagedItemBean addManagedItemBean = new AddManagedItemBean();
        addManagedItemBean.setItem(pProductVo);
        flowInputScope.put(addManagedItemBean);
    }

    /**
     * handles search Error messages
     * 
     * @param fdbSearchOption model attribute
     * @return search errors
     */
    private Errors handleSearchErrorMessages(FDBSearchOptionVo fdbSearchOption) {

        Errors searchErrors = new Errors();
        String errorMessage = fdbSearchOption.getErrorMessage();

        if (StringUtils.isNotEmpty(errorMessage)) {
            ErrorKey key = ErrorKey.FDB_ERROR_MESSAGE;
            switch (fdbSearchOption.getFdbSearchOptionType()) {
                case GCNSEQNO_SEARCH:
                    if (errorMessage.toLowerCase(Locale.US).contains("id not found")) {
                        return new Errors();
                    }

                    key = ErrorKey.COMMON_NOT_NUMERIC;
                    break;
                case LABEL_SEARCH:
                    key = ErrorKey.EMPTY_SEARCH;
                    break;
                case LABEL_GENERIC_SEARCH:
                    key = ErrorKey.EMPTY_SEARCH;
                    break;
                case GENERIC_SEARCH:
                    key = ErrorKey.EMPTY_SEARCH;
                    break;
                case NDC_SEARCH:
                    key = ErrorKey.INVALID_NDC_FORMAT;
                    break;
                default:
                    searchErrors.addError(ErrorKey.FDB_ERROR_MESSAGE, errorMessage);
                    break;
            }

            searchErrors.addError(FieldKey.FDB_SEARCH_STRING, key,
                            FieldKey.FDB_SEARCH_STRING.getLocalizedName(request.getLocale()));
        }

        return searchErrors;

    }

    /**
     * Helper method that returns list NDCs which were
     * selected 
     *
     * @param pNdcIds ndc ids
     * @param pFdbItem fdb item
     * @return list of selected ndcs
    */
    private List<String> getSelectedItems(String[] pNdcIds, String[] pFdbItem) {
        int i = 0;
        List<String> ndcList = new ArrayList<String>();

        for (int f = 0; f < pFdbItem.length; f++) {

            if (!StringUtils.isBlank(pFdbItem[f])) {
                i = Integer.valueOf((pFdbItem[f]));
                ndcList.add(pNdcIds[i]);
            }
        }

        return ndcList;
    }

    /**
     * getIndexes
     *
     * @param allItems allItems
     * @param selItems selItems
     * @return list of selected indexes
     */
    private List<Integer> getIndexes(String[] allItems, String[] selItems) {

        List<Integer> indexList = new ArrayList<Integer>();

        for (String selItem : selItems) {
            for (int i = 0; i < allItems.length; i++) {
                if (allItems[i].trim().equals(selItem.trim())) {
                    indexList.add(i);
                }
            }
        }

        return indexList;
    }

    /**
     * add Array To List
     * @param itemIds itemIds
     * @return List<String>
     */
    private List<String> addArrayToList(String[] itemIds) {
        List<String> itemList = new ArrayList<String>();

        for (String item : itemIds) {
            itemList.add(item);
        }

        return itemList;
    }

    /**
     * getSequenceNumbers
     *
     * @param fdbAddBean fdbAddBean
     * @return List<String>
    */
    private List<String> getSequenceNumbers(FdbAddBean fdbAddBean) {

        List<Integer> selectedIndexes = getIndexes(fdbAddBean.getNdcId(), fdbAddBean.getItemIds());

        Integer[] selIndexes = selectedIndexes.toArray(new Integer[selectedIndexes.size()]);

        List<String> seqList = new ArrayList<String>();

        for (Integer selIndex : selIndexes) {
            seqList.add(fdbAddBean.getGcnSeqno()[selIndex]);
        }

        return seqList;
    }

    /**
     * Validates the Selected sequence Nos.
     * Selected seq Nos must be all the same and the
     * entire group should be selected.
     * @param allSeqNos all the seq nos
     * @param pSelectedSeqNos selected seq nos
     * @return false = failed, true = success
     */
    private boolean validateSequenceNos(String[] allSeqNos, List<String> pSelectedSeqNos) {
        
        String firstSeqNo = pSelectedSeqNos.get(0);

        for (String selectedSeqNo : pSelectedSeqNos) {
            if (!selectedSeqNo.equals(firstSeqNo)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Removes FDBAdds from epl_fdb_add table after a successful addWizard submission
     * @param productVo product
     */
    private void deleteNdcsFromFdbAddList(ProductVo productVo) {

        List<String> ndcList = new ArrayList<String>();
        List<NdcVo> ndcs = productVo.getNdcs();

        for (NdcVo ndcVo : ndcs) {
            if (StringUtils.isBlank(ndcVo.getNdc())) {
                continue;
            }

            ndcList.add(ndcVo.getNdc());
        }

        String[] ndcArray = ndcList.toArray(new String[ndcList.size()]);
        fDBUpdateProcessService.deleteItemsFromAddList(ndcArray);

    }
    
    /**
     * getFdbUpdateDate
     * @return fdbUpdateDate
     */
    private String getFdbUpdateDate() {

        return fDBUpdateProcessService.retrieveFdbUpdateDate();
       
    }

    /**
     * is NationalManager Supervisor Role
     * @return true if user has PSS_PPSN_MANAGER or  role, otherwise false
     */
    private boolean isNationalManagerOrSupervisorRole() {

        return (getUser().hasRole(Role.PSS_PPSN_MANAGER) || getUser().hasRole(Role.PSS_PPSN_SUPERVISOR));
    }



}
