/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.messagingservice.utility;


/**
 * Stores JMS factory, queue, and topic names
 */
public interface MessagingServiceJmsConfiguration {

    /**
     * CONNECTION_FACTORY
     */
    String CONNECTION_FACTORY = "jms/gov/va/med/pharmacy/peps/messagingservice/factory";
    
    /**
     * DEFAULT_QUEUE
     */
    String DEFAULT_QUEUE = "jms/gov/va/med/pharmacy/peps/messagingservice/queue/national/receive";
    
    /**
     * DEFAULT_TOPIC
     */
    String DEFAULT_TOPIC = "jms/gov/va/med/pharmacy/peps/messagingservice/topic/local/receive";
    
    /**
     * HOSPITAL_QUEUE
     */
    String HOSPITAL_QUEUE = "jms/gov/va/med/pharmacy/peps/messagingservice/queue/hospital";

    // test queues and topics
    
    /**
     * TEST_QUEUE
     */
    String TEST_QUEUE = "jms/gov/va/med/pharmacy/peps/messagingservice/queue/test";
    
    /**
     * TEST_QUEUE
     */
    String TEST_TOPIC = "jms/gov/va/med/pharmacy/peps/messagingservice/topic/test";

}
