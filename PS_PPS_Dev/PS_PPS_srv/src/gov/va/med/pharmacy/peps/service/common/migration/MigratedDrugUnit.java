/**
 * Source file created in 2011 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedDrugUnit
 *
 */
public class MigratedDrugUnit extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedDrugUnit
     */
    public MigratedDrugUnit() {
        super();
        
        this.processDomainType = ProcessDomainType.DRUG_UNITS_ACTIVE;

    }

    /**
     * getFileNumber for MigratedDrugUnit.
     * @return fileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName for MigratedDrugUnit.
     * @return name
     */
    public String getName() {
        return processDomainType.getName();
    }

    /**
     * getProcessDomainType for MigratedDrugUnit
     * @return processDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType for MigratedDrugUnit
     * @param pProcessDomainType pProcessDomainType
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

}
