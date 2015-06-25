/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;


import gov.va.med.pharmacy.peps.service.common.capability.AutoAddUpdateCapability;


/**
 * 
 * RunTask's brief summary
 * 
 * Details of RunTask's operations, special dependencies
 * or protocols developers shall know about when using the class.
 *
 */
public interface FdbAddTask {
    
    /**
     * 
     * printMe for FdbAddTask.
     *
     */
    void printMe();
    
    
    /**
     * runFdbAddProcess for FdbAddTask.
     */
    void runFdbAddProcess();
    
    /**
     * getAutoAddUpdateCapability for FdbAddTask.
     * @return the autoAddUpdateCapability
     */
    AutoAddUpdateCapability getAutoAddUpdateCapability();



    /**
     * setAutoAddUpdateCapability for FdbAddTask.
     * @param autoAddUpdateCapability the autoAddUpdateCapability to set
     */
    void setAutoAddUpdateCapability(AutoAddUpdateCapability autoAddUpdateCapability);
}
