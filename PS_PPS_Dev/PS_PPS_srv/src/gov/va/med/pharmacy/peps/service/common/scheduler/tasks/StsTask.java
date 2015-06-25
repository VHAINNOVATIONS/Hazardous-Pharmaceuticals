/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;


import gov.va.med.pharmacy.peps.service.common.capability.StsCapability;


/**
 * 
 * StsTask
*
 */
public interface StsTask {

    /**
     * 
     * printMe
     *
     */
    void printMe();

    /**
     * runStsProcess
     */
    void runStsProcess();

    /**
     * getS Sts Capability
     * @return the stsCapability
     */
    StsCapability getStsCapability();

    /**
     * setS the StsCapability
     * @param stsCapability the stsCapability to set
     */
    void setStsCapability(StsCapability stsCapability);

}
