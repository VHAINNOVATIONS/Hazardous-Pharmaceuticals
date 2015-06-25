/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.scheduler.tasks;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import gov.va.med.pharmacy.peps.service.common.capability.FdbSchedulerProcessCapability;
import gov.va.med.pharmacy.peps.service.common.capability.ProposedInactivateCapability;




/**
 * InactivationCheckTaskImpl
 * 
 */
public class InactivationCheckTaskImpl implements InactivationCheckTask {

    private static final Logger LOG = Logger.getLogger(InactivationCheckTaskImpl.class);
    private ProposedInactivateCapability proposedInactivateCapability;
    
    @Autowired
    private FdbSchedulerProcessCapability fdbSchedulerProcessCapability;
    
    /**
     * runs all fdb processes
     */
    @Override
    public void runInactivationCheckProcess() {
        LOG.debug("..... InactivationCheckProcess .....");
        
        if (fdbSchedulerProcessCapability.getMessageStatus()) {
            LOG.debug(".... running runInactivationCheckProcess() process .........");
            proposedInactivateCapability.synchronizedProposedInactivationDate();
        }
    }
    
    /**
     * set setProposedInactivateCapability
     * @param proposedInactivateCapability the proposedInactivateCapability to set
     */
    public void setProposedInactivateCapability(
            ProposedInactivateCapability proposedInactivateCapability) {
        this.proposedInactivateCapability = proposedInactivateCapability;
    }

    /**
     * getFdbSchedulerProcessCapability
     * @return the fdbSchedulerProcessCapability
     */
    public FdbSchedulerProcessCapability getFdbSchedulerProcessCapability() {
        return fdbSchedulerProcessCapability;
    }

    /**
     * setFdbSchedulerProcessCapability for the InactivationCheckTaskImpl.
     * @param fdbSchedulerProcessCapability the fdbSchedulerProcessCapability to set
     */
    public void setFdbSchedulerProcessCapability(
            FdbSchedulerProcessCapability fdbSchedulerProcessCapability) {
        this.fdbSchedulerProcessCapability = fdbSchedulerProcessCapability;
    }
    
    

}
