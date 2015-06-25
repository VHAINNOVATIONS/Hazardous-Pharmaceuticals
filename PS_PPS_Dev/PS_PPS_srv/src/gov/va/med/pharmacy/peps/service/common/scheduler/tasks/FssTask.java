/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;



import gov.va.med.pharmacy.peps.service.common.capability.FssCapability;


/**
 * FdbFssTask
 */
public interface FssTask {

    
    /**
     * 
     * printMe
     *
     */
    void printMe();
    
    
    /**
     * runFdbAddProcess
     */
    void runFssProcess();
    
    /**
     * getFssCapablity
     * @return the fssCapability
     */
    FssCapability getFssCapability();



    /**
     * setFssCapability
     * @param fssCapability the fssCapability to set
     */
    void setFssCapability(FssCapability fssCapability);
}
