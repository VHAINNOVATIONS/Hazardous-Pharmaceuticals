/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.scheduler;



import java.util.Date;

import gov.va.med.pharmacy.peps.common.vo.NationalSetting;




/**
 * EplNationalInfo
 * contains EPL_NATIONAL_SETTINGS table settings
 *
 */
public class EplNationalInfo {

    private FdbJobNames fdbJobNames;
    private ProcessStatus processStatus;
    private Date lastSuccessRunDate;
    private String fdaHostName;
    private NationalSetting nationalSettings;
    
    /**
     * get NationalSetting
     * @return the nationalSettings
     */
    public NationalSetting getNationalSettings() {
        return nationalSettings;
    }

    /**
     * set NationalSetting
     * @param nationalSettings the nationalSettings to set
     */
    public void setNationalSettings(NationalSetting nationalSettings) {
        this.nationalSettings = nationalSettings;
    }

    /**
     * getFdbJobNames
     * @return the fdbJobNames
     */
    public FdbJobNames getFdbJobNames() {
        return fdbJobNames;
    }
    
    /**
     * sets FdbJobNames for the EplNationalInfo.
     * @param fdbJobNames the fdbJobNames to set
     */
    public void setFdbJobNames(FdbJobNames fdbJobNames) {
        this.fdbJobNames = fdbJobNames;
    }
    
    /**
     * gets Process Status
     * @return the processStatus
     */
    public ProcessStatus getProcessStatus() {
        return processStatus;
    }
    
    /**
     * setProcessStatus
     * @param processStatus the processStatus to set
     */
    public void setProcessStatus(ProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    /**
     * gets Last Success RunDate
     * @return the lastSuccessRunDate
     */
    public Date getLastSuccessRunDate() {
        return lastSuccessRunDate;
    }
    
    
    /**
     * sets Last Success Run Date
     * @param lastSuccessRunDate the lastSuccessRunDate to set
     */
    public void setLastSuccessRunDate(Date lastSuccessRunDate) {
        this.lastSuccessRunDate = lastSuccessRunDate;
    }
    
    /**
     * gets FdaHostName
     * @return the fdaHostName
     */
    public String getFdaHostName() {
        return fdaHostName;
    }
    
    /**
     * sets FdaHostName
     * @param fdaHostName the fdaHostName to set
     */
    public void setFdaHostName(String fdaHostName) {
        this.fdaHostName = fdaHostName;
    }
    
}
