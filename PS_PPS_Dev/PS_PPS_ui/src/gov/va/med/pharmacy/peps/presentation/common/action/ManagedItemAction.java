/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.action;


import gov.va.med.pharmacy.peps.common.exception.ItemNotFoundException;
import gov.va.med.pharmacy.peps.common.exception.OptimisticLockingException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.vo.ActiveIngredientVo;
import gov.va.med.pharmacy.peps.common.vo.DosageFormVo;
import gov.va.med.pharmacy.peps.common.vo.DrugUnitVo;
import gov.va.med.pharmacy.peps.common.vo.EntityType;
import gov.va.med.pharmacy.peps.common.vo.FieldKey;
import gov.va.med.pharmacy.peps.common.vo.GenericNameVo;
import gov.va.med.pharmacy.peps.common.vo.IngredientVo;
import gov.va.med.pharmacy.peps.common.vo.ItemModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ItemType;
import gov.va.med.pharmacy.peps.common.vo.LanguageChoice;
import gov.va.med.pharmacy.peps.common.vo.ManagedDataVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.MergeVo;
import gov.va.med.pharmacy.peps.common.vo.ModDifferenceVo;
import gov.va.med.pharmacy.peps.common.vo.ModificationSummaryVo;
import gov.va.med.pharmacy.peps.common.vo.NdcVo;
import gov.va.med.pharmacy.peps.common.vo.OrderableItemVo;
import gov.va.med.pharmacy.peps.common.vo.PaginatedList;
import gov.va.med.pharmacy.peps.common.vo.PartialSaveVo;
import gov.va.med.pharmacy.peps.common.vo.PatientMedicationInstructionVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedItemVo;
import gov.va.med.pharmacy.peps.common.vo.ProcessedRequestVo;
import gov.va.med.pharmacy.peps.common.vo.ProductVo;
import gov.va.med.pharmacy.peps.common.vo.RequestItemStatus;
import gov.va.med.pharmacy.peps.common.vo.RequestState;
import gov.va.med.pharmacy.peps.common.vo.RequestType;
import gov.va.med.pharmacy.peps.common.vo.RequestVo;
import gov.va.med.pharmacy.peps.common.vo.SearchRequestCriteriaVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.SortOrder;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.VuidStatusHistoryVo;
import gov.va.med.pharmacy.peps.common.vo.datafield.ListDataField;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.DefaultPrintTemplateFactory;
import gov.va.med.pharmacy.peps.common.vo.printtemplate.PrintTemplateVo;
import gov.va.med.pharmacy.peps.common.vo.validator.ErrorKey;
import gov.va.med.pharmacy.peps.common.vo.validator.Errors;
import gov.va.med.pharmacy.peps.common.vo.validator.ValidationError;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.Authorize;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.ConversationScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.FlashScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.FlowScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.RequestScope;
import gov.va.med.pharmacy.peps.presentation.common.spring.annotation.SessionScope;
import gov.va.med.pharmacy.peps.presentation.common.utility.ManagedDataWizardController;
import gov.va.med.pharmacy.peps.service.common.session.ManagedItemService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Actions performed on ManagedItemVo.
 */
public class ManagedItemAction extends PepsAction {
    public static final String TAB_ELEMENT_ID = "tabElementId";
    public static final String ALPHA_TAB = "alpha_tab";
    public static final String REQUESTS_TAB = "requests_tab";
    public static final String CHILDREN_TAB = "children_tab";
    public static final String HISTORY_TAB = "history_tab";
    public static final String REPORTS_TAB = "reports_tab";

    public static final String COMMON_TAB = "common_tab";
    public static final String OPTIONS_TAB = "options_tab";
    public static final String APPLICATION_DATA_TAB = "application_data_tab";
    public static final String NATIONAL_VIEW_TAB = "national_view_tab";
    public static final String DRUG_DATA_TAB = "drug_data_tab";
    public static final String DISPENSE_DATA_TAB = "dispense_data_tab";
    public static final String ADMINISTRATION_DATA_TAB = "administration_data_tab";
    public static final String WARNING_LABELS_TAB = "warning_labels_tab";

    public static final String DETAILS_TAB = "details_tab";
    public static final String SYNONYMS_TAB = "synonyms_tab";
    public static final String SAFETY_TAB = "safety_tab";
    public static final String LABS_TAB = "labs_tab";

    public static final String NDCMAIN_TAB = "ndcmain_tab";
    public static final String NDCPRICE_TAB = "ndcprice_tab";

    public static final String NDC_LIST_TABLE = "ndcList";
    public static final String PRODUCT_LIST_TABLE = "productList";

    public static final String OUTPATIENT_TAB = "outpatient_tab";
    public static final String INPATIENT_UNITDOSE_TAB = "inpatient_unitdose_tab";
    public static final String WARD_STOCK_TAB = "ward_stock_tab";
    public static final String DRUG_ACCOUNTABILITY_TAB = "drug_accountability_tab";
    public static final String CONTROLLED_SUBSTANCE_TAB = "controlled_substance_tab";
    public static final String NONVA_MED_TAB = "nonva_med_tab";
    public static final String CMOP_MARKUNMARK_TAB = "cmop_MarkUnmark_tab";
    public static final String ENTEREDIT_DOSAGES_TAB = "enteredit_Dosages_tab";

    public static final String PHARMACY_SYSTEM_PARAMETERS = "pharmcy_system_parameters";
    public static final String PHARMACY_SYSTEM_OHTER_LANGUAGE = "pharmacy_system_other_language";

    private static final String WIZARD_ONE = "wizard1";
    private static final String WIZARD_TWO = "wizard2";
    private static final String WIZARD_THREE = "wizard3";
    private static final String WIZARD_FOUR = "wizard4";
    private static final String WIZARD_FIVE = "wizard5";
    private static final String WIZARD_SIX = "wizard6";
    private static final String WIZARD_SEVEN = "wizard7";
    private static final String WIZARD_EIGHT = "wizard8";
    private static final String WIZARD_NINE = "wizard9";
    private static final String WIZARD_TEN = "wizard10";
    private static final String WIZARD_ELEVEN = "wizard11";
    private static final String WIZARD_TWELVE = "wizard12";
    private static final String WIZARD_THIRTEEN = "wizard13";
    private static final String WIZARD_FOURTEEN = "wizard14";
    private static final String WIZARD_FIFTEEN = "wizard15";
    private static final String WIZARD_SIXTEEN = "wizard16";
    private static final String WIZARD_SEVENTEEN = "wizard17";
    private static final String WIZARD_EIGHTEEN = "wizard18";
    private static final String WIZARD_NINETEEN = "wizard19";

