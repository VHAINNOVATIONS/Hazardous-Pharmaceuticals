/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet;


import gov.va.med.pharmacy.peps.common.servlet.AbstractPepsServlet;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.OrderCheckService;


/**
 * Handle the order check request from VistA
 */
public class OrderCheckServlet extends AbstractPepsServlet<OrderCheckService> {
    private static final long serialVersionUID = 1L;

    /**
     * Handle the XML Request from VistA.
     * 
     * @param xmlRequest String request from VistA
     * @return String response for VistA
     */
    protected String getResponse(String xmlRequest) {
        return getService().performOrderCheck(xmlRequest);
    }
}
