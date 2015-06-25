/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Contains the processed item and any warnings
 */
public class ProcessedItemVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private Errors warnings = new Errors();
    private ManagedItemVo item;

    /**
     * Default, empty constructor.
     */
    public ProcessedItemVo() {
        super();
    }

    /**
     * Set the {@link ManagedItemVo} and the warnings.
     * 
     * @param item processed {@link ManagedItemVo}
     * @param warnings {@link Errors} representing warnings for the {@link ManagedItemVo}
     */
    public ProcessedItemVo(ManagedItemVo item, Errors warnings) {
        this.item = item;
        this.warnings = warnings;
    }

    /**
     * Return true if the warnings is not null and has warnings.
     * 
     * @return boolean
     */
    public boolean hasWarnings() {
        return warnings != null && warnings.hasErrors();
    }

    /**
     * getWarnings
     * 
     * @return warnings property
     */
    public Errors getWarnings() {
        return warnings;
    }

    /**
     * setWarnings
     * 
     * @param warnings warnings property
     */
    public void setWarnings(Errors warnings) {
        this.warnings = warnings;
    }

    /**
     * getItem
     * 
     * @return item property
     */
    public ManagedItemVo getItem() {
        return item;
    }

    /**
     * setItem
     * 
     * @param item item property
     */
    public void setItem(ManagedItemVo item) {
        this.item = item;
    }
}