    private static final long serialVersionUID = 1L;
    private static final PrintTemplateVo NDC_LIST_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultNdcList();
    private static final PrintTemplateVo PRODUCT_LIST_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultProductList();
    private static final PrintTemplateVo PRODUCT_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultProductSearch();
    private static final PrintTemplateVo NDC_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultNdcSearch();
    private static final PrintTemplateVo OI_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory.defaultOrderableItemSearch();
    private static final PrintTemplateVo DOMAIN_SEARCH_PRINT_TEMPLATE = DefaultPrintTemplateFactory
        .defaultManagedDataSearch();

    @FlashScope
    private Errors warnings;

    @FlowScope
    private String itemId;

    @FlowScope
    private String parentAttribute;

    @Authorize
    @FlowScope
    private ManagedItemVo item;

    @FlowScope
    private ManagedItemVo childitem;

    @ConversationScope
    private List<PartialSaveVo> partialItems;

    @ConversationScope
    private List<ManagedItemVo> items;

    @FlowScope
    private ManagedItemVo oldItem;

    @FlowScope
    private EntityType itemType;

    @FlowScope
    private Collection<ModDifferenceVo> modDifferences;
    
    @ConversationScope
    private int isFirst = 1;

	// added to support use of table.tag for VA Data Fields from national modSummary.jsp
    private PrintTemplateVo modSummaryPrintTemplate = DefaultPrintTemplateFactory.defaultEditableModificationSummary();

    // added to support use of table.tag for local Non-Editable VA Data Fields from local modSummary.jsp
    private PrintTemplateVo nonEditableModSummaryPrintTemplate = DefaultPrintTemplateFactory
        .defaultNoneditableModificationSummary();

    // added to support use of table.tag for VA Data Fields from local modSummary.jsp
    private PrintTemplateVo editableModSummaryPrintTemplate = DefaultPrintTemplateFactory
        .defaultEditableModificationSummary();

    @FlowScope
    private Collection<ItemModDifferenceVo> itemModDifferences;

    private PrintTemplateVo iahPrintTemplate = DefaultPrintTemplateFactory.defaultItemAuditHistoryConfiguration();

    @ConversationScope
    private RequestVo mainRequest;

    // partial save comment, also used for problem report text
    // saved in ConversationScope so that the same comment can be used on a second bookmark
    @ConversationScope
    private String comment;

    private ManagedItemService managedItemService;

    @ConversationScope
    private SearchTemplateVo searchTemplate;

    @ConversationScope
    @RequestScope
    private String tabElementId;

    @FlashScope
    @RequestScope
    private String fieldKey;

    @FlashScope
    @RequestScope
    private String ognlKey;

    // This variable is slightly misnamed, but its sole purpose is to store the entity type
    // and I want to avoid naming conflicts
    @FlashScope
    private ManagedItemVo parentEntityType;

    @ConversationScope
    private boolean isFirstItem;

    @FlowScope
    private MergeVo mergeItem;

    @ConversationScope
    private ManagedDataWizardController wizard;

    @ConversationScope
    private List<FieldKey> wizardFields;

    @ConversationScope
    private int partialItemsCount;

    @SessionScope
    private SearchRequestCriteriaVo searchRequestCriteria; // shared by RequestAction during Domain Request searches

    @FlowScope
    private PatientMedicationInstructionVo patientMedicationInstruction; // shared with DrugReferenceAction for print PMI

    @FlowScope
    private LanguageChoice spanishText; // shared with DrugReferenceAction for print PMI

    /**
     * 
     * @return wizardFields property
     */
    public List<FieldKey> getWizardFields() {
        return wizardFields;
    }

    /**
     * 
     * @param wizardFields wizardFields property
     */
    public void setWizardFields(List<FieldKey> wizardFields) {
        this.wizardFields = wizardFields;
    }

    /**
     * Retrieve the pharmacy system.
     * 
     * @return result of {@link #retrieve()}
     * @throws ItemNotFoundException if cannot find the pharmacy system for this site
     */
    public String retrievePharmacySystem() throws ItemNotFoundException {
        this.itemId = getEnvironmentUtility().getSiteNumber();
        this.itemType = EntityType.PHARMACY_SYSTEM;

        return retrieve();
    }

    /**
     * If the {@link ValidationError} is a field error, {@link ValidationError#isFieldError()}, then call
     * {@link #addFieldError(String, String)} using the {@link FieldKey} as the name and the localized error message.
     * Otherwise call {@link #addActionError(String)} using the localized error message.
     * <p>
     * This method overrides the default PepsAction implementation to allow for localization of managedItem {@link FieldKey}
     * errors.
     * 
     * @param error {@link ValidationError} to add
     */
    @Override
    public void addError(ValidationError error) {
        if (error.isFieldError()) {
            addFieldError(error.getFieldKey().getKey(), error.getLocalizedError(getLocale(), item.getEntityType()));
        }
        else {
            addActionError(error.getLocalizedError(getLocale()));
        }
    }

    /**
     * Compute product uniqueness fields.
     * 
     * @return SUCCESS
     */
    public String computeProductUniquenessFields() {
        SortedMap<String, ActiveIngredientVo> ingredientsMap = new TreeMap<String, ActiveIngredientVo>();

        if (item instanceof ProductVo) {
            ProductVo product = (ProductVo) item;
            Collection<ActiveIngredientVo> ingredients = product.getActiveIngredients();
            String vaProductName = "NA";
            String vaPrintName = "NA";
            String dosageFormVaProductName = "NA";
            String dosageFormVaPrintName = "NA";
            String dosageFormString = "NA";
            OrderableItemVo orderableItem = product.getOrderableItem();
            DosageFormVo dosageForm = null;

            if (orderableItem != null) {
                dosageForm = orderableItem.getDosageForm();
            }

            if (orderableItem != null && dosageForm != null && dosageForm.getDosageFormName() != null
                && dosageForm.getDosageFormName().trim().length() > 0) {
                dosageFormString = dosageForm.getDosageFormName();

                // check if the dosage form value has a "," in it
                String[] strArray = dosageFormString.split(",");

                if (strArray.length > 1) {
                    String tmp = strArray[0];
                    strArray[0] = strArray[1];
                    strArray[1] = tmp;
                    dosageFormVaPrintName = strArray[0] + " " + strArray[1];
                }
                else {
                    dosageFormVaPrintName = dosageFormString;
                }

                dosageFormVaProductName = dosageFormString;

            }

            if (!ingredients.isEmpty()) {
                for (ActiveIngredientVo ingredient : ingredients) {

                    if ((ingredient != null) && (ingredient.getIngredient() != null)) {
                        ingredientsMap.put(ingredient.getIngredient().getValue(), ingredient);
                    }

                }

                // now iterate through the set and compute the string
                StringBuffer buf = new StringBuffer();
                ActiveIngredientVo activeIngredient;

                if (!ingredientsMap.isEmpty()) {
                    Set keys = ingredientsMap.keySet();
                    Iterator itr = keys.iterator();

                    while (itr.hasNext()) {

                        // initial values were NA - should be blank
                        String ingredientName = "";
                        String ingredientStrength = "";
                        String ingredientUnit = "";
                        activeIngredient = ingredientsMap.get(itr.next());
                        IngredientVo ing = activeIngredient.getIngredient();
                        DrugUnitVo ingUnit = activeIngredient.getDrugUnit();

                        if (ing != null && ing.getValue() != null && ing.getValue().trim().length() > 0) {
                            ingredientName = ing.getValue();
                        }

                        if (activeIngredient.getStrength() != null && activeIngredient.getStrength().trim().length() > 0) {
                            ingredientStrength = activeIngredient.getStrength();
                        }

                        if (ingUnit != null && ingUnit.getValue() != null && ingUnit.getValue().trim().length() > 0) {
                            ingredientUnit = ingUnit.getValue();
                        }

                        buf.append(ingredientName + " " + ingredientStrength + ingredientUnit + "/");

                    }// end while

                    if (buf.length() > 0) {

                        buf.replace(buf.length() - 1, buf.length(), " ");
                        vaProductName = buf.toString() + dosageFormVaProductName;
                        vaPrintName = buf.toString() + dosageFormVaPrintName;

                    }

                }// end if

            }// end if

            if (vaPrintName.length() > 40) {
                product.setVaPrintName(vaPrintName.substring(0, 40));
            }
            else {
                product.setVaPrintName(vaPrintName);
            }

            if (vaProductName.length() > 63) {
                product.setVaProductName(vaProductName.substring(0, 64));
            }
            else {
                product.setVaProductName(vaProductName);
            }

        }

        return SUCCESS;
    }

