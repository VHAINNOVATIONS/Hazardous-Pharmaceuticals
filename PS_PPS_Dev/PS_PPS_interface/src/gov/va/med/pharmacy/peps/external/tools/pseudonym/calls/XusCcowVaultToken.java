/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * XusCcowVaultToken
 */
public class XusCcowVaultToken extends AbstractCall {

    /**
     * default no-arg constructor
     */
    public XusCcowVaultToken() {
        super();
    }

    /**
     * handles the requst for this call
     * @param call the call that was made
     * @return the response to the request
     * @throws PseudonymException PseudonymException
     * 
     * @see gov.va.med.pharmacy.external.pseudonym.calls.Call#getResponse(
     *      gov.va.med.pharmacy.external.pseudonym.vistalink.Request)
     */
    public Response getResponse(Request call) throws PseudonymException {
        return new Response(call, processTemplate(this), "gov.va.med.foundations.rpc.response", "rpcResponse.xsd");
    }
}
