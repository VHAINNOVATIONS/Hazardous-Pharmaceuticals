/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session;


/**
 * Perform an order check requested by VistA using XML.
 */
public interface OrderCheckService {

    /**
     * Perform and order check for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    String performOrderCheck(String request);
}
