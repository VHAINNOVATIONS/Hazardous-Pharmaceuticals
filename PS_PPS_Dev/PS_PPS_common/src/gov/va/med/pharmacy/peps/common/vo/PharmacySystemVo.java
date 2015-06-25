/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Collection;
import java.util.Set;


/**
 * Data representing a PharmacySystemVo.
 * 
 */
public class PharmacySystemVo extends ManagedDataVo {

    private static final long serialVersionUID = 1L;

    private Integer psSiteNumber;
    private String psAnd;
    private String psDays;
    private String psEight;
    private String psExcept;
    private String psFive;
    private String psFor;
    private String psFour;
    private String psHours;
    private String psMinutes;
    private String psMonths;
    private String psNine;
    private String psOne;
    private String psOneFourth;
    private String psOneHalf;
    private String psOneThird;
    private String psPmisPrinter;
    private String psPmisSectionDelete;
    private String psSeconds;
    private String psSeven;
    private String psSix;
    private String psTen;
    private String psThen;
    private String psThree;
    private String psThreeFourths;
    private String psTwo;
    private String psTwoThirds;
    private String psWeeks;
    private String psSiteName;
    private ItemStatus itemStatus;

    private PharmacySystemWarningLabelSource psCmopWarningLabelSource = PharmacySystemWarningLabelSource.PEPS;
    private PharmacySystemWarningLabelSource psOpaiWarningLabelSource = PharmacySystemWarningLabelSource.PEPS;
    private PharmacySystemWarningLabelSource psWarningLabelSource = PharmacySystemWarningLabelSource.PEPS;
    private PharmacySystemPmisLanguage psPmisLanguage;

    /**
     * Returns the entity for the managed item
     * 
     * @return EntityType
     */
    public EntityType getEntityType() {

        return EntityType.PHARMACY_SYSTEM;
    }

    /**
     * returns the domain group of the managed Data for the PharamcySystemVo.
     * 
     * @return domainGroup
     */
    public DomainGroup getDomainGroup() {

        return DomainGroup.GROUP_6;
    }

    /**
     * Returns enum Item Status (0,1,2)
     * 
     * @return ACTIVE, INACTIVE, ARCHIVE
     */
    public ItemStatus getItemStatus() {

        return itemStatus;
    }

    /**
     * Sets the Item Status 
     * 
     * @param itemStatus ACTIVE, INACTIVE, ARCHIVE
     */
    public void setItemStatus(ItemStatus itemStatus) {

        this.itemStatus = itemStatus;
    }

    /**
     * Returns PharmacySystem and field.
     * 
     * @return and
     */
    public String getPsAnd() {

        return psAnd;
    }

    /**
     * getPsDays
     * 
     * @return psDays property
     */
    public String getPsDays() {

        return psDays;
    }

    /**
     * getPsEight
     * 
     * @return psEight property
     */
    public String getPsEight() {

        return psEight;
    }

    /**
     * getPsExcept
     * 
     * @return psExcept property
     */
    public String getPsExcept() {

        return psExcept;
    }

    /**
     * getPsFive
     * 
     * @return psFive property
     */
    public String getPsFive() {

        return psFive;
    }

    /**
     * getPsFor
     * 
     * @return psFor property
     */
    public String getPsFor() {

        return psFor;
    }

    /**
     * getPsFour
     * 
     * @return psFour property
     */
    public String getPsFour() {

        return psFour;
    }

    /**
     * getPsHours
     * 
     * @return psHours property
     */
    public String getPsHours() {

        return psHours;
    }

    /**
     * getPsMinutes
     * 
     * @return psMinutes property
     */
    public String getPsMinutes() {

        return psMinutes;
    }

    /**
     * getPsMonths
     * 
     * @return psMonths property
     */
    public String getPsMonths() {

        return psMonths;
    }

    /**
     * getPsNine
     * 
     * @return psNine property
     */
    public String getPsNine() {

        return psNine;
    }

