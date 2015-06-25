/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.fatkaat.usermanagement;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;


/**
 * Exception that is thrown when trying to retrieve the verified user but no user has been verified yet
 */
public class NoUserSetException extends PseudonymException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor used to chain another exception to the new one along with a error message
     * @param message the error message to associate with this exception
     * @param e the exception to chain with this exception
     */
    public NoUserSetException(String message, Exception e) {
        super(message, e);
    }

    /**
     * Constructor used to create this exception type with a message associated with it
     * @param message the error message to associate with this exception
     */
    public NoUserSetException(String message) {
        super(message);
    }

    /**
     * Constructor used to chain another exception to the new one
     * @param e the exception to chain with this exception
     */
    public NoUserSetException(Exception e) {
        super(e);
    }

}
