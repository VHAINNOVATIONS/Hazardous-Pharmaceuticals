/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.preencapsulation.outbound.capability;


import gov.va.med.pharmacy.peps.common.vo.UserVo;


/**
 * This is the interface for the QuickActionCapability
 */
public interface QuickActionCapability {

    /**
     * This method is used to indicate that vista should send the drug file to the external interface
     * 
     * @param user The user
     */
    void sendDrugFileToExternalInterface(UserVo user);

}