    /**
     * getPsOne
     * 
     * @return psOne property
     */
    public String getPsOne() {

        return psOne;
    }

    /**
     * getPsOneFourth
     * 
     * @return psOneFourth property
     */
    public String getPsOneFourth() {

        return psOneFourth;
    }

    /**
     * getPsOneHalf
     * 
     * @return psOneHalf property
     */
    public String getPsOneHalf() {

        return psOneHalf;
    }

    /**
     * getPsOneThird
     * 
     * @return psOneThird property
     */
    public String getPsOneThird() {

        return psOneThird;
    }

    /**
     * getPsPmisPrinter
     * 
     * @return psPmisPrinter property
     */
    public String getPsPmisPrinter() {

        return psPmisPrinter;
    }

    /**
     * getPsPmisSectionDelete
     * 
     * @return psPmisSectionDelete property
     */
    public String getPsPmisSectionDelete() {

        return psPmisSectionDelete;
    }

    /**
     * getPsSeconds
     * 
     * @return psSeconds property
     */
    public String getPsSeconds() {

        return psSeconds;
    }

    /**
     * getPsSeven
     * 
     * @return psSeven property
     */
    public String getPsSeven() {

        return psSeven;
    }

    /**
     * getPsSix
     * 
     * @return psSix property
     */
    public String getPsSix() {

        return psSix;
    }

    /**
     * getPsTen
     * 
     * @return psTen property
     */
    public String getPsTen() {

        return psTen;
    }

    /**
     * getPsThen
     * 
     * @return psThen property
     */
    public String getPsThen() {

        return psThen;
    }

    /**
     * getPsThree
     * 
     * @return psThree property
     */
    public String getPsThree() {

        return psThree;
    }

    /**
     * getPsThreeFourths
     * 
     * @return psThreeFourths property
     */
    public String getPsThreeFourths() {

        return psThreeFourths;
    }

    /**
     * The SiteName field is the unique field for this domain. The SiteName value is stored in the value field.
     * 
     * @return siteName property
     */
    public String getSiteName() {

        return getValue();
    }

    /**
     * Returns true if this is a local only domain for the PharamcySystemVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return true;
    }

    /**
     * Returns true if the domain is standardized  for the PharamcySystemVo.
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return false;
    }

    /**
     * Returns true if the domain is an NDF domain for the PharamcySystemVo.
     * 
     * @return boolean
     */
    public boolean isNdf() {

        return false;
    }

    /**
     * setPharmacySystem
     * 
     * @param siteName siteName property
     */
    public void setPharmacySystem(String siteName) {

        setValue(siteName);
    }

    /**
     * Sets PharmacySystem and field.
     * 
     * @param and property
     */
    public void setPsAnd(String and) {

        this.psAnd = trimToEmpty(and);
    }

    /**
     * setPsDays
     * 
     * @param psDays psDays property
     */
    public void setPsDays(String psDays) {

        this.psDays = trimToEmpty(psDays);
    }

    /**
     * setPsEight
     * 
     * @param psEight psEight property
     */
    public void setPsEight(String psEight) {

        this.psEight = trimToEmpty(psEight);
    }

    /**
     * setPsExcept
     * 
     * @param psExcept psExcept property
     */
    public void setPsExcept(String psExcept) {

        this.psExcept = trimToEmpty(psExcept);
    }

    /**
     * Description
     * 
     * @param psFive psFive property
     */
    public void setPsFive(String psFive) {

        this.psFive = trimToEmpty(psFive);
    }

    /**
     * Description
     * 
     * @param psFor psFor property
     */
    public void setPsFor(String psFor) {

        this.psFor = trimToEmpty(psFor);
    }

    /**
     * Description
     * 
     * @param psFour psFour property
     */
    public void setPsFour(String psFour) {

        this.psFour = trimToEmpty(psFour);
    }

