/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.impl;


import gov.va.med.pharmacy.peps.external.common.document.ExceptionDocument;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.inbound.capability.ProcessOrderChecksCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.OrderCheckService;


/**
 * Perform an order check requested by VistA using XML.
 */
public class OrderCheckServiceImpl implements OrderCheckService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OrderCheckServiceImpl.class);
    private ProcessOrderChecksCapability processOrderChecksCapability;

    /**
     * Perform and order check for the given XML request.
     * 
     * @param request request XML from VistA
     * @return response XML
     */
    public String performOrderCheck(String request) {
        try {
            return processOrderChecksCapability.handleRequest(request);
        } catch (Throwable t) {
            String xmlError = ExceptionDocument.instance().createXmlErrorMessage(t);
            LOG.error("Error message sent to VistA: " + xmlError);

            return xmlError;
        }
    }

    /**
     * description
     * @param processOrderChecksCapability processOrderChecksCapability property
     */
    public void setProcessOrderChecksCapability(ProcessOrderChecksCapability processOrderChecksCapability) {
        this.processOrderChecksCapability = processOrderChecksCapability;
    }
}