    /**
     * Invoked when the 'Approve' button is hit from the page as part of the "Add" Request review flow.
     * 
     * @return SUCCESS
     * @throws ValidationException
     */
    public String approveRequest() throws ValidationException {
        this.setModDifferences(oldItem.compareDifferences(item));
        ProcessedRequestVo processedRequest = managedItemService
            .approveRequest(item, mainRequest, modDifferences, getUser());
        this.item = processedRequest.getItem();
        this.setMainRequest(processedRequest.getRequest());

        this.warnings = processedRequest.getWarnings();

        return SUCCESS;
    }

    /**
     * Commit the modifications to the ManagedItemVo and the ManagedItemVo itself.
     * 
     * @return SUCCESS
     * @throws ValidationException if error validating data in the ManagedItemVo {@link #item}
     * @throws OptimisticLockingException if revision ID from ManagedItemVo in database is different
     */
    public String commitModifications() throws ValidationException, OptimisticLockingException {
        ProcessedItemVo processedItem = managedItemService.commitModifications(modDifferences, oldItem, getUser());
        this.item = processedItem.getItem();
        this.warnings = processedItem.getWarnings();

        if (item != null) {
            this.itemId = item.getId();
            this.itemType = item.getEntityType();
            deleteCurrentTableSelection(items);
            isFirstItem = false;
        }

        return SUCCESS;
    }

    /**
     * Commit the Inactivation, verifies a reason was entered to the ManagedItemVo and the ManagedItemVo itself.
     * 
     * @return SUCCESS
     * @throws ValidationException if error validating data in the ManagedItemVo {@link #item}
     * @throws OptimisticLockingException if revision ID from ManagedItemVo in database is different
     */
    public String commitInactivation() throws ValidationException, OptimisticLockingException {

        boolean hasReason = false;

        for (ModDifferenceVo mod : modDifferences) {
            hasReason |= (mod.getModificationReason() != null && mod.getModificationReason().trim().length() > 0);
        }

        if (hasReason) {
            return commitModifications();
        }
        else {
            Errors errors = new Errors();

            errors.addError(FieldKey.MODIFICATION_REASON, ErrorKey.INACTIVATION_REASON_REQUIRED);

            throw new ValueObjectValidationException(errors);
        }

    }

    /**
     * Invoked when the 'submit' button is hit from the page as part of the mod Request review flow.
     * 
     * @return SUCCESS
     * 
     * @throws ValidationException if error validating data
     */
    public String submitModRequests() throws ValidationException {
        this.setModDifferences(oldItem.compareDifferences(item));

        ProcessedRequestVo processedRequest = managedItemService.submitRequest(item, mainRequest, modDifferences, getUser());
        this.item = processedRequest.getItem();
        this.setMainRequest(processedRequest.getRequest());
        this.warnings = processedRequest.getWarnings();

        return SUCCESS;
    }

    /**
     * Commit request and item modification changes to the database as the final step in the review flow.
     * 
     * @return SUCCESS
     * @throws ValidationException if either the RequestVo or the updated ManagedItemVo is invalid for request processing.
     */
    public String commitRequest() throws ValidationException {
        UserVo saveUser = mainRequest.getLastReviewer();
        mainRequest.setLastReviewer(this.getUser());
        ProcessedRequestVo processedRequest;

        // Need to catch validation exceptions and reset the user to the old user for checking on the
        // request mod screen for infor messages.
        try {
            processedRequest = managedItemService.commitRequest(oldItem, mainRequest, modDifferences, getUser());
        }
        catch (ValidationException e) {
            mainRequest.setLastReviewer(saveUser);
            throw e;
        }

        this.item = processedRequest.getItem();
        this.setMainRequest(processedRequest.getRequest());
        this.warnings = processedRequest.getWarnings();
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        deleteCurrentTableSelection(items);

        return SUCCESS;
    }

    /**
     * 
     * @return comment property
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @return fieldKey property
     */
    public String getFieldKey() {
        return fieldKey;
    }

    /**
     * 
     * @return item property
     */
    public ManagedItemVo getItem() {
        return item;
    }

    /**
     * 
     * @return itemId property
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 
     * @return itemType property
     */
    public EntityType getItemType() {
        return itemType;
    }

    /**
     * 
     * @return request property
     */
    public RequestVo getMainRequest() {
        return mainRequest;
    }

    /**
     * 
     * @return modDifferences property
     */
    public Collection<ModDifferenceVo> getModDifferences() {
        return modDifferences;
    }

    /**
     * Getter for OGNL to resolve {@link #NDC_LIST_PRINT_TEMPLATE}
     * 
     * @return NDC_LIST_PRINT_TEMPLATE property
     */
    public PrintTemplateVo getNdcListPrintTemplate() {
        return NDC_LIST_PRINT_TEMPLATE;
    }

    /**
     * 
     * @return oldItem property
     */
    public ManagedItemVo getOldItem() {
        return oldItem;
    }

    /**
     * Getter for OGNL to resolve {@link #PRODUCT_LIST_PRINT_TEMPLATE}
     * 
     * @return PRODUCT_LIST_PRINT_TEMPLATE property
     */
    public PrintTemplateVo getProductListPrintTemplate() {
        return PRODUCT_LIST_PRINT_TEMPLATE;
    }

    /**
     * 
     * @return tabElementId property
     */
    public String getTabElementId() {
        return tabElementId;
    }

