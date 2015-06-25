/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import java.util.ArrayList;
import java.util.Collection;


/**
 * Types available to an Item
 */
public enum TemplateType {

    /** PERSONAL */
    PERSONAL,

    /** LOCAL */
    LOCAL,

    /** NATIONAL */
    NATIONAL;

    /** 
     * Get the user's PERSONAL templates
     * 
     * @return Collection of PERSONAL templates
     */
    public static Collection<TemplateType> getPersonalValues() {
        ArrayList<TemplateType> ret = new ArrayList<TemplateType>();
        ret.add(PERSONAL);
        
        return ret;
    }
    
    /**
     * Get the Local Templates
     * 
     * @return Collection of the LOCAL and PERSONAL templates 
     */
    public static Collection<TemplateType> getLocalValues() {
        ArrayList<TemplateType> ret = new ArrayList<TemplateType>();
        ret.add(PERSONAL);
        ret.add(LOCAL);
        
        return ret;
    }

    /**
     * Get the National Templates
     * 
     * @return Collection of the NATIONAL and PERSONAL templates
     */
    public static Collection<TemplateType> getNationalValues() {
        ArrayList<TemplateType> ret = new ArrayList<TemplateType>();
        ret.add(PERSONAL);
        ret.add(NATIONAL);
        
        return ret;
    }

    /**
     * Return true if this {@link TemplateType} is {@link #LOCAL} or {@link #NATIONAL}.
     * 
     * @return boolean
     */
    public boolean isSystemLevel() {
        return LOCAL.equals(this) || NATIONAL.equals(this);
    }

}
