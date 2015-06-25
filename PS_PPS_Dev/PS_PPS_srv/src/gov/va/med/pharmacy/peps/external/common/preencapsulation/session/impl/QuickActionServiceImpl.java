/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session.impl;


import gov.va.med.pharmacy.peps.common.vo.UserVo;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability.QuickActionCapability;
import gov.va.med.pharmacy.peps.external.common.preencapsulation.session.QuickActionService;


/**
 * This class implements the Quick action service implmentation
 */
public class QuickActionServiceImpl implements QuickActionService {

    private QuickActionCapability quickActionCapability;

    /**
     * Setup the QuickActionCapability
     * 
     * @param quickActionCapability QuickActionCapability
     */
    public void setQuickActionCapability(QuickActionCapability quickActionCapability) {
        this.quickActionCapability = quickActionCapability;
    }

    /**
     * Calls the capability.sendDrugFileToExternalInterface
     * 
     * @param user The user who initiated the action.
     * 
     * @see gov.va.med.pharmacy.peps.external.common.preencapsulation.session.QuickActionService#
     *      sendDrugFileToExternalInterface(gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    public void sendDrugFileToExternalInterface(UserVo user) {

        quickActionCapability.sendDrugFileToExternalInterface(user);

    }
}
