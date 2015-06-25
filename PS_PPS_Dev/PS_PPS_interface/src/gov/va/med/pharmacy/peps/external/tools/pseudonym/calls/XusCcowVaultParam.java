/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * XusCcowVaultParam
 */
public class XusCcowVaultParam extends AbstractCall {

    /**
     * default no-arg constructor
     */
    public XusCcowVaultParam() {
        super();
    }

    /**
     * handles the request
     * @param call the call that was made
     * @return the response to the call
     * @throws PseudonymException if an error occured
     *  
     * @see gov.va.med.pharmacy.external.pseudonym.calls.Call#getResponse(
     *      gov.va.med.pharmacy.external.pseudonym.vistalink.Request)
     */
    public Response getResponse(Request call) throws PseudonymException {
        return new Response(call, processTemplate(this), "gov.va.med.foundations.rpc.fault", "rpcFault.xsd");
    }
}
