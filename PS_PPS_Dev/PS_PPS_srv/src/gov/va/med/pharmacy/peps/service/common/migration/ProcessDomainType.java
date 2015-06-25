/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * ProcessDomainType
 *
 */
public enum ProcessDomainType {

    /** DRUG_UNITS_ACTIVE */
    DRUG_UNITS_ACTIVE(350, PPSConstants.VA_DRUG_UNIT_FILE, 50, DomainState.ACTIVE, "processing drug units...",
        PPSConstants.DRUG_UNIT),

    /** DRUG_UNITS_INACTIVE */
    DRUG_UNITS_INACTIVE(350, PPSConstants.VA_DRUG_UNIT_FILE, 50, DomainState.INACTIVE, "processing inactive drug units...",
        PPSConstants.DRUG_UNIT),

    /** VA_DISPENSE_UNIT_ACTIVE */
    VA_DISPENSE_UNIT_ACTIVE(63, PPSConstants.VA_DISPENSE_UNIT_FILE, 50, DomainState.ACTIVE, "processing VA Dispense units...",
        PPSConstants.DISPENSE_UNIT),

    /** VA_DISPENSE_UNIT_INACTIVE */
    VA_DISPENSE_UNIT_INACTIVE(63, PPSConstants.VA_DISPENSE_UNIT_FILE, 50, DomainState.INACTIVE,
        "processing  inactive VA Dispense units...", PPSConstants.DISPENSE_UNIT),

    /** replace */
    VA_GENERIC_NAME_ACTIVE(4613, PPSConstants.VA_GENERIC_NAME_FILE, 50, DomainState.ACTIVE, "processing generic names...",
        PPSConstants.GENERIC_NAME),

    /** replace */
    VA_GENERIC_NAME_INACTIVE(4613, PPSConstants.VA_GENERIC_NAME_FILE, 50, DomainState.INACTIVE,
        "processing  inactive generic names...", PPSConstants.GENERIC_NAME),

    /** replace */
    DOSAGE_FORM_ACTIVE(308, PPSConstants.VA_DOSAGE_FORM_FILE, 10, DomainState.ACTIVE, "processing dosage form...",
        PPSConstants.DOSAGE_FORM),

    /** replace */
    DOSAGE_FORM_INACTIVE(308, PPSConstants.VA_DOSAGE_FORM_FILE, 10, DomainState.INACTIVE,
        "processing inactive  dosage form...", PPSConstants.DOSAGE_FORM),

    /** replace */
    DRUG_CLASS_0(578, PPSConstants.VA_DRUG_CLASS_FILE, 10, DomainState.CLASS0, "processing drug class 0",
        PPSConstants.DRUG_CLASS),

    /** replace */
    DRUG_CLASS_1(578, PPSConstants.VA_DRUG_CLASS_FILE, 10, DomainState.CLASS1, "processing drug class 1",
        PPSConstants.DRUG_CLASS),

    /** replace */
    DRUG_CLASS_2(578, PPSConstants.VA_DRUG_CLASS_FILE, 10, DomainState.CLASS2, "processing drug class 2",
        PPSConstants.DRUG_CLASS),

    /** replace */
    DRUG_INGREDIENTS_ACTIVE(4736, PPSConstants.VA_DRUG_INGREDIENT_FILE, 50, DomainState.ACTIVE,
        "processing drug ingredients...", PPSConstants.DRUG_INGREDIENTS),
        
     /** DRUG_INGREDIENTS_INACTIVE */
    DRUG_INGREDIENTS_INACTIVE(4736, PPSConstants.VA_DRUG_INGREDIENT_FILE, 50, DomainState.INACTIVE,
        "processing  inactive drug ingredients...", PPSConstants.DRUG_INGREDIENTS),
        
    /** DRUG_INGREDIENTS_LIST */
    DRUG_INGREDIENTS_LIST(4736, PPSConstants.VA_DRUG_INGREDIENT_FILE, 50, DomainState.INGREDIENTS_LIST,
        "processing  inactive drug ingredients list", PPSConstants.DRUG_INGREDIENTS + " list"),
        
    /**
     * ORDERABLE_ITEMS_CSV_FILE_ACTIVE
     */
    ORDERABLE_ITEMS_CSV_FILE_ACTIVE(850, PPSConstants.VA_ORDERABLE_ITEM_FILE, 15, DomainState.ACTIVE,
        "processing items CSV file...", "Orderable Items CSV File"),
        
