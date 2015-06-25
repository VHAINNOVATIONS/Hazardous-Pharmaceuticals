/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.vo.diff.IgnoreDifference;


/**
 * Represent National Possible Dosages Value Object
 * 
 * @see ValueObject
 */
public class NationalPossibleDosagesVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    @IgnoreEquals
    @IgnoreDifference
    private Long id;

    private Double bcmaUnitsPerDose;
    private Double possibleDosagesDispenseUnitsPerDose;
    private Double dose;
    private Collection<PossibleDosagesPackageVo> possibleDosagePackage = new ArrayList<PossibleDosagesPackageVo>(0);

    /**
     * return the Vo Id
     * 
     * @return id property
     */
    public Long getId() {
        return id;
    }

    /**
     * set the Vo Id
     * 
     * @param id id property
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * return the BCMA Units per dose property
     * 
     * @return bcmaUnitsPerDose property
     */
    public Double getBcmaUnitsPerDose() {
        return bcmaUnitsPerDose;
    }

    /**
     * set the BCMA units per dose property
     * 
     * @param bcmaUnitsPerDose bcmaUnitsPerDose property
     */
    public void setBcmaUnitsPerDose(Double bcmaUnitsPerDose) {
        this.bcmaUnitsPerDose = bcmaUnitsPerDose;
    }

    /**
     * return the dispense units per dose property
     * 
     * @return dispenseUnitsPerDose property
     */
    public Double getPossibleDosagesDispenseUnitsPerDose() {
        return possibleDosagesDispenseUnitsPerDose;
    }

    /**
     * set the dispense units per dose
     * 
     * @param dispenseUnitsPerDose dispenseUnitsPerDose property
     */
    public void setPossibleDosagesDispenseUnitsPerDose(Double dispenseUnitsPerDose) {
        this.possibleDosagesDispenseUnitsPerDose = dispenseUnitsPerDose;
    }

    /**
     * return the dose property
     * 
     * @return dose property
     */
    public Double getDose() {
        return dose;
    }

    /**
     * set the dose property
     * 
     * @param dose dose property
     */
    public void setDose(Double dose) {
        this.dose = dose;
    }

    /**
     * 
     * returns a collection of possible dose packages
     * 
     * @return possibleDosagesPackage property
     */
    public Collection<PossibleDosagesPackageVo> getPossibleDosagePackage() {
        return possibleDosagePackage;
    }

    /**
     * 
     * sets the list of possible dose packages
     * 
     * @param possibleDosagesPackage property
     */
    public void setPossibleDosagePackage(Collection<PossibleDosagesPackageVo> possibleDosagesPackage) {
        this.possibleDosagePackage = new ArrayList<PossibleDosagesPackageVo>();

        if (possibleDosagesPackage != null && !possibleDosagesPackage.isEmpty()) {
            this.possibleDosagePackage.addAll(possibleDosagesPackage);
        }
    }

    /**
     * toShortString returns toString unless overridden in NationalPossibleDosagesVo
     * 
     * @return String properties and values of the ValueObject
     */
    @Override
    public String toShortString() {

        String s1 = FieldKey.getLocalizedName(FieldKey.BCMA_UNITS_PER_DOSE, Locale.getDefault());
        String s2 = FieldKey.getLocalizedAbbreviation(FieldKey.DISPENSE_UNIT_PER_DOSE, Locale.getDefault());
        String s3 = FieldKey.getLocalizedName(FieldKey.DOSE_UNIT, Locale.getDefault());
        String s4 = FieldKey.getLocalizedName(FieldKey.PACKAGE_PREFIX, Locale.getDefault());

        StringBuffer sb = new StringBuffer();
        sb.append(s1 + ": " + (bcmaUnitsPerDose == null ? "(Not specified)" : bcmaUnitsPerDose) + "<p>");
        sb.append(s2 + ": "
            + (possibleDosagesDispenseUnitsPerDose == null ? "(Not specified)" : possibleDosagesDispenseUnitsPerDose) + "<p>");
        sb.append(s3 + ": " + dose + "<p>");

        if (possibleDosagePackage == null || possibleDosagePackage.size() <= 0) {
            sb.append(s4 + ": No Package specified");
        } else {
            sb.append(s4 + ": ");
            boolean bFirstTime = true;

            for (PossibleDosagesPackageVo possD : possibleDosagePackage) {

                // Only put in the , if this is not the first item in the list.
                if (bFirstTime) {
                    bFirstTime = false;
                } else {
                    sb.append(", ");
                }

                // add the short string of the possible dosage
                sb.append(possD.toShortString());
            }
        }

        return sb.toString();
    }

}
