/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym;


/**
 * PseudonymRuntimeException
 */
public class PseudonymRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1;

    /**
     * PseudonymRuntimeException
     */
    public PseudonymRuntimeException() {
        super();
    }

    /**
     * PseudonymRuntimeException
     * @param arg0 the description of what happened
     */
    public PseudonymRuntimeException(String arg0) {
        super(arg0);
    }

    /**
     * PseudonymRuntimeException
     * @param arg0 the exception to wrap
     */
    public PseudonymRuntimeException(Throwable arg0) {
        super(arg0);
    }

    /**
     * PseudonymRuntimeException
     * @param arg0 the description of what happened
     * @param arg1 the exception to wrap
     */
    public PseudonymRuntimeException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
