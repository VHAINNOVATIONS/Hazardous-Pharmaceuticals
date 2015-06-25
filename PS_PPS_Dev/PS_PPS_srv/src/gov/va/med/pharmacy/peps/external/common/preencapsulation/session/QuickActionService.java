/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.session;


import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * This is the interface for the QuickActionService
 */
public interface QuickActionService {

    /**
     * Calls the capability.sendDrugFileToExternalInterface
     * 
     * @param user The user who initiated the action.
     * 
     * @see gov.va.med.pharmacy.peps.external.common.preencapsulation.session.QuickActionService#
     *      sendDrugFileToExternalInterface(gov.va.med.pharmacy.peps.common.vo.UserVo)
     */
    void sendDrugFileToExternalInterface(UserVo user);

}
