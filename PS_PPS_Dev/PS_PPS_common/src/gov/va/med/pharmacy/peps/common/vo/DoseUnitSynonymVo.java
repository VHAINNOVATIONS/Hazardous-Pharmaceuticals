/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;


/**
 * Managed data for dose unit Synonym.
 */
public class DoseUnitSynonymVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private String doseUnitSynonymName;
    private String doseUnitId;
    
    /**
     * getDoseUnitSynonymName.
     * @return doseUnitSynonymName property
     */
    public String getDoseUnitSynonymName() {
        return doseUnitSynonymName;
    }
    
    /**
     * setDoseUnitSynonymName.
     * @param doseUnitSynonymName doseUnitSynonymName property
     */
    public void setDoseUnitSynonymName(String doseUnitSynonymName) {
        this.doseUnitSynonymName = trimToEmpty(doseUnitSynonymName);
    }
    
    /**
     * getDoseUnitId.
     * @return doseUnitId property
     */
    public String getDoseUnitId() {
        return doseUnitId;
    }

    /**
     * setDoseUnitId.
     * 
     * @param doseUnitId doseUnitId property
     */
    public void setDoseUnitId(String doseUnitId) {
        this.doseUnitId = trimToEmpty(doseUnitId);
    }       
   
    /**
     * toShortString returns toString unless overridden in VO.
     * 
     * @return String properties and values of the ValueObject
     * 
     * @see java.lang.Object#toString()
     */
    public String toShortString() {
        
        
        String s1 = FieldKey.getLocalizedName(FieldKey.DOSE_UNIT_SYNONYM_NAME, Locale.getDefault());

        StringBuffer value = new StringBuffer(s1 + ": " 
            + (doseUnitSynonymName == null ? "(Not specified)" + PPSConstants.P_TAG : doseUnitSynonymName));  
        
        return value.toString();
    }
}
