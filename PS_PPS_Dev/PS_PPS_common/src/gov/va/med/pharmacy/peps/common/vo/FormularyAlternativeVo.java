/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.Locale;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;




/**
 * Data representing a Product's formulary alternative association
 */
public class FormularyAlternativeVo extends ValueObject {
    private static final long serialVersionUID = 1L;

//    private String nonFormularyId;
    private String formularyAlternativeId;

    /**
     * getFormularyAlternativeId
     * 
     * @return formularyAlternativeId property
     */
    public String getFormularyAlternativeId() {
        return formularyAlternativeId;
    }

    /**
     * setFormularyAlternativeId
     * 
     * @param formularyAlternativeId formularyAlternativeId property
     */
    public void setFormularyAlternativeId(String formularyAlternativeId) {
        this.formularyAlternativeId = trimToEmpty(formularyAlternativeId);
    }

    /**
     * toShortString returns toString unless overridden in FormularyAlternativeVo
     * 
     * @return String properties and values of the ValueObject
     */
    @Override
    public String toShortString() {

        String s1 = FieldKey.getLocalizedName(FieldKey.FORMULARY_ALTERNATIVE, Locale.getDefault());
 
        return s1 + ": " + (formularyAlternativeId == null ? "(Not specified)" : formularyAlternativeId) 
            + PPSConstants.P_TAG;
            
    }

}
