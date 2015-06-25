/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.bean;


import javax.jms.Message;

import gov.va.med.pharmacy.peps.common.message.bean.AbstractPepsMessageDrivenBean;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.ReceiveFromLocalService;


/**
 * EJB implementation of InboundMessagingService
 * 
 * @ejb.bean destination-type = "javax.jms.Queue"
 * 
 * @ejb.transaction type = "Required"
 * 
 * @weblogic.message-driven connection-factory-jndi-name = "jms/gov/va/med/pharmacy/peps/messagingservice/factory"
 *                          destination-jndi-name = "jms/gov/va/med/pharmacy/peps/messagingservice/queue/national/receive"
 */
public class ReceiveFromLocalServiceBean extends AbstractPepsMessageDrivenBean<ReceiveFromLocalService> implements
    ReceiveFromLocalService {
    private static final long serialVersionUID = 1L;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ReceiveFromLocalServiceBean.class);

    /**
     * 
     * Called automatically by server.
     * 
     * @param message Message from Local.
     * 
     * @see gov.va.med.pharmacy.peps.external.local.messagingservice.depricated.inbound.message.
     *      MessagingServiceMdb#onMessage(javax.jms.Message)
     */
    public void onMessage(Message message) {
        try {
            getService().onMessage(message);
        } catch (Exception e) {
            LOG.error("Rolling back transaction for message: " + message, e);
            getMessageDrivenContext().setRollbackOnly();
        }
    }
}
