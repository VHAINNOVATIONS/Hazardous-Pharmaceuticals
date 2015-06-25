/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;




/**
 * This class encapsulates functionality for Local Possible Dosages.
 */
public class LocalPossibleDosagesVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    @IgnoreEquals
    private Long id;
    
    private Double bcmaUnitsPerDose;
    private String localPossibleDosage;
    private String otherLanguageDosageName;
    private Collection<PossibleDosagesPackageVo> possibleDosagePackage = new ArrayList<PossibleDosagesPackageVo>(0);
    private DoseUnitVo doseUnit;
    private double numericDose;

    /**
     * getId
     * 
     * @return id property
     */
    public Long getId() {
        return id;
    }

    /**
     * setId
     * 
     * @param id id property
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getBcmaUnitsPerDose
     * 
     * @return bcmaUnitsPerDose property
     */
    public Double getBcmaUnitsPerDose() {
        return bcmaUnitsPerDose;
    }

    /**
     * setBcmaUnitsPerDose
     * 
     * @param bcmaUnitsPerDose bcmaUnitsPerDose property
     */
    public void setBcmaUnitsPerDose(Double bcmaUnitsPerDose) {
        this.bcmaUnitsPerDose = bcmaUnitsPerDose;
    }

    /**
     * getDoseUnitVo
     * 
     * @return doseUnitVo property
     */
    public DoseUnitVo getDoseUnitVo() {
        return doseUnit;
    }

    /**
     * setDoseUnitVo
     * 
     * @param doseUnitVo doseUnitVo property
     */
    public void setDoseUnitVo(DoseUnitVo doseUnitVo) {
        this.doseUnit = doseUnitVo;
    }

    /**
     * getLocalPossibleDosage
     * 
     * @return localPossibleDosage property
     */
    public String getLocalPossibleDosage() {
        return localPossibleDosage;
    }

    /**
     * setLocalPossibleDosage
     * 
     * @param localPossibleDosage localPossibleDosage property
     */
    public void setLocalPossibleDosage(String localPossibleDosage) {
        this.localPossibleDosage = trimToEmpty(localPossibleDosage);
    }

    /**
     * getOtherLanguageDosageName
     * 
     * @return otherLanguageDosageName property
     */
    public String getOtherLanguageDosageName() {
        return otherLanguageDosageName;
    }

    /**
     * setOtherLanguageDosageName
     * 
     * @param otherLanguageDosageName otherLanguageDosageName property
     */
    public void setOtherLanguageDosageName(String otherLanguageDosageName) {
        this.otherLanguageDosageName = otherLanguageDosageName;
    }

    /**
     * getPossibleDosagePackage
     * 
     * @return possibleDosagePackage property
     */
    public Collection<PossibleDosagesPackageVo> getPossibleDosagePackage() {
        return possibleDosagePackage;
    }

    /**
     * setPossibleDosagePackage
     * 
     * @param possibleDosagePackage property
     */
    public void setPossibleDosagePackage(Collection<PossibleDosagesPackageVo> possibleDosagePackage) {
        this.possibleDosagePackage = new ArrayList<PossibleDosagesPackageVo>();

        if (possibleDosagePackage != null && !possibleDosagePackage.isEmpty()) {
            this.possibleDosagePackage.addAll(possibleDosagePackage);
        }
    }
    
    /**
     * toShortString returns toString unless overridden in LocalPossibleDosagesVo.
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toShortString() {

        String s1 = FieldKey.getLocalizedName(FieldKey.BCMA_UNITS_PER_DOSE, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.LOCAL_POSSIBLE_DOSAGE, Locale.getDefault()); 
        String s3 = FieldKey.getLocalizedName(FieldKey.DOSE_UNIT, Locale.getDefault());
        String s4 = FieldKey.getLocalizedName(FieldKey.OTHER_LANGUAGE_DOSAGE_NAME, Locale.getDefault());
        String s5 = FieldKey.getLocalizedName(FieldKey.NUMERIC_DOSE, Locale.getDefault());
        String s6 = FieldKey.getLocalizedName(FieldKey.PACKAGE_PREFIX, Locale.getDefault());
        
        
        StringBuffer sb = new StringBuffer();
        sb.append(s1 + ": " + (bcmaUnitsPerDose == null ? PPSConstants.NOT_SPECIFIED : bcmaUnitsPerDose) + PPSConstants.P_TAG);
        sb.append(s2 + ": " + (localPossibleDosage == null ? PPSConstants.NOT_SPECIFIED : localPossibleDosage) 
            + PPSConstants.P_TAG);
        sb.append(s3 + ": " + (doseUnit == null ? PPSConstants.NOT_SPECIFIED : doseUnit.getDoseUnitName())
            + PPSConstants.P_TAG);
        sb.append(s4 + ": " + (otherLanguageDosageName == null ? PPSConstants.NOT_SPECIFIED : otherLanguageDosageName) 
            + PPSConstants.P_TAG);
        sb.append(s5 + ": " + numericDose + PPSConstants.P_TAG);
       
       
        if (possibleDosagePackage == null || possibleDosagePackage.size() <= 0) {
            sb.append(s6 + ": No Package specified");
        } else {
            sb.append(s6 + ": ");
            boolean bFirstTime = true;
            
            for (PossibleDosagesPackageVo possD : possibleDosagePackage) {
            
                // Only put in the , if this is not the first item in the list.
                if (bFirstTime) {
                    bFirstTime = false;
                } else {
                    sb.append(", ");
                }
         
                sb.append(possD.toShortString());
            }
        }

        return sb.toString();
    }

    /**
     * getDoseUnit
     * 
     * @return DoseUnitVo
     */
    public DoseUnitVo getDoseUnit() {
        return doseUnit;
    }

    /**
     * setDoseUnit
     * 
     * @param doseUnit value
     */
    public void setDoseUnit(DoseUnitVo doseUnit) {
        this.doseUnit = doseUnit;
    }

    /**
     * getNumericDose
     * 
     * @return numericDose property
     */
    public double getNumericDose() {
        return numericDose;
    }

    /**
     * setNumericDose
     * 
     * @param numericDose value
     */
    public void setNumericDose(double numericDose) {
        this.numericDose = numericDose;
    }

}
