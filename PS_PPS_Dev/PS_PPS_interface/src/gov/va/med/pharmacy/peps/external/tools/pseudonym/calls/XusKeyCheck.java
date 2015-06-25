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
public class XusKeyCheck extends AbstractCall {

    /**
     * default no-arg constructor
     */
    public XusKeyCheck() {
        super();
    }

    /**
     * This method generates a Response object and set's the attribute values of the Response object. 
     * In addition, a template file is used to simplify maintainability for the XusKeyCheck.
     *  
     * @param call client request
     * @return Response XusKeyCheck object
     * @exception PseudonymException If an exception occurred
     * 
     * @see gov.va.med.pharmacy.external.pseudonym.calls.Call
     * #getResponse(gov.va.med.pharmacy.external.pseudonym.vistalink.Request)
     */
    public Response getResponse(Request call) throws PseudonymException {
        return new Response(call, processTemplate(this));
    }

}
