/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Lab test monitor
 */
public class LabTestMonitorVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;

    private String value;

    /**
     * getId for LabTestMonitorVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getId()
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue for LabTestMonitorVo.
     * 
     * @return value property
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.Selectable#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for LabTestMonitorVo.
     * 
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for LabTestMonitorVo.
     * 
     * @return {@link #getValue()}
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#toShortString()
     */
    public String toShortString() {
        return getValue();
    }
    
    /**
     * Added specific code for null value objects to = others
     * 
     * @param other object to compare too
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        
        if (other instanceof LabTestMonitorVo) {
            return super.equals(other);
        }
        
        if ((this.value == null || this.value.length() == 0)  && other == null) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Override of hashCode to prevent PMD errors  for LabTestMonitorVo.
     * 
     * @return hashCode
     * 
     * @see gov.va.med.pharmacy.peps.common.vo.ValueObject#hashCode()
     */
    public int hashCode() {
        int x = super.hashCode();
      
        return x;
    }
}