    /**
     * Description
     * 
     * @param psHours psHours property
     */
    public void setPsHours(String psHours) {

        this.psHours = trimToEmpty(psHours);
    }

    /**
     * Description
     * 
     * @param psMinutes psMinutes property
     */
    public void setPsMinutes(String psMinutes) {

        this.psMinutes = trimToEmpty(psMinutes);
    }

    /**
     * Description
     * 
     * @param psMonths psMonths property
     */
    public void setPsMonths(String psMonths) {

        this.psMonths = trimToEmpty(psMonths);
    }

    /**
     * Description
     * 
     * @param psNine psNine property
     */
    public void setPsNine(String psNine) {

        this.psNine = trimToEmpty(psNine);
    }

    /**
     * Description
     * 
     * @param psOne psOne property
     */
    public void setPsOne(String psOne) {

        this.psOne = trimToEmpty(psOne);
    }

    /**
     * Description
     * 
     * @param psOneFourth psOneFourth property
     */
    public void setPsOneFourth(String psOneFourth) {

        this.psOneFourth = trimToEmpty(psOneFourth);
    }

    /**
     * Description
     * 
     * @param psOneHalf psOneHalf property
     */
    public void setPsOneHalf(String psOneHalf) {

        this.psOneHalf = trimToEmpty(psOneHalf);
    }

    /**
     * Description
     * 
     * @param psOneThird psOneThird property
     */
    public void setPsOneThird(String psOneThird) {

        this.psOneThird = trimToEmpty(psOneThird);
    }

    /**
     * Description
     * 
     * @param psPmisPrinter psPmisPrinter property
     */
    public void setPsPmisPrinter(String psPmisPrinter) {

        this.psPmisPrinter = trimToEmpty(psPmisPrinter);
    }

    /**
     * Description
     * 
     * @param psPmisSectionDelete psPmisSectionDelete property
     */
    public void setPsPmisSectionDelete(String psPmisSectionDelete) {

        this.psPmisSectionDelete = trimToEmpty(psPmisSectionDelete);
    }

    /**
     * Description
     * 
     * @param psSeconds psSeconds property
     */
    public void setPsSeconds(String psSeconds) {

        this.psSeconds = trimToEmpty(psSeconds);
    }

    /**
     * Description
     * 
     * @param psSeven psSeven property
     */
    public void setPsSeven(String psSeven) {

        this.psSeven = trimToEmpty(psSeven);
    }

    /**
     * Description
     * 
     * @param psSix psSix property
     */
    public void setPsSix(String psSix) {

        this.psSix = trimToEmpty(psSix);
    }

    /**
     * Description
     * 
     * @param psTen psTen property
     */
    public void setPsTen(String psTen) {

        this.psTen = trimToEmpty(psTen);
    }

    /**
     * Description
     * 
     * @param psThen psThen property
     */
    public void setPsThen(String psThen) {

        this.psThen = trimToEmpty(psThen);
    }

    /**
     * Description
     * 
     * @param psThree psThree property
     */
    public void setPsThree(String psThree) {

        this.psThree = trimToEmpty(psThree);
    }

    /**
     * Description
     * 
     * @param psThreeFourths psThreeFourths property
     */
    public void setPsThreeFourths(String psThreeFourths) {

        this.psThreeFourths = trimToEmpty(psThreeFourths);
    }

    /**
     * Description
     * 
     * @return psTwo property
     */
    public String getPsTwo() {

        return psTwo;
    }

    /**
     * Description
     * 
     * @param psTwo psTwo property
     */
    public void setPsTwo(String psTwo) {

        this.psTwo = trimToEmpty(psTwo);
    }

    /**
     * Description
     * 
     * @return psTwoThirds property
     */
    public String getPsTwoThirds() {

        return psTwoThirds;
    }

