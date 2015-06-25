/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Data representing a dispense unit per dose multidatafield
 */
public class DispenseUnitPerDoseVo extends ValueObject {

    private static final long serialVersionUID = 1L;

    private String strDispenseUnitPerDose;
    private Collection<PossibleDosagesPackageVo> packages = new ArrayList<PossibleDosagesPackageVo>(0);

    //added during Migration
    private String dispenseUnitPerDosIen = "";

    /**
     * getDispenseUnitPerDosIen.
     * @return the IEN string
     */
    public String getDispenseUnitPerDosIen() {

        return dispenseUnitPerDosIen;
    }

    /**
     * Set the IEN string value.
     * 
     * @param dispenseUnitPerDosIen dispenseUnitPerDosIen
     */
    public void setDispenseUnitPerDosIen(String dispenseUnitPerDosIen) {

        this.dispenseUnitPerDosIen = dispenseUnitPerDosIen;
    }

    /**
     * Returns true if the domain is standardized.
     * 
     * @return boolean
     */
    public boolean isStandardized() {

        return true;
    }

    /**
     * Returns true if this is a local only domain for the DispenseUnitPerDoseVo.
     * 
     * @return boolean
     */
    public boolean isLocal() {

        return false;
    }

    /**
     * Returns true if this is a local only domain for DispenseUnitPerDoseVo
     * 
     * @return boolean
     */
    public boolean isLocalOnlyData() {

        return false;
    }

    /**
     * getStrDispenseUnitPerDose for DispenseUnitPerDoseVo.
     * 
     * @return strDispenseUnitPerDose property
     */
    public String getStrDispenseUnitPerDose() {

        return strDispenseUnitPerDose;
    }

    /**
     * setStrDispenseUnitPerDose.
     * 
     * @param strDispenseUnitPerDose strDispenseUnitPerDose property
     */
    public void setStrDispenseUnitPerDose(String strDispenseUnitPerDose) {

        this.strDispenseUnitPerDose = trimToEmpty(strDispenseUnitPerDose);
        this.strDispenseUnitPerDose = removeTrailingZeros(strDispenseUnitPerDose);
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

    /**
     * toShortString returns toString unless overridden in VO
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toShortString() {

        String s1 = FieldKey.getLocalizedName(FieldKey.DRUG_UNIT, Locale.getDefault());
        String s2 = FieldKey.getLocalizedName(FieldKey.PACKAGES, Locale.getDefault());

        StringBuffer value = new StringBuffer(s1 + ": "
            + (strDispenseUnitPerDose == null ? "(Not specified)" + PPSConstants.P_TAG : strDispenseUnitPerDose + "<br //>")
            + s2 + ": ");

        // Check the Packages
        for (PossibleDosagesPackageVo pk : packages) {
            value.append(pk.toShortString() + " ");
        }

        value.append(PPSConstants.P_TAG);

        return value.toString();
    }
    
    /**
     * Removes trailing zeros from string for DispenseUnitPerDoseVo
     * @param str String to remove trailing zeros from
     * @return substring without trailing zeros
     */
    private String removeTrailingZeros(String str) {

        String returnString = str;
        String[] split = StringUtils.split(returnString, ".");

        if (split != null && split.length > 1) {
            String stripped = StringUtils.stripEnd(split[1], "0");

            if (StringUtils.isNotEmpty(stripped)) {
                returnString = split[0] + "." + stripped;
            } else {
                returnString = split[0];
            }

        }

        return returnString;
    }

}
