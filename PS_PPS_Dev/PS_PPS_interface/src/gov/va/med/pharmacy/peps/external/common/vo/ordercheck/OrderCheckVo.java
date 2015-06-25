/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.vo.ordercheck;


import java.util.ArrayList;
import java.util.Collection;

import gov.va.med.pharmacy.peps.common.vo.ValueObject;


/**
 * Contains data required to perform order checks
 */
public class OrderCheckVo extends ValueObject {
    private static final long serialVersionUID = 1L;
    private boolean prospectiveOnly;
    private boolean drugDrugCheck;
    private boolean drugTherapyCheck;
    private boolean drugDoseCheck;
    private Collection<DrugCheckVo> drugsToScreen = new ArrayList<DrugCheckVo>();
    private long ageInDays;
    private double bodySurfaceAreaInSqM;
    private double weightInKg;
    private boolean duplicateAllowance;
    private OrderCheckHeaderVo header;
    private boolean customTables;

    /**
     * description
     */
    public OrderCheckVo() {
        super();
    }

    /**
     * description
     * @return ageInDays property
     */
    public long getAgeInDays() {
        return ageInDays;
    }

    /**
     * description
     * @param ageInDays ageInDays property
     */
    public void setAgeInDays(long ageInDays) {
        this.ageInDays = ageInDays;
    }

    /**
     * description
     * @return bodySurfaceAreaInSqM property
     */
    public double getBodySurfaceAreaInSqM() {
        return bodySurfaceAreaInSqM;
    }

    /**
     * description
     * @param bodySurfaceAreaInSqM bodySurfaceAreaInSqM property
     */
    public void setBodySurfaceAreaInSqM(double bodySurfaceAreaInSqM) {
        this.bodySurfaceAreaInSqM = bodySurfaceAreaInSqM;
    }

    /**
     * description
     * @return drugDoseCheck property
     */
    public boolean isDrugDoseCheck() {
        return drugDoseCheck;
    }

    /**
     * description
     * @param drugDoseCheck drugDoseCheck property
     */
    public void setDrugDoseCheck(boolean drugDoseCheck) {
        this.drugDoseCheck = drugDoseCheck;
    }

    /**
     * description
     * @return drugDrugCheck property
     */
    public boolean isDrugDrugCheck() {
        return drugDrugCheck;
    }

    /**
     * description
     * @param drugDrugCheck drugDrugCheck property
     */
    public void setDrugDrugCheck(boolean drugDrugCheck) {
        this.drugDrugCheck = drugDrugCheck;
    }

    /**
     * description
     * @return prospectiveOnly property
     */
    public boolean isProspectiveOnly() {
        return prospectiveOnly;
    }

    /**
     * description
     * @param prospectiveOnly prospectiveOnly property
     */
    public void setProspectiveOnly(boolean prospectiveOnly) {
        this.prospectiveOnly = prospectiveOnly;
    }

    /**
     * description
     * @return therapyCheck property
     */
    public boolean isDrugTherapyCheck() {
        return drugTherapyCheck;
    }

    /**
     * description
     * @param therapyCheck therapyCheck property
     */
    public void setDrugTherapyCheck(boolean therapyCheck) {
        this.drugTherapyCheck = therapyCheck;
    }

    /**
     * description
     * @return weightInKg property
     */
    public double getWeightInKg() {
        return weightInKg;
    }

    /**
     * description
     * @param weightInKg weightInKg property
     */
    public void setWeightInKg(double weightInKg) {
        this.weightInKg = weightInKg;
    }

    /**
     * description
     * @return drugsToScreen property
     */
    public Collection<DrugCheckVo> getDrugsToScreen() {
        return drugsToScreen;
    }
    
    /**
     * True if {@link #drugsToScreen} is not null as is not empty.
     * 
     * @return boolean true if there are drugs to screen
     */
    public boolean hasDrugsToScreen() {
        return drugsToScreen != null && !drugsToScreen.isEmpty();
    }

    /**
     * description
     * @param drugsToScreen drugsToScreen property
     */
    public void setDrugsToScreen(Collection<DrugCheckVo> drugsToScreen) {
        this.drugsToScreen = drugsToScreen;
    }

    /**
     * description
     * @return duplicateAllowance property
     */
    public boolean isDuplicateAllowance() {
        return duplicateAllowance;
    }

    /**
     * description
     * @param duplicateAllowance duplicateAllowance property
     */
    public void setDuplicateAllowance(boolean duplicateAllowance) {
        this.duplicateAllowance = duplicateAllowance;
    }

    /**
     * description
     * @return header property
     */
    public OrderCheckHeaderVo getHeader() {
        return header;
    }

    /**
     * description
     * @param header header property
     */
    public void setHeader(OrderCheckHeaderVo header) {
        this.header = header;
    }

    /**
     * description
     * @return customTables property
     */
    public boolean isCustomTables() {
        return customTables;
    }

    /**
     * description
     * @param customTables customTables property
     */
    public void setCustomTables(boolean customTables) {
        this.customTables = customTables;
    }
}
