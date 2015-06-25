/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * Loaded by pseodonyM server if needed to generate response.  Generates a response object using a Velocity Template File. 
 */
public class Logout extends AbstractCall {

    /**
     * default no-arg constructor
     */
    public Logout() {
        super();
    }

    /**
     * This method generates a Response object and set's the attribute values of the Response object. 
     * In addition, a template file is used to simplify maintainability. 
     * 
     * @param call The request object from the Browser
     *      * @return Response object
     * @exception PseudonymException If an exception occurred 
     * 
     */
    public Response getResponse(Request call) throws PseudonymException {
        return new Response(call, processTemplate(this), "gov.va.med.foundations.security.response", "secSimpleResponse.xsd");
    }
}
