/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


/**
 * Lab display order entry
 */
public class LabDisplayOrderEntryVo extends ValueObject implements Selectable {
    private static final long serialVersionUID = 1L;
    
    private String value;

    /**
     * getId for LabDisplayOrderEntryVo
     * @return {@link #getValue()}
     */
    public String getId() {
        return getValue();
    }

    /**
     * getValue  for LabDisplayOrderEntryVo
     * @return value property
     */
    public String getValue() {
        return value;
    }

    /**
     * setValue for LabDisplayOrderEntryVo
     * @param value property
     */
    public void setValue(String value) {
        this.value = trimToEmpty(value);
    }

    /**
     * toShortString for LabDisplayOrderEntryVo
     * 
     * @return {@link #getValue()}
     */
    public String toShortString() {
        return getValue() + "<p>";
    }
}
