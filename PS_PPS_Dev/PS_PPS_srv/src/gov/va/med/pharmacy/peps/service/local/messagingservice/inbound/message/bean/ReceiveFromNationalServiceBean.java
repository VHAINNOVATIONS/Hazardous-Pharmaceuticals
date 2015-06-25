/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.message.bean;


import javax.jms.Message;

import gov.va.med.pharmacy.peps.common.message.bean.AbstractPepsMessageDrivenBean;
import gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.message.ReceiveFromNationalService;


/**
 * EJB implementation of RecieveFromNationalService
 * 
 * @ejb.bean destination-type = "javax.jms.Topic"
 * 
 * @ejb.transaction type = "Required"
 * 
 * @weblogic.message-driven connection-factory-jndi-name = "jms/gov/va/med/pharmacy/peps/messagingservice/factory"
 *                          destination-jndi-name = "jms/gov/va/med/pharmacy/peps/messagingservice/topic/local/receive"
 */
public class ReceiveFromNationalServiceBean extends AbstractPepsMessageDrivenBean<ReceiveFromNationalService> implements
    ReceiveFromNationalService {

    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ReceiveFromNationalServiceBean.class);

    /**
     * Default constructor.
     */
    public ReceiveFromNationalServiceBean() {
        super();
    }

    /**
     * This method is called by the server when a message arrives.
     * 
     * @param message A Message that is received from National via JMS.
     * 
     * @see gov.va.med.pharmacy.peps.external.local.messagingservice.depricated.inbound.message.MessagingServiceMdb#onMessage(
     *      javax.jms.Message)
     * 
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