    /**
     * Description
     * 
     * @param psTwoThirds psTwoThirds property
     */
    public void setPsTwoThirds(String psTwoThirds) {

        this.psTwoThirds = trimToEmpty(psTwoThirds);
    }

    /**
     * Description
     * 
     * @return psWeeks property
     */
    public String getPsWeeks() {

        return psWeeks;
    }

    /**
     * Description
     * 
     * @param psWeeks psWeeks property
     */
    public void setPsWeeks(String psWeeks) {

        this.psWeeks = trimToEmpty(psWeeks);
    }

    /**
     * Description
     * 
     * @return psCmopWarningLabelSource property
     */
    public PharmacySystemWarningLabelSource getPsCmopWarningLabelSource() {

        return psCmopWarningLabelSource;
    }

    /**
     * Description
     * 
     * @param psCmopWarningLabelSource psCmopWarningLabelSource property
     */
    public void setPsCmopWarningLabelSource(PharmacySystemWarningLabelSource psCmopWarningLabelSource) {

        this.psCmopWarningLabelSource = psCmopWarningLabelSource;
    }

    /**
     * Description
     * 
     * @return psOpaiWarningLabelSource property
     */
    public PharmacySystemWarningLabelSource getPsOpaiWarningLabelSource() {

        return psOpaiWarningLabelSource;
    }

    /**
     * Description
     * 
     * @param psOpaiWarningLabelSource psOpaiWarningLabelSource property
     */
    public void setPsOpaiWarningLabelSource(PharmacySystemWarningLabelSource psOpaiWarningLabelSource) {

        this.psOpaiWarningLabelSource = psOpaiWarningLabelSource;
    }

    /**
     * Description
     * 
     * @return psWarningLabelSource property
     */
    public PharmacySystemWarningLabelSource getPsWarningLabelSource() {

        return psWarningLabelSource;
    }

    /**
     * Description
     * 
     * @param psWarningLabelSource psWarningLabelSource property
     */
    public void setPsWarningLabelSource(PharmacySystemWarningLabelSource psWarningLabelSource) {

        this.psWarningLabelSource = psWarningLabelSource;
    }

    /**
     * Description
     * 
     * @return psPmisLanguage property
     */
    public PharmacySystemPmisLanguage getPsPmisLanguage() {

        return psPmisLanguage;
    }

    /**
     * Description
     * 
     * @param psPmisLanguage psPmisLanguage property
     */
    public void setPsPmisLanguage(PharmacySystemPmisLanguage psPmisLanguage) {

        this.psPmisLanguage = psPmisLanguage;
    }

    /**
     * List all required fields for this ValueObject, with the pre-condition that the current instance is not a read-only
     * one for the PharmacySystemVo.
     * 
     * @param environment the current {@link Environment}
     * @param roles Collection of {@link Role} instances for the current user
     * @return Set<FieldKey> All required fields for this object.
     */
    protected Set<FieldKey> handleListRequiredFields(Environment environment, Collection<Role> roles) {

        Set<FieldKey> requiredFields = super.handleListRequiredFields(environment, roles);

        requiredFields.add(FieldKey.SITE_NUMBER);
        requiredFields.add(FieldKey.PHARMACY_SYSTEM_PMIS_PRINTER);

        return requiredFields;
    }

    /**
     * Description
     * 
     * @return psSiteNumber property
     */
    public Integer getPsSiteNumber() {

        return psSiteNumber;
    }

    /**
     * Description
     * 
     * @param psSiteNumber psSiteNumber property
     */
    public void setPsSiteNumber(Integer psSiteNumber) {

        this.psSiteNumber = psSiteNumber;
    }

    /**
     * Description
     * 
     * @return psSiteName property
     */
    public String getPsSiteName() {

        return psSiteName;
    }

    /**
     * Description
     * 
     * @param psSiteName psSiteName property
     */
    public void setPsSiteName(String psSiteName) {

        this.psSiteName = psSiteName;
    }

}
