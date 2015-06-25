/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.peps;


import gov.va.med.pharmacy.peps.external.tools.pseudonym.PseudonymException;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.calls.AbstractCall;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Request;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.vistalink.Response;


/**
 * XusPepsDomainsXml
 */
public class XusPepsDomainsXml extends AbstractCall {

    /**
     * default no-arg constructor
     */
    public XusPepsDomainsXml() {
        super();
    }

    /**
     * gets the response for the XusPepsDomainsXml call.
     * 
     * @param call the request
     * @return the response
     * @throws PseudonymException PseudonymException
     * 
     * @see gov.va.med.pharmacy.external.pseudonym.calls.Call#getResponse(
     *      gov.va.med.pharmacy.external.pseudonym.vistalink.Request)
     */
    public Response getResponse(Request call) throws PseudonymException {
        return new Response(call, processTemplate(this));
    }
}