    /**
     * Used to indicate no processing of the request details should occur.
     * 
     * @return SUCCESS
     */
    public String markUnderReview() {
        this.setMainRequest(managedItemService.markRequestUnderReview(item, mainRequest, getUser()));
        this.setModDifferences(oldItem.compareDifferences(item));

        return SUCCESS;
    }

    /**
     * Used to indicate that the request needs a PEPS second review.
     * 
     * @return SUCCESS
     */
    public String markForPsr() {
        this.setMainRequest(managedItemService.markRequestForPsr(item, mainRequest, getUser()));

        return SUCCESS;
    }

    /**
     * Create a new ManagedItemVo template as the type of the current item's child.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    public String openBlankChildTemplate() throws ItemNotFoundException {
        this.item = managedItemService.retrieveBlankChildTemplate(itemId, itemType.getParent());
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        this.comment = null;

        return SUCCESS;
    }

    /**
     * Create a new ManageItemVo local template. Only applies for ManagedItemVo types that have a national/local
     * relationship, which currently is only OrderableItemVo.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    public String openBlankLocalTemplate() throws ItemNotFoundException {
        this.item = managedItemService.retrieveBlankLocalTemplate(itemId, itemType);
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        this.comment = null;

        return SUCCESS;
    }

    /**
     * Creates a new ManagedItemVo template with the appropriate parent
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    public String openBlankTemplate() throws ItemNotFoundException {
        this.item = managedItemService.retrieveBlankTemplate(itemType);
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        this.comment = null;

        if (itemType.isDomainType()) {
            this.wizard = new ManagedDataWizardController(itemType, (ManagedDataVo) item);
            this.wizardFields = wizard.getNextPageFields();
        }

        return SUCCESS;
    }

    /**
     * Modifies the current ManageItemVo to use as a local template. Only applies for ManagedItemVo types that have a
     * national/local relationship, which currently is only OrderableItemVo.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    public String openLocalTemplate() throws ItemNotFoundException {
        this.item = managedItemService.retrieveLocalTemplate(itemId, itemType);
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        this.comment = null;

        return SUCCESS;
    }

    /**
     * Retrieves a list of all partial save items that the current user is allowed to see
     * 
     * @return SUCCESS
     */
    public String retrievePartialItems() {

        // If its not a valid manager, return an empty list
        if (getUser().isEitherManager() || getUser().isAdministrativeLevel()) {
            this.partialItems = managedItemService.retrievePartialSaves();
        }
        else {
            this.partialItems = new ArrayList<PartialSaveVo>();
        }

        this.searchTemplate = new SearchTemplateVo();
        this.searchTemplate.setPrintTemplate(DefaultPrintTemplateFactory.defaultPartialSave());

        return SUCCESS;
    }

    /**
     * Retrieve all partial saves, then limit the result to the first five.
     * 
     * @return SUCCESS
     */
    public String retrieveNewestPartialItems() {
        retrievePartialItems();

        this.partialItemsCount = partialItems.size();

        if (partialItems.size() > 5) {
            this.partialItems = partialItems.subList(0, 5);
        }

        return SUCCESS;
    }

    /**
     * Modifies the current ManagedItemVo to use as a template.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find the ManagedItemVo with the given ID
     */
    public String openTemplate() throws ItemNotFoundException {
        this.item = managedItemService.retrieveTemplate(itemId, itemType);
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        this.comment = null;

        return SUCCESS;
    }

