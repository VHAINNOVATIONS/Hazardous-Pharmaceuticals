/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo.printtemplate;


/**
 * Locations where tables (print templates) are used.
 */
public enum TemplateLocation {
    
    /**
     * NOTIFICATIONS.
     */
    NOTIFICATIONS,
    
    /**
     * ITEM_AUDIT_HISTORY.
     */
    ITEM_AUDIT_HISTORY,
    
    /**
     * REQUEST_SEARCH.
     */
    REQUEST_SEARCH,
    
    /**
     * ORDERABLE_ITEM_SEARCH.
     */
    ORDERABLE_ITEM_SEARCH,
    
    /**
     * PRODUCT_SEARCH.
     */
    PRODUCT_SEARCH,
    
    /**
     * NDC_SEARCH.
     */
    NDC_SEARCH,
    
    /**
     * PRODUCT_LIST.
     */
    PRODUCT_LIST,
    
    /**
     * NDC_LIST.
     */
    NDC_LIST,
    
    /**
     * EDITABLE_MODIFICATION_SUMMARY.
     */
    EDITABLE_MODIFICATION_SUMMARY,
    
    /**
     * NONEDITABLE_MODIFICATION_SUMMARY.
     */
    NONEDITABLE_MODIFICATION_SUMMARY,
    
    /**
     * PENDING_ITEMS_LIST.
     */
    PENDING_ITEMS_LIST,
    
    /**
     * PARTIAL_SAVE.
     */
    PARTIAL_SAVE,
    
    /**
     * SITE_CONFIGURATION.
     */
    SITE_CONFIGURATION,
    
    /**
     * NATIONAL_STATUS.
     */
    NATIONAL_STATUS,
    
    /**
     * PRINT_TEMPLATE_CONFIG.
     */
    PRINT_TEMPLATE_CONFIG,
    
    /**
     * MANAGE_SEARCH_TEMPLATES.
     */
    MANAGE_SEARCH_TEMPLATES,
    
    /**
     * MANAGED_DATA_SEARCH.
     */
    MANAGED_DATA_SEARCH,
    
    /**
     * SETUP_ATC_SEARCH.
     */
    SETUP_ATC_SEARCH,
    
    /**
     * WARNING_LABEL.
     */
    WARNING_LABEL,
    
    /**
     * MIGRATION_SUMMARY.
     */
    MIGRATION_SUMMARY,
    
    /**
     * MIGRATION_ERROR
     */
    MIGRATION_ERROR,
    
    /**
     * DRUG_CLASS_REPORT.
     */
    DRUG_CLASS_REPORT,
    
    /**
     * EXCLUSIONS_REPORT.
     */
    EXCLUSIONS_REPORT,
    
    /**
     * NO_ACTIVE_NDCS_REPORT.
     */
    NO_ACTIVE_NDCS_REPORT,
    
    /**
     * PERSONAL_ORDERABLE_ITEM_SEARCH.
     */
    PERSONAL_ORDERABLE_ITEM_SEARCH,
    
    /**
     * PERSONAL_PRODUCT_SEARCH.
     */
    PERSONAL_PRODUCT_SEARCH,
    
    /**
     * PERSONAL_NDC_SEARCH.
     */
    PERSONAL_NDC_SEARCH;

    /**
     * isRequestSearch.
     * @return boolean true if this TableLocation is for the Pending Modification Request search
     */
    public boolean isRequestSearch() {
        return REQUEST_SEARCH.equals(this);
    }

    /**
     * isOrderableItemSearch.
     * @return boolean true if this TableLocation is for the Orderable Item search
     */
    public boolean isOrderableItemSearch() {
        return ORDERABLE_ITEM_SEARCH.equals(this);
    }

    /**
     * isProductSearch.
     * @return boolean true if this TableLocation is for the Product search
     */
    public boolean isProductSearch() {
        return PRODUCT_SEARCH.equals(this);
    }

    /**
     * isManagedDataSearch.
     * @return boolean true if this TableLocation is for the Managed Data search
     */
    public boolean isManagedDataSearch() {
        return MANAGED_DATA_SEARCH.equals(this);
    }

    /**
     * isNdcSearch.
     * @return boolean true if this TableLocation is for the NDC search
     */
    public boolean isNdcSearch() {
        return NDC_SEARCH.equals(this);
    }

    /**
     * isProductList.
     * @return boolean true if this TableLocation is for a list of Products
     */
    public boolean isProductList() {
        return PRODUCT_LIST.equals(this);
    }

