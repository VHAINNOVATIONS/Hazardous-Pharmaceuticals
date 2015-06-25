/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * Test ping of Vista server
 */
public class XobvTestPing extends AbstractCall {

    /**
     * Test ping of Vista server
     * 
     * @param parameters Vista request
     * @return Response for Vista
     * @throws PseudonymException PseudonymException
     * 
     * @see gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.Call#getResponse(
     *      gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request)
     */
    public Response getResponse(Request parameters) throws PseudonymException {
        return new Response(parameters, "<Response type=\"array\"><![CDATA[XOBV TEST PING RESPONSE]]></Response>");
    }
}