    /**
     * Get the {@link #itemId} and {@link #itemType} from the current selected item in the search table.
     * 
     * @return SUCCESS
     * @throws IllegalArgumentException
     */
    public String parseUniqueId() throws IllegalArgumentException {

        this.item = getCurrentTableSelection(items);

        if (item != null) {
            this.itemId = item.getId();
            this.itemType = item.getEntityType();
        }
        else {

            // No items selected handled by the javascript attached to the table.tag edit items fu
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * Reject a problem report.
     * 
     * @return SUCCESS
     * @throws ValidationException ValidationException
     */
    public String rejectProblemReport() throws ValidationException {
        this.setMainRequest(managedItemService.rejectProblemReport(item, mainRequest, getUser()));

        return SUCCESS;
    }

    /**
     * Invoked when the 'Reject' button is hit from the page as part of the "Add" Request review flow.
     * 
     * @return SUCCESS
     */
    public String rejectRequest() {
        this.setModDifferences(oldItem.compareDifferences(item));
        this.setMainRequest(managedItemService.rejectRequest(item, mainRequest, modDifferences, getUser()));

        return SUCCESS;
    }

    /**
     * Retrieve the item using the provided {@link #itemId} and {@link ItemType}. Clone the result and set the cloned value
     * to {@link #oldItem}.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find ManagedItemVo with the given ID
     */
    public String retrieve() throws ItemNotFoundException {

        this.item = managedItemService.retrieve(itemId, itemType, getUser());

        // Per CR1959 "Show Updated Values During Modification Review", apply 'approved' changes to the item so the
        // next PNM reviewer 'sees' the item's data fields set as they would look if the request completed.
        if (mainRequest != null && getEnvironment().isNational()) {
            managedItemService.applyChanges(this.item, mainRequest, getUser());
        }

        this.oldItem = item.copy();

        // Set item to read only if pending and out of request and environment = national
        // Also handles peps second reviewers looking at items without requests
        // TODO - Mdittmer - 2/4/09 - Move this to the Service call. Authorization should not be in the action!
        if (mainRequest == null
            && (RequestItemStatus.PENDING == item.getRequestItemStatus() && getEnvironment().isNational() || getUser()
                .isOnlySecondReviewer())) {
            item.setReadOnlyInstance(true);
        }

        // Additions should go to the default tab for that item type
        if (REQUESTS_TAB.equals(tabElementId) && getMainRequest().isAddition()) {
            this.tabElementId = null;
        }

        // Added so that the correct tab is highlighted during requests
        if (REQUESTS_TAB.equals(tabElementId) && getMainRequest().isProblemReport()) {
            this.tabElementId = "reports_tab";
        }

        defaultTabElementId();

        return SUCCESS;
    }

    /**
     * Set the default tab to display if one has not yet been requested.
     */
    private void defaultTabElementId() {
        if (tabElementId == null) {
            switch (itemType) {
                case ORDERABLE_ITEM:
                    this.tabElementId = DETAILS_TAB;
                    break;

                case PRODUCT:
                    this.tabElementId = NATIONAL_VIEW_TAB;
                    break;

                case NDC:
                    this.tabElementId = NDCMAIN_TAB;
                    break;

                case PHARMACY_SYSTEM:
                    this.tabElementId = PHARMACY_SYSTEM_PARAMETERS;
                    break;

                default:
                    this.tabElementId = ALPHA_TAB;
                    break;
            }
        }
    }

    /**
     * 
     * @return searchTemplate property
     */
    public SearchTemplateVo getSearchTemplate() {
        return searchTemplate;
    }

    /**
     * 
     * @param searchTemplate searchTemplate property
     */
    public void setSearchTemplate(SearchTemplateVo searchTemplate) {
        this.searchTemplate = searchTemplate;
    }

    /**
     * Retrieve a partially saved ManagedItemVo.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if partially saved ManagedItemVo with given ID cannot be found
     * @throws ValidationException if an optimistic locking error is found
     */
    public String retrievePartial() throws ItemNotFoundException, ValidationException {

        PartialSaveVo partialSaveVo = managedItemService.retrievePartialSave(itemId, itemType);

        this.item = managedItemService.retrievePartialItem(itemId, itemType);

        // be sure the item still existed when we retrieved
        if (this.item != null) {
            this.itemId = item.getId();
            this.itemType = item.getEntityType();

            if (this.itemId != null) {
                this.oldItem = managedItemService.retrieve(this.itemId, this.itemType);
            }
            else {
                this.oldItem = item.copy();
            }

            this.comment = partialSaveVo.getComment();

            defaultTabElementId();

            return SUCCESS;
        }
        else {

            // if not return an error

            return ERROR;
        }
    }

    /**
     * Save a partially entered ManagedIteVo.
     * 
     * @return SUCCESS
     * @throws ValidationException if comment is null or empty
     */
    public String savePartial() throws ValidationException {

        if (this.comment == null || this.comment.trim().length() == 0) {
            Errors errors = new Errors();

            errors.addError(FieldKey.PARTIAL_SAVE, ErrorKey.EMPTY_COMMENT);

            throw new ValueObjectValidationException(errors);
        }

        this.item = managedItemService.savePartial(item, comment, getUser());
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        this.comment = null;
        deleteCurrentTableSelection(items);

        return SUCCESS;
    }

    /**
     * Save a problem report.
     * 
     * @return SUCCESS
     */
    public String submitProblemReport() {
        managedItemService.submitProblemReport(item, comment, getUser());

        return SUCCESS;
    }

    /**
     * Retrieve the parent with the given itemId and itemType, then set the current item's (the child) parent.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if parent ID not found
     */
    public String selectParent() throws ItemNotFoundException {
        this.item = managedItemService.selectPartent(childitem, itemId, itemType);
        this.itemId = item.getId();
        this.itemType = item.getEntityType();

        return SUCCESS;
    }

    /**
     * 
     * @param comment comment property
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 
     * @param fieldKey fieldKey property
     */
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * 
     * @param item item property
     */
    public void setItem(ManagedItemVo item) {
        this.item = item;
    }

    /**
     * 
     * @param itemId itemId property
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 
     * @param itemType itemType property
     */
    public void setItemType(EntityType itemType) {
        this.itemType = itemType;
    }

    /**
     * 
     * @param mainRequest mainRequest property
     */
    public void setMainRequest(RequestVo mainRequest) {
        this.mainRequest = mainRequest;
    }

    /**
     * If this message is populated in the jsp mod summary screen so the user knows that the current request will stay in the
     * pending 2nd review state even if they click the Accept changes button
     * 
     * @return String message
     */
    public String getRequestStateStatusMessage() {
        String message = "";

        if (mainRequest != null) {

            // if state is pending second review
            if (RequestState.PENDING_SECOND_REVIEW.equals(mainRequest.getRequestState())) {

                // If this same user approved the request last time
                if (mainRequest.getLastReviewer().getUsername().equals(getUser().getUsername())) {
                    message = getText("stay.message.sameuser");

                }
                else {
                    if (RequestType.ADDITION.equals(mainRequest.getRequestType())) {

                        // TODO: Add if you changed previously pending mods
                        if (modDifferences != null && modDifferences.size() > 0) {
                            message = getText("stay.message.add.changed");
                        }

                        if (mainRequest.hasConflicts()) {
                            message = getText("stay.message.add.conflict");
                        }

                    }

                    if (RequestType.MODIFICATION.equals(mainRequest.getRequestType())) {

                        // Iterates through the modDifferences to see if any are Second Review data fields
                        for (ModDifferenceVo dif : modDifferences) {
                            if (dif.getDifference().getFieldKey().isRequiresSecondApproval()) {
                                message = getText("stay.message.modify.changed");
                            }

                            if (mainRequest.hasConflicts()) {
                                message = getText("stay.message.modify.conflict");
                            }
                        }
                    }
                }
            }
        }

        return message;
    }

    /**
     * 
     * @param managedItemService managedDataService property
     */
    public void setManagedItemService(ManagedItemService managedItemService) {
        this.managedItemService = managedItemService;
    }

    /**
     * 
     * @param modDifferences modDifferences property
     */
    public void setModDifferences(Collection<ModDifferenceVo> modDifferences) {
        this.modDifferences = modDifferences;
    }

    /**
     * 
     * @param oldItem oldItem property
     */
    public void setOldItem(ManagedItemVo oldItem) {
        this.oldItem = oldItem;
    }

    /**
     * 
     * @param tabElementId tabElementId property
     */
    public void setTabElementId(String tabElementId) {
        this.tabElementId = tabElementId;
    }

    /**
     * 
     * Activate the domain
     * 
     * @return SUCCESS
     * @throws ValidationException
     */
    public String activate() throws ValidationException {
        if (item.getEntityType().isDomainType()) {
            ((ManagedDataVo) item).activate();
        }

        this.setModDifferences(oldItem.compareDifferences(item));

        return SUCCESS;
    }

    /**
     * Submit the ManagedItemVo for saving to the database.
     * 
     * If the current ManagedItemVo is a new instance, then call ManagedItemService#create() and return END. Otherwise, it is
     * an update and will compare the current ManagedItemVo to be saved to the old ManagedItemVo and return SUCCESS.
     * 
     * @return If the ManagedItemVo is a new instance, END. Otherwise, SUCCESS.
     * @throws ValidationException if error validating ManagedItemVo {@link #item}
     */
    public String inactivate() throws ValidationException {
        if (item.getEntityType().isDomainType()) {
            item.inactivate();
        }

        this.setModDifferences(oldItem.compareDifferences(item));

        return SUCCESS;
    }

    /**
     * Submit the modified ManagedItemVo for saving to the database.
     * 
     * Compare the current ManagedItemVo to be saved to the old ManagedItemVo and return SUCCESS.
     * 
     * @return If the ManagedItemVo is a new instance, END. Otherwise, SUCCESS.
     * @throws ValidationException if error validating ManagedItemVo {@link #item}
     */
    public String submitModifications() throws ValidationException {

        // Set effective data hack
        VuidStatusHistoryVo effectiveDate = new VuidStatusHistoryVo();

        effectiveDate.setEffectiveDateTime(new Date(System.currentTimeMillis());
        effectiveDate.setStatus(false);
        List<VuidStatusHistoryVo> list = new ArrayList<VuidStatusHistoryVo>();
        list.add(effectiveDate);
        ((ProductVo) item).setEffectiveDates(list);
        ((ProductVo) oldItem).setEffectiveDates(list);
        // End effective date hack

        ModificationSummaryVo modificationSummary = managedItemService.submitModifications(oldItem, item, getUser());
        this.setModDifferences(modificationSummary.getModDifferences());
        this.warnings = modificationSummary.getWarnings();

        return SUCCESS;
    }

    /**
     * Submit a new ManagedItemVo for saving to the database.
     * 
     * @return SUCCESS or WARN (if there are warnings)
     * @throws ValidationException if error validating the ManagedItemVo
     */
    public String submitAdd() throws ValidationException {
        ProcessedItemVo processedItem = managedItemService.create(item, getUser());
        this.item = processedItem.getItem();
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        this.warnings = processedItem.getWarnings();

        if (processedItem.hasWarnings()) {
            return WARN;
        }
        else {
            return SUCCESS;
        }
    }

    /**
     * Commit the modifications to the ManagedItemVo and the ManagedItemVo itself.
     * 
     * @return SUCCESS
     * @throws ValidationException if error validating data in the ManagedItemVo {@link #item}
     * @throws OptimisticLockingException if revision ID from ManagedItemVo in database is different
     */
    public String commitAllModifications() throws ValidationException, OptimisticLockingException {
        managedItemService.commitAllModifications(itemModDifferences, getUser());

        // force the flow to end, since we are done editing multiple items
        setTableSelections(Collections.EMPTY_LIST);

        return SUCCESS;
    }

    /**
     * 
     * 
     * @return String
     * @throws ValidationException
     */
    public String reviewChangeAll() throws ValidationException {
        this.itemModDifferences = managedItemService.submitAllModifications(modDifferences, getTableSelections(items),
            getUser());
        this.isFirstItem = false;

        return SUCCESS;
    }

    /**
     * Validate a page from a wizard.
     * 
     * @return result from validation methods, default is SUCCESS
     * @throws ValidationException if error in data
     */
    public String validateWizard() throws ValidationException {
        String result = SUCCESS;

        if (itemType.isOrderableItem()) {
            result = validateOrderableItemWizard();
        }
        else if (itemType.isProduct()) {
            result = validateProductWizard();
        }
        else if (itemType.isNdc()) {
            result = validateNdcWizard();
        }
        else if (itemType.isDomainType()) {
            result = validateDomainWizard();
        }

        return result;
    }

    /**
     * Validate a page from the Orderable Item Wizard.
     * 
     * @return SUCCESS
     * 
     * @throws ValueObjectValidationException if error in data
     */
    private String validateOrderableItemWizard() throws ValueObjectValidationException {
        OrderableItemVo orderableItem = (OrderableItemVo) item;

        if (isPreviousViewStateId("wizard1")) {
            orderableItem.validate(getUser(), getEnvironment(), 1);
        }

        else if (isPreviousViewStateId("wizard2")) {
            orderableItem.validate(getUser(), getEnvironment(), 2);
        }

        else if (isPreviousViewStateId("wizard3")) {
            orderableItem.validate(getUser(), getEnvironment(), 3);
        }

        return SUCCESS;
    }

    /**
     * Validate a page from the Product Wizard.
     * 
     * @return SUCCESS
     * 
     * @throws ValueObjectValidationException if error in Product data
     */
    private String validateProductWizard() throws ValueObjectValidationException {
        ProductVo product = (ProductVo) item;
        OrderableItemVo orderableItem = product.getOrderableItem();
        GenericNameVo genericName = product.getGenericName();
        String strDosageForm = "";
        String strGenericName = "";

        if ((orderableItem != null) && (orderableItem.getDosageForm() != null)
            && !isNullOrEmptyString(orderableItem.getDosageForm().getValue())) {
            strDosageForm = orderableItem.getDosageForm().getValue();
        }

        if ((genericName != null) && !isNullOrEmptyString(genericName.getValue())) {
            strGenericName = genericName.getValue();
        }

        if ((isPreviousViewStateId(WIZARD_TWO)) || (isPreviousViewStateId(WIZARD_THIRTEEN))) {

            // national formulary indicator in inherited from the orderable item

            if (orderableItem != null) {
                product.setNationalFormularyIndicator(orderableItem.getNationalFormularyIndicator());
            }// endif

            // At the PEPS Local instance, local non-formulary is set to the opposite of national formulary indicator
            if (getEnvironment().isLocal()) {
                product.getDataFields().getDataField(FieldKey.LOCAL_NON_FORMULARY).selectValue(
                    !product.getNationalFormularyIndicator());
            }
            else {

                // at the PEPS National instance, local non-formulary is always set to "NO"
                product.getDataFields().getDataField(FieldKey.LOCAL_NON_FORMULARY).selectValue(false);
            }

            // auto-populate the national formulary name
            // national formulary name is derived from generic name and dosage form
            // length is between 1-94 characters
            String nationalformularyName = strGenericName + " " + strDosageForm;

            if (nationalformularyName.length() > 94) {
                product.setNationalFormularyName(nationalformularyName.substring(0, 94));
            }// end if
            else {
                product.setNationalFormularyName(nationalformularyName);
            }// end else

        }// end if

        // validate add product screen
        // ProductValidator validator = (ProductValidator) FieldKey.PRODUCT.newValidatorInstance();

        if ((isPreviousViewStateId(WIZARD_ONE)) || (isPreviousViewStateId(WIZARD_ELEVEN))) {
            if (this.isLocal()) {

                // set the default value of Local Print Name to VA Print Name
                product.setLocalPrintName(product.getVaPrintName());
            }

            product.validate(getUser(), getEnvironment(), 1);
        }

        else if ((isPreviousViewStateId(WIZARD_TWO)) || (isPreviousViewStateId(WIZARD_TWELVE))) {

            // set default value for cmop dispense
            boolean cmopDispense = product.getCmopDispense();

            if (product.isNewInstance()) {
                ListDataField<String> productType = product.getDataFields().getDataField(FieldKey.CATEGORIES);

                // set the default value to true, unless cs fed schedule is 2 or
                // product type is investigational or compound
                if ((product.getCsFedSchedule() != null) && (productType != null)) {
                    if ((!("2 - Schedule II").equals(product.getCsFedSchedule().getValue()))
                        && (!productType.contains("INVESTIGATIONAL")) && (!productType.contains("COMPOUNDED"))) {
                        product.setCmopDispense(true);
                    }// end if
                    else {
                        product.setCmopDispense(false);
                    }
                }

            }// end if

            product.validate(getUser(), getEnvironment(), 2);
        }// end else if

        else if (isPreviousViewStateId(WIZARD_THREE) || isPreviousViewStateId(WIZARD_THIRTEEN)) {
            product.validate(getUser(), getEnvironment(), 3);
        }

        else if (isPreviousViewStateId(WIZARD_FOUR) || isPreviousViewStateId(WIZARD_FOURTEEN)) {
            product.validate(getUser(), getEnvironment(), 4);
        }

        else if (isPreviousViewStateId(WIZARD_FIVE) || isPreviousViewStateId(WIZARD_FIFTEEN)) {
            product.validate(getUser(), getEnvironment(), 5);
        }

        else if (isPreviousViewStateId(WIZARD_SIX) || isPreviousViewStateId(WIZARD_SIXTEEN)) {
            product.validate(getUser(), getEnvironment(), 6);
        }

        else if (isPreviousViewStateId(WIZARD_SEVEN) || isPreviousViewStateId(WIZARD_SEVENTEEN)) {
            product.validate(getUser(), getEnvironment(), 7);
        }

        else if (isPreviousViewStateId(WIZARD_EIGHT) || isPreviousViewStateId(WIZARD_EIGHTEEN)) {
            product.validate(getUser(), getEnvironment(), 8);
        }

        else if (isPreviousViewStateId(WIZARD_NINE) || isPreviousViewStateId(WIZARD_NINETEEN)) {
            product.validate(getUser(), getEnvironment(), 9);
        }

        return SUCCESS;
    }

    /**
     * Validate a page from the NDC Wizard.
     * 
     * @return SUCCESS
     * 
     * @throws ValueObjectValidationException if error in data
     */
    private String validateNdcWizard() throws ValueObjectValidationException {
        NdcVo ndc = (NdcVo) item;
        ProductVo productItem = ndc.getProduct();

        if (isPreviousViewStateId("wizard1")) {
            if ((productItem != null) && (productItem.getDataFields() != null)) {

                // Only change the value if the environment is local
                if (getEnvironment().isLocal()) {

                    if (ndc.getDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE).getValue().size() == 0) {
                        ndc.getDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE).selectValue(
                            productItem.getDataFields().getDataField(FieldKey.APPLICATION_PACKAGE_USE).getValue());
                        item = ndc;
                    }
                }

                ndc.validate(getUser(), getEnvironment(), 1);

            }
        }

        return SUCCESS;
    }

    /**
     * Validate a page from the Managed Domain Wizard.
     * 
     * @return SUCCESS, unless no more fields to display then END
     */
    private String validateDomainWizard() {
        this.wizardFields = wizard.getNextPageFields();

        if (wizardFields == null) {
            return END;
        }

        return SUCCESS;
    }

    /**
     * validate method
     * 
     * @return String
     */
    public String previousDomainWizard() {

        if (itemType.isDomainType()) {
            this.wizardFields = wizard.getPrevPageFields();

            if (wizardFields == null) {
                return END;
            }
        }

        return SUCCESS;
    }

    /**
     * 
     * @param mergeItem mergeItem property
     */
    public void setMergeItem(MergeVo mergeItem) {
        this.mergeItem = mergeItem;
    }

    /**
     * 
     * @return mergeItem property
     */
    public MergeVo getMergeItem() {
        return mergeItem;
    }

    /**
     * Commit the merge modifications to the ManagedItemVo and the ManagedItemVo itself.
     * 
     * @return SUCCESS
     * @throws OptimisticLockingException if someone else already updated the item
     * @throws ValidationException if error validating data in the ManagedItemVo {@link #item}
     */
    public String commitMergeModifications() throws OptimisticLockingException, ValidationException {
        this.oldItem = managedItemService.retrieve(item.getId(), item.getEntityType());
        this.item = managedItemService.commitMergeModifications(mergeItem, oldItem, getUser());
        this.itemId = item.getId();
        this.itemType = item.getEntityType();
        deleteCurrentTableSelection(items);
        isFirstItem = false;

        return SUCCESS;
    }

    /**
     * Compute the {@link MergeVo}.
     * 
     * @return SUCCESS
     * @throws ItemNotFoundException if cannot find item in the database
     */
    public String computeMergeInformation() throws ItemNotFoundException {
        this.mergeItem = managedItemService.computeMergeInformation(item);

        return SUCCESS;
    }

    /**
     * 
     * @return partialItems property
     */
    public List<PartialSaveVo> getPartialItems() {
        return partialItems;
    }

    /**
     * 
     * @param partialItems partialItems property
     */
    public void setPartialItems(List<PartialSaveVo> partialItems) {
        this.partialItems = partialItems;
    }

    /**
     * 
     * @return managedItemService property
     */
    public ManagedItemService getManagedItemService() {
        return managedItemService;
    }

    /**
     * 
     * @return items property
     */
    public List<ManagedItemVo> getItems() {
        return items;
    }

    /**
     * 
     * @param items items property
     */
    public void setItems(List<ManagedItemVo> items) {
        this.items = items;
    }

    /**
     * Updates the "parent" ManagedItemVo with the new ManagedDataVo that was added for it.
     * 
     * @return SUCCESS
     */
    public String selectDomain() {

        // item is set to the "parent" ManagedItemVo, parentAttribute is set to the OGNL path to the ManagedDataVo on the
        // parent item, and itemId is set to the ID for the ManagedDataVo
        // see selectNewDomainItem.xml
        setOgnlValue(parentAttribute, itemId);

        return SUCCESS;
    }

    /**
     * 
     * 
     * @return int
     */
    public int getItemCount() {

        // if this is the first item to be reviewed then
        if (isFirstItem) {

            // return the size of the table
            return getTableSelectionSize();
        }
        else {

            // ignore the size
            return 1;
        }
    }

    /**
     * Generate the VistA OI Name and OI name from the selected VA Generic name and dosage
     * 
     * @return SUCCESS
     */
    public String generateOINames() {
        this.item = managedItemService.generateOINames(item);

        return SUCCESS;
    }

    /**
     * 
     * @return wizard property
     */
    public ManagedDataWizardController getWizard() {
        return wizard;
    }

    /**
     * 
     * @param wizard wizard property
     */
    public void setWizard(ManagedDataWizardController wizard) {
        this.wizard = wizard;
    }

    /**
     * @return String ognlKey
     */
    public String getOgnlKey() { // was misspelled - getOnglKey
        return this.ognlKey;
    }

    /**
     * 
     * 
     * @param ognlKey String
     */
    public void setOgnlKey(String ognlKey) {
        this.ognlKey = ognlKey;
    }

    /**
     * 
     * 
     * @return int number of requests
     */
    public int mainRequestSize() {
        return this.mainRequest.getRequestDetails().size();
    }

    /**
     * determines if the string is null or empty
     * 
     * @param str the String parameter
     * @return boolean
     */
    private boolean isNullOrEmptyString(String str) {
        if ((str != null) && (str.length() > 0)) {
            return false;
        }

        return true;
    }

    /**
     * 
     * @return iahPrintTemplate property
     */
    public PrintTemplateVo getIahPrintTemplate() {
        return iahPrintTemplate;
    }

    /**
     * added this to support use of table.tag for national modSummary tables
     * 
     * @return modSummaryPrintTemplate property
     */
    public PrintTemplateVo getModSummaryPrintTemplate() {
        return modSummaryPrintTemplate;
    }

    /**
     * added this to support use of table.tag for local modSummary tables
     * 
     * @return nonEditableModSummaryPrintTemplate property
     */
    public PrintTemplateVo getNonEditableModSummaryPrintTemplate() {
        return nonEditableModSummaryPrintTemplate;
    }

    /**
     * added this to support use of table.tag for local modSummary tables
     * 
     * @return editableModSummaryPrintTemplate property
     */
    public PrintTemplateVo getEditableModSummaryPrintTemplate() {
        return editableModSummaryPrintTemplate;
    }

    /**
     * 
     * @return attribute property
     */
    public String getParentAttribute() {
        return parentAttribute;
    }

    /**
     * 
     * @param attribute attribute property
     */
    public void setParentAttribute(String attribute) {
        this.parentAttribute = attribute;
    }

    /**
     * 
     * @return partialItemsCount property
     */
    public int getPartialItemsCount() {
        return partialItemsCount;
    }

    /**
     * 
     * @return itemModDifferences property
     */
    public Collection<ItemModDifferenceVo> getItemModDifferences() {
        return itemModDifferences;
    }

    /**
     * 
     * @param itemModDifferences itemModDifferences property
     */
    public void setItemModDifferences(Collection<ItemModDifferenceVo> itemModDifferences) {
        this.itemModDifferences = itemModDifferences;
    }

    /**
     * Externally page and sort the tables supported by the ManagedItemAction.
     * 
     * @param tableId String HTML ID of the table being paged
     * @param sortedFieldKey {@link FieldKey} of the column being sorted, may be null if no sort (or default sort) is
     *            selected
     * @param sortOrder {@link SortOrder} of the column being sorted, may be null if no sort (or default sort) is selected
     * @param startRow integer starting row index within the full list for the next (or previous) page
     * @param pageSize integer number of rows to return for the next (or previous) page
     * 
     * @return SUCCESS
     * 
     * @throws Exception in case paging a table causes an exception
     */
    @Override
    protected String pageTable(String tableId, FieldKey sortedFieldKey, SortOrder sortOrder, int startRow, int pageSize)
        throws Exception {

        if (NDC_LIST_TABLE.equals(tableId) || PRODUCT_LIST_TABLE.equals(tableId)) {
            pageManagedItemChildren(sortedFieldKey, sortOrder, startRow, pageSize);
        }
        else if (SearchAction.SEARCH_RESULTS_TABLE.equals(tableId)) {
            pageDomainSearchResults(sortedFieldKey, sortOrder, startRow, pageSize);
        }

        return SUCCESS;
    }

    /**
     * Externally page and sort the list of NDCs on a Product.
     * 
     * @param sortedFieldKey {@link FieldKey} of the column being sorted, may be null if no sort (or default sort) is
     *            selected
     * @param sortOrder {@link SortOrder} of the column being sorted, may be null if no sort (or default sort) is selected
     * @param startRow integer starting row index within the full list for the next (or previous) page
     * @param pageSize integer number of rows to return for the next (or previous) page
     */
    private void pageManagedItemChildren(FieldKey sortedFieldKey, SortOrder sortOrder, int startRow, int pageSize) {
        FieldKey sort = sortedFieldKey;
        SortOrder order = sortOrder;

        if (sort == null) {
            sort = item.getChildDefaultSortedFieldKey();
            order = SortOrder.ASCENDING;
        }

        PaginatedList children = managedItemService.retrieveChildren(itemId, itemType, sort, order, startRow, pageSize);
        item.setChildren(children);
    }

    /**
     * Externally page and sort the search results for Domains while editing a domain.
     * <p>
     * Domains are edited while simultaneously displaying the search results, so we need to be able to page the search
     * results from the ManagedItemAction (which handled the edits).
     * <p>
     * Uses the {@link SearchAction} to perform the paging, since that class already has the logic in it.
     * 
     * @param sortedFieldKey {@link FieldKey} of the column being sorted, may be null if no sort (or default sort) is
     *            selected
     * @param sortOrder {@link SortOrder} of the column being sorted, may be null if no sort (or default sort) is selected
     * @param startRow integer starting row index within the full list for the next (or previous) page
     * @param pageSize integer number of rows to return for the next (or previous) page
     * 
     * @throws ValidationException if the search criteria is invalid
     */
    private void pageDomainSearchResults(FieldKey sortedFieldKey, SortOrder sortOrder, int startRow, int pageSize)
        throws ValidationException {

        SearchAction searchAction = ApplicationContextUtility.getSpringBean(SearchAction.class);
        searchAction.setSearchTemplate(searchTemplate);
        searchAction.setParameters(getParameters());
        searchAction.pageTable(SearchAction.SEARCH_RESULTS_TABLE, sortedFieldKey, sortOrder, startRow, pageSize);

        this.items = searchAction.getItems();
    }

    /**
     * 
     * 
     * @return warnings property
     */
    public Errors getWarnings() {
        return warnings;
    }

    /**
     * 
     * 
     * @param warnings property
     */
    public void setWarnings(Errors warnings) {
        this.warnings = warnings;
    }

    /**
     * 
     * @return childitem property
     */
    public ManagedItemVo getChilditem() {
        return childitem;
    }

    /**
     * 
     * @param childitem childitem property
     */
    public void setChilditem(ManagedItemVo childitem) {
        this.childitem = childitem;
    }

    /**
     * 
     * @return searchRequestCriteria property
     */
    public SearchRequestCriteriaVo getSearchRequestCriteria() {
        return searchRequestCriteria;
    }

    /**
     * 
     * @param searchRequestCriteria searchRequestCriteria property
     */
    public void setSearchRequestCriteria(SearchRequestCriteriaVo searchRequestCriteria) {
        this.searchRequestCriteria = searchRequestCriteria;
    }

    /**
     * 
     * @return patientMedicationInstruction property
     */
    public PatientMedicationInstructionVo getPatientMedicationInstruction() {
        return patientMedicationInstruction;
    }

    /**
     * 
     * @param patientMedicationInstruction patientMedicationInstruction property
     */
    public void setPatientMedicationInstruction(PatientMedicationInstructionVo patientMedicationInstruction) {
        this.patientMedicationInstruction = patientMedicationInstruction;
    }

    /**
     * 
     * @return spanishText property
     */
    public LanguageChoice getSpanishText() {
        return spanishText;
    }

    /**
     * 
     * @param spanishText spanishText property
     */
    public void setSpanishText(LanguageChoice spanishText) {
        this.spanishText = spanishText;
    }

    /**
     * 
     * @return parentEntityType property
     */
    public ManagedItemVo getParentEntityType() {
        return parentEntityType;
    }

    /**
     * 
     * @param parentEntityType parentEntityType property
     */
    public void setParentEntityType(ManagedItemVo parentEntityType) {
        this.parentEntityType = parentEntityType;
    }
    
    /**
     * 
     * @return isFirst property
     */
    public int getIsFirst(){
      return isFirst;
    }
    /**
     * 
     * @param isFirst property
     */
    public void setIsFirst(int first){
      isFirst = first;
    }


}