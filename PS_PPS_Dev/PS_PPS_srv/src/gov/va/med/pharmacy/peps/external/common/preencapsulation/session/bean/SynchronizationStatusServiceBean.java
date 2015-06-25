/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.bean;


import gov.va.med.pharmacy.peps.common.session.bean.AbstractPepsStatelessSessionBean;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.SynchronizationStatusService;


/**
 * Process Vista synchronization status updates.
 * 
 * @ejb.bean
 * 
 * @ejb.home extends="javax.ejb.EJBHome" local-extends="javax.ejb.EJBLocalHome"
 * 
 * @ejb.interface extends="javax.ejb.EJBObject" local-extends="javax.ejb.EJBLocalObject"
 */
public class SynchronizationStatusServiceBean extends AbstractPepsStatelessSessionBean<SynchronizationStatusService>
    implements SynchronizationStatusService {
    private static final long serialVersionUID = 1L;

    /**
     * Process status update.
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "RequiresNew"
     * 
     * @param status VistA Status
     * @return Response
     */
    public String processStatus(String status) {
        return getService().processStatus(status);
    }

    /**
     * Message sent count.
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "RequiresNew"
     * 
     * @return count
     */
    public long getVistaMessageSentCount() {
        return getService().getVistaMessageSentCount();
    }

    /**
     * Message processed count.
     * 
     * @ejb.interface-method
     * @ejb.transaction type = "RequiresNew"
     * 
     * @return count
     */
    public long getVistaMessageProcessedCount() {
        return getService().getVistaMessageProcessedCount();
    }
}
