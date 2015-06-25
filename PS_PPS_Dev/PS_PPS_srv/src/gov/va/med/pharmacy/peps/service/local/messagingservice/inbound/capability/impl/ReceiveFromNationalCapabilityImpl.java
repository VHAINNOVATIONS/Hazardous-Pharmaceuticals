/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.capability.impl;


import gov.va.med.pharmacy.peps.common.exception.ValidationException;
import gov.va.med.pharmacy.peps.common.vo.DifUpdateVo;
import gov.va.med.pharmacy.peps.common.vo.ManagedItemVo;
import gov.va.med.pharmacy.peps.common.vo.SearchTemplateMessageVo;
import gov.va.med.pharmacy.peps.common.vo.SiteConfigVo;
import gov.va.med.pharmacy.peps.common.vo.SiteUpdateScheduleVo;
import gov.va.med.pharmacy.peps.common.vo.ValueObject;
import gov.va.med.pharmacy.peps.service.common.capability.SearchTemplateCapability;
import gov.va.med.pharmacy.peps.service.local.capability.ConsoleCapability;
import gov.va.med.pharmacy.peps.service.local.capability.ManagedItemCapability;
import gov.va.med.pharmacy.peps.service.local.capability.SiteUpdateScheduleCapability;
import gov.va.med.pharmacy.peps.service.local.messagingservice.inbound.capability.ReceiveFromNationalCapability;


/**
 * Implementation for receiving from National via JMS.
 */
public class ReceiveFromNationalCapabilityImpl implements ReceiveFromNationalCapability {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
        .getLogger(ReceiveFromNationalCapabilityImpl.class);
    private ConsoleCapability consoleCapability;
    private ManagedItemCapability managedItemCapability;
    private SiteUpdateScheduleCapability siteUpdateScheduleCapability;
    private SearchTemplateCapability searchTemplateCapability;

    /**
     * Empty constructor
     */
    public ReceiveFromNationalCapabilityImpl() {
        super();
    }

    /**
     * Handle the response sent from a local
     * @param valueObject ValueObject returned from local
     * @throws ValidationException if error validating ValueObject data
     * 
     * 
     * @see gov.va.med.pharmacy.peps.external.national.messagingservice.inbound.capability.ReceiveResponseCapability#
     *      receiveResponse(gov.va.med.pharmacy.peps.common.vo.ValueObject)
     */
    public synchronized void onMessage(ValueObject valueObject) throws ValidationException {
        LOG.debug("Received message from national: " + valueObject.toShortString());

        if (valueObject instanceof ManagedItemVo) {
            managedItemCapability.processFromNational((ManagedItemVo) valueObject);
        } else if (valueObject instanceof SiteConfigVo) {
            consoleCapability.processFromNational((SiteConfigVo) valueObject);
        } else if (valueObject instanceof SearchTemplateMessageVo) {
            searchTemplateCapability.saveSearch((SearchTemplateMessageVo) valueObject);
        } else if (valueObject instanceof SiteUpdateScheduleVo) {
            siteUpdateScheduleCapability.processFromNational((SiteUpdateScheduleVo) valueObject);
        } else if (valueObject instanceof DifUpdateVo) {
            siteUpdateScheduleCapability.processFromNational((DifUpdateVo) valueObject);
        }
    }

    /**
     * (System Information)
     * 
     * @param consoleCapability property
     */
    public void setConsoleCapability(ConsoleCapability consoleCapability) {
        this.consoleCapability = consoleCapability;
    }

    /**
     * getManagedItemCapability
     * @return managedItemCapability property
     */
    public ManagedItemCapability getManagedItemCapability() {
        return managedItemCapability;
    }

    /**
     * setManagedItemCapability
     * @param managedItemCapability managedItemCapability property
     */
    public void setManagedItemCapability(ManagedItemCapability managedItemCapability) {
        this.managedItemCapability = managedItemCapability;
    }

    /**
     * setSearchTemplateCapability
     * @param searchTemplateCapability searchTemplateCapability property
     */
    public void setSearchTemplateCapability(SearchTemplateCapability searchTemplateCapability) {
        this.searchTemplateCapability = searchTemplateCapability;
    }

    /**
     * setSiteUpdateScheduleCapability
     * @param siteUpdateScheduleCapability siteUpdateScheduleCapability property
     */
    public void setSiteUpdateScheduleCapability(SiteUpdateScheduleCapability siteUpdateScheduleCapability) {
        this.siteUpdateScheduleCapability = siteUpdateScheduleCapability;
    }
}
