/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.common.vo;


import gov.va.med.pharmacy.peps.common.vo.validator.Errors;


/**
 * Contains the processed RequestVo and ManagedItemVo after a both have been updated.
 * <p>
 * Allows the Service layer to return both objects from a method for the Presentation layer to display.
 */
public class ProcessedRequestVo extends ValueObject {
    private static final long serialVersionUID = 1L;

    private RequestVo request;
    private ManagedItemVo item;
    private Errors warnings;

    /**
     * Default, empty constructor.
     * <p>
     * Be sure to call {@link #setItem(ManagedItemVo)}, {@link #setRequest(RequestVo)}, and {@link #setWarnings(Errors)} to
     * populate this instance.
     */
    public ProcessedRequestVo() {
        super();
    }

    /**
     * Set the {@link RequestVo}, {@link ManagedItemVo}, and warnings.
     * 
     * @param item updated {@link ManagedItemVo}
     * @param request updated {@link RequestVo}
     * @param warnings {@link Errors} representing warnings for the given {@link ManagedItemVo}
     */
    public ProcessedRequestVo(ManagedItemVo item, RequestVo request, Errors warnings) {
        this.request = request;
        this.item = item;
        this.warnings = warnings;
    }

    /**
     * getRequest
     * 
     * @return request property
     */
    public RequestVo getRequest() {
        return request;
    }

    /**
     * setRequest
     * 
     * @param request request property
     */
    public void setRequest(RequestVo request) {
        this.request = request;
    }

    /**
     * getItem for ProcessedRequestVo
     * 
     * @return item property
     */
    public ManagedItemVo getItem() {
        return item;
    }

    /**
     * setWarnings
     * 
     * @param item item property
     */
    public void setItem(ManagedItemVo item) {
        this.item = item;
    }

    /**
     * getWarnings for the ProcessRequestVo.
     * 
     * @return warnings property
     */
    public Errors getWarnings() {
        return warnings;
    }

    /**
     * setWarnings for the ProcessRequestVo.
     * 
     * @param warnings property
     */
    public void setWarnings(Errors warnings) {
        this.warnings = warnings;
    }
}
