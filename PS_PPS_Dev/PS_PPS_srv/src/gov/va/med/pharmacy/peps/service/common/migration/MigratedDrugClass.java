/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedDrugClass
 *
 */
public class MigratedDrugClass extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedDrugClass
     */
    public MigratedDrugClass() {
        super();
        
        this.processDomainType = ProcessDomainType.DRUG_CLASS_0;
    }

    /**
     * getFileNumber for MigratedDrugClass.
     * @return getFileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName for MigratedDrugClass.
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getProcessDomainType for MigratedDrugClass.
     * @return ProcessDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType for MigratedDrugClass.
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }
}
