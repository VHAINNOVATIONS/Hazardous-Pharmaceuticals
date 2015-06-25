/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedVaGenericeName
 *
 */
public class MigratedVaGenericeName extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedVaGenericeName
     */
    public MigratedVaGenericeName() {
        super();
        
        this.processDomainType = ProcessDomainType.VA_GENERIC_NAME_ACTIVE;
    }

    /**
     * getFileNumber for MigratedVaGenericName.
     * @return fileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName for MigratedVaGenericName.
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getProcessDomainType for MigratedVaGenericName.
     * @return processDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType for MigratedVaGenericName.
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

}
