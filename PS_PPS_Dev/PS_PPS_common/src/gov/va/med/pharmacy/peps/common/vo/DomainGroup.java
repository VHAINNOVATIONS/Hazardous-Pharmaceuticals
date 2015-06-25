/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Enum type representing the domain groups. Basic functionality to determine what a particular group can do
 */
public enum DomainGroup {
    
    /**
     * GROUP_1
     */
    GROUP_1(false, true, true, true, true),
    
    /**
     * GROUP_2
     */
    GROUP_2(false, true, true, true, true),
    
    /**
     * GROUP_3.
     */
    GROUP_3(false, true, true, true, false),
    
    /**
     * GROUP_5.
     */
    GROUP_5(true, true, true, true, true),
    
    /**
     * GROUP_6.
     */
    GROUP_6(true, false, true, false, false),
    
    /**
     * RX_CONSULT.
     */
    RX_CONSULT(true, true, true, true, true),
    
    /**
     * STANDARD_MED_ROUTE.
     */
    STANDARD_MED_ROUTE(false, true, true, true, true),
    
    /**
     * MEDICATION_INSTRUCTION.
     */
    MEDICATION_INSTRUCTION(true, false, true, false, false),
    
    /**
     * LOCAL_MEDICATION_ROUTE.
     */
    LOCAL_MEDICATION_ROUTE(true, false, true, false, false),
    
    /**
     * DOSAGE_FORM.
     */
    DOSAGE_FORM(true, true, true, true, true),
    
    /**
     * DRUG_CLASS_CODE.
     */
    DRUG_CLASS_CODE(false, true, true, true, true),
    
    /**
     * DRUG_TEXT.
     */
    DRUG_TEXT(true, true, true, true, false),
    
    /**
     * INGREDIENT.
     */
    INGREDIENT(false, true, true, true, true);

    private boolean localEdit;
    private boolean nationalEdit;
    private boolean localAdd;
    private boolean nationalAdd;
    private boolean requiresSecondApproval;

    /**
     * Private constructor
     * 
     * @param localEdit can it be edited locally
     * @param nationalEdit can it be edited nationally
     * @param localAdd can a local add a pending item
     * @param nationalAdd can national add an item
     * @param requiresSecondApproval does a national add require a second approval
     */
    private DomainGroup(boolean localEdit, boolean nationalEdit, boolean localAdd, boolean nationalAdd,
                        boolean requiresSecondApproval) {
        this.localEdit = localEdit;
        this.nationalEdit = nationalEdit;
        this.localAdd = localAdd;
        this.nationalAdd = nationalAdd;
        this.requiresSecondApproval = requiresSecondApproval;

    }
    
    /**
     * canEdit.
     * @param environemnt current {@link Environment}
     * @return if this domain can be edited at the given environment
     */
    public boolean canEdit(Environment environemnt) {
        boolean allowed = false;

        switch (environemnt) {
            case LOCAL:
                allowed = canEditLocal();
                break;
            case NATIONAL:
                allowed = canEditNational();
                break;
            default:
                break;
        }
        
        return allowed;
    }

    /**
     * canEditLocal.
     * 
     * @return if this domain can be edited locally
     */
    public boolean canEditLocal() {
        return localEdit;
    }

    /**
     * canEditNational.
     * 
     * @return if this domain can be edited nationally
     */
    public boolean canEditNational() {
        return nationalEdit;
    }

    /**
     * canAdd.
     * @param environemnt current {@link Environment}
     * @return if this domain can be added at the given environment
     */
    public boolean canAdd(Environment environemnt) {
        boolean allowed = false;

        switch (environemnt) {
            case LOCAL:
                allowed = canAddLocal();
                break;
            case NATIONAL:
                allowed = canAddNational();
                break;
            default:
                break;
        }
        
        return allowed;
    }

    /**
     * canAddLocal.
     * 
     * @return if this domain can be added by local
     */
    public boolean canAddLocal() {
        return localAdd;
    }

    /**
     * canAddNational.
     * 
     * @return if this domain can be added by national
     */
    public boolean canAddNational() {
        return nationalAdd;
    }

    /**
     * addRequiresSecond.
     * 
     * @return if this domain requires second approval for a national add
     */
    public boolean addRequiresSecond() {
        return requiresSecondApproval;
    }
}
