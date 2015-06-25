/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;


import gov.va.med.pharmacy.peps.service.common.capability.AutoAddUpdateCapability;


/**
 * FdbUpdateTask
 */
public interface FdbUpdateTask {

    /**
     * 
     * printMe for FdbUpdateTask.
     *
     */
    void printMe();
    
    
    /**
     * runFdbAddProcess for FdbUpdateTask.
     */
    void runFdbUpdateProcess();
    
    /**
     * getAutoAddUpdateCapability for FdbUpdateTask.
     * @return the autoAddUpdateCapability
     */
    AutoAddUpdateCapability getAutoAddUpdateCapability();



    /**
     * setAutoAddUpdateCapability for FdbUpdateTask.
     * @param autoAddUpdateCapability the autoAddUpdateCapability to set
     */
    void setAutoAddUpdateCapability(AutoAddUpdateCapability autoAddUpdateCapability);
}


