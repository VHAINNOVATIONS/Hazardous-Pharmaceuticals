/**
 * Source file created in 2011 by Southwest Research Institute
 */



package gov.va.med.pharmacy.peps.service.common.migration;


/**
 * MigratedVaDispenseUnit
 *
 */
public class MigratedVaDispenseUnit extends MigratedDomainBase {

    private ProcessDomainType processDomainType;

    /**
     * MigratedVaDispenseUnit
     */
    public MigratedVaDispenseUnit() {
        super();
        
        this.processDomainType = ProcessDomainType.VA_DISPENSE_UNIT_ACTIVE;
    }

    /**
     * getFileNumber for MigratedVaDispenseUnit.
     * @return fileNumber
     */
    public String getFileNumber() {
        return processDomainType.getFileNumber();
    }

    /**
     * getName() for MigratedVaDispenseUnit.
     * @return name
     */
    public String getName() {
        return processDomainType.getName().toString();
    }

    /**
     * getprocessDomainType for MigratedVaDispenseUnit.
     * @return processDomainType
     */
    @Override
    public ProcessDomainType getProcessDomainType() {
        return processDomainType;
    }

    /**
     * setProcessDomainType
     * @param pProcessDomainType pProcessDomaintype
     */
    @Override
    public void setProcessDomainType(ProcessDomainType pProcessDomainType) {
        processDomainType = pProcessDomainType;
    }

}
