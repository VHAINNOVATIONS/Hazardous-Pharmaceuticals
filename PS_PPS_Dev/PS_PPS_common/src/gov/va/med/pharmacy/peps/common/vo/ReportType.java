/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Types for reports
 */
public enum ReportType {

    /** NDC_LIST_PRINT_TEMPLATE */
    NDC_LIST_PRINT_TEMPLATE("CaptureNDF", false, false, false, false),

    /** LIST_INGREDIENTS */
    LIST_INGREDIENTS("ListIngredients", false, false, false, false),

    /** PRINT_PRODUCTS_WARNING_LABELS */
    PRINT_PRODUCTS_WARNING_LABELS("WarningLabels", false, false, false, false),

    /** VA_DRUG_CLASSIFICATION_REPORT_PRINT_TEMPLATE */
    VA_DRUG_CLASSIFICATION_REPORT_PRINT_TEMPLATE("DrugClass", true, false, false, true),

    /** PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE */
    PRODUCTS_WITH_EXCLUSIONS_DRG_DRG_REPORT_PRINT_TEMPLATE("Exclusions", true, true, true, false),

    /** ACTIVE_PRDUCTS_NO_ACTIVE_NDCS_REPORT_PRINT_TEMPLATE */
    ACTIVE_PRDUCTS_NO_ACTIVE_NDCS_REPORT_PRINT_TEMPLATE("NoActiveNDCS", true, false, false, false),

    /** ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE */
    ACTIVE_PRODUCTS_PROPOSED_INACTIVATION_REPORT_PRINT_TEMPLATE("ProposedInactivation", true, true, false, false),

    /** ITEM_AUDIT_HISTORY_PRINT_TEMPLATE */
    ITEM_AUDIT_HISTORY_PRINT_TEMPLATE("VUIDApproval", true, true, false, false);

    private String view;
    private Boolean print;
    private Boolean start;
    private Boolean stop;
    private Boolean description;

    /**
     * ReportType
     * @param pView pView
     * @param pPrint pPrint
     * @param pStart pStart
     * @param pStop pStop
     * @param pDescription pDescription
     */
    ReportType(String pView, Boolean pPrint, Boolean pStart, Boolean pStop, Boolean pDescription) {

        this.view = pView;
        this.print = pPrint;
        this.start = pStart;
        this.stop = pStop;
        this.description = pDescription;
    }

    /**
     * getDescription
     * @return description
     */
    public Boolean getDescription() {
    
        return description;
    }

    /**
     * getView
     * @return view
     */
    public String getView() {

        return this.view;
    }

    /**
     * isPrintable
     * @return print
     */
    public Boolean isPrintable() {

        return this.print;
    }

    /**
     * getSTart
     * @return start
     */
    public Boolean getStart() {
    
        return start;
    }

    /**
     * getStop
     * @return stop
     */
    public Boolean getStop() {
    
        return stop;
    }

}
