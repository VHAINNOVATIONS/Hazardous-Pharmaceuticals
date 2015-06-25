/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Data representing a DosageForm's dosage form unit MDF
 */
public class DosageFormUnitVo extends ValueObject   {
    private static final long serialVersionUID = 1L;

    private DrugUnitVo drugUnit;
    private Collection<PossibleDosagesPackageVo> packages = new ArrayList<PossibleDosagesPackageVo>(0);

    /**
     * toShortString returns toString unless overridden in VO.
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toShortString() {
        String s1 = FieldKey.getLocalizedName(FieldKey.DRUG_UNIT, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.PACKAGES, Locale.getDefault());

        StringBuffer value = new StringBuffer(s1 + ": " 
            + (drugUnit == null ? "(Not specified)" + PPSConstants.P_TAG : drugUnit.getValue() + "<br //>")
            + s2 + ": "); 
        
        for (PossibleDosagesPackageVo pk : packages) {
            value.append(pk.toShortString() + " ");
        }

        value.append(PPSConstants.P_TAG);
        
        return value.toString();
    }
    
    /**
     * getDrugUnit.
     * @return drugUnit property
     */
    public DrugUnitVo getDrugUnit() {
        return drugUnit;
    }

    /**
     * setDrugUnit.
     * 
     * @param drugUnit drugUnit property
     */
    public void setDrugUnit(DrugUnitVo drugUnit) {
        this.drugUnit = drugUnit;
    }

    /**
     * Returns true if the domain is standardized
     * 
     * @return boolean
     */
    public boolean isStandardized() {
        return true;
    }

    /**
     * Returns true if this is a local only domain
     * 
     * @return boolean
     */
    public boolean isLocal() {
        return false;
    }

    /**
     * Returns true if this is a local only domain for DosageFormUnitVo.
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {
        return false;
    }

    /**
     * getPackages.
     * @return packages property
     */
    public Collection<PossibleDosagesPackageVo> getPackages() {
        return packages;
    }

    /**
     * setPackages.
     * 
     * @param packages property
     */
    public void setPackages(Collection<PossibleDosagesPackageVo> packages) {
        this.packages = new ArrayList<PossibleDosagesPackageVo>();

        if (packages != null && !packages.isEmpty()) {
            this.packages.addAll(packages);
        }
    }
}
