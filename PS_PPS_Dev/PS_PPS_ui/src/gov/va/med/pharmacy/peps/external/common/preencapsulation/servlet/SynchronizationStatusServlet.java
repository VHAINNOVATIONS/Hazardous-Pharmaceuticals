/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.servlet;


import gov.va.med.pharmacy.peps.common.servlet.AbstractPepsServlet;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.SynchronizationStatusService;


/**
 * Update the status for a synchronization message.
 */
public class SynchronizationStatusServlet extends AbstractPepsServlet<SynchronizationStatusService> {
    private static final long serialVersionUID = 1L;

    /**
     * Process the status message.
     * 
     * @param xmlRequest Status message from Vista
     * @return String Response
     */
    @Override
    protected String getResponse(String xmlRequest) {
        return getService().processStatus(xmlRequest);
    }
}
