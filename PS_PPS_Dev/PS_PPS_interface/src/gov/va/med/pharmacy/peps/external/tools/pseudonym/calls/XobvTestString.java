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
public class XobvTestString extends AbstractCall {

    /**
     * default no-arg constructor
     */
    public XobvTestString() {
        super();
    }

    /**
     * This method generates a Response object and set's the attribute values of the Response object. 
     * In addition, a template file is used to simplify maintainability. 
     * 
     * @param call client request
     * @return Response The response returned.
     * @exception PseudonymException If an exception occurred
     */
    public Response getResponse(Request call) throws PseudonymException {
        String decryptedString = ((Request.StringParameter) call.getParameters().get(0)).getValue();

        return new Response(call, 
            "<Response type=\"array\"><![CDATA[Returned Input Value: " + decryptedString + "]]></Response>");
    }

}
