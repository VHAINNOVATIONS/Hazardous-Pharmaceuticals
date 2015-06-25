/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym;


/**
 * The exception class that should be thrown to all clients for any exceptions occuring within PseudonyM
 */
public class PseudonymException extends Exception {

    private static final long serialVersionUID = 1;
    
    /**
     * Used to create the exception with only a string parameter
     * @param message the error message describing what went wrong
     */
    public PseudonymException(String message) {
        super(message);
    }

    /**
     * Constructor used to chain exceptions and provide an error message
     * @param message the error message describing what went wrong
     * @param e the exception to chain to this exception
     */
    public PseudonymException(String message, Exception e) {
        super(message, e);
    }

    /**
     * Used to only chain exceptions.
     * @param e the exception to chain to this exception
     */
    public PseudonymException(Exception e) {
        super(e);
    }
}