    /**
     * VA_PRODUCT_ACTIVE
     */
    VA_PRODUCT_ACTIVE(22547, PPSConstants.VA_PRODUCT_FILE, 4, DomainState.ACTIVE, "processing VA products...", "VA Product"),
    
    /**
     * VA_PRODUCT_INACTIVE
     */
    VA_PRODUCT_INACTIVE(22547, PPSConstants.VA_PRODUCT_FILE, 4, DomainState.INACTIVE, "processing  inactive VA products...",
        "VA Products"),
        
    /**
     * NDC_CSV_FILE_ACTIVE
     */
    NDC_CSV_FILE_ACTIVE(2000, PPSConstants.VA_NDC_FILE, 3, DomainState.ACTIVE, "processing NDC CSV File...", "NDC CSV FILE"),

    /**
     * PACKAGE_TYPES
     */
    PACKAGE_TYPES(2000, PPSConstants.VA_PACKAGE_TYPE_FILE, 1, DomainState.ACTIVE, "Package types", "package types"),

    /**
     * MANUFACTURES
     */
    MANUFACTURES(2000, PPSConstants.VA_MANUFACTURES_FILE, 1, DomainState.ACTIVE, "Manufacturers", "Manufacturer");

    private int maxRecords;
    private String message;
    private String fileNumber;
    private int recordFetchQty;
    private DomainState domainState;
    private String name;

    /**
     * DomainState
     *
     */
    public enum DomainState {
        
        /** ACTIVE(1) */
        ACTIVE(1), 
        
        /** INACTIVE(0) */
        INACTIVE(0), 
        
        /** CLASS0(0) */
        CLASS0(0), 
        
        /** CLASS1(1) */
        CLASS1(1), 
        
        /** CLASS2(2) */
        CLASS2(2), 
        
        /**  INGREDIENTS_LIST(3) */
        INGREDIENTS_LIST(3);

        private int state;

        /**
         * DomainState
         * @param pState pState
         */
        DomainState(int pState) {
            this.state = pState;
        }

        /**
         * getState
         * @return state
         */
        public int getState() {
            return this.state;
        }
    };

    /**
     * getState
     * @param pMaxRecords pMaxRecords
     * @param pFileNumber pFileNumber
     * @param pRecordFetchQty pRecordFetchQty
     * @param pDomainState pDomainState
     * @param pMessage pMessage
     * @param pName pName
     */
    ProcessDomainType(int pMaxRecords, String pFileNumber, int pRecordFetchQty, DomainState pDomainState,
                      String pMessage, String pName) {
        this.maxRecords = pMaxRecords;
        this.fileNumber = pFileNumber;
        this.recordFetchQty = pRecordFetchQty;
        this.domainState = pDomainState;
        this.message = pMessage;
        this.name = pName;

    }

    /**
     * getAllDomainMaxRecords
     * @return maxrecords
     */
    public int getAllDomainMaxRecords() {
        int maxrecords =
                DRUG_UNITS_ACTIVE.getMaxRecord() + VA_DISPENSE_UNIT_ACTIVE.getMaxRecord()
                        + VA_GENERIC_NAME_ACTIVE.getMaxRecord() + DOSAGE_FORM_ACTIVE.getMaxRecord()
                        + DRUG_CLASS_0.getMaxRecord() + DRUG_INGREDIENTS_ACTIVE.getMaxRecord()
                        + VA_PRODUCT_ACTIVE.getMaxRecord();

        return maxrecords;
    }

    /**
     * getMaxRecords
     * @return maxRecords
     */
    public int getMaxRecord() {
        return this.maxRecords;
    }

    /**
     * getMessage
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * getFileNumber
     * @return fileNumber
     */
    public String getFileNumber() {
        return this.fileNumber;
    }

    /**
     * getRecordFetchQty
     * @return recordFetchQty
     */
    public int getRecordFetchQty() {
        return this.recordFetchQty;
    }

    /**
     * getDomainState
     * @return domainState
     */
    public DomainState getDomainState() {
        return this.domainState;
    }

    /**
     * getName()
     * @return name
     */
    public String getName() {
        return this.name;
    }

}
