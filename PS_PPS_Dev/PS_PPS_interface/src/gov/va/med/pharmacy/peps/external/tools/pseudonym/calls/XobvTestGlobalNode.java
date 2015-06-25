/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * Test ping from Vista
 */
public class XobvTestGlobalNode extends AbstractCall {

    /**
     * Test ping from Vista 
     * 
     * @param parameters Request
     * @return Response form Vista for th testPing
     * @throws PseudonymException PseudonymException
     * 
     * @see gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.Call#getResponse(
     *      gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request)
     */
    public Response getResponse(Request parameters) throws PseudonymException {
        return new Response(parameters, "<Response type=\"array\"><![CDATA[XOBV TEST GLOBAL NODE RESPONSE]]></Response>");
    }
}
