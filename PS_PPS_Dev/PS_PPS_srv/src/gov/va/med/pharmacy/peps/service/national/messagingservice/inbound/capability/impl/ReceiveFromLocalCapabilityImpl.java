/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability.impl;


import gov.va.med.pharmacy.peps.common.exception.DuplicateItemException;
import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.exception.ValueObjectValidationException;
import gov.va.med.pharmacy.peps.common.utility.EnvironmentUtility;
import gov.va.med.pharmacy.peps.common.vo.RequestMessageVo;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.national.capability.ConsoleCapability;
import gov.va.med.pharmacy.peps.service.national.capability.RequestCapability;
import gov.va.med.pharmacy.peps.service.national.capability.SiteUpdateScheduleCapability;
import gov.va.med.pharmacy.peps.service.national.messagingservice.inbound.capability.ReceiveFromLocalCapability;


/**
 * Receive messages from Local
 */
public class ReceiveFromLocalCapabilityImpl implements ReceiveFromLocalCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ReceiveFromLocalCapabilityImpl.class);

    private RequestCapability requestCapability;
    private ConsoleCapability consoleCapability;
    private SiteUpdateScheduleCapability siteUpdateScheduleCapability;
    private EnvironmentUtility environmentUtility;

    /**
     * Empty constructor
     */
    public ReceiveFromLocalCapabilityImpl() {
        super();
    }

    /**
     * Handle the response sent from a local
     * 
     * @param valueObject ValueObject returned from local
     * @throws ValidationException if error
     * 
     * @see gov.va.med.pharmacy.peps.external.national.messagingservice.inbound.capability.
     *      ReceiveResponseCapability#receiveResponse(gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public void onMessage(ValueObject valueObject) throws ValidationException {

        // LOG.debug("Received message from local: " + valueObject);
        LOG.debug("Received mssage from local");

        UserVo systemUser = UserVo.getSystemUser(environmentUtility);

        if (valueObject instanceof SiteConfigVo) {

            consoleCapability.insertFromLocal((SiteConfigVo) valueObject, systemUser);

        } else if (valueObject instanceof SiteUpdateScheduleVo) {
            siteUpdateScheduleCapability.processFromLocal((SiteUpdateScheduleVo) valueObject, systemUser);
        } else {
            try {
                requestCapability.processRequestMessage((RequestMessageVo) valueObject, systemUser);
            } catch (DuplicateItemException e) {
                LOG.error("Duplicate Item");
            } catch (ValueObjectValidationException e) {
                LOG.error("ValueObjectValidationException");
            }
        }
    }

    /**
     * setRequestCapability
     * 
     * @param requestCapability {@link RequestCapability}
     */
    public void setRequestCapability(RequestCapability requestCapability) {
        this.requestCapability = requestCapability;
    }

    /**
     * getRequestCapability
     * 
     * @return {@link RequestCapability}
     */
    public RequestCapability getRequestCapability() {
        return this.requestCapability;
    }

    /**
     * getConsoleCapability
     * 
     * @return {@link ConsoleCapability}
     */
    public ConsoleCapability getConsoleCapability() {
        return consoleCapability;
    }

    /**
     * setConsoleCapability
     * 
     * @param consoleCapability {@link ConsoleCapability}
     */
    public void setConsoleCapability(ConsoleCapability consoleCapability) {
        this.consoleCapability = consoleCapability;
    }

    /**
     * setSiteUpdateScheduleCapability
     * 
     * @param siteUpdateScheduleCapability siteUpdateScheduleCapability property
     */
    public void setSiteUpdateScheduleCapability(SiteUpdateScheduleCapability siteUpdateScheduleCapability) {
        this.siteUpdateScheduleCapability = siteUpdateScheduleCapability;
    }

    /**
     * setEnvironmentUtility
     * 
     * @param environmentUtility environmentUtility property
     */
    public void setEnvironmentUtility(EnvironmentUtility environmentUtility) {
        this.environmentUtility = environmentUtility;
    }

}
