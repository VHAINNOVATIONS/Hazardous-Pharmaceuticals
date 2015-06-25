/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * Interface for generating server responses
 */
public interface Call {

    /**
     * Method that the class loader will look for to handle requests in any class that has been specified to handle a
     * request.
     * @param parameters data for generating response based on client request
     * @return Response object 
     * @throws PseudonymException If an exception occurred
     */
    Response getResponse(Request parameters) throws PseudonymException;
}
