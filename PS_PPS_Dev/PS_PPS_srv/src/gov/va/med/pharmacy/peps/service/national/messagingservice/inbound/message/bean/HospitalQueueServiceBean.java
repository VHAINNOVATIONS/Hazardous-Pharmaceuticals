/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.bean;


import javax.jms.Message;

import gov.va.med.pharmacy.peps.common.message.bean.AbstractPepsMessageDrivenBean;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message.HospitalQueueService;


/**
 * Receive messages from the Hospital Queue, which are messages that failed delivery on the Local Topic or National Queue.
 * 
 * @ejb.bean destination-type = "javax.jms.Queue"
 * 
 * @ejb.transaction type = "Required"
 * 
 * @weblogic.message-driven connection-factory-jndi-name = "jms/gov/va/med/pharmacy/peps/messagingservice/factory"
 *                          destination-jndi-name = "jms/gov/va/med/pharmacy/peps/messagingservice/queue/hospital"
 */
public class HospitalQueueServiceBean extends AbstractPepsMessageDrivenBean<HospitalQueueService> implements
    HospitalQueueService {
    private static final long serialVersionUID = 1L;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HospitalQueueServiceBean.class);

    /**
     * Receive messages from the Hospital Queue, which are messages that failed delivery on the Local Topic or National
     * Queue.
     * 
     * @param message JMS Message
     * 
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message message) {
        try {
            getService().onMessage(message);
        } catch (Exception e) {
            LOG.error("Error processing the failed delivery of a message!", e);
        }
    }
}
