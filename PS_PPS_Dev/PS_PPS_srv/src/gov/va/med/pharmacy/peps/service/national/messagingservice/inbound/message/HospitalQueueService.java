/**
 * Source file created in 2008 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.message;


import gov.va.med.pharmacy.peps.service.common.messagingservice.message.MessagingServiceMdb;


/**
 * Receive messages off of the hospital queue while deployed. Log the messages as they come in. These are messages that
 * failed to execute three times on one of the normal topics or queues.
 */
public interface HospitalQueueService extends MessagingServiceMdb {

}