    /**
     * isNdcList.
     * @return boolean true if this TableLocation is for a list of NDCs
     */
    public boolean isNdcList() {
        return NDC_LIST.equals(this);
    }

    /**
     * isEditableModificationSummary.
     * @return boolean true if this TableLocation is for a list of Modification Summaries
     */
    public boolean isEditableModificationSummary() {
        return EDITABLE_MODIFICATION_SUMMARY.equals(this);
    }

    /**
     * isNoneditableModificationSummary.
     * @return boolean true if this TableLocation is for a list of Modification Summaries
     */
    public boolean isNoneditableModificationSummary() {
        return NONEDITABLE_MODIFICATION_SUMMARY.equals(this);
    }

    /**
     * isPendingItemsList.
     * @return boolean true if this TableLocation is for a list of Pending Items
     */
    public boolean isPendingItemsList() {
        return PENDING_ITEMS_LIST.equals(this);
    }

    /**
     * isSiteConfiguration.
     * 
     * @return boolean true if this TableLocation is for the Site Configuration List
     */
    public boolean isSiteConfiguration() {
        return SITE_CONFIGURATION.equals(this);
    }

    /**
     * isNationalStatusConfiguration.
     * 
     * @return boolean true if this TableLocation is for the National System Information
     */
    public boolean isNationalStatusConfiguration() {
        return NATIONAL_STATUS.equals(this);
    }
    
    /**
     * isPartialSaveConfiguration.
     * 
     *  @return boolean true if this TableLocation is for the Partial Save Configuration
     */
    public boolean isPartialSaveConfiguration() {
        return PARTIAL_SAVE.equals(this);
    }
    
    /**
     * isPrintTemplateConfiguration.
     * 
     *  @return boolean true if this TableLocation is for the Print template configuration
     */
    public boolean isPrintTemplateConfiguration() {
        return PRINT_TEMPLATE_CONFIG.equals(this);
    }
    
    /**
     * isManageSearchTemplates.
     * @return boolean true if this TableLocation is for the Manage Search Templates configuration
     */
    public boolean isManageSearchTemplates() {
        return MANAGE_SEARCH_TEMPLATES.equals(this);
    }
    
    /**
     * isMigrationSummary.
     *  
     *  @return boolean true if this TableLocation is for the Migration Summary table
     */
    public boolean isMigrationSummary() {
        return MIGRATION_SUMMARY.equals(this);
    }
    
    /**
     * isMigrationError.
     * @return boolean true if this TableLocation is for the Migration Error table
     */
    public boolean isMigrationError() {
        return MIGRATION_ERROR.equals(this);
    }
    
    /**
     * Tests if this enum instance is the SETUP_ATC_SEARCH one.
     * 
     * @return boolean True if this enum instance is SETUP_ATC_SEARCH.
     */
    public boolean isSetupAtcConfiguration() {
        return SETUP_ATC_SEARCH.equals(this);
    }

    /**
     * Tests if this enum instance is the DRUG_CLASS_REPORT one.
     * 
     * @return boolean True if this enum instance is DRUG_CLASS_REPORT.
     */
    public boolean isDrugClassReport() {
        return DRUG_CLASS_REPORT.equals(this);
    }
    
    /**
     * Tests if this enum instance is the EXCLUSIONS_REPORT one.
     * 
     * @return boolean True if this enum instance is EXCLUSIONS_REPORT.
     */
    public boolean isExclusionsReport() {
        return EXCLUSIONS_REPORT.equals(this);
    }
    
    /**
     * Tests if this enum instance is the NO_ACTIVE_NDCS_REPORT one.
     * 
     * @return boolean True if this enum instance is NO_ACTIVE_NDCS_REPORT.
     */
    public boolean isNoActiveNdcsReport() {
        return NO_ACTIVE_NDCS_REPORT.equals(this);
    }
    
    /**
     * isPersonalOrderableItemSearch.
     * @return boolean true if this TableLocation is for the Personal Orderable Item search
     */
    public boolean isPersonalOrderableItemSearch() {
        return PERSONAL_ORDERABLE_ITEM_SEARCH.equals(this);
    }

    /**
     * isPersonalProductSearch.
     * @return boolean true if this TableLocation is for the Personal Product search
     */
    public boolean isPersonalProductSearch() {
        return PERSONAL_PRODUCT_SEARCH.equals(this);
    }

    /**
     * isPersonalNdcSearch.
     * @return boolean true if this TableLocation is for the Personal NDC search
     */
    public boolean isPersonalNdcSearch() {
        return PERSONAL_NDC_SEARCH.equals(this);
    }
       
}
