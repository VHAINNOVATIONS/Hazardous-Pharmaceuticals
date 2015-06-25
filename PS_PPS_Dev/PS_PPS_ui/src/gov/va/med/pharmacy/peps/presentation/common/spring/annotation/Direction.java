/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.presentation.common.spring.annotation;


/**
 * The "direction" the flash, flow, conversation, or session scoped field should be set.
 */
public enum Direction {
    
    /**
     * IN
     */
    IN,
    
    /**
     * OUT
     */
    OUT,
    
    /**
     * BOTH
     */
    BOTH;
    
    /**
     * isIn
     * @return true if this instance is equal to {@link #IN}
     */
    public boolean isIn() {
        return IN.equals(this);
    }
    
    /**
     * isOut
     * @return true if this instance is equal to {@link #OUT}
     */
    public boolean isOut() {
        return OUT.equals(this);
    }
    
    /**
     * isBoth
     * @return true if this instance is equal to {@link #BOTH}
     */
    public boolean isBoth() {
        return BOTH.equals(this);
    }
}
